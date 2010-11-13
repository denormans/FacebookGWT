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
import com.denormans.facebookgwt.api.client.common.js.FBEventResponse;
import com.denormans.facebookgwt.api.shared.common.events.FBEventType;
import com.denormans.facebookgwt.api.shared.common.events.FBEventTypes;
import com.denormans.gwtutil.client.js.JSFunction;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.testing.CountingEventBus;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

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

  private JSFunction subscribeToEvent(final FBEventType eventType) {
    Log.fine("Subscribing to event: " + eventType + " (" + eventType.getApiValue() + ")");
    return subscribeToEventJS(eventType.getApiValue());
  }

  private native JSFunction subscribeToEventJS(final String eventName) /*-{
    try {
      var self = this;
      var callback = function(response) {
        try {
          var responseObj;
          if (response === null || response === undefined || typeof(response) === "object") {
            responseObj = response;
          } else {
            var value;
            if(typeof(response) === "number" || typeof(response) === "boolean") {
              value = response;
            } else {
              value = String(response);
            }
            responseObj = { _simpleValue: value };
          }
          self.@com.denormans.facebookgwt.api.client.FBIntegration::handleFBEvent(Ljava/lang/String;Lcom/denormans/facebookgwt/api/client/common/js/FBEventResponse;)(eventName, responseObj);
        } catch(e) {
          @com.denormans.facebookgwt.api.client.FBGWT::raiseException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
        }
      };

      $wnd.FB.Event.subscribe(eventName, callback);

      return callback;
    } catch(e) {
      return @com.denormans.facebookgwt.api.client.FBGWT::throwException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
    }
  }-*/;

  private void unsubscribeFromEvent(final FBEventType eventType, final JSFunction callback) {
    Log.fine("Unsubscribing from event: " + eventType + " (" + eventType.getApiValue() + ")");
    unsubscribeFromEventJS(eventType.getApiValue(), callback);
  }

  private native void unsubscribeFromEventJS(final String eventName, final JSFunction callback) /*-{
    try {
      $wnd.FB.Event.unsubscribe(eventName, callback);
    } catch(e) {
      @com.denormans.facebookgwt.api.client.FBGWT::throwException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
    }
  }-*/;

  @SuppressWarnings ({ "UnusedDeclaration" })
  private void handleFBEvent(final String eventName, final FBEventResponse apiResponse) {
    FBEventType eventType = FBEventTypes.valueFromApiValue(eventName);
    if (eventType == null) {
      Log.warning("Unknown event: " + eventName);
      return;
    }

    if (eventType instanceof FBEventTypes) {
      handleFBEvent((FBEventTypes)eventType, apiResponse);
    } else {
      handleFBEvent(eventType, apiResponse);
    }
  }

  protected void handleFBEvent(final FBEventType eventType, final FBEventResponse apiResponse) {
    Log.warning("Unhandled event " + eventType + " with response: " + apiResponse.getJSONString());
  }

  protected void handleFBEvent(final FBEventTypes eventType, final FBEventResponse apiResponse) {
    Log.warning("Unhandled event " + eventType + " with response: " + apiResponse.getJSONString());
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

  public void fireEvent(final GwtEvent<?> event) {
    eventBus.fireEvent(event);
  }
}
