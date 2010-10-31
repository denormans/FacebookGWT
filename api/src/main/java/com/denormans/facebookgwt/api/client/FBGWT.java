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

import com.denormans.facebookgwt.api.client.events.FBEventHandler;
import com.denormans.facebookgwt.api.client.events.FBEventType;
import com.denormans.facebookgwt.api.client.events.auth.FBLoginEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBLoginHandler;
import com.denormans.facebookgwt.api.client.events.auth.FBLogoutEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBLogoutHandler;
import com.denormans.facebookgwt.api.client.events.auth.FBSessionChangeEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBSessionChangeHandler;
import com.denormans.facebookgwt.api.client.events.auth.FBStatusChangeEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBStatusChangeHandler;
import com.denormans.facebookgwt.api.client.events.auth.HasFBAuthHandlers;
import com.denormans.facebookgwt.api.client.js.FBAuthEventResponse;
import com.denormans.facebookgwt.api.client.js.FBEventResponse;
import com.denormans.facebookgwt.api.client.js.FBInitOptions;
import com.denormans.facebookgwt.api.client.js.FBJSException;
import com.denormans.facebookgwt.api.client.js.FBLoginOptions;
import com.denormans.facebookgwt.api.client.js.FBSession;
import com.denormans.gwtutil.client.js.JSError;
import com.denormans.gwtutil.client.js.JSFunction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.testing.CountingEventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class FBGWT implements HasFBAuthHandlers {
  public static final Logger Log = Logger.getLogger(FBGWT.class.getName());

  private static final FBGWT sInstance = new FBGWT();

  public static final FBInitialization Init = new FBInitialization();

  private CountingEventBus eventBus = new CountingEventBus();

  private Map<FBEventType, JSFunction> callbackFunctionsByEvent = new HashMap<FBEventType, JSFunction>();

  public void initialize(final FBInitOptions initOptions) {
    Init.initialize(initOptions);
  }

  public void initialize(final FBInitOptions initOptions, final int initializationTimeout) {
    Init.initialize(initOptions, initializationTimeout);
  }

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
      var ex = @com.denormans.facebookgwt.api.client.FBGWT::createException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
      callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
    }
  }-*/;

  public native FBSession getSession() /*-{
    try {
      return $wnd.FB.getSession();
    } catch(e) {
      @com.denormans.facebookgwt.api.client.FBGWT::raiseException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
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
        var ex = @com.denormans.facebookgwt.api.client.FBGWT::createException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
      } else {
        @com.denormans.facebookgwt.api.client.FBGWT::raiseException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
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
        var ex = @com.denormans.facebookgwt.api.client.FBGWT::createException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
      } else {
        @com.denormans.facebookgwt.api.client.FBGWT::raiseException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
      }
    }
  }-*/;

  @SuppressWarnings ( { "ThrowableResultOfMethodCallIgnored" })
  public static void raiseException(final String message) {
    GWT.UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
    if (uncaughtExceptionHandler == null) {
      throw new FBGWTException(message);
    }

    uncaughtExceptionHandler.onUncaughtException(createException(message));
  }

  public static FBGWTException createException(final String message) {
    FBGWTException fbgwtException = new FBGWTException(message);
    fbgwtException.fillInStackTrace();
    return fbgwtException;
  }

  @SuppressWarnings ( { "UnusedDeclaration", "ThrowableResultOfMethodCallIgnored" })
  public static void raiseException(final JSError error) {
    GWT.UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
    if (uncaughtExceptionHandler == null) {
      throw new FBJSException(error);
    }

    uncaughtExceptionHandler.onUncaughtException(createException(error));
  }

  public static FBJSException createException(final JSError error) {
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
          self.@com.denormans.facebookgwt.api.client.FBGWT::handleFBEvent(Ljava/lang/String;Lcom/denormans/facebookgwt/api/client/js/FBEventResponse;)(eventName, response);
        } catch(e) {
          @com.denormans.facebookgwt.api.client.FBGWT::raiseException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
        }
      };

      $wnd.FB.Event.subscribe(eventName, callback);

      return callback;
    } catch(e) {
      return @com.denormans.facebookgwt.api.client.FBGWT::raiseException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
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
      @com.denormans.facebookgwt.api.client.FBGWT::raiseException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
    }
  }-*/;

  @SuppressWarnings ({ "UnusedDeclaration" })
  private void handleFBEvent(final String eventName, final FBEventResponse apiResponse) {
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

  public static FBGWT get() {
    return sInstance;
  }

}
