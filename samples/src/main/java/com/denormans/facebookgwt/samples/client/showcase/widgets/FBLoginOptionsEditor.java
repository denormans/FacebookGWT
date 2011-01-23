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

import com.denormans.facebookgwt.api.client.auth.FBLoginOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasEnabled;

public class FBLoginOptionsEditor extends Composite implements Editor<FBLoginOptions>, HasEnabled {
  interface FBLoginOptionsEditorUIBinder extends UiBinder<HTMLPanel, FBLoginOptionsEditor> {}
  private static FBLoginOptionsEditorUIBinder sUIBinder = GWT.create(FBLoginOptionsEditorUIBinder.class);

  @UiField
  FBPermissionListEditor permissionsEditor;

  private FBLoginOptions loginOptions;

  public FBLoginOptionsEditor() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);
  }

  public FBLoginOptions getLoginOptions() {
    return loginOptions;
  }

  public void setLoginOptions(final FBLoginOptions loginOptions) {
    // todo: replace with editor logic when GWT supports lists/JavaScriptObjects in editors

    this.loginOptions = loginOptions;

    permissionsEditor.setValue(this.loginOptions.getPermissions());
  }

  public FBLoginOptions flush() {
    // todo: replace with editor logic when GWT supports lists/JavaScriptObjects in editors

    loginOptions.setPermissions(permissionsEditor.getValue());

    return loginOptions;
  }

  @Override
  public boolean isEnabled() {
    return permissionsEditor.isEnabled();
  }

  @Override
  public void setEnabled(final boolean enabled) {
    permissionsEditor.setEnabled(enabled);
  }
}