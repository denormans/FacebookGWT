package com.denormans.facebookgwt.samples.client.showcase.impl;

import com.denormans.facebookgwt.api.client.FBGWT;
import com.denormans.facebookgwt.api.client.auth.events.FBLogoutEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBLogoutHandler;
import com.denormans.facebookgwt.api.client.auth.events.FBSessionChangeEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBSessionChangeHandler;
import com.denormans.facebookgwt.api.client.auth.events.FBStatusChangeEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBStatusChangeHandler;
import com.denormans.facebookgwt.api.client.auth.js.FBAuthEventResponse;
import com.denormans.facebookgwt.api.client.auth.js.FBLoginOptions;
import com.denormans.facebookgwt.api.client.common.events.FBEvent;
import com.denormans.facebookgwt.api.client.common.js.Attachment;
import com.denormans.facebookgwt.api.client.common.js.Link;
import com.denormans.facebookgwt.api.client.core.events.FBLogEvent;
import com.denormans.facebookgwt.api.client.common.events.FBLogHandler;
import com.denormans.facebookgwt.api.client.auth.events.FBLoginEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBLoginHandler;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessHandler;
import com.denormans.facebookgwt.api.client.ui.FBUserInterface;
import com.denormans.facebookgwt.api.client.ui.events.FBAddCommentEvent;
import com.denormans.facebookgwt.api.client.ui.events.FBAddCommentHandler;
import com.denormans.facebookgwt.api.client.ui.events.FBEdgeCreateEvent;
import com.denormans.facebookgwt.api.client.ui.events.FBEdgeCreateHandler;
import com.denormans.facebookgwt.api.client.ui.events.XFBMLRenderEvent;
import com.denormans.facebookgwt.api.client.ui.events.XFBMLRenderHandler;
import com.denormans.facebookgwt.api.client.init.js.FBInitOptions;
import com.denormans.facebookgwt.api.client.auth.js.FBSession;
import com.denormans.facebookgwt.api.client.ui.js.StreamPublishCallbackResponse;
import com.denormans.facebookgwt.api.client.ui.js.StreamPublishOptions;
import com.denormans.facebookgwt.api.client.ui.widgets.Like;
import com.denormans.facebookgwt.api.shared.auth.FBPermission;
import com.denormans.facebookgwt.samples.client.FacebookGWTSamples;
import com.denormans.facebookgwt.samples.client.showcase.Showcase;
import com.denormans.gwtutil.client.js.EnhancedJSObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class ShowcaseImpl extends Composite implements Showcase {
  private static final Logger Log = Logger.getLogger(ShowcaseImpl.class.getName());

  interface ShowcaseUIBinder extends UiBinder<DockLayoutPanel, ShowcaseImpl> {}
  private static ShowcaseUIBinder sUIBinder = GWT.create(ShowcaseUIBinder.class);

  @UiField DivElement initializationSection;
  @UiField Button initButton;

  @UiField DivElement eventHandlersSection;
  @UiField Button resetEventHandlersButton;
  @UiField Button removeEventHandlersButton;

  @UiField DivElement authenticationSection;
  @UiField Button loginButton;
  @UiField Button logoutButton;

  @UiField DivElement widgetsSection;
  @UiField Button parseXFBMLButton;
  @UiField Button streamPublishButton;
  @UiField Like fbLike;

  @UiField ScrollPanel eventContainer;
  @UiField FlowPanel eventPanel;

  private HandlerRegistration initSuccessHandlerRegistration;
  private List<HandlerRegistration> eventHandlerRegistrations = new ArrayList<HandlerRegistration>();

  public ShowcaseImpl() {
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

    resetEventHandlers();
    updateButtonsOnInitialization();

    FBSession session = FBGWT.Auth.getSession();
    Log.info("Session before login status: " + session.getJSONString());

    FBGWT.Auth.retrieveLoginStatus(new AsyncCallback<FBAuthEventResponse>() {
      @Override
      public void onFailure(final Throwable caught) {
        FacebookGWTSamples.get().handleError("Error retrieving login status", caught);
      }

      @Override
      public void onSuccess(final FBAuthEventResponse result) {
        addApiEventMessage("Retrieve Login Status result", result);

        Log.info("Permissions: " + result.getPermissions());

        updateConnectionButtons(result.isConnected());

        FBSession session = FBGWT.Auth.getSession();
        Log.info("Session after login status: " + session.getJSONString());
      }
    });
  }

  private void updateButtonsOnInitialization() {
    boolean isInitialized = FBGWT.Init.isInitialized();
    resetEventHandlersButton.setEnabled(isInitialized);
    removeEventHandlersButton.setEnabled(isInitialized);
    loginButton.setEnabled(isInitialized);
    parseXFBMLButton.setEnabled(isInitialized);
    streamPublishButton.setEnabled(isInitialized);
  }

  private void removeEventHandlers() {
    for (final HandlerRegistration handlerRegistration : eventHandlerRegistrations) {
      handlerRegistration.removeHandler();
    }
    eventHandlerRegistrations.clear();
  }

  private void resetEventHandlers() {
    removeEventHandlers();

    eventHandlerRegistrations.add(FBGWT.Core.addFBLogHandler(new FBLogHandler() {
      @Override
      public void onFBStub(final FBLogEvent event) {
        handleFBLogEvent(event);
      }
    }));

    eventHandlerRegistrations.add(FBGWT.Auth.addFBLoginHandler(new FBLoginHandler() {
      @Override
      public void onFBLogin(final FBLoginEvent event) {
        handleLogin(event);
      }
    }));

    eventHandlerRegistrations.add(FBGWT.Auth.addFBLogoutHandler(new FBLogoutHandler() {
      @Override
      public void onFBLogout(final FBLogoutEvent event) {
        handleLogout(event);
      }
    }));

    eventHandlerRegistrations.add(FBGWT.Auth.addFBSessionChangeHandler(new FBSessionChangeHandler() {
      @Override
      public void onFBSessionChange(final FBSessionChangeEvent event) {
        handleSessionChange(event);
      }
    }));

    eventHandlerRegistrations.add(FBGWT.Auth.addFBStatusChangeHandler(new FBStatusChangeHandler() {
      @Override
      public void onFBStatusChange(final FBStatusChangeEvent event) {
        handleStatusChange(event);
      }
    }));

    eventHandlerRegistrations.add(FBGWT.UI.addFBAddCommentHandler(new FBAddCommentHandler() {
      @Override
      public void onFBAddComment(final FBAddCommentEvent event) {
        handleAddComment(event);
      }
    }));

    eventHandlerRegistrations.add(FBGWT.UI.addFBEdgeCreateHandler(new FBEdgeCreateHandler() {
      @Override
      public void onFBEdgeCreate(final FBEdgeCreateEvent event) {
        handleEdgeCreate(event);
      }
    }));

    eventHandlerRegistrations.add(FBGWT.UI.addXFBMLRenderHandler(new XFBMLRenderHandler() {
      @Override
      public void onXFBMLRender(final XFBMLRenderEvent event) {
        handleXFBMLRender(event);
      }
    }));
  }

  public void handleFBLogEvent(final FBLogEvent event) {
    addApiEventMessage("Log Event", event);
  }

  public void handleLogin(final FBLoginEvent event) {
    addApiEventMessage("Login Event", event);
  }

  public void handleLogout(final FBLogoutEvent event) {
    addApiEventMessage("Logout Event", event);
  }

  private void handleSessionChange(final FBSessionChangeEvent event) {
    addApiEventMessage("Session Change Event", event);
  }

  private void handleStatusChange(final FBStatusChangeEvent event) {
    addApiEventMessage("Status Change Event", event);
  }

  private void handleAddComment(final FBAddCommentEvent event) {
    addApiEventMessage("Add Comment Event", event);
  }

  private void handleEdgeCreate(final FBEdgeCreateEvent event) {
    addApiEventMessage("Edge Create Event", event);
  }

  private void handleXFBMLRender(final XFBMLRenderEvent event) {
    addApiEventMessage("XFBML Render Event", event);
  }

  private void addApiEventMessage(final String title, final FBEvent<?, ?> event) {
    addApiEventMessage(title, event.getApiResponse());
  }

  private void addApiEventMessage(final String title, final EnhancedJSObject apiObject) {
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

  private void updateConnectionButtons(final boolean isConnected) {
    boolean isInitialized = FBGWT.Init.isInitialized();
    logoutButton.setEnabled(isInitialized && isConnected);
  }

  @UiHandler ("initButton")
  public void handleInitButtonClick(final ClickEvent event) {
    FBGWT.Init.initialize(FBInitOptions.createInitOptions(FacebookGWTSamples.SamplesFacebookApplicationID, false));
  }

  @UiHandler ("resetEventHandlersButton")
  public void handleResetEventHandlersClick(final ClickEvent event) {
    resetEventHandlers();
  }

  @UiHandler ("removeEventHandlersButton")
  public void handleRemoveEventHandlersClick(final ClickEvent event) {
    removeEventHandlers();
  }

  @UiHandler ("loginButton")
  protected void handleLoginButtonClick(final ClickEvent event) {
    FBGWT.Auth.login(new AsyncCallback<FBAuthEventResponse>() {
      @Override
      public void onFailure(final Throwable caught) {
        FacebookGWTSamples.get().handleError("Error logging in", caught);
      }

      @Override
      public void onSuccess(final FBAuthEventResponse result) {
        addApiEventMessage("Login result", result);

        Log.info("Permissions: " + result.getPermissions());

        updateConnectionButtons(result.isConnected());
      }
    }, FBLoginOptions.createLoginOptions(FBPermission.Email, FBPermission.UserPhotoVideoTags));
  }

  @UiHandler ("logoutButton")
  protected void handleLogoutButtonClick(final ClickEvent event) {
    FBGWT.Auth.logout(new AsyncCallback<FBAuthEventResponse>() {
      @Override
      public void onFailure(final Throwable caught) {
        FacebookGWTSamples.get().handleError("Error logging out", caught);
      }

      @Override
      public void onSuccess(final FBAuthEventResponse result) {
        addApiEventMessage("Logout result", result);

        updateConnectionButtons(result.isConnected());
      }
    });
  }

  @UiHandler ("parseXFBMLButton")
  public void handleParseXFBMLClick(final ClickEvent event) {
    FBGWT.UI.parseXFBML(widgetsSection);
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