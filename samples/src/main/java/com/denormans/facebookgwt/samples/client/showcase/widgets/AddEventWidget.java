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

import com.denormans.facebookgwt.api.shared.common.events.FBEventTypes;
import com.denormans.facebookgwt.gwtutil.shared.events.HasValueAddHandlers;
import com.denormans.facebookgwt.gwtutil.shared.events.ValueAddEvent;
import com.denormans.facebookgwt.gwtutil.shared.events.ValueAddHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class AddEventWidget extends Composite implements HasValueAddHandlers<EventDescriptor>, HasEnabled {
  interface AddEventWidgetUIBinder extends UiBinder<HTMLPanel, AddEventWidget> {}
  private static AddEventWidgetUIBinder sUIBinder = GWT.create(AddEventWidgetUIBinder.class);

  @UiField ListBox eventTypesListBox;
  @UiField HTML eventTypesError;

  @UiField TextBox eventMessageTextBox;
  @UiField HTML eventMessageError;

  @UiField Button addEventHandlerButton;

  public AddEventWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    eventTypesListBox.addItem("Please select a type...", "");
    for (final FBEventTypes eventType : FBEventTypes.values()) {
      eventTypesListBox.addItem(eventType.name());
    }
    eventTypesListBox.setSelectedIndex(0);
  }

  @Override
  public boolean isEnabled() {
    return addEventHandlerButton.isEnabled();
  }

  @Override
  public void setEnabled(final boolean enabled) {
    eventTypesListBox.setEnabled(enabled);
    eventMessageTextBox.setEnabled(enabled);
    addEventHandlerButton.setEnabled(enabled);
  }

  @UiHandler ("addEventHandlerButton")
  public void handleAddEventHandlerClick(final ClickEvent event) {
    FBEventTypes fbEventType = getSelectedEventType();
    if (fbEventType == null) {
      eventTypesError .setText("Please choose an event type.");
      eventTypesError.setVisible(true);
      return;
    }

    ValueAddEvent.fire(this, new EventDescriptor(fbEventType, eventMessageTextBox.getText()));

    eventTypesListBox.setSelectedIndex(0);
    eventMessageTextBox.setText("");
  }

  private FBEventTypes getSelectedEventType() {
    FBEventTypes fbEventType = null;
    String eventTypeName = eventTypesListBox.getValue(eventTypesListBox.getSelectedIndex());
    if (eventTypeName.length() > 0) {
      fbEventType = FBEventTypes.valueOf(eventTypeName);
    }
    return fbEventType;
  }

  @UiHandler ("eventTypesListBox")
  public void handleEventTypesListBoxChange(final ChangeEvent event) {
    eventTypesError.setVisible(false);
    eventMessageError.setVisible(false);
  }

  @UiHandler ("eventMessageTextBox")
  public void handleEventMessageTextBoxValueChange(final ValueChangeEvent<String> event) {
    eventMessageError.setVisible(false);
  }

  @Override
  public HandlerRegistration addValueAddHandler(final ValueAddHandler<EventDescriptor> eventDescriptorValueAddHandler) {
    return addHandler(eventDescriptorValueAddHandler, ValueAddEvent.getType());
  }
}