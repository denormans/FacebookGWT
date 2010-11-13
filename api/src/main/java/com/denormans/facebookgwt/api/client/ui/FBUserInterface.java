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

package com.denormans.facebookgwt.api.client.ui;

import com.denormans.facebookgwt.api.client.FBIntegration;
import com.denormans.facebookgwt.api.client.common.js.FBEventResponse;
import com.denormans.facebookgwt.api.client.ui.events.FBAddCommentEvent;
import com.denormans.facebookgwt.api.client.ui.events.FBAddCommentHandler;
import com.denormans.facebookgwt.api.client.ui.events.FBEdgeCreateEvent;
import com.denormans.facebookgwt.api.client.ui.events.FBEdgeCreateHandler;
import com.denormans.facebookgwt.api.client.ui.events.HasFBUIHandlers;
import com.denormans.facebookgwt.api.client.ui.events.XFBMLRenderEvent;
import com.denormans.facebookgwt.api.client.ui.events.XFBMLRenderHandler;
import com.denormans.facebookgwt.api.client.ui.js.FBAddCommentEventResponse;
import com.denormans.facebookgwt.api.client.ui.js.FBEdgeCreateEventResponse;
import com.denormans.facebookgwt.api.client.ui.js.FBUIActionOptions;
import com.denormans.facebookgwt.api.client.ui.js.StreamPublishCallbackResponse;
import com.denormans.facebookgwt.api.client.ui.js.StreamPublishOptions;
import com.denormans.facebookgwt.api.client.ui.js.XFBMLRenderEventResponse;
import com.denormans.facebookgwt.api.shared.common.events.FBEventTypes;
import com.denormans.facebookgwt.api.shared.ui.DisplayFormat;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;


public final class FBUserInterface extends FBIntegration implements HasFBUIHandlers {
  /**
   * Parse all XFBML elements in the current page.
   */
  public void parseXFBML() {
    parseXFBML((Element)null);
  }

  /**
   * Parse XFBML elements within the given widget.
   *
   * @param widget The widget to parse within
   */
  public void parseXFBML(final IsWidget widget) {
    parseXFBML(widget != null ? widget.asWidget().getElement() : null);
  }

  /**
   * Parse XFBML elements within the given element.
   * 
   * @param element The element to parse within, or <tt>null</tt> to parse the entire document
   */
  public native void parseXFBML(final Element element) /*-{
    try {
      $wnd.FB.XFBML.parse(element);
    } catch(e) {
      @com.denormans.facebookgwt.api.client.FBGWT::throwException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
    }
  }-*/;

  public void publishToStream(final StreamPublishOptions publishOptions, final DisplayFormat displayFormat, final AsyncCallback<StreamPublishCallbackResponse> callback) {
    executeAction("stream.publish", displayFormat, publishOptions, callback);
  }

  public native void executeAction(final String method, final DisplayFormat displayFormat, final FBUIActionOptions actionOptions, final AsyncCallback<? extends FBEventResponse> callback) /*-{
    try {
      actionOptions.method = method;
      if (displayFormat != null) {
        actionOptions.display = displayFormat.@com.denormans.facebookgwt.api.shared.ui.DisplayFormats::getApiValue()();
      }

      var cb;
      if (callback != null) {
        cb = function(response) {
          callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(Ljava/lang/Object;)(response);
        };
      }

      $wnd.FB.ui(actionOptions, cb);
    } catch(e) {
      if (callback != null) {
        var ex = @com.denormans.facebookgwt.api.client.FBGWT::createException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
      } else {
        @com.denormans.facebookgwt.api.client.FBGWT::throwException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
      }
    }
  }-*/;

  @Override
  protected void handleFBEvent(final FBEventTypes eventType, final FBEventResponse apiResponse) {
    switch (eventType) {
      case CommentsAdd:
        FBAddCommentEvent.fire(this, apiResponse.<FBAddCommentEventResponse>cast());
        break;

      case EdgeCreate:
        FBEdgeCreateEvent.fire(this, apiResponse.<FBEdgeCreateEventResponse>cast());
        break;

      case XFBMLRender:
        XFBMLRenderEvent.fire(this, apiResponse.<XFBMLRenderEventResponse>cast());
        break;

      default:
        super.handleFBEvent(eventType, apiResponse);
    }
  }

  @Override
  public HandlerRegistration addFBAddCommentHandler(final FBAddCommentHandler handler) {
    return addFBEventHandler(handler, FBAddCommentEvent.getType(), FBEventTypes.CommentsAdd);
  }

  @Override
  public HandlerRegistration addFBEdgeCreateHandler(final FBEdgeCreateHandler handler) {
    return addFBEventHandler(handler, FBEdgeCreateEvent.getType(), FBEventTypes.EdgeCreate);
  }

  @Override
  public HandlerRegistration addXFBMLRenderHandler(final XFBMLRenderHandler handler) {
    return addFBEventHandler(handler, XFBMLRenderEvent.getType(), FBEventTypes.XFBMLRender);
  }

}
