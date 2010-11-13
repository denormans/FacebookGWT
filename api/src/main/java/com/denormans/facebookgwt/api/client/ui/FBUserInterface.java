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
import com.denormans.facebookgwt.api.client.ui.js.BookmarkApplicationCallbackResponse;
import com.denormans.facebookgwt.api.client.ui.js.FBAddCommentEventResponse;
import com.denormans.facebookgwt.api.client.ui.js.FBEdgeCreateEventResponse;
import com.denormans.facebookgwt.api.client.ui.js.FBUIMethodOptions;
import com.denormans.facebookgwt.api.client.ui.js.StreamPublishCallbackResponse;
import com.denormans.facebookgwt.api.client.ui.js.StreamPublishOptions;
import com.denormans.facebookgwt.api.client.ui.js.XFBMLRenderEventResponse;
import com.denormans.facebookgwt.api.shared.common.events.FBEventTypes;
import com.denormans.facebookgwt.api.shared.ui.DisplayFormat;
import com.denormans.facebookgwt.api.shared.ui.UIMethod;
import com.denormans.facebookgwt.api.shared.ui.UIMethods;

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

  /**
   * Open a dialog to allow the user to publish a message to their stream.
   *
   * @param displayFormat The display format of the dialog
   * @param publishOptions What to publish
   * @param callback Called when the method is complete
   */
  public void publishToStream(final DisplayFormat displayFormat, final StreamPublishOptions publishOptions, final AsyncCallback<StreamPublishCallbackResponse> callback) {
    executeUIMethod(UIMethods.PublishToStream, displayFormat, publishOptions, callback);
  }

  /**
   * Open a dialog to allow the user to bookmark the application.
   *
   * @param displayFormat The display format of the dialog
   * @param callback Called when the method is complete
   */
  public void bookmarkApplication(final DisplayFormat displayFormat, final AsyncCallback<BookmarkApplicationCallbackResponse> callback) {
    executeUIMethod(UIMethods.AddBookmark, displayFormat, null, callback);
  }

  /**
   * Executes a UI method.
   *
   * @param method The method to execute
   * @param displayFormat The display format of the dialog
   * @param methodOptions The method options
   * @param callback Called when the method is complete
   */
  public void executeUIMethod(final UIMethod method, final DisplayFormat displayFormat, final FBUIMethodOptions methodOptions, final AsyncCallback<? extends FBEventResponse> callback) {
    executeUIMethodJS(method.getApiValue(), displayFormat, methodOptions, callback);
  }

  private native void executeUIMethodJS(final String method, final DisplayFormat displayFormat, final FBUIMethodOptions methodOptions, final AsyncCallback<? extends FBEventResponse> callback) /*-{
    try {
      if (methodOptions == null) {
        methodOptions = {};
      }

      methodOptions.method = method;
      if (displayFormat != null) {
        methodOptions.display = displayFormat.@com.denormans.facebookgwt.api.shared.ui.DisplayFormats::getApiValue()();
      } else {
        methodOptions.display = null;
      }

      var cb;
      if (callback != null) {
        cb = function(response) {
          callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(Ljava/lang/Object;)(response);
        };
      }

      $wnd.FB.ui(methodOptions, cb);
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
