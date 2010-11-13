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
import com.denormans.facebookgwt.api.client.common.js.Attachment;
import com.denormans.facebookgwt.api.client.common.js.Link;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessHandler;
import com.denormans.facebookgwt.api.client.ui.js.StreamPublishCallbackResponse;
import com.denormans.facebookgwt.api.client.ui.js.StreamPublishOptions;
import com.denormans.facebookgwt.api.client.ui.widgets.Like;
import com.denormans.facebookgwt.api.shared.ui.DisplayFormats;
import com.denormans.facebookgwt.samples.client.FacebookGWTSamples;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;

import java.util.logging.Logger;

public class FBWidgetsWidget extends ShowcaseWidget {
  private static final Logger Log = Logger.getLogger(FBWidgetsWidget.class.getName());

  interface FBWidgetsWidgetUIBinder extends UiBinder<HTMLPanel, FBWidgetsWidget> {}
  private static FBWidgetsWidgetUIBinder sUIBinder = GWT.create(FBWidgetsWidgetUIBinder.class);

  @UiField Button parseXFBMLButton;
  @UiField Button streamPublishButton;
  @UiField Like fbLike;

  public FBWidgetsWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    FBGWT.Init.addFBInitSuccessHandler(new FBInitSuccessHandler() {
      @Override
      public void onFBInitSuccess(final FBInitSuccessEvent event) {
        parseXFBMLButton.setEnabled(FBGWT.Init.isInitialized());
        streamPublishButton.setEnabled(FBGWT.Init.isInitialized());
      }
    });
  }

  @UiHandler ("parseXFBMLButton")
  public void handleParseXFBMLClick(final ClickEvent event) {
    FBGWT.UI.parseXFBML(getWidget());
  }

  @UiHandler ("streamPublishButton")
  public void handleStreamPublishClick(final ClickEvent event) {
    StreamPublishOptions streamPublishOptions =
        StreamPublishOptions.createStreamPublishOptions()
            .setMessage("Learning about FacebookGWT")
            .setAttachment(Attachment.createAttachment().setName("FacebookGWT").setCaption("Facebook API for GWT").setDescription("Facebook GWT is a Java API of the Facebook JavaScript API for use with Google Web Toolkit.").setHref("http://denormans.github.com/FacebookGWT/"))
            .setActionLinks(Link.createLink("Code", "https://github.com/denormans/FacebookGWT"), Link.createLink("Issues", "https://github.com/denormans/FacebookGWT/issues"))
            .setUserMessagePrompt("Share your thoughts about FacebookGWT");

    Log.info("Stream publish options: " + streamPublishOptions.getJSONString());

    FBGWT.UI.publishToStream(streamPublishOptions, DisplayFormats.Dialog, new AsyncCallback<StreamPublishCallbackResponse>() {
      @Override
      public void onFailure(final Throwable caught) {
        FacebookGWTSamples.get().handleError("Error publishing to stream", caught);
      }

      @Override
      public void onSuccess(final StreamPublishCallbackResponse result) {
        addApiEventMessage("Stream Publish result", result);
      }
    });
  }
}