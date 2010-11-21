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

import com.denormans.facebookgwt.api.client.FBGWTException;
import com.denormans.facebookgwt.api.client.FBIntegration;
import com.denormans.facebookgwt.api.client.graph.js.FBFeedPostOptions;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphCallOptions;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphDataListResult;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphError;
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
   * Posts the current user's wall feed.
   *
   * @param options The call options
   * @param callback Called when complete
   */
  public void postToCurrentUserWall(final FBFeedPostOptions options, final AsyncCallback<Post> callback) {
    postToItemWall(CurrentUserID, options, callback);
  }

  /**
   * Posts to the item's wall feed.
   *
   * @param itemID The item ID
   * @param options The call options
   * @param callback Called when complete
   */
  public void postToItemWall(final String itemID, final FBFeedPostOptions options, final AsyncCallback<Post> callback) {
    postItem(itemID, ConnectionTypes.WallFeed, options, callback);
  }

  /**
   * Like an item (e.g. post, comment, etc.)
   *
   * @param itemID The item ID
   * @param callback Called when complete
   */
  public void likeItem(final String itemID, final AsyncCallback<Boolean> callback) {
    postItem(itemID, ConnectionTypes.Likes, null, callback);
  }

  /**
   * Unlike an item (e.g. post, comment, etc.)
   *
   * @param itemID The item ID
   * @param callback Called when complete
   */
  public void unlikeItem(final String itemID, final AsyncCallback<Boolean> callback) {
    deleteConnection(itemID, ConnectionTypes.Likes, null, callback);
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
  public void deleteItem(final String itemID, final FBGraphCallOptions options, final AsyncCallback<Boolean> callback) {
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
  public void deleteConnection(final String itemID, final ConnectionType connectionType, final FBGraphCallOptions options, final AsyncCallback<Boolean> callback) {
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
          if (typeof(response) === "boolean") {
            self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallback(Lcom/google/gwt/user/client/rpc/AsyncCallback;Z)(callback, response);
          } else if (typeof(response) === "number") {
            self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallback(Lcom/google/gwt/user/client/rpc/AsyncCallback;D)(callback, response);
          } else {
            if (response != null && response.error) {
              self.@com.denormans.facebookgwt.api.client.graph.FBGraph::executeCallbackError(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/denormans/facebookgwt/api/client/graph/js/FBGraphError;)(callback, response.error);
              return;
            }
            self.@com.denormans.facebookgwt.api.client.FBIntegration::executeCallback(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/Object;)(callback, response);
          }
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

  protected void executeCallbackError(final AsyncCallback callback, final FBGraphError error) {
    String message = error.getMessage();
    String type = error.getType();
    if (type != null && type.length() > 0) {
      message += " (" + type + ")";
    }

    FBGWTException fbgwtException = new FBGWTException(message);
    fbgwtException.fillInStackTrace();
    callback.onFailure(fbgwtException);
  }
}
