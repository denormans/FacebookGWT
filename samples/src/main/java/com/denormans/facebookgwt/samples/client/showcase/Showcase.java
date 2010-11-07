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

package com.denormans.facebookgwt.samples.client.showcase;

import com.denormans.facebookgwt.api.client.FBGWT;
import com.denormans.facebookgwt.api.client.common.events.FBEvent;
import com.denormans.facebookgwt.api.client.common.js.Attachment;
import com.denormans.facebookgwt.api.client.common.js.Link;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessHandler;
import com.denormans.facebookgwt.api.client.ui.FBUserInterface;
import com.denormans.facebookgwt.api.client.ui.js.StreamPublishCallbackResponse;
import com.denormans.facebookgwt.api.client.ui.js.StreamPublishOptions;
import com.denormans.facebookgwt.api.client.ui.widgets.Like;
import com.denormans.facebookgwt.samples.client.FacebookGWTSamples;
import com.denormans.facebookgwt.samples.client.showcase.widgets.AuthenticationWidget;
import com.denormans.facebookgwt.samples.client.showcase.widgets.EventsWidget;
import com.denormans.facebookgwt.samples.client.showcase.widgets.InitializationWidget;
import com.denormans.gwtutil.client.js.EnhancedJSObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Showcase extends Composite {
  private static final Logger Log = Logger.getLogger(Showcase.class.getName());

  interface ShowcaseUIBinder extends UiBinder<DockLayoutPanel, Showcase> {}
  private static ShowcaseUIBinder sUIBinder = GWT.create(ShowcaseUIBinder.class);

  @UiField
  InitializationWidget initWidget;
  @UiField EventsWidget eventsWidget;

  @UiField AuthenticationWidget authWidget;

  @UiField ScrollPanel widgets;
  @UiField Button parseXFBMLButton;
  @UiField Button streamPublishButton;
  @UiField Like fbLike;

  @UiField ScrollPanel eventContainer;
  @UiField FlowPanel eventPanel;

  private HandlerRegistration initSuccessHandlerRegistration;

  public Showcase() {
    DockLayoutPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    initSuccessHandlerRegistration = FBGWT.Init.addFBInitSuccessHandler(new FBInitSuccessHandler() {
      @Override
      public void onFBInitSuccess(final FBInitSuccessEvent event) {
        handleFacebookInitialized(event);

        initSuccessHandlerRegistration.removeHandler();
      }
    });
  }

  private void handleFacebookInitialized(final FBInitSuccessEvent event) {
    addEventMessage("Facebook loaded");

    updateButtonsOnInitialization();
  }

  private void updateButtonsOnInitialization() {
    parseXFBMLButton.setEnabled(FBGWT.Init.isInitialized());
    streamPublishButton.setEnabled(FBGWT.Init.isInitialized());
  }

  public void addApiEventMessage(final String title, final FBEvent<?, ?> event) {
    addApiEventMessage(title, event.getApiResponse());
  }

  public void addApiEventMessage(final String title, final EnhancedJSObject apiObject) {
    if (Log.isLoggable(Level.FINE)) {
      Log.fine(title + ": " + apiObject.getJSONString());
    }

    addEventMessage(title, apiObject.getJSONString());
  }

  public interface MyTemplates extends SafeHtmlTemplates {
    @Template("<span><span class='FBGWTTitle'>{0}</span><span class='FBGWTEvent'>{1}</span></span>")
    SafeHtml eventMessage(final String message, final String eventText);
  }

  private MyTemplates EventMessageTemplates = GWT.create(MyTemplates.class);

  private void addEventMessage(final String message) {
    addEventMessage(new HTML(SafeHtmlUtils.fromString(message)));
  }

  private void addEventMessage(final String title, final String event) {
    addEventMessage(new HTML(EventMessageTemplates.eventMessage(title, event)));
  }

  private void addEventMessage(final HTML html) {
    html.setStyleName("FBGWTEventMessage");
    eventPanel.add(html);
    eventContainer.scrollToBottom();
  }

  @UiHandler ("parseXFBMLButton")
  public void handleParseXFBMLClick(final ClickEvent event) {
    FBGWT.UI.parseXFBML(widgets);
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

    FBGWT.UI.publishToStream(streamPublishOptions, FBUserInterface.DisplayFormat.Dialog, new AsyncCallback<StreamPublishCallbackResponse>() {
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