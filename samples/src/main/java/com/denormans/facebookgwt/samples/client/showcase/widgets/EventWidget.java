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

package com.denormans.facebookgwt.samples.client.showcase.widgets;

import com.denormans.facebookgwt.api.client.FBGWT;
import com.denormans.facebookgwt.api.client.auth.events.FBLoginEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBLoginHandler;
import com.denormans.facebookgwt.api.client.auth.events.FBLogoutEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBLogoutHandler;
import com.denormans.facebookgwt.api.client.auth.events.FBSessionChangeEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBSessionChangeHandler;
import com.denormans.facebookgwt.api.client.auth.events.FBStatusChangeEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBStatusChangeHandler;
import com.denormans.facebookgwt.api.client.common.events.FBEvent;
import com.denormans.facebookgwt.api.client.common.events.FBLogHandler;
import com.denormans.facebookgwt.api.client.core.events.FBLogEvent;
import com.denormans.facebookgwt.api.client.ui.events.FBAddCommentEvent;
import com.denormans.facebookgwt.api.client.ui.events.FBAddCommentHandler;
import com.denormans.facebookgwt.api.client.ui.events.FBEdgeCreateEvent;
import com.denormans.facebookgwt.api.client.ui.events.FBEdgeCreateHandler;
import com.denormans.facebookgwt.api.client.ui.events.XFBMLRenderEvent;
import com.denormans.facebookgwt.api.client.ui.events.XFBMLRenderHandler;
import com.denormans.facebookgwt.samples.client.FacebookGWTSamples;
import com.denormans.gwtutil.shared.events.HasValueRemoveHandlers;
import com.denormans.gwtutil.shared.events.ValueRemoveEvent;
import com.denormans.gwtutil.shared.events.ValueRemoveHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;

public class EventWidget extends ShowcaseWidget implements TakesValue<EventDescriptor>, HasValueRemoveHandlers<EventDescriptor> {
  interface EventWidgetUIBinder extends UiBinder<HTMLPanel, EventWidget> {}
  private static EventWidgetUIBinder sUIBinder = GWT.create(EventWidgetUIBinder.class);

  @UiField SpanElement eventTypeSpan;
  @UiField SpanElement eventMessageSpan;
  @UiField CheckBox eventEnabledCheckBox;
  @UiField Button removeEventButton;

  private EventDescriptor eventDescriptor;

  private HandlerRegistration eventHandlerRegistration;

  public EventWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);
  }

  public EventDescriptor getValue() {
    return eventDescriptor;
  }

  public void setValue(final EventDescriptor eventDescriptor) {
    this.eventDescriptor = eventDescriptor;

    eventEnabledCheckBox.setValue(true);
    eventEnabledCheckBox.setEnabled(eventDescriptor != null);
    removeEventButton.setEnabled(eventDescriptor != null);

    if (eventDescriptor != null) {
      eventTypeSpan.setInnerText(eventDescriptor.getEventType().name());
      eventMessageSpan.setInnerText(eventDescriptor.getMessage());

      createFBEventHandler();
    }
  }

  private void createFBEventHandler() {
    if (eventDescriptor != null) {
      switch (eventDescriptor.getEventType()) {
        case AuthLogin:
          eventHandlerRegistration = FBGWT.Auth.addFBLoginHandler(new FBLoginHandler() {
            @Override
            public void onFBLogin(final FBLoginEvent event) {
              handleEvent(event, eventDescriptor);
            }
          });
          break;

        case AuthLogout:
          eventHandlerRegistration = FBGWT.Auth.addFBLogoutHandler(new FBLogoutHandler() {
            @Override
            public void onFBLogout(final FBLogoutEvent event) {
              handleEvent(event, eventDescriptor);
            }
          });
          break;

        case AuthSessionChange:
          eventHandlerRegistration = FBGWT.Auth.addFBSessionChangeHandler(new FBSessionChangeHandler() {
            @Override
            public void onFBSessionChange(final FBSessionChangeEvent event) {
              handleEvent(event, eventDescriptor);
            }
          });
          break;

        case AuthStatusChange:
          eventHandlerRegistration = FBGWT.Auth.addFBStatusChangeHandler(new FBStatusChangeHandler() {
            @Override
            public void onFBStatusChange(final FBStatusChangeEvent event) {
              handleEvent(event, eventDescriptor);
            }
          });
          break;

        case CommentsAdd:
          eventHandlerRegistration = FBGWT.UI.addFBAddCommentHandler(new FBAddCommentHandler() {
            @Override
            public void onFBAddComment(final FBAddCommentEvent event) {
              handleEvent(event, eventDescriptor);
            }
          });
          break;

        case EdgeCreate:
          eventHandlerRegistration = FBGWT.UI.addFBEdgeCreateHandler(new FBEdgeCreateHandler() {
            @Override
            public void onFBEdgeCreate(final FBEdgeCreateEvent event) {
              handleEvent(event, eventDescriptor);
            }
          });
          break;

        case Log:
          eventHandlerRegistration = FBGWT.Core.addFBLogHandler(new FBLogHandler() {
            @Override
            public void onFBStub(final FBLogEvent event) {
              handleEvent(event, eventDescriptor);
            }
          });
          break;

        case XFBMLRender:
          eventHandlerRegistration = FBGWT.UI.addXFBMLRenderHandler(new XFBMLRenderHandler() {
            @Override
            public void onXFBMLRender(final XFBMLRenderEvent event) {
              handleEvent(event, eventDescriptor);
            }
          });
          break;

        default:
          FacebookGWTSamples.get().handleError("Unknown event type: " + eventDescriptor.getEventType());
      }
    }
  }

  private void removeFBEventHandler() {
    if (eventHandlerRegistration != null) {
      eventHandlerRegistration.removeHandler();
      eventHandlerRegistration = null;
    }
  }

  private void handleEvent(final FBEvent<?, ?> event, final EventDescriptor eventDescriptor) {
    addApiEventMessage(eventDescriptor.getMessage(), event);
  }

  @UiHandler ("eventEnabledCheckBox")
  public void handleEventEnabledCheckboxValueChange(final ValueChangeEvent<Boolean> event) {
    if (event.getValue()) {
      if (eventHandlerRegistration == null) {
        createFBEventHandler();
      }
    } else {
      removeFBEventHandler();
    }
  }

  @UiHandler ("removeEventButton")
  public void handleRemoveEventButtonClick(final ClickEvent event) {
    ValueRemoveEvent.fire(this, eventDescriptor);

    removeFBEventHandler();
  }

  @Override
  public HandlerRegistration addValueRemoveHandler(final ValueRemoveHandler<EventDescriptor> eventDescriptorValueRemoveHandler) {
    return addHandler(eventDescriptorValueRemoveHandler, ValueRemoveEvent.getType());
  }
}