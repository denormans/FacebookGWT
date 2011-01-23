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
import com.denormans.facebookgwt.api.client.common.events.FBEventResponse;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessHandler;
import com.denormans.facebookgwt.api.client.legacy.FBLegacyMethodOptions;
import com.denormans.facebookgwt.api.shared.legacy.LegacyMethods;
import com.denormans.gwtutil.client.js.EnhancedJSObject;
import com.denormans.gwtutil.client.js.GenericJSObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;

public class LegacyWidget extends ShowcaseWidget {
  interface LegacyWidgetUIBinder extends UiBinder<HTMLPanel, LegacyWidget> {}

  private static LegacyWidgetUIBinder sUIBinder = GWT.create(LegacyWidgetUIBinder.class);

  @UiField Button linkStatsButton;

  public LegacyWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    FBGWT.Init.addFBInitSuccessHandler(new FBInitSuccessHandler() {
      @Override
      public void onFBInitSuccess(final FBInitSuccessEvent event) {
        linkStatsButton.setEnabled(true); 
      }
    });
  }

  @UiHandler ("linkStatsButton")
  public void handleLinkStatsButtonClick(final ClickEvent event) {
    FBLegacyMethodOptions linkStatsOptions = GenericJSObject.createGenericJSObject().setProperty("urls", "facebook.com,developers.facebook.com").cast();

    FBGWT.Legacy.executeLegacyMethod(LegacyMethods.LinksGetStats, linkStatsOptions, new AsyncCallback<JsArray<FBEventResponse>>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error getting link stats", caught);
      }

      @Override
      public void onSuccess(final JsArray<FBEventResponse> result) {
        String description = new JSONArray(result).toString();
        addApiEventMessage("Links Get Stats result", description);
        setItemDisplay("Link Stats", EnhancedJSObject.convertJsArrayToList(result));
      }
    });
  }
}