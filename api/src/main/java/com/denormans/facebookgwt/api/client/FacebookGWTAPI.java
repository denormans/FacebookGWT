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

package com.denormans.facebookgwt.api.client;

import com.denormans.facebookgwt.api.client.events.FacebookInitFailureEvent;
import com.denormans.facebookgwt.api.client.events.FacebookInitFailureHandler;
import com.denormans.facebookgwt.api.client.events.FacebookInitSuccessEvent;
import com.denormans.facebookgwt.api.client.events.FacebookInitSuccessHandler;
import com.denormans.facebookgwt.api.client.events.HasFacebookInitHandlers;

import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

import java.util.logging.Logger;

public final class FacebookGWTAPI implements HasFacebookInitHandlers {
  private static final Logger Log = Logger.getLogger(FacebookGWTAPI.class.getName());

  private static final String FacebookRootElementID = "fb-root";
  private static final String FacebookScriptElementID = "fb-script-all";
  private static final String FacebookScriptServer = "connect.facebook.net";
  private static final String FacebookScriptLocation = "en_US/all.js";

  private static final int InitializationTimeout = 10000;

  private static FacebookGWTAPI sInstance;

  private EventBus eventBus = new SimpleEventBus();

  private boolean isInitializing;
  private boolean isInitialized;
  private Timer initializationTimer;

  /**
   * Initialize Facebook.  This is handled asynchronously so callbacks should be registered as required for when Facebook is initialized.
   *
   * @return <tt>true</tt> if initialization was initiated.  <tt>false</tt> if initialization has already begun or is complete.
   */
  public synchronized boolean initialize() {
    if (isInitializing || isInitialized) {
      return false;
    }

    isInitializing = true;
    Log.fine("Initializing Facebook...");
    setupFBAsyncInitCallback();

    Document doc = Document.get();
    Element fbRootElement = doc.getElementById(FacebookRootElementID);
    if (fbRootElement == null) {
      fbRootElement = DOM.createDiv();
      fbRootElement.setId(FacebookRootElementID);
      BodyElement bodyElement = doc.getBody();
      bodyElement.appendChild(fbRootElement);
    }

    ScriptElement script = Document.get().createScriptElement();
    script.setType("text/javascript");
    script.setId(FacebookScriptElementID);
    script.setSrc(Window.Location.getProtocol() + "//" + FacebookScriptServer + "/" + FacebookScriptLocation);
    // facebook seems to think this async is necessary
    script.setPropertyBoolean("async", true);

    if (initializationTimer == null) {
      initializationTimer = new Timer() {
        @Override
        public void run() {
          FacebookInitFailureEvent.fire(FacebookGWTAPI.this);
          unloadFacebookScript();
        }
      };
    }
    initializationTimer.schedule(InitializationTimeout);

    fbRootElement.appendChild(script);

    return true;
  }

  /**
   * Cancel facebook initialization
   */
  public void cancelInitialization() {
    initializationTimer.cancel();
    unloadFacebookScript();
  }

  private native void setupFBAsyncInitCallback() /*-{
    var self = this;
    var oldFBAsyncInit = $wnd.fbAsyncInit;
    $wnd.fbAsyncInit = function() {
      // call the old one first
      if (oldFBAsyncInit) {
        oldFBAsyncInit();
      }

      self.@com.denormans.facebookgwt.api.client.FacebookGWTAPI::handleFBAsyncInit()();
    }
  }-*/;

  private void handleFBAsyncInit() {
    isInitialized = true;
    initializationTimer.cancel();
    Log.fine("Facebook initialized!");
    FacebookInitSuccessEvent.fire(this);
    unloadFacebookScript();
  }

  public boolean isInitializing() {
    return isInitializing;
  }

  public boolean isInitialized() {
    return isInitialized;
  }

  private void unloadFacebookScript() {
    if (true) {
      return;
    }

    // IE doesn't allow the script tag to be removed inside the script itself.
    DeferredCommand.addCommand(new Command() {
      @Override
      public void execute() {
        Log.fine("Unloading Facebook script");

        Element scriptElement = Document.get().getElementById(FacebookScriptElementID);
        if (scriptElement != null) {
          Element parentElement = scriptElement.getParentElement();
          if (parentElement != null) {
            parentElement.removeChild(scriptElement);
          }
        }
      }
    });
  }

  @Override
  public HandlerRegistration addFacebookInitSuccessHandler(final FacebookInitSuccessHandler handler) {
    return eventBus.addHandler(FacebookInitSuccessEvent.getType(), handler);
  }

  @Override
  public HandlerRegistration addFacebookInitFailureHandler(final FacebookInitFailureHandler handler) {
    return eventBus.addHandler(FacebookInitFailureEvent.getType(), handler);
  }

  @Override
  public void fireEvent(final GwtEvent<?> event) {
    eventBus.fireEvent(event);
  }

  public static FacebookGWTAPI get() {
    if (sInstance == null) {
      sInstance = new FacebookGWTAPI();
    }

    return sInstance;
  }
}
