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

import java.util.logging.Logger;

public final class FBInitialization implements HasFBInitHandlers  {
  private static final Logger Log = Logger.getLogger(FBInitialization.class.getName());

  public enum InitializationState { Uninitialized, LoadingScript, ScriptLoaded, Initialized;}

  public static final String FacebookRootElementID = "fb-root";
  public static final String FacebookScriptElementID = "fb-script-all";
  private static final String FacebookScriptServer = "connect.facebook.net";
  private static final String FacebookScriptLocation = "en_US/all.js";

  private EventBus eventBus = new SimpleEventBus();

  public static final int InitializationTimeout = 10;

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
    initialize(initOptions, FBInitialization.InitializationTimeout);
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

    ScriptElement script = doc.createScriptElement();
    script.setType("text/javascript");
    script.setId(FacebookScriptElementID);
    script.setSrc(Window.Location.getProtocol() + "//" + FacebookScriptServer + "/" + FacebookScriptLocation);
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

  private native void setupFBAsyncInitCallback(final FBInitOptions initOptions) /*-{
    try {
      var self = this;
      var oldFBAsyncInit = $wnd.fbAsyncInit;
      $wnd.fbAsyncInit = function() {
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
  private void handleFBAsyncInit(final FBInitOptions initOptions) {
    try {
      initializationState = InitializationState.ScriptLoaded;

      initializationTimer.cancel();
      initializationTimer = null;

      if (initOptions != null) {
        executeFBInit(initOptions);
      }

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

  public native void executeFBInit(final FBInitOptions initOptions) /*-{
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
