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
import com.denormans.facebookgwt.api.shared.auth.FBPermissions;
import com.denormans.gwtutil.shared.events.ValueRemoveEvent;
import com.denormans.gwtutil.shared.events.ValueRemoveHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;

import java.util.ArrayList;
import java.util.List;

public class FBPermissionListEditor extends Composite implements LeafValueEditor<List<FBPermission>>, HasValue<List<FBPermission>>, HasEnabled {
  interface PermissionsWidgetUIBinder extends UiBinder<HTMLPanel, FBPermissionListEditor> {}
  private static PermissionsWidgetUIBinder sUIBinder = GWT.create(PermissionsWidgetUIBinder.class);

  @UiField ListBox permissionsListBox;
  @UiField ScrollPanel permissionWidgets;
  @UiField FlowPanel permissionWidgetsPanel;

  private List<FBPermission> permissions;

  private ValueRemoveHandler<FBPermission> permissionRemoveHandler;

  public FBPermissionListEditor() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    permissionsListBox.addItem("Select a permission to add...", "");
    for (final FBPermissions permission : FBPermissions.values()) {
      if (permission.isRequestable()) {
        permissionsListBox.addItem(permission.name(), permission.getApiValue());
      }
    }
    permissionsListBox.setSelectedIndex(0);

    permissionRemoveHandler = new ValueRemoveHandler<FBPermission>() {
      @Override
      public void onValueRemove(final ValueRemoveEvent<FBPermission> permissionValueRemoveEvent) {
        removePermission((PermissionWidget) permissionValueRemoveEvent.getSource());
      }
    };
  }

  @Override
  public List<FBPermission> getValue() {
    return permissions;
  }

  @Override
  public void setValue(final List<FBPermission> value) {
    setValue(value, false);
  }

  @Override
  public void setValue(final List<FBPermission> value, final boolean fireEvents) {
    if (fireEvents) {
      ValueChangeEvent.fireIfNotEqual(this, permissions, value);
    }

    if (value != null) {
      permissions = new ArrayList<FBPermission>(value);
    } else {
      permissions = null;
    }

    updatePermissions();
  }

  @Override
  public boolean isEnabled() {
    return permissionsListBox.isEnabled();
  }

  @Override
  public void setEnabled(final boolean enabled) {
    permissionsListBox.setEnabled(enabled);
  }

  private void updatePermissions() {
    permissionWidgetsPanel.clear();

    if (permissions == null || permissions.isEmpty()) {
      return;
    }

    permissionWidgetsPanel.clear();

    for (final FBPermission permission : permissions) {
      addPermissionWidget(permission);
    }

    resizePermissionWidgets();
  }

  private void addPermissionWidget(final FBPermission permission) {
    PermissionWidget permissionWidget = new PermissionWidget();
    permissionWidget.setValue(permission);
    permissionWidget.addValueRemoveHandler(permissionRemoveHandler);
    permissionWidgetsPanel.add(permissionWidget);
  }

  private void addPermission(final FBPermission permission) {
    if (permissions == null) {
      permissions = new ArrayList<FBPermission>();
    }

    permissions.add(permission);

    ValueChangeEvent.fire(this, permissions);

    addPermissionWidget(permission);

    resizePermissionWidgets();
  }

  private void removePermission(final PermissionWidget permissionWidget) {
    permissions.remove(permissionWidget.getValue());

    ValueChangeEvent.fire(this, permissions);

    permissionWidgetsPanel.remove(permissionWidget);

    resizePermissionWidgets();
  }

  private void resizePermissionWidgets() {
    int numWidgetsToShow = Math.min(permissionWidgetsPanel.getWidgetCount(), 5);
    if (numWidgetsToShow > 0) {
      permissionWidgets.setHeight((numWidgetsToShow * 39 - 1) + "px");
      permissionWidgets.scrollToBottom();
    }
    permissionWidgets.setVisible(numWidgetsToShow > 0);
  }

  @UiHandler ("permissionsListBox")
  public void handleEventTypesListBoxChange(final ChangeEvent event) {
    FBPermission fbPermission = getSelectedPermission();
    if (fbPermission == null) {
      return;
    }

    addPermission(fbPermission);

    permissionsListBox.setSelectedIndex(0);
  }

  private FBPermission getSelectedPermission() {
    FBPermission fbPermission = null;
    String permissionApiValue = permissionsListBox.getValue(permissionsListBox.getSelectedIndex());
    if (permissionApiValue.length() > 0) {
      fbPermission = FBPermissions.valueFromApiValue(permissionApiValue);
    }
    return fbPermission;
  }

  @Override
  public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<List<FBPermission>> listValueChangeHandler) {
    return addHandler(listValueChangeHandler, ValueChangeEvent.getType());
  }
}