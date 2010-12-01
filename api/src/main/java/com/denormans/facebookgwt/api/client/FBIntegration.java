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

import com.denormans.facebookgwt.api.client.common.events.FBEventHandler;
import com.denormans.facebookgwt.api.client.core.FBAPIException;
import com.denormans.facebookgwt.api.shared.common.events.FBEventType;
import com.denormans.facebookgwt.api.shared.common.events.FBEventTypes;
import com.denormans.gwtutil.client.js.EnhancedJSObject;
import com.denormans.gwtutil.client.js.JSFunction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.testing.CountingEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class FBIntegration implements HasHandlers {
  public static final Logger Log = Logger.getLogger(FBIntegration.class.getName());

  private CountingEventBus eventBus = new CountingEventBus();

  private Map<FBEventType, JSFunction> callbackFunctionsByEvent = new HashMap<FBEventType, JSFunction>();

  protected EventBus getEventBus() {
    return eventBus;
  }

  protected CountingEventBus getCountingEventBus() {
    return eventBus;
  }

  @SuppressWarnings ( { "unchecked" })
  protected void executeCallback(final AsyncCallback callback, final Object result) {
    try {
      callback.onSuccess(result);
    } catch (Throwable t) {
      GWT.UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
      if (uncaughtExceptionHandler == null) {
        throw new CallbackException("Error executing callback", t);
      }

      uncaughtExceptionHandler.onUncaughtException(t);
    }
  }

  protected void executeCallback(final AsyncCallback callback, final boolean result) {
    executeCallback(callback, Boolean.valueOf(result));
  }

  protected void executeCallback(final AsyncCallback callback, final double result) {
    executeCallback(callback, Double.valueOf(result));
  }

  protected void executeCallbackError(final AsyncCallback callback, final int errorCode, final String errorMessage) {
    FBAPIException fbapiException = new FBAPIException(errorMessage, errorCode);
    fbapiException.fillInStackTrace();
    callback.onFailure(fbapiException);
  }

  private JSFunction subscribeToEvent(final FBEventType eventType) {
    Log.fine("Subscribing to event: " + eventType + " (" + eventType.getApiValue() + ")");
    return subscribeToEventJS(eventType.getApiValue());
  }

  private native JSFunction subscribeToEventJS(final String eventName) /*-{
    var self = this;
    var callback = function(response) {
      self.@com.denormans.facebookgwt.api.client.FBIntegration::handleFBEvent(Ljava/lang/String;Ljava/lang/Object;)(eventName, response);
    };

    $wnd.FB.Event.subscribe(eventName, callback);

    return callback;
  }-*/;

  private void unsubscribeFromEvent(final FBEventType eventType, final JSFunction callback) {
    Log.fine("Unsubscribing from event: " + eventType + " (" + eventType.getApiValue() + ")");
    unsubscribeFromEventJS(eventType.getApiValue(), callback);
  }

  private native void unsubscribeFromEventJS(final String eventName, final JSFunction callback) /*-{
    $wnd.FB.Event.unsubscribe(eventName, callback);
  }-*/;

  @SuppressWarnings ({ "UnusedDeclaration" })
  private void handleFBEvent(final String eventName, final Object apiResponse) {
    try {
      FBEventType eventType = FBEventTypes.valueFromApiValue(eventName);
      if (eventType == null) {
        Log.warning("Unknown event: " + eventName + " with response: " + describeAPIResponse(apiResponse));
        return;
      }

      if (eventType instanceof FBEventTypes) {
        handleFBEvent((FBEventTypes)eventType, apiResponse);
      } else {
        handleFBEvent(eventType, apiResponse);
      }
    } catch (Throwable t) {
      GWT.UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
      if (uncaughtExceptionHandler == null) {
        throw new EventHandlerException("Error handling Facebook event", t);
      }

      uncaughtExceptionHandler.onUncaughtException(t);
    }
  }

  protected void handleFBEvent(final FBEventType eventType, final Object apiResponse) {
    Log.warning("Unhandled event " + eventType + " with response: " + describeAPIResponse(apiResponse));
  }

  protected void handleFBEvent(final FBEventTypes eventType, final Object apiResponse) {
    handleFBEvent((FBEventType) eventType, apiResponse);
  }

  private String describeAPIResponse(final Object apiResponse) {
    if (apiResponse instanceof EnhancedJSObject) {
      return ((EnhancedJSObject) apiResponse).toJSONString();
    }

    if (apiResponse != null) {
      apiResponse.toString();
    }

    return "null";
  }

  protected <H extends FBEventHandler> HandlerRegistration addFBEventHandler(final H handler, final GwtEvent.Type<H> eventType, final FBEventType fbEventType) {
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
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
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

  public void fireEvent(final GwtEvent<?> event) {
    eventBus.fireEvent(event);
  }
}
