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
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessHandler;
import com.denormans.facebookgwt.api.shared.common.events.FBEventTypes;
import com.denormans.facebookgwt.gwtutil.shared.events.ValueAddEvent;
import com.denormans.facebookgwt.gwtutil.shared.events.ValueRemoveEvent;
import com.denormans.facebookgwt.gwtutil.shared.events.ValueRemoveHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class EventsWidget extends Composite {
  interface EventsWidgetUIBinder extends UiBinder<HTMLPanel, EventsWidget> {}
  private static EventsWidgetUIBinder sUIBinder = GWT.create(EventsWidgetUIBinder.class);

  @UiField AddEventWidget addEventWidget;
  @UiField ScrollPanel eventWidgets;
  @UiField FlowPanel eventWidgetsPanel;

  private ValueRemoveHandler<EventDescriptor> eventDescriptorRemoveHandler;

  public EventsWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    FBGWT.Init.addFBInitSuccessHandler(new FBInitSuccessHandler() {
      @Override
      public void onFBInitSuccess(final FBInitSuccessEvent event) {
        addEventWidget.setEnabled(FBGWT.Init.isInitialized());

        if (eventWidgetsPanel.getWidgetCount() == 0) {
          for (final FBEventTypes eventType : FBEventTypes.values()) {
            addEvent(new EventDescriptor(eventType));
          }
        }
      }
    });

    eventDescriptorRemoveHandler = new ValueRemoveHandler<EventDescriptor>() {
      @Override
      public void onValueRemove(final ValueRemoveEvent<EventDescriptor> eventDescriptorValueRemoveEvent) {
        removeEvent((EventWidget) eventDescriptorValueRemoveEvent.getSource());
      }
    };
  }

  private void addEvent(final EventDescriptor eventDescriptor) {
    EventWidget eventWidget = new EventWidget();
    eventWidget.setValue(eventDescriptor);

    eventWidget.addValueRemoveHandler(eventDescriptorRemoveHandler);

    eventWidgetsPanel.add(eventWidget);
    resizeEventWidgets();
  }

  private void removeEvent(final EventWidget eventWidget) {
    eventWidgetsPanel.remove(eventWidget);

    resizeEventWidgets();
  }

  private void resizeEventWidgets() {
    int numWidgetsToShow = Math.min(eventWidgetsPanel.getWidgetCount(), 5);
    if (numWidgetsToShow > 0) {
      eventWidgets.setHeight((numWidgetsToShow * 39 - 1) + "px");
      eventWidgets.scrollToBottom();
    }
    eventWidgets.setVisible(numWidgetsToShow > 0);
  }

  @UiHandler ("addEventWidget")
  public void handleAddEventValueAdd(final ValueAddEvent<EventDescriptor> event) {
    addEvent(event.getValue());
  }
}