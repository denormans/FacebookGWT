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

import com.denormans.facebookgwt.api.client.events.FBEventType;
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
import com.denormans.facebookgwt.api.client.js.FBInitOptions;
import com.denormans.facebookgwt.api.client.js.FBJSException;
import com.denormans.facebookgwt.api.client.js.FBLoginOptions;
import com.denormans.facebookgwt.api.client.js.FBSession;
import com.denormans.gwtutil.client.js.JSError;
import com.denormans.gwtutil.client.js.JSFunction;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class FacebookGWTAPI implements HasFBInitHandlers, HasFBAuthHandlers {
  public static final Logger Log = Logger.getLogger(FacebookGWTAPI.class.getName());

  public enum InitializationState { Uninitialized, LoadingScript, ScriptLoaded, Initialized }

  public static final int InitializationTimeout = 10;

  public static final String FacebookRootElementID = "fb-root";
  public static final String FacebookScriptElementID = "fb-script-all";
  private static final String FacebookScriptServer = "connect.facebook.net";
  private static final String FacebookScriptLocation = "en_US/all.js";

  private static FacebookGWTAPI sInstance;

  private CountingEventBus eventBus = new CountingEventBus();

  private InitializationState initializationState = InitializationState.Uninitialized;
  private Timer initializationTimer;

  private Map<FBEventType, JSFunction> callbackFunctionsByEvent = new HashMap<FBEventType, JSFunction>();

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
   *
   * @param initOptions initialization options
   */
  public void initialize(final FBInitOptions initOptions) {
    initialize(initOptions, InitializationTimeout);
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
    if (initializationState == InitializationState.LoadingScript || initializationState == InitializationState.ScriptLoaded) {
      return;
    }

    if (initializationState == InitializationState.Initialized) {
      fireInitSuccess();
      return;
    }

    initializationState = InitializationState.LoadingScript;
    Log.fine("Initializing Facebook...");

    setupFBAsyncInitCallback(initOptions);

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

          self.@com.denormans.facebookgwt.api.client.FacebookGWTAPI::handleFBAsyncInit(Lcom/denormans/facebookgwt/api/client/js/FBInitOptions;)(initOptions);
        } catch(e) {
          @com.denormans.facebookgwt.api.client.FacebookGWTAPI::raiseException(Lcom/denormans/facebookgwt/api/client/js/JavaScriptError;)(e);
        }
      }
    } catch(e) {
    }
  }-*/;

  @SuppressWarnings ({ "UnusedDeclaration" })
  private void handleFBAsyncInit(final FBInitOptions initOptions) {
    initializationState = InitializationState.ScriptLoaded;

    initializationTimer.cancel();
    initializationTimer = null;

    if (initOptions != null) {
      executeFBInit(initOptions);
    }

    initializationState = InitializationState.Initialized;

    Log.fine("Facebook initialized");
    fireInitSuccess();
  }

  public native void executeFBInit(final FBInitOptions initOptions) /*-{
    try {
      $wnd.FB.init(initOptions);
    } catch(e) {
      @com.denormans.facebookgwt.api.client.FacebookGWTAPI::raiseException(Lcom/denormans/facebookgwt/api/client/js/JavaScriptError;)(e);
    }
  }-*/;

  /**
   * Retrieves the login status asynchronously via the Facebook JSAPI.
   *
   * @param callback The callback to receive the results
   */
  public native void retrieveLoginStatus(final AsyncCallback<FBAuthEventResponse> callback) /*-{
    try {
      $wnd.FB.getLoginStatus(function(response) {
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(Ljava/lang/Object;)(response);
      });
    } catch(e) {
      var ex = @com.denormans.facebookgwt.api.client.FacebookGWTAPI::createException(Lcom/denormans/facebookgwt/api/client/js/JavaScriptError;)(e);
      callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
    }
  }-*/;

  public native FBSession getSession() /*-{
    try {
      return $wnd.FB.getSession();
    } catch(e) {
      @com.denormans.facebookgwt.api.client.FacebookGWTAPI::raiseException(Lcom/denormans/facebookgwt/api/client/js/JavaScriptError;)(e);
    }
  }-*/;

  public void login(final AsyncCallback<FBAuthEventResponse> callback) {
    login(callback, null);
  }

  public native void login(final AsyncCallback<FBAuthEventResponse> callback, final FBLoginOptions loginOptions) /*-{
    try {
      $wnd.FB.login(function(response) {
        if (callback != null) {
          callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(Ljava/lang/Object;)(response);
        }
      }, loginOptions);
    } catch(e) {
      if (callback != null) {
        var ex = @com.denormans.facebookgwt.api.client.FacebookGWTAPI::createException(Lcom/denormans/facebookgwt/api/client/js/JavaScriptError;)(e);
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
      } else {
        @com.denormans.facebookgwt.api.client.FacebookGWTAPI::raiseException(Lcom/denormans/facebookgwt/api/client/js/JavaScriptError;)(e);
      }
    }
  }-*/;

  public void logout(final AsyncCallback<FBAuthEventResponse> callback) {
    logout(callback, null);
  }

  public native void logout(final AsyncCallback<FBAuthEventResponse> callback, final FBLoginOptions loginOptions) /*-{
    try {
      $wnd.FB.logout(function(response) {
        if (callback != null) {
          callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(Ljava/lang/Object;)(response);
        }
      }, loginOptions);
    } catch(e) {
      if (callback != null) {
        var ex = @com.denormans.facebookgwt.api.client.FacebookGWTAPI::createException(Lcom/denormans/facebookgwt/api/client/js/JavaScriptError;)(e);
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
      } else {
        @com.denormans.facebookgwt.api.client.FacebookGWTAPI::raiseException(Lcom/denormans/facebookgwt/api/client/js/JavaScriptError;)(e);
      }
    }
  }-*/;

  @SuppressWarnings ( { "ThrowableResultOfMethodCallIgnored" })
  private static void raiseException(final String message) {
    GWT.UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
    if (uncaughtExceptionHandler == null) {
      throw new FBGWTException(message);
    }

    uncaughtExceptionHandler.onUncaughtException(createException(message));
  }

  private static FBGWTException createException(final String message) {
    FBGWTException fbgwtException = new FBGWTException(message);
    fbgwtException.fillInStackTrace();
    return fbgwtException;
  }

  @SuppressWarnings ( { "UnusedDeclaration", "ThrowableResultOfMethodCallIgnored" })
  private static void raiseException(final JSError error) {
    GWT.UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
    if (uncaughtExceptionHandler == null) {
      throw new FBJSException(error);
    }

    uncaughtExceptionHandler.onUncaughtException(createException(error));
  }

  private static FBJSException createException(final JSError error) {
    FBJSException fbjsException = new FBJSException(error);
    fbjsException.fillInStackTrace();
    return fbjsException;
  }

  private JSFunction subscribeToEvent(final FBEventType eventType) {
    Log.fine("Subscribing to event: " + eventType);
    return subscribeToEventJS(eventType.getApiValue());
  }

  private native JSFunction subscribeToEventJS(final String eventName) /*-{
    try {
      var self = this;
      var callback = function(response) {
        try {
          self.@com.denormans.facebookgwt.api.client.FacebookGWTAPI::handleFBEvent(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(eventName, response);
        } catch(e) {
          @com.denormans.facebookgwt.api.client.FacebookGWTAPI::raiseException(Lcom/denormans/facebookgwt/api/client/js/JavaScriptError;)(e);
        }
      };

      $wnd.FB.Event.subscribe(eventName, callback);

      return callback;
    } catch(e) {
      return @com.denormans.facebookgwt.api.client.FacebookGWTAPI::raiseException(Lcom/denormans/facebookgwt/api/client/js/JavaScriptError;)(e);
    }
  }-*/;

  private void unsubscribeFromEvent(final FBEventType eventType, final JSFunction callback) {
    Log.fine("Unsubscribing from event: " + eventType);
    unsubscribeFromEventJS(eventType.getApiValue(), callback);
  }

  private native void unsubscribeFromEventJS(final String eventName, final JSFunction callback) /*-{
    try {
      $wnd.FB.Event.unsubscribe(eventName, callback);
    } catch(e) {
      @com.denormans.facebookgwt.api.client.FacebookGWTAPI::raiseException(Lcom/denormans/facebookgwt/api/client/js/JavaScriptError;)(e);
    }
  }-*/;

  @SuppressWarnings ({ "UnusedDeclaration" })
  private void handleFBEvent(final String eventName, final JavaScriptObject apiResponse) {
    FBEventType eventType = FBEventType.valueFromApiValue(eventName);
    if (eventType == null) {
      Log.warning("Unknown event: " + eventName);
      return;
    }

    switch (eventType) {
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
        Log.warning("Unknown event: " + eventType);
    }
  }

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
    return addFBEventHandler(handler, FBLoginEvent.getType(), FBEventType.AuthLogin);
  }

  @Override
  public HandlerRegistration addFBLogoutHandler(final FBLogoutHandler handler) {
    return addFBEventHandler(handler, FBLogoutEvent.getType(), FBEventType.AuthLogout);
  }

  @Override
  public HandlerRegistration addFBSessionChangeHandler(final FBSessionChangeHandler handler) {
    return addFBEventHandler(handler, FBSessionChangeEvent.getType(), FBEventType.AuthSessionChange);
  }

  @Override
  public HandlerRegistration addFBStatusChangeHandler(final FBStatusChangeHandler handler) {
    return addFBEventHandler(handler, FBStatusChangeEvent.getType(), FBEventType.AuthStatusChange);
  }

  private <H extends FBEventHandler> HandlerRegistration addFBEventHandler(final H handler, final GwtEvent.Type<H> eventType, final FBEventType fbEventType) {
    if (callbackFunctionsByEvent.get(fbEventType) == null) {
      JSFunction callbackFunction = subscribeToEvent(fbEventType);
      callbackFunctionsByEvent.put(fbEventType, callbackFunction);
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
            JSFunction callbackFunction = callbackFunctionsByEvent.get(fbEventType);
            if (callbackFunction != null && eventBus.getCount(eventType) == 0) {
              unsubscribeFromEvent(fbEventType, callbackFunction);
              callbackFunctionsByEvent.remove(fbEventType);
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

}
