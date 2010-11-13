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

import com.denormans.facebookgwt.api.shared.auth.FBPermission;
import com.denormans.gwtutil.shared.events.HasValueRemoveHandlers;
import com.denormans.gwtutil.shared.events.ValueRemoveEvent;
import com.denormans.gwtutil.shared.events.ValueRemoveHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class PermissionWidget extends Composite implements TakesValue<FBPermission>, HasValueRemoveHandlers<FBPermission> {
  interface PermissionWidgetUIBinder extends UiBinder<HTMLPanel, PermissionWidget> {}
  private static PermissionWidgetUIBinder sUIBinder = GWT.create(PermissionWidgetUIBinder.class);

  @UiField Button removePermissionButton;
  @UiField SpanElement permissionSpan;

  private FBPermission permission;

  public PermissionWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);
  }

  public FBPermission getValue() {
    return permission;
  }

  public void setValue(final FBPermission permission) {
    this.permission = permission;

    permissionSpan.setInnerText(permission.getApiValue());
    removePermissionButton.setEnabled(true);
  }

  @UiHandler ("removePermissionButton")
  public void handleRemovePermissionButtonClick(final ClickEvent event) {
    ValueRemoveEvent.fire(this, permission);
  }

  @Override
  public HandlerRegistration addValueRemoveHandler(final ValueRemoveHandler<FBPermission> fbPermissionValueRemoveHandler) {
    return addHandler(fbPermissionValueRemoveHandler, ValueRemoveEvent.getType());
  }
}