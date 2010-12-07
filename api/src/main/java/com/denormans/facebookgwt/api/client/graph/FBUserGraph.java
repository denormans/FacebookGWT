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

import com.denormans.facebookgwt.api.client.graph.js.FBFeedPostOptions;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphCallOptions;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphDataListResult;
import com.denormans.facebookgwt.api.client.graph.js.Post;
import com.denormans.facebookgwt.api.client.graph.js.Postable;
import com.denormans.facebookgwt.api.client.graph.js.User;
import com.denormans.facebookgwt.api.shared.graph.ConnectionTypes;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class FBUserGraph extends FBItemGraph<User> {
  public static final String CurrentUserID = "me";

  /**
   * Retrieves the current user.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveCurrentUser(final FBGraphCallOptions options, final AsyncCallback<User> callback) {
    retrieveItem(FBUserGraph.CurrentUserID, options, callback);
  }

  /**
   * Retrieves the given user's home feed.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveCurrentUserHomeFeed(final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Post>> callback) {
    retrieveUserHomeFeed(FBUserGraph.CurrentUserID, options, callback);
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
   * Retrieves the given user's wall feed.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveCurrentUserWallFeed(final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Post>> callback) {
    retrieveUserWallFeed(FBUserGraph.CurrentUserID, options, callback);
  }

  /**
   * Retrieves the given user's wall feed.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserWallFeed(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Post>> callback) {
    retrieveConnections(userID, ConnectionTypes.WallFeed, options, callback);
  }

  /**
   * Retrieves the posts, photos and videos that the current user is tagged in.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveCurrentUserTaggedIn(final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Postable>> callback) {
    retrieveUserTaggedIn(FBUserGraph.CurrentUserID, options, callback);
  }

  /**
   * Retrieves the posts, photos and videos that the given user is tagged in.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserTaggedIn(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Postable>> callback) {
    retrieveConnections(userID, ConnectionTypes.Tagged, options, callback);
  }

  /**
   * Retrieves the given user's posts.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveCurrentUserPosts(final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Post>> callback) {
    retrieveUserPosts(FBUserGraph.CurrentUserID, options, callback);
  }

  /**
   * Retrieves the given user's posts.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserPosts(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Post>> callback) {
    retrieveConnections(userID, ConnectionTypes.Posts, options, callback);
  }

  /**
   * Retrieves the current user's friends.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveCurrentUserFriends(final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<User>> callback) {
    retrieveUserFriends(FBUserGraph.CurrentUserID, options, callback);
  }

  /**
   * Retrieves the given user's friends.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserFriends(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<User>> callback) {
    retrieveConnections(userID, ConnectionTypes.Friends, options, callback);
  }

  /**
   * Posts the current user's wall feed.
   *
   * @param options The call options
   * @param callback Called when complete
   */
  public void postToCurrentUserWall(final FBFeedPostOptions options, final AsyncCallback<Post> callback) {
    postToItemWall(FBUserGraph.CurrentUserID, options, callback);
  }

}
