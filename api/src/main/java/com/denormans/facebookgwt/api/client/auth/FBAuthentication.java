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
import com.denormans.facebookgwt.api.client.auth.events.FBLoginHandler;
import com.denormans.facebookgwt.api.client.auth.events.FBLogoutEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBSessionChangeEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBSessionChangeHandler;
import com.denormans.facebookgwt.api.client.auth.js.FBAuthEventResponse;
import com.denormans.facebookgwt.api.client.auth.events.FBLoginEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBLogoutHandler;
import com.denormans.facebookgwt.api.client.auth.events.FBStatusChangeEvent;
import com.denormans.facebookgwt.api.client.auth.events.FBStatusChangeHandler;
import com.denormans.facebookgwt.api.client.auth.events.HasFBAuthHandlers;
import com.denormans.facebookgwt.api.client.common.js.FBEventResponse;
import com.denormans.facebookgwt.api.client.auth.js.FBLoginOptions;
import com.denormans.facebookgwt.api.client.auth.js.FBSession;
import com.denormans.facebookgwt.api.shared.common.events.FBEventTypes;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;

public final class FBAuthentication extends FBIntegration implements HasFBAuthHandlers {
  /**
   * Retrieves the login status asynchronously via the Facebook JSAPI.
   *
   * @param callback The callback to receive the results
   */
  public native void retrieveLoginStatus(final AsyncCallback<FBAuthEventResponse> callback) /*-{
    try {
      $wnd.FB.getLoginStatus(function(response) {
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(Ljava/lang/Object;)(response);
      });
    } catch(e) {
      var ex = @com.denormans.facebookgwt.api.client.FBGWT::createException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
      callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
    }
  }-*/;

  /**
   * Gets the session synchronously.
   *
   * @return The Facebook session
   */
  public native FBSession getSession() /*-{
    try {
      return $wnd.FB.getSession();
    } catch(e) {
      @com.denormans.facebookgwt.api.client.FBGWT::throwException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
    }
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
  public native void login(final FBLoginOptions loginOptions, final AsyncCallback<FBAuthEventResponse> callback) /*-{
    try {
      $wnd.FB.login(function(response) {
        if (callback != null) {
          callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(Ljava/lang/Object;)(response);
        }
      }, loginOptions);
    } catch(e) {
      if (callback != null) {
        var ex = @com.denormans.facebookgwt.api.client.FBGWT::createException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
      } else {
        @com.denormans.facebookgwt.api.client.FBGWT::throwException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
      }
    }
  }-*/;

  /**
   * Logout of Facebook.
   *
   * @param callback Called after logout
   */
  public native void logout(final AsyncCallback<FBAuthEventResponse> callback) /*-{
    try {
      $wnd.FB.logout(function(response) {
        if (callback != null) {
          callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(Ljava/lang/Object;)(response);
        }
      });
    } catch(e) {
      if (callback != null) {
        var ex = @com.denormans.facebookgwt.api.client.FBGWT::createException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
      } else {
        @com.denormans.facebookgwt.api.client.FBGWT::throwException(Lcom/denormans/gwtutil/client/js/JSError;)(e);
      }
    }
  }-*/;

  @Override
  protected void handleFBEvent(final FBEventTypes eventType, final FBEventResponse apiResponse) {
    switch (eventType) {
      case AuthLogin:
        fireLogin(apiResponse.<FBAuthEventResponse>cast());
        break;

      case AuthLogout:
        fireLogout(apiResponse.<FBAuthEventResponse>cast());
        break;

      case AuthSessionChange:
        fireSessionChange(apiResponse.<FBAuthEventResponse>cast());
        break;

      case AuthStatusChange:
        fireStatusChange(apiResponse.<FBAuthEventResponse>cast());
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
