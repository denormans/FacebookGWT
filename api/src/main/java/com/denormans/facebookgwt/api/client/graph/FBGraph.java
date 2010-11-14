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
   * @param callback Called when the method is complete
   */
  public void retrieveCurrentUser(final AsyncCallback<FBUser> callback) {
    retrieveUser(CurrentUserID, callback);
  }

  /**
   * Retrieves the given user.
   *
   * @param userID The user ID
   * @param callback Called when the method is complete
   */
  public void retrieveUser(final String userID, final AsyncCallback<FBUser> callback) {
    executeGraphCall(userID, null, HTTPMethods.Get, null, callback);
  }

  /**
   * Executes a Graph REST API method.
   *
   * @param objectID The object to retrieve from/post to
   * @param connectionType The type of connections (if any) to retrieve from the object
   * @param httpMethod The method to execute
   * @param callOptions The method options
   * @param callback Called when the method is complete
   */
  public native void executeGraphCall(final String objectID, final ConnectionType connectionType, final HTTPMethod httpMethod, final FBGraphCallOptions callOptions, final AsyncCallback<?> callback) /*-{
    try {
      var path = "/" + objectID;
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
        cb = function(response) {
          callback.@com.google.gwt.user.client.rpc.AsyncCallback::onSuccess(Ljava/lang/Object;)(response);
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
