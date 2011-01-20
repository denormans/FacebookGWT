/*
 * Copyright (C) 2010 deNormans
 * http://www.denormans.com/
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of deNormans ("Confidential Information"). You 
 * shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with deNormans.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * DENORMANS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.denormans.facebookgwt.api.client.init;

import com.denormans.facebookgwt.api.client.EventHandlerException;
import com.denormans.facebookgwt.api.client.init.events.FBInitFailureEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitFailureHandler;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessHandler;
import com.denormans.facebookgwt.api.client.init.events.HasFBInitHandlers;
import com.denormans.facebookgwt.api.client.init.js.FBInitOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class FBInitialization implements HasFBInitHandlers  {
  private static final Logger Log = Logger.getLogger(FBInitialization.class.getName());

  public enum InitializationState { Uninitialized, LoadingScript, ScriptLoaded, Initialized;}

  public static final int InitializationTimeoutSeconds = 10;

  public static final String FacebookRootElementID = "fb-root";
  public static final String FacebookScriptElementID = "fb-script-all";
  public static final String DefaultLocale = "en_US";
  private static final String FacebookScriptServer = "connect.facebook.net";
  private static final String FacebookScriptName = "all.js";

  private EventBus eventBus = new SimpleEventBus();

  private List<Scheduler.ScheduledCommand> deferredFBCommands = new ArrayList<Scheduler.ScheduledCommand>();

  private FBInitialization.InitializationState initializationState = FBInitialization.InitializationState.Uninitialized;
  private Timer initializationTimer;

  /**
   * Determines whether or not Facebook has been initialized with this API.
   *
   * @return whether or not Facebook has been initialized.
   */
  public boolean isInitialized() {
    return initializationState == FBInitialization.InitializationState.Initialized;
  }

  /**
   * Returns the current initialization state.  If {@link #initialize} call hasn't been called yet, the state is {@link FBInitialization.InitializationState#Uninitialized}.
   *
   * @return the initialization state.
   */
  public FBInitialization.InitializationState getInitializationState() {
    return initializationState;
  }

  /**
   * Initialize Facebook.  This is handled asynchronously so callbacks should be registered to be called when Facebook is initialized.
   *
   * Uses the default initialization timeout.
   *
   * @param initOptions initialization options
   */
  public void initialize(final FBInitOptions initOptions) {
    initialize(null, initOptions, FBInitialization.InitializationTimeoutSeconds);
  }

  /**
   * Initialize Facebook.  This is handled asynchronously so callbacks should be registered to be called when Facebook is initialized.
   *
   * Uses the default initialization timeout.
   *
   * @param locale The explicit locale
   * @param initOptions initialization options
   */
  public void initialize(final String locale, final FBInitOptions initOptions) {
    initialize(locale, initOptions, FBInitialization.InitializationTimeoutSeconds);
  }

  /**
   * Initialize Facebook.  This is handled asynchronously so callbacks should be registered to be called when Facebook is initialized.
   * <p>
   * Calling this method will eventually fire all registered init events, even if already initialized.
   *
   * @param initOptions initialization options
   * @param initializationTimeout The timeout (in seconds) before an init failure event is fired.
   */
  public synchronized void initialize(final FBInitOptions initOptions, final int initializationTimeout) {
    initialize(null, initOptions, initializationTimeout);
  }

  /**
   * Initialize Facebook.  This is handled asynchronously so callbacks should be registered to be called when Facebook is initialized.
   * <p>
   * Calling this method will eventually fire all registered init events, even if already initialized.
   *
   * @param locale The explicit locale
   * @param initOptions initialization options
   * @param initializationTimeout The timeout (in seconds) before an init failure event is fired.
   */
  public synchronized void initialize(final String locale, final FBInitOptions initOptions, final int initializationTimeout) {
    if (initializationState == FBInitialization.InitializationState.LoadingScript || initializationState == FBInitialization.InitializationState.ScriptLoaded) {
      return;
    }

    if (initializationState == FBInitialization.InitializationState.Initialized) {
      if (initOptions != null) {
        executeFBInit(initOptions);
        Log.fine("Facebook reinitialized");
      }

      fireInitSuccess();
      return;
    }

    initializationState = FBInitialization.InitializationState.LoadingScript;
    Log.fine("Initializing Facebook...");

    setupFBAsyncInitCallback(initOptions);

    if (hasFacebookScriptElement()) {
      // found the script block, so assume we're initialized
      initializationState = FBInitialization.InitializationState.Initialized;
      fireInitSuccess();
      return;
    }

    Document doc = Document.get();
    Element fbRootElement = doc.getElementById(FacebookRootElementID);
    if (fbRootElement == null) {
      fbRootElement = DOM.createDiv();
      fbRootElement.setId(FacebookRootElementID);
      BodyElement bodyElement = doc.getBody();
      bodyElement.appendChild(fbRootElement);
    }

    String fbLocale = locale;
    if (fbLocale == null) {
      // todo: get default locale from user, accounting for list of Facebook-allowed locales, available from http://www.facebook.com/translations/FacebookLocales.xml
      fbLocale = DefaultLocale;
    }

    ScriptElement script = doc.createScriptElement();
    script.setType("text/javascript");
    script.setId(FacebookScriptElementID);
    script.setSrc(Window.Location.getProtocol() + "//" + FacebookScriptServer + "/" + fbLocale + "/" + FacebookScriptName);
    // facebook seems to think this async is necessary
    script.setPropertyBoolean("async", true);

    if (initializationTimeout > 0) {
      if (initializationTimer == null) {
        initializationTimer = new Timer() {
          @Override
          public void run() {
            fireInitFailure();
          }
        };
      }
      initializationTimer.cancel();
      initializationTimer.schedule(initializationTimeout * 1000);
    }

    fbRootElement.appendChild(script);
  }

  private boolean hasFacebookScriptElement() {
    Document doc = Document.get();
    if (doc.getElementById(FacebookScriptElementID) != null) {
      return true;
    }

    // enhance: check other script elements for facebook API access (?)

    return false;
  }

  /**
   * Executes the given command with Facebook, whenever it's initialized.
   *
   * @param command The command to execute
   */
  public synchronized void executeWithFB(final Scheduler.ScheduledCommand command) {
    if(isInitialized()) {
      command.execute();
    } else {
      deferredFBCommands.add(command);
    }
  }

  private native void setupFBAsyncInitCallback(final FBInitOptions initOptions) /*-{
    try {
      var self = this;
      var oldFBAsyncInit = $wnd.fbAsyncInit;
      $wnd.fbAsyncInit = function() {
        // reset the old fbAsyncInit in case this is called again
        $wnd.fbAsyncInit = oldFBAsyncInit;

        try {
          // call the old one first
          if (oldFBAsyncInit) {
            oldFBAsyncInit();
          }
        } catch(e) {
          @com.denormans.gwtutil.client.js.JSError::raiseException(Ljava/lang/Object;)(e);
        }

        self.@com.denormans.facebookgwt.api.client.init.FBInitialization::handleFBAsyncInit(Lcom/denormans/facebookgwt/api/client/init/js/FBInitOptions;)(initOptions);
      }
    } catch(e) {
    }
  }-*/;

  @SuppressWarnings ({ "UnusedDeclaration" })
  private synchronized void handleFBAsyncInit(final FBInitOptions initOptions) {
    try {
      initializationState = InitializationState.ScriptLoaded;

      initializationTimer.cancel();
      initializationTimer = null;

      if (initOptions != null) {
        executeFBInit(initOptions);
      }

      final List<Scheduler.ScheduledCommand> deferredCommands = new ArrayList<Scheduler.ScheduledCommand>(deferredFBCommands);
      deferredFBCommands.clear();

      Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
        @Override
        public void execute() {
          for(Scheduler.ScheduledCommand deferredCommand : deferredCommands) {
            deferredCommand.execute();
          }
        }
      });

      initializationState = InitializationState.Initialized;

      Log.fine("Facebook initialized");
      fireInitSuccess();
    } catch (Throwable t) {
      GWT.UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
      if (uncaughtExceptionHandler == null) {
        throw new EventHandlerException("Error handling Facebook init callback", t);
      }

      uncaughtExceptionHandler.onUncaughtException(t);
    }
  }

  /**
   * Must be called after FB script is loaded.
   *
   * @param initOptions The initialization options
   */
  private native void executeFBInit(final FBInitOptions initOptions) /*-{
    $wnd.FB.init(initOptions);
  }-*/;

  protected void fireInitSuccess() {
    FBInitSuccessEvent.fire(this);
  }

  protected void fireInitFailure() {
    FBInitFailureEvent.fire(this);
  }

  @Override
  public HandlerRegistration addFBInitSuccessHandler(final FBInitSuccessHandler handler) {
    return eventBus.addHandler(FBInitSuccessEvent.getType(), handler);
  }

  @Override
  public HandlerRegistration addFBInitFailureHandler(final FBInitFailureHandler handler) {
    return eventBus.addHandler(FBInitFailureEvent.getType(), handler);
  }

  @Override
  public void fireEvent(final GwtEvent<?> event) {
    eventBus.fireEvent(event);
  }
}
