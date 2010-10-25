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

import com.denormans.facebookgwt.api.client.events.FBEvent;
import com.denormans.facebookgwt.api.client.events.FBEventHandler;
import com.denormans.facebookgwt.api.client.events.auth.FBLoginEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBLoginHandler;
import com.denormans.facebookgwt.api.client.events.auth.FBLogoutEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBLogoutHandler;
import com.denormans.facebookgwt.api.client.events.auth.FBSessionChangeEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBSessionChangeHandler;
import com.denormans.facebookgwt.api.client.events.auth.FBStatusChangeEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBStatusChangeHandler;
import com.denormans.facebookgwt.api.client.events.auth.HasFBAuthHandlers;
import com.denormans.facebookgwt.api.client.events.init.FBInitFailureEvent;
import com.denormans.facebookgwt.api.client.events.init.FBInitFailureHandler;
import com.denormans.facebookgwt.api.client.events.init.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.events.init.FBInitSuccessHandler;
import com.denormans.facebookgwt.api.client.events.init.HasFBInitHandlers;
import com.denormans.facebookgwt.api.client.js.FBAuthEventResponse;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.BodyElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.testing.CountingEventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class FacebookGWTAPI implements HasFBInitHandlers, HasFBAuthHandlers {
  private static final Logger Log = Logger.getLogger(FacebookGWTAPI.class.getName());

  public enum InitializationState { Uninitialized, Initializing, Initialized }

  public static final int InitializationTimeout = 10;

  public static final String FacebookRootElementID = "fb-root";
  public static final String FacebookScriptElementID = "fb-script-all";
  private static final String FacebookScriptServer = "connect.facebook.net";
  private static final String FacebookScriptLocation = "en_US/all.js";

  private static FacebookGWTAPI sInstance;

  private CountingEventBus eventBus = new CountingEventBus();

  private InitializationState initializationState = InitializationState.Uninitialized;
  private Timer initializationTimer;

  private Map<FBEvent, JSFunction> callbackFunctionsByEvent = new HashMap<FBEvent, JSFunction>();

  /**
   * Determines whether or not Facebook has been initialized with this API.
   *
   * @return whether or not Facebook has been initialized.
   */
  public boolean isInitialized() {
    return initializationState == InitializationState.Initialized;
  }

  /**
   * Returns the current initialization state.  If {@link #initialize} call hasn't been called yet, the state is {@link InitializationState#Uninitialized}.
   *
   * @return the initialization state.
   */
  public InitializationState getInitializationState() {
    return initializationState;
  }

  /**
   * Initialize Facebook.  This is handled asynchronously so callbacks should be registered to be called when Facebook is initialized.
   *
   * Uses the default initialization timeout.
   */
  public void initialize() {
    initialize(InitializationTimeout);
  }

  /**
   * Initialize Facebook.  This is handled asynchronously so callbacks should be registered to be called when Facebook is initialized.
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

    Log.fine("Facebook initialized");
    fireInitSuccess();
  }

  private void handleFBEvent(final String eventName, final JavaScriptObject apiResponse) {
    FBEvent event = FBEvent.valueFromApiValue(eventName);
    if (event == null) {
      Log.warning("Unknown event: " + eventName);
      return;
    }

    switch (event) {
      case AuthLogin:
        fireLogin(apiResponse.<FBAuthEventResponse>cast());
        break;

      case AuthLogout:
        fireLogout(apiResponse.<FBAuthEventResponse>cast());
        break;

      case AuthSessionChange:
        fireSessionChange(apiResponse.<FBAuthEventResponse>cast());
        break;

      case AuthStatusChange:
        fireStatusChange(apiResponse.<FBAuthEventResponse>cast());
        break;

      default:
        Log.warning("Unknown event: " + event);
    }
  }

  private JSFunction subscribeToEvent(final FBEvent event) {
    return subscribeToEventJS(event.getApiValue());
  }

  private native JSFunction subscribeToEventJS(final String eventName) /*-{
    var self = this;

    var callback = function(response) {
      self.@com.denormans.facebookgwt.api.client.FacebookGWTAPI::handleFBEvent(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(eventName, response);
    };

    $wnd.FB.Event.subscribe(eventName, callback);

    return callback;
  }-*/;

  private void unsubscribeFromEvent(final FBEvent event, final JSFunction callback) {
    unsubscribeFromEventJS(event.getApiValue(), callback);
  }

  private native void unsubscribeFromEventJS(final String eventName, final JSFunction callback) /*-{
    $wnd.FB.Event.unsubscribe(eventName, callback);
  }-*/;

  protected void fireInitSuccess() {
    FBInitSuccessEvent.fire(this);
  }

  protected void fireInitFailure() {
    FBInitFailureEvent.fire(this);
  }

  protected void fireLogin(final FBAuthEventResponse apiResponse) {
    FBLoginEvent.fire(this, apiResponse);
  }

  protected void fireLogout(final FBAuthEventResponse apiResponse) {
    FBLogoutEvent.fire(this, apiResponse);
  }

  protected void fireSessionChange(final FBAuthEventResponse apiResponse) {
    FBSessionChangeEvent.fire(this, apiResponse);
  }

  protected void fireStatusChange(final FBAuthEventResponse apiResponse) {
    FBStatusChangeEvent.fire(this, apiResponse);
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
  public HandlerRegistration addFBLoginHandler(final FBLoginHandler handler) {
    return addFBEventHandler(handler, FBLoginEvent.getType(), FBEvent.AuthLogin);
  }

  @Override
  public HandlerRegistration addFBLogoutHandler(final FBLogoutHandler handler) {
    return addFBEventHandler(handler, FBLogoutEvent.getType(), FBEvent.AuthLogout);
  }

  @Override
  public HandlerRegistration addFBSessionChangeHandler(final FBSessionChangeHandler handler) {
    return addFBEventHandler(handler, FBSessionChangeEvent.getType(), FBEvent.AuthSessionChange);
  }

  @Override
  public HandlerRegistration addFBStatusChangeHandler(final FBStatusChangeHandler handler) {
    return addFBEventHandler(handler, FBStatusChangeEvent.getType(), FBEvent.AuthStatusChange);
  }

  private <H extends FBEventHandler> HandlerRegistration addFBEventHandler(final H handler, final GwtEvent.Type<H> eventType, final FBEvent fbEvent) {
    if (callbackFunctionsByEvent.get(fbEvent) == null) {
      JSFunction callbackFunction = subscribeToEvent(fbEvent);
      callbackFunctionsByEvent.put(fbEvent, callbackFunction);
    }

    final HandlerRegistration handlerRegistration = eventBus.addHandler(eventType, handler);
    return new HandlerRegistration() {
      @Override
      public void removeHandler() {
        handlerRegistration.removeHandler();

        // defer in case the handler removal has been deferred
        DeferredCommand.addCommand(new Command() {
          @Override
          public void execute() {
            JSFunction callbackFunction = callbackFunctionsByEvent.get(fbEvent);
            if (callbackFunction != null && eventBus.getCount(eventType) == 0) {
              unsubscribeFromEvent(fbEvent, callbackFunction);
              callbackFunctionsByEvent.remove(fbEvent);
            }
          }
        });
      }
    };
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

  private static class JSFunction extends JavaScriptObject {
    protected JSFunction() {
    }
  }
}
