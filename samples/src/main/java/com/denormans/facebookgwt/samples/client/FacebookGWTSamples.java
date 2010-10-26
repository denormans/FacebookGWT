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

package com.denormans.facebookgwt.samples.client;

import com.denormans.facebookgwt.api.client.FacebookGWTAPI;
import com.denormans.facebookgwt.api.client.events.auth.FBLoginEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBLoginHandler;
import com.denormans.facebookgwt.api.client.events.auth.FBLogoutEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBLogoutHandler;
import com.denormans.facebookgwt.api.client.events.auth.FBSessionChangeEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBSessionChangeHandler;
import com.denormans.facebookgwt.api.client.events.auth.FBStatusChangeEvent;
import com.denormans.facebookgwt.api.client.events.auth.FBStatusChangeHandler;
import com.denormans.facebookgwt.api.client.events.init.FBInitFailureEvent;
import com.denormans.facebookgwt.api.client.events.init.FBInitFailureHandler;
import com.denormans.facebookgwt.api.client.events.init.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.events.init.FBInitSuccessHandler;
import com.denormans.facebookgwt.api.client.js.FBAuthEventResponse;
import com.denormans.facebookgwt.api.client.js.FBInitOptions;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FacebookGWTSamples implements EntryPoint {
  private static final Logger Log = Logger.getLogger(FacebookGWTSamples.class.getName());

  private static final String SamplesFacebookApplicationID = "160704113964450";

  private static FacebookGWTSamples sInstance;

  private HandlerRegistration initFailureHandlerRegistration;
  private HandlerRegistration initSuccessHandlerRegistration;

  public void onModuleLoad() {
    if (sInstance != null) {
      return;
    }

    sInstance = this;

    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(final Throwable t) {
        handleError("An unknown error occurred", t);
      }
    });

    initFailureHandlerRegistration = FacebookGWTAPI.get().addFBInitFailureHandler(new FBInitFailureHandler() {
      @Override
      public void onFBInitFailure(final FBInitFailureEvent event) {
        handleError("Facebook failed to load");
      }
    });

    initSuccessHandlerRegistration = FacebookGWTAPI.get().addFBInitSuccessHandler(new FBInitSuccessHandler() {
      @Override
      public void onFBInitSuccess(final FBInitSuccessEvent event) {
        handleFacebookInitialized();

        // Don't want to do this twice
        initFailureHandlerRegistration.removeHandler();
        initSuccessHandlerRegistration.removeHandler();
      }
    });

    FacebookGWTAPI.get().initialize(FBInitOptions.create(SamplesFacebookApplicationID));

    handleModuleLoaded();

    Log.info("FacebookGWTSamples Module loaded");
  }

  private void handleModuleLoaded() {
    RootPanel.get("FBGWTLoadingTextID").setVisible(false);
    RootPanel.get().add(new HTML("Module Loaded"));
  }

  private void handleFacebookInitialized() {
    RootPanel.get().add(new HTML("Facebook Loaded"));
    Log.info("Facebook loaded");

    FacebookGWTAPI.get().addFBLoginHandler(new FBLoginHandler() {
      @Override
      public void onFBLogin(final FBLoginEvent event) {
        Log.info("Login event");
      }
    });

    FacebookGWTAPI.get().addFBLogoutHandler(new FBLogoutHandler() {
      @Override
      public void onFBLogout(final FBLogoutEvent event) {
        Log.info("Logout event");
      }
    });

    FacebookGWTAPI.get().addFBSessionChangeHandler(new FBSessionChangeHandler() {
      @Override
      public void onFBSessionChange(final FBSessionChangeEvent event) {
        Log.info("Session change event");
      }
    });

    FacebookGWTAPI.get().addFBStatusChangeHandler(new FBStatusChangeHandler() {
      @Override
      public void onFBStatusChange(final FBStatusChangeEvent event) {
        Log.info("Status change event");
      }
    });

    FacebookGWTAPI.get().retrieveLoginStatus(new AsyncCallback<FBAuthEventResponse>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error retrieving login status", caught);
      }

      @Override
      public void onSuccess(final FBAuthEventResponse result) {
        Log.info("Retrieved login status: " + new JSONObject(result).toString());
      }
    });
  }

  public void handleError(final String message) {
    handleError(message, null);
  }

  public void handleError(final String message, final Throwable t) {
    if (t != null) {
      Log.log(Level.SEVERE, message, t);
    } else {
      Log.log(Level.SEVERE, message);
    }
  }

  public static FacebookGWTSamples get() {
    if (sInstance == null) {
      throw new IllegalStateException("Module not loaded");
    }

    return sInstance;
  }
}
