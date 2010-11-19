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
import com.denormans.facebookgwt.api.client.graph.js.FBFeedPostOptions;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphCallOptions;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphDataListResult;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphObject;
import com.denormans.facebookgwt.api.client.graph.js.Post;
import com.denormans.facebookgwt.api.client.graph.js.User;
import com.denormans.facebookgwt.api.shared.common.HTTPMethod;
import com.denormans.facebookgwt.api.shared.common.HTTPMethods;
import com.denormans.facebookgwt.api.shared.graph.ConnectionType;
import com.denormans.facebookgwt.api.shared.graph.ConnectionTypes;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class FBGraph extends FBIntegration {
  public static final String CurrentUserID = "me";

  /**
   * Retrieves the current user.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveCurrentUser(final FBGraphCallOptions options, final AsyncCallback<User> callback) {
    retrieveUser(CurrentUserID, options, callback);
  }

  /**
   * Retrieves the given user.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUser(final String userID, final FBGraphCallOptions options, final AsyncCallback<User> callback) {
    retrieveItem(userID, options, callback);
  }

  /**
   * Retrieves the given user's home feed.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveCurrentUserHomeFeed(final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Post>> callback) {
    retrieveUserHomeFeed(CurrentUserID, options, callback);
  }

  /**
   * Retrieves the given user's home feed.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserHomeFeed(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Post>> callback) {
    retrieveConnections(userID, ConnectionTypes.HomeFeed, options, callback);
  }

  /**
   * Posts to the user's wall.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called when complete
   */
  public void postToUserWall(final String userID, final FBFeedPostOptions options, final AsyncCallback<Post> callback) {
    postItem(userID, ConnectionTypes.WallFeed, options, callback);
  }

  // Generic graph methods
  /**
   * Retrieves an item by ID.
   *
   * @param itemID The item ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveItem(final String itemID, final FBGraphCallOptions options, final AsyncCallback<? extends FBGraphObject> callback) {
    executeGraphCall(itemID, null, HTTPMethods.Get, options, callback);
  }

  /**
   * Retrieves an item's connections by ID.
   *
   * @param itemID The item ID
   * @param connectionType The type of connection to retrieve
   * @param options The call options
   * @param callback Called with the results
   */
  public void retrieveConnections(final String itemID, final ConnectionType connectionType, final FBGraphCallOptions options, final AsyncCallback<? extends FBGraphDataListResult<? extends FBGraphObject>> callback) {
    executeGraphCall(itemID, connectionType, HTTPMethods.Get, options, callback);
  }

  /**
   * Post an item to the owner's feed/likes/etc.
   *
   * @param ownerID The owner item ID
   * @param connectionType The type of connection to post
   * @param options The call options
   * @param callback Called when complete
   */
  public void postItem(final String ownerID, final ConnectionType connectionType, final FBGraphCallOptions options, final AsyncCallback<?> callback) {
    executeGraphCall(ownerID, connectionType, HTTPMethods.Post, options, callback);
  }

  /**
   * Deletes the item.
   *
   * @param itemID The item ID
   * @param options The call options
   * @param callback Called when complete
   */
  public void deleteItem(final String itemID, final FBGraphCallOptions options, final AsyncCallback<?> callback) {
    executeGraphCall(itemID, null, HTTPMethods.Delete, options, callback);
  }

  /**
   * Deletes the unidentified connection (e.g. Like) for an item.
   *
   * @param itemID The item ID
   * @param connectionType The type of connection
   * @param options The call options
   * @param callback Called when complete
   */
  public void deleteConnection(final String itemID, final ConnectionType connectionType, final FBGraphCallOptions options, final AsyncCallback<?> callback) {
    executeGraphCall(itemID, connectionType, HTTPMethods.Delete, options, callback);
  }

  /**
   * Executes a Graph REST API method.
   *
   * @param objectID The object to retrieve from/post to
   * @param connectionType The type of connections (if any) to retrieve from the object
   * @param httpMethod The HTTP method to use
   * @param options The call options
   * @param callback Called with the result
   */
  public native void executeGraphCall(final String objectID, final ConnectionType connectionType, final HTTPMethod httpMethod, final FBGraphCallOptions options, final AsyncCallback<?> callback) /*-{
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

      var params = options;
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
