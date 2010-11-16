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

package com.denormans.facebookgwt.api.client.graph;

import com.denormans.facebookgwt.api.client.FBIntegration;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphCallOptions;
import com.denormans.facebookgwt.api.client.graph.js.FBRetrieveUserOptions;
import com.denormans.facebookgwt.api.client.graph.js.FBUser;
import com.denormans.facebookgwt.api.shared.common.HTTPMethod;
import com.denormans.facebookgwt.api.shared.common.HTTPMethods;
import com.denormans.facebookgwt.api.shared.graph.ConnectionType;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class FBGraph extends FBIntegration {
  public static final String CurrentUserID = "me";

  /**
   * Retrieves the current user.
   *
   * @param options The call options
   * @param callback Called when the method is complete
   */
  public void retrieveCurrentUser(final FBRetrieveUserOptions options, final AsyncCallback<FBUser> callback) {
    retrieveUser(CurrentUserID, options, callback);
  }

  /**
   * Retrieves the given user.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called when the method is complete
   */
  public void retrieveUser(final String userID, final FBRetrieveUserOptions options, final AsyncCallback<FBUser> callback) {
    executeGraphCall(userID, null, HTTPMethods.Get, options, callback);
  }

  /**
   * Executes a Graph REST API method.
   *
   * @param objectID The object to retrieve from/post to
   * @param connectionType The type of connections (if any) to retrieve from the object
   * @param httpMethod The HTTP method to use
   * @param callOptions The call options
   * @param callback Called when the call is complete
   */
  public native void executeGraphCall(final String objectID, final ConnectionType connectionType, final HTTPMethod httpMethod, final FBGraphCallOptions callOptions, final AsyncCallback<?> callback) /*-{
    try {
      var path = "/";

      if (objectID != null) {
        path += objectID;
      }

      if (connectionType != null) {
        path += "/" + connectionType.@com.denormans.facebookgwt.api.shared.graph.ConnectionType::getApiValue()();
      }

      var method = "get";
      if (httpMethod != null) {
        method = httpMethod.@com.denormans.facebookgwt.api.shared.common.HTTPMethod::getApiValue()();
      }

      var params = callOptions;
      if (params == null) {
        params = {};
      }

      var cb;
      if (callback != null) {
        var self = this;
        cb = function(response) {
          self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallback(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/Object;)(callback, response);
        };
      }

      $wnd.FB.api(path, method, params, cb);
    } catch(e) {
      if (callback != null) {
        var ex = @com.denormans.gwtutil.client.js.JSError::createException(Ljava/lang/Object;)(e);
        callback.@com.google.gwt.user.client.rpc.AsyncCallback::onFailure(Ljava/lang/Throwable;)(ex);
      } else {
        throw e;
      }
    }
  }-*/;
}
