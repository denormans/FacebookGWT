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
import com.denormans.facebookgwt.api.client.init.js.FBInitOptions;
import com.denormans.facebookgwt.samples.client.FacebookGWTSamples;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;

public class InitializationWidget extends ShowcaseWidget {
  interface InitializationWidgetUIBinder extends UiBinder<HTMLPanel, InitializationWidget> {}
  private static InitializationWidgetUIBinder sUIBinder = GWT.create(InitializationWidgetUIBinder.class);

//  interface FBInitOptionsEditorDriver extends SimpleBeanEditorDriver<FBInitOptions, FBInitOptionsEditor> {}
//  static FBInitOptionsEditorDriver sFBInitOptionsEditorDriver = GWT.create(FBInitOptionsEditorDriver.class);

  @UiField FBInitOptionsEditor initOptionsEditor;
  @UiField Button initButton;

  public InitializationWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    FBInitOptions initOptions = FBInitOptions.createInitOptions(FacebookGWTSamples.SamplesFacebookApplicationID).
        setEnableCookieSupport(false).
        setEnableLogging(true).
        setFetchStatus(true).
        setParseXFBMLTags(false);

    initOptionsEditor.setInitOptions(initOptions);
  }

  @UiHandler ("initButton")
  public void handleInitButtonClick(final ClickEvent event) {
    initOptionsEditor.flush();
    FBInitOptions initOptions = initOptionsEditor.getInitOptions();
    FBGWT.Init.initialize(initOptions);
  }
}