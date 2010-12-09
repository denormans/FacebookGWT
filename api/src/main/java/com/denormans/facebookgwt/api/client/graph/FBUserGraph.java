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

import com.denormans.facebookgwt.api.client.graph.js.FBGraphDataListResult;
import com.denormans.facebookgwt.api.client.graph.js.model.Account;
import com.denormans.facebookgwt.api.client.graph.js.model.Activity;
import com.denormans.facebookgwt.api.client.graph.js.model.Book;
import com.denormans.facebookgwt.api.client.graph.js.model.CheckIn;
import com.denormans.facebookgwt.api.client.graph.js.model.Event;
import com.denormans.facebookgwt.api.client.graph.js.model.FriendList;
import com.denormans.facebookgwt.api.client.graph.js.model.Group;
import com.denormans.facebookgwt.api.client.graph.js.model.Interest;
import com.denormans.facebookgwt.api.client.graph.js.model.Like;
import com.denormans.facebookgwt.api.client.graph.js.model.Link;
import com.denormans.facebookgwt.api.client.graph.js.model.Movie;
import com.denormans.facebookgwt.api.client.graph.js.model.Music;
import com.denormans.facebookgwt.api.client.graph.js.model.Note;
import com.denormans.facebookgwt.api.client.graph.js.model.Photo;
import com.denormans.facebookgwt.api.client.graph.js.model.PhotoAlbum;
import com.denormans.facebookgwt.api.client.graph.js.model.Post;
import com.denormans.facebookgwt.api.client.graph.js.model.Postable;
import com.denormans.facebookgwt.api.client.graph.js.model.StatusMessage;
import com.denormans.facebookgwt.api.client.graph.js.model.TelevisionShow;
import com.denormans.facebookgwt.api.client.graph.js.model.User;
import com.denormans.facebookgwt.api.client.graph.js.model.Video;
import com.denormans.facebookgwt.api.client.graph.js.options.FBFeedPostOptions;
import com.denormans.facebookgwt.api.client.graph.js.options.FBGraphCallOptions;
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
   * Retrieves the given user's activities.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserActivities(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Activity>> callback) {
    retrieveConnections(userID, ConnectionTypes.Activities, options, callback);
  }

  /**
   * Retrieves the given user's interests.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserInterests(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Interest>> callback) {
    retrieveConnections(userID, ConnectionTypes.Interests, options, callback);
  }

  /**
   * Retrieves the given user's music.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserMusic(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Music>> callback) {
    retrieveConnections(userID, ConnectionTypes.Music, options, callback);
  }

  /**
   * Retrieves the given user's books.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserBooks(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Book>> callback) {
    retrieveConnections(userID, ConnectionTypes.Books, options, callback);
  }

  /**
   * Retrieves the given user's movies.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserMovies(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Movie>> callback) {
    retrieveConnections(userID, ConnectionTypes.Movies, options, callback);
  }

  /**
   * Retrieves the given user's television shows.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserTelevisionShows(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<TelevisionShow>> callback) {
    retrieveConnections(userID, ConnectionTypes.Television, options, callback);
  }

  /**
   * Retrieves the given user's likes.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserLikes(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Like>> callback) {
    retrieveConnections(userID, ConnectionTypes.Likes, options, callback);
  }

  /**
   * Retrieves the given user's photos.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserPhotos(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Photo>> callback) {
    retrieveConnections(userID, ConnectionTypes.Photos, options, callback);
  }

  /**
   * Retrieves the given user's albums.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserPhotoAlbums(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<PhotoAlbum>> callback) {
    retrieveConnections(userID, ConnectionTypes.PhotoAlbums, options, callback);
  }

  /**
   * Retrieves the given user's videos.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserVideos(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Video>> callback) {
    retrieveConnections(userID, ConnectionTypes.Videos, options, callback);
  }

  /**
   * Retrieves the given user's groups.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserGroups(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Group>> callback) {
    retrieveConnections(userID, ConnectionTypes.Groups, options, callback);
  }

  /**
   * Retrieves the given user's status messages.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserStatusMessages(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<StatusMessage>> callback) {
    retrieveConnections(userID, ConnectionTypes.Statuses, options, callback);
  }

  /**
   * Retrieves the given user's links.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserLinks(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Link>> callback) {
    retrieveConnections(userID, ConnectionTypes.Links, options, callback);
  }

  /**
   * Retrieves the given user's albums.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserNotes(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Note>> callback) {
    retrieveConnections(userID, ConnectionTypes.Notes, options, callback);
  }

  /**
   * Retrieves the given user's events.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserEvents(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Event>> callback) {
    retrieveConnections(userID, ConnectionTypes.Events, options, callback);
  }

  /**
   * Retrieves the given user's inbox.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserInbox(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Note>> callback) {
    retrieveConnections(userID, ConnectionTypes.Inbox, options, callback);
  }

  /**
   * Retrieves the given user's outbox.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserOutbox(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Note>> callback) {
    retrieveConnections(userID, ConnectionTypes.Outbox, options, callback);
  }

  /**
   * Retrieves the given user's updates.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserUpdates(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Note>> callback) {
    retrieveConnections(userID, ConnectionTypes.Updates, options, callback);
  }

  /**
   * Retrieves the given user's accounts.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserAccounts(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<Account>> callback) {
    retrieveConnections(userID, ConnectionTypes.Accounts, options, callback);
  }

  /**
   * Retrieves the given user's events.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserCheckIns(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<CheckIn>> callback) {
    retrieveConnections(userID, ConnectionTypes.CheckIns, options, callback);
  }

  /**
   * Retrieves the given user's friend lists.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveUserFriendLists(final String userID, final FBGraphCallOptions options, final AsyncCallback<FBGraphDataListResult<FriendList>> callback) {
    retrieveConnections(userID, ConnectionTypes.FriendLists, options, callback);
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
