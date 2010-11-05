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

import com.denormans.facebookgwt.api.client.init.js.FBInitOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;


public class FBInitOptionsEditor extends Composite implements Editor<FBInitOptions> {
  interface FBInitOptionsEditorUIBinder extends UiBinder<HTMLPanel, FBInitOptionsEditor> {}
  private static FBInitOptionsEditorUIBinder sUIBinder = GWT.create(FBInitOptionsEditorUIBinder.class);

  @UiField TextBox applicationIDEditor;
  @UiField CheckBox enableCookieSupportEditor;
  @UiField CheckBox enableLoggingEditor;
  @UiField CheckBox fetchStatusEditor;
  @UiField CheckBox parseXFBMLTagsEditor;

  private FBInitOptions initOptions;

  public FBInitOptionsEditor() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);
  }

  public FBInitOptions getInitOptions() {
    return initOptions;
  }

  public void setInitOptions(final FBInitOptions initOptions) {
    // todo: replace with editor logic once GWT supports primitives (booleans in this case)

    this.initOptions = initOptions;

    applicationIDEditor.setText(initOptions.getApplicationID());
    enableCookieSupportEditor.setValue(initOptions.getEnableCookieSupport());
    enableLoggingEditor.setValue(initOptions.getEnableLogging());
    fetchStatusEditor.setValue(initOptions.getFetchStatus());
    parseXFBMLTagsEditor.setValue(initOptions.getParseXFBMLTags());
  }

  public void flush() {
    initOptions.setApplicationID(applicationIDEditor.getText());
    initOptions.setEnableCookieSupport(enableCookieSupportEditor.getValue());
    initOptions.setEnableLogging(enableLoggingEditor.getValue());
    initOptions.setFetchStatus(fetchStatusEditor.getValue());
    initOptions.setParseXFBMLTags(parseXFBMLTagsEditor.getValue());
  }
}