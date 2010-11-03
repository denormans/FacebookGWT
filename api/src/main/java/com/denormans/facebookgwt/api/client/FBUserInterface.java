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
import com.denormans.facebookgwt.api.client.events.ui.FBAddCommentEvent;
import com.denormans.facebookgwt.api.client.events.ui.FBAddCommentHandler;
import com.denormans.facebookgwt.api.client.events.ui.FBEdgeCreateEvent;
import com.denormans.facebookgwt.api.client.events.ui.FBEdgeCreateHandler;
import com.denormans.facebookgwt.api.client.events.ui.HasFBUIHandlers;
import com.denormans.facebookgwt.api.client.events.ui.XFBMLRenderEvent;
import com.denormans.facebookgwt.api.client.events.ui.XFBMLRenderHandler;
import com.denormans.facebookgwt.api.client.js.FBAddCommentEventResponse;
import com.denormans.facebookgwt.api.client.js.FBEdgeCreateEventResponse;
import com.denormans.facebookgwt.api.client.js.FBEventResponse;
import com.denormans.facebookgwt.api.client.js.XFBMLRenderEventResponse;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.dom.client.Element;
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
    $wnd.FB.XFBML.parse(element);
  }-*/;

  @Override
  protected void handleFBEvent(final FBEventType eventType, final FBEventResponse apiResponse) {
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
    return addFBEventHandler(handler, FBAddCommentEvent.getType(), FBEventType.CommentsAdd);
  }

  @Override
  public HandlerRegistration addFBEdgeCreateHandler(final FBEdgeCreateHandler handler) {
    return addFBEventHandler(handler, FBEdgeCreateEvent.getType(), FBEventType.EdgeCreate);
  }

  @Override
  public HandlerRegistration addXFBMLRenderHandler(final XFBMLRenderHandler handler) {
    return addFBEventHandler(handler, XFBMLRenderEvent.getType(), FBEventType.XFBMLRender);
  }
}
