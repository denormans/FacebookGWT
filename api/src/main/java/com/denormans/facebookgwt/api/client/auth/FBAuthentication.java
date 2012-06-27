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

package com.denormans.facebookgwt.api.client.auth;

import com.denormans.facebookgwt.api.client.FBIntegration;
import com.denormans.facebookgwt.api.client.auth.events.FBAuthEventResponse;
import com.denormans.facebookgwt.api.client.auth.events.FBLoginEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBLoginHandler;
import com.denormans.facebookgwt.api.client.auth.events.FBLogoutEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBLogoutHandler;
import com.denormans.facebookgwt.api.client.auth.events.FBSessionChangeEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBSessionChangeHandler;
import com.denormans.facebookgwt.api.client.auth.events.FBStatusChangeEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBStatusChangeHandler;
import com.denormans.facebookgwt.api.client.auth.events.HasFBAuthHandlers;
import com.denormans.facebookgwt.api.shared.common.events.FBEventTypes;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;

public final class FBAuthentication extends FBIntegration implements HasFBAuthHandlers {
  /**
   * Retrieves the login status asynchronously via the Facebook JSAPI.
   * <p>
   * Doesn't force the status to reload
   *
   * @param callback The callback to receive the results
   */
  public void retrieveLoginStatus(final AsyncCallback<FBAuthEventResponse> callback) {
    retrieveLoginStatus(false, callback);
  }

  /**
   * Retrieves the login status asynchronously via the Facebook JSAPI.
   *
   * @param forceReload Whether or not to force reloading the login status
   * @param callback The callback to receive the results
   */
  public void retrieveLoginStatus(final boolean forceReload, final AsyncCallback<FBAuthEventResponse> callback) {
    executeWithFB(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        retrieveLoginStatusJS(forceReload, callback);
      }
    });
  }

  private native void retrieveLoginStatusJS(final boolean forceReload, final AsyncCallback<FBAuthEventResponse> callback) /*-{
    try {
      var self = this;
      $wnd.FB.getLoginStatus(function(response) {
        if (response != null && response.error_code) {
          self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallbackError(Lcom/google/gwt/user/client/rpc/AsyncCallback;ILjava/lang/String;)(callback, response.error_code, response.error_msg);
          return;
        }
        self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallback(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/Object;)(callback, response);
      }, forceReload);
    } catch(e) {
      var ex = @com.denormans.facebookgwt.gwtutil.client.js.JSError::createException(Ljava/lang/Object;)(e);
      callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
    }
  }-*/;

  /**
   * Gets the auth response synchronously.  If Facebook is not initialized, this will return an empty object.
   *
   * @return The Facebook auth response
   */
  public native FBAuthResponse getAuthResponse() /*-{
    return $wnd.FB != null ? $wnd.FB.getAuthResponse() : {};
  }-*/;

  /**
   * Login to facebook
   *
   * @param callback Called after login
   */
  public void login(final AsyncCallback<FBAuthEventResponse> callback) {
    login(null, callback);
  }

  /**
   * Login to facebook with options.
   *
   * @param loginOptions Any options (e.g. permissions) to be used during login
   * @param callback Called after login
   */
  public void login(final FBLoginOptions loginOptions, final AsyncCallback<FBAuthEventResponse> callback) {
    executeWithFB(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        loginJS(loginOptions, callback);
      }
    });
  }

  private native void loginJS(final FBLoginOptions loginOptions, final AsyncCallback<FBAuthEventResponse> callback) /*-{
    try {
      var self = this;
      $wnd.FB.login(function(response) {
        if (callback != null) {
          if (response != null && response.error_code) {
            self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallbackError(Lcom/google/gwt/user/client/rpc/AsyncCallback;ILjava/lang/String;)(callback, response.error_code, response.error_msg);
            return;
          }
          self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallback(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/Object;)(callback, response);
        }
      }, loginOptions);
    } catch(e) {
      if (callback != null) {
        var ex = @com.denormans.facebookgwt.gwtutil.client.js.JSError::createException(Ljava/lang/Object;)(e);
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
      } else {
        throw e;
      }
    }
  }-*/;

  /**
   * Logout of Facebook.
   *
   * @param callback Called after logout
   */
  public void logout(final AsyncCallback<FBAuthEventResponse> callback) {
    executeWithFB(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        logoutJS(callback);
      }
    });
  }

  private native void logoutJS(final AsyncCallback<FBAuthEventResponse> callback) /*-{
    try {
      var self = this;
      $wnd.FB.logout(function(response) {
        if (callback != null) {
          if (response != null && response.error_code) {
            self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallbackError(Lcom/google/gwt/user/client/rpc/AsyncCallback;ILjava/lang/String;)(callback, response.error_code, response.error_msg);
            return;
          }
          self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallback(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/Object;)(callback, response);
        }
      });
    } catch(e) {
      if (callback != null) {
        var ex = @com.denormans.facebookgwt.gwtutil.client.js.JSError::createException(Ljava/lang/Object;)(e);
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
      } else {
        throw e;
      }
    }
  }-*/;

  @Override
  protected void handleFBEvent(final FBEventTypes eventType, final Object apiResponse) {
    switch (eventType) {
      case AuthLogin:
        fireLogin(((JavaScriptObject) apiResponse).<FBAuthEventResponse>cast());
        break;

      case AuthLogout:
        fireLogout(((JavaScriptObject) apiResponse).<FBAuthEventResponse>cast());
        break;

      case AuthSessionChange:
        fireSessionChange(((JavaScriptObject) apiResponse).<FBAuthEventResponse>cast());
        break;

      case AuthStatusChange:
        fireStatusChange(((JavaScriptObject) apiResponse).<FBAuthEventResponse>cast());
        break;

      default:
        super.handleFBEvent(eventType, apiResponse);
    }
  }

  protected void fireLogin(final FBAuthEventResponse apiResponse) {
    FBLoginEvent.fire(this, apiResponse);
  }

  protected void fireLogout(final FBAuthEventResponse apiResponse) {
    FBLogoutEvent.fire(this, apiResponse);
  }

  protected void fireSessionChange(final FBAuthEventResponse apiResponse) {
    FBSessionChangeEvent.fire(this, apiResponse);
  }

  protected void fireStatusChange(final FBAuthEventResponse apiResponse) {
    FBStatusChangeEvent.fire(this, apiResponse);
  }

  @Override
  public HandlerRegistration addFBLoginHandler(final FBLoginHandler handler) {
    return addFBEventHandler(handler, FBLoginEvent.getType(), FBEventTypes.AuthLogin);
  }

  @Override
  public HandlerRegistration addFBLogoutHandler(final FBLogoutHandler handler) {
    return addFBEventHandler(handler, FBLogoutEvent.getType(), FBEventTypes.AuthLogout);
  }

  @Override
  public HandlerRegistration addFBSessionChangeHandler(final FBSessionChangeHandler handler) {
    return addFBEventHandler(handler, FBSessionChangeEvent.getType(), FBEventTypes.AuthSessionChange);
  }

  @Override
  public HandlerRegistration addFBStatusChangeHandler(final FBStatusChangeHandler handler) {
    return addFBEventHandler(handler, FBStatusChangeEvent.getType(), FBEventTypes.AuthStatusChange);
  }

}
