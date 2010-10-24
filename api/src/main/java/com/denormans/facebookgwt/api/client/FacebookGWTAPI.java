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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

import java.util.logging.Logger;

public final class FacebookGWTAPI implements HasFacebookInitHandlers {
  private static final Logger Log = Logger.getLogger(FacebookGWTAPI.class.getName());

  public enum InitializationState { Uninitialized, Initializing, Initialized }

  private static final String FacebookRootElementID = "fb-root";
  private static final String FacebookScriptElementID = "fb-script-all";
  private static final String FacebookScriptServer = "connect.facebook.net";
  private static final String FacebookScriptLocation = "en_US/all.js";

  private static final int InitializationTimeout = 10;

  private static FacebookGWTAPI sInstance;

  private EventBus eventBus = new SimpleEventBus();

  private InitializationState initializationState = InitializationState.Uninitialized;
  private Timer initializationTimer;

  /**
   * Initialize Facebook.  This is handled asynchronously so callbacks should be registered as required for when Facebook is initialized.
   */
  public void initialize() {
    initialize(InitializationTimeout);
  }

  /**
   * Initialize Facebook.  This is handled asynchronously so callbacks should be registered as required for when Facebook is initialized.
   * <p>
   * Calling this method will eventually fire all registered init events, even if already initialized.
   *
   * @param initializationTimeout The timeout (in seconds) before an init failure event is fired.
   */
  public synchronized void initialize(final int initializationTimeout) {
    if (initializationState == InitializationState.Initializing) {
      return;
    }

    if (initializationState == InitializationState.Initialized) {
      fireInitSuccess();
      return;
    }

    initializationState = InitializationState.Initializing;
    Log.fine("Initializing Facebook...");
    setupFBAsyncInitCallback();

    if (hasFacebookScriptElement()) {
      // found the script block, so assume we're initialized
      initializationState = InitializationState.Initialized;
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
            FacebookInitFailureEvent.fire(FacebookGWTAPI.this);
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

    return false;
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
    initializationState = InitializationState.Initialized;

    initializationTimer.cancel();
    initializationTimer = null;

    Log.fine("Facebook initialized!");
    fireInitSuccess();
  }

  private void fireInitSuccess() {
    FacebookInitSuccessEvent.fire(this);
  }

  public InitializationState getInitializationState() {
    return initializationState;
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
