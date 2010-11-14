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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;

public class CanvasWidget extends ShowcaseWidget {
  interface CanvasWidgetUIBinder extends UiBinder<HTMLPanel, CanvasWidget> {}
  private static CanvasWidgetUIBinder sUIBinder = GWT.create(CanvasWidgetUIBinder.class);
  @UiField Button enableAutoResizeButton;
  @UiField Button disableAutoResizeButton;
  @UiField Button setSizeButton;

  public CanvasWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    FBGWT.Init.addFBInitSuccessHandler(new FBInitSuccessHandler() {
      @Override
      public void onFBInitSuccess(final FBInitSuccessEvent event) {
        enableAutoResizeButton.setEnabled(FBGWT.Init.isInitialized());
        disableAutoResizeButton.setEnabled(FBGWT.Init.isInitialized());
        setSizeButton.setEnabled(FBGWT.Init.isInitialized());
      }
    });
  }

  @UiHandler ("enableAutoResizeButton")
  public void handleEnableAutoResizeClick(final ClickEvent event) {
    FBGWT.UI.enableCanvasAutoResize();
  }

  @UiHandler ("disableAutoResizeButton")
  public void handleDisableAutoResizeClick(final ClickEvent event) {
    FBGWT.UI.disableCanvasAutoResize();
  }

  @UiHandler ("setSizeButton")
  public void handleSetSizeClick(final ClickEvent event) {
    FBGWT.UI.setCanvasSize();
  }
}