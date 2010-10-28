package com.denormans.facebookgwt.samples.client.showcase.impl;

import com.denormans.facebookgwt.api.client.FacebookGWTAPI;
import com.denormans.facebookgwt.api.client.events.init.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.events.init.FBInitSuccessHandler;
import com.denormans.facebookgwt.api.client.js.FBAuthEventResponse;
import com.denormans.facebookgwt.api.client.js.FBLoginOptions;
import com.denormans.facebookgwt.api.client.js.FBSession;
import com.denormans.facebookgwt.api.shared.FBExtendedPermission;
import com.denormans.facebookgwt.samples.client.FacebookGWTSamples;
import com.denormans.facebookgwt.samples.client.showcase.Showcase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;

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

  @UiField Button loginButton;
  @UiField Button logoutButton;

  public ShowcaseImpl() {
    DockLayoutPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    FacebookGWTAPI.get().addFBInitSuccessHandler(new FBInitSuccessHandler() {
      @Override
      public void onFBInitSuccess(final FBInitSuccessEvent event) {
        FBSession session = FacebookGWTAPI.get().getSession();
        Log.info("Session before login status: " + session.getJSONString());

        FacebookGWTAPI.get().retrieveLoginStatus(new AsyncCallback<FBAuthEventResponse>() {
          @Override
          public void onFailure(final Throwable caught) {
            FacebookGWTSamples.get().handleError("Error retrieving login status", caught);
          }

          @Override
          public void onSuccess(final FBAuthEventResponse result) {
            Log.info("Retrieved login status: " + new JSONObject(result).toString());

            updateConnectionButtons(result.isConnected());

            FBSession session = FacebookGWTAPI.get().getSession();
            Log.info("Session after login status: " + session.getJSONString());
          }
        });
      }
    });
  }

  private void updateConnectionButtons(final boolean isConnected) {
    loginButton.setEnabled(true);
    loginButton.setVisible(true);
    logoutButton.setEnabled(isConnected);
    logoutButton.setVisible(isConnected);
  }

  @UiHandler ("loginButton")
  protected void handleLoginButtonClick(final ClickEvent event) {
    FacebookGWTAPI.get().login(new AsyncCallback<FBAuthEventResponse>() {
      @Override
      public void onFailure(final Throwable caught) {
        FacebookGWTSamples.get().handleError("Error logging in", caught);
      }

      @Override
      public void onSuccess(final FBAuthEventResponse result) {
        Log.info("Login result: " + result.getJSONString());

        updateConnectionButtons(result.isConnected());
      }
    }, FBLoginOptions.create(FBExtendedPermission.Email, FBExtendedPermission.UserPhotoVideoTags));
  }

  @UiHandler ("logoutButton")
  protected void handleLogoutButtonClick(final ClickEvent event) {
    FacebookGWTAPI.get().logout(new AsyncCallback<FBAuthEventResponse>() {
      @Override
      public void onFailure(final Throwable caught) {
        FacebookGWTSamples.get().handleError("Error logging out", caught);
      }

      @Override
      public void onSuccess(final FBAuthEventResponse result) {
        Log.info("Logout result: " + result.getJSONString());

        updateConnectionButtons(result.isConnected());
      }
    });
  }
}