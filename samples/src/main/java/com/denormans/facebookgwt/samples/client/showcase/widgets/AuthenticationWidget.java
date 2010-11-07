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
import com.denormans.facebookgwt.api.client.auth.js.FBAuthEventResponse;
import com.denormans.facebookgwt.api.client.auth.js.FBLoginOptions;
import com.denormans.facebookgwt.api.client.auth.js.FBSession;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessHandler;
import com.denormans.facebookgwt.api.shared.auth.FBPermission;
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

public class AuthenticationWidget extends ShowcaseWidget {
  private static final Logger Log = Logger.getLogger(AuthenticationWidget.class.getName());

  interface AuthenticationWidgetUIBinder extends UiBinder<HTMLPanel, AuthenticationWidget> {}
  private static AuthenticationWidgetUIBinder sUIBinder = GWT.create(AuthenticationWidgetUIBinder.class);

  @UiField Button checkStatusButton;
  @UiField Button getSessionButton;
  @UiField Button loginButton;
  @UiField Button logoutButton;

  public AuthenticationWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    FBGWT.Init.addFBInitSuccessHandler(new FBInitSuccessHandler() {
      @Override
      public void onFBInitSuccess(final FBInitSuccessEvent event) {
        checkStatusButton.setEnabled(FBGWT.Init.isInitialized());
        getSessionButton.setEnabled(FBGWT.Init.isInitialized());
        loginButton.setEnabled(FBGWT.Init.isInitialized());
      }
    });
  }

  private void updateConnectionButtons(final boolean isConnected) {
    boolean isInitialized = FBGWT.Init.isInitialized();
    logoutButton.setEnabled(isInitialized && isConnected);
  }

  @UiHandler ("checkStatusButton")
  public void handleCheckStatusButtonClick(final ClickEvent event) {
    FBGWT.Auth.retrieveLoginStatus(new AsyncCallback<FBAuthEventResponse>() {
      @Override
      public void onFailure(final Throwable caught) {
        FacebookGWTSamples.get().handleError("Error retrieving login status", caught);
      }

      @Override
      public void onSuccess(final FBAuthEventResponse result) {
        FacebookGWTSamples.get().getShowcase().addApiEventMessage("Retrieve Login Status result", result);

        updateConnectionButtons(result.isConnected());
      }
    });
  }

  @UiHandler ("getSessionButton")
  public void handleGetSessionButtonClick(final ClickEvent event) {
    FBSession session = FBGWT.Auth.getSession();
    FacebookGWTSamples.get().getShowcase().addApiEventMessage("Get Session result", session);
  }

  @UiHandler ("loginButton")
  protected void handleLoginButtonClick(final ClickEvent event) {
    FBLoginOptions loginOptions = FBLoginOptions.createLoginOptions(FBPermission.Email, FBPermission.UserPhotoVideoTags);

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
    }, loginOptions);
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

}