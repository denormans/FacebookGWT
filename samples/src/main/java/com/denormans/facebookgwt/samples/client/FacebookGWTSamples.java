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
import com.denormans.facebookgwt.api.client.events.FacebookInitFailureEvent;
import com.denormans.facebookgwt.api.client.events.FacebookInitFailureHandler;
import com.denormans.facebookgwt.api.client.events.FacebookInitSuccessEvent;
import com.denormans.facebookgwt.api.client.events.FacebookInitSuccessHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FacebookGWTSamples implements EntryPoint {
  private static final Logger Log = Logger.getLogger(FacebookGWTSamples.class.getName());

  private static FacebookGWTSamples sInstance;

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

    RootPanel.get("FBGWTLoadingTextID").setVisible(false);
    RootPanel.get().add(new HTML("Module Loaded"));

    FacebookGWTAPI.get().addFacebookInitFailureHandler(new FacebookInitFailureHandler() {
      @Override
      public void onFacebookInitFailure(final FacebookInitFailureEvent event) {
        handleError("Facebook failed to load");
      }
    });
    FacebookGWTAPI.get().initialize();

    FacebookGWTAPI.get().addFacebookInitSuccessHandler(new FacebookInitSuccessHandler() {
      @Override
      public void onFacebookInitialized(final FacebookInitSuccessEvent event) {
        RootPanel.get().add(new HTML("Facebook Loaded"));
        Log.info("Facebook loaded");
      }
    });

    Log.info("FacebookGWTSamples Module loaded");
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
