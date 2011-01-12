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

import com.denormans.facebookgwt.api.client.graph.js.FBGraphCallOptions;
import com.denormans.facebookgwt.api.client.graph.js.model.Account;
import com.denormans.facebookgwt.api.client.graph.js.model.Activity;
import com.denormans.facebookgwt.api.client.graph.js.model.Book;
import com.denormans.facebookgwt.api.client.graph.js.model.CheckIn;
import com.denormans.facebookgwt.api.client.graph.js.model.Event;
import com.denormans.facebookgwt.api.client.graph.js.model.FriendList;
import com.denormans.facebookgwt.api.client.graph.js.model.Group;
import com.denormans.facebookgwt.api.client.graph.js.model.Interest;
import com.denormans.facebookgwt.api.client.graph.js.model.Link;
import com.denormans.facebookgwt.api.client.graph.js.model.MessageThread;
import com.denormans.facebookgwt.api.client.graph.js.model.Movie;
import com.denormans.facebookgwt.api.client.graph.js.model.Music;
import com.denormans.facebookgwt.api.client.graph.js.model.Note;
import com.denormans.facebookgwt.api.client.graph.js.model.Page;
import com.denormans.facebookgwt.api.client.graph.js.model.Photo;
import com.denormans.facebookgwt.api.client.graph.js.model.PhotoAlbum;
import com.denormans.facebookgwt.api.client.graph.js.model.Post;
import com.denormans.facebookgwt.api.client.graph.js.model.Postable;
import com.denormans.facebookgwt.api.client.graph.js.model.StatusMessage;
import com.denormans.facebookgwt.api.client.graph.js.model.TelevisionShow;
import com.denormans.facebookgwt.api.client.graph.js.model.User;
import com.denormans.facebookgwt.api.client.graph.js.model.Video;
import com.denormans.facebookgwt.api.client.graph.js.options.CreateFriendListOptions;
import com.denormans.facebookgwt.api.client.graph.js.options.FeedPostOptions;
import com.denormans.facebookgwt.api.shared.graph.ConnectionTypes;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public class UserGraph extends FBItemGraph<User> {
  public static final String CurrentUserID = "me";

  /**
   * Retrieves the current user.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveCurrentUser(final FBGraphCallOptions options, final AsyncCallback<User> callback) {
    retrieveByID(CurrentUserID, options, callback);
  }

  /**
   * Retrieves the current user's home feed.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveHomeFeed(final FBGraphCallOptions options, final AsyncCallback<List<Post>> callback) {
    retrieveHomeFeed(CurrentUserID, options, callback);
  }

  @Override
  public void retrieveHomeFeed(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Post>> callback) {
    super.retrieveHomeFeed(userID, options, callback);
  }

  /**
   * Retrieves the current user's wall feed.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveWallFeed(final FBGraphCallOptions options, final AsyncCallback<List<Post>> callback) {
    retrieveWallFeed(CurrentUserID, options, callback);
  }

  @Override
  public void retrieveWallFeed(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Post>> callback) {
    super.retrieveWallFeed(userID, options, callback);
  }

  /**
   * Retrieves the current user's friends.
   *
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveFriends(final FBGraphCallOptions options, final AsyncCallback<List<User>> callback) {
    retrieveFriends(CurrentUserID, options, callback);
  }

  @Override
  public void retrieveFriends(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<User>> callback) {
    super.retrieveFriends(userID, options, callback);
  }

  @Override
  public void retrieveTaggedIn(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Postable>> callback) {
    super.retrieveTaggedIn(userID, options, callback);
  }

  @Override
  public void retrievePosts(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Post>> callback) {
    super.retrievePosts(userID, options, callback);
  }

  @Override
  public void retrieveActivities(final String itemID, final FBGraphCallOptions options, final AsyncCallback<List<Activity>> callback) {
    super.retrieveActivities(itemID, options, callback);
  }

  @Override
  public void retrieveInterests(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Interest>> callback) {
    super.retrieveInterests(userID, options, callback);
  }

  @Override
  public void retrieveMusic(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Music>> callback) {
    super.retrieveMusic(userID, options, callback);
  }

  @Override
  public void retrieveBooks(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Book>> callback) {
    super.retrieveBooks(userID, options, callback);
  }

  @Override
  public void retrieveMovies(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Movie>> callback) {
    super.retrieveMovies(userID, options, callback);
  }

  @Override
  public void retrieveTelevisionShows(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<TelevisionShow>> callback) {
    super.retrieveTelevisionShows(userID, options, callback);
  }

  @Override
  public void retrieveLikedPages(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Page>> callback) {
    super.retrieveLikedPages(userID, options, callback);
  }

  @Override
  public void retrievePhotos(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Photo>> callback) {
    super.retrievePhotos(userID, options, callback);
  }

  @Override
  public void retrievePhotoAlbums(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<PhotoAlbum>> callback) {
    super.retrievePhotoAlbums(userID, options, callback);
  }

  @Override
  public void retrieveVideos(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Video>> callback) {
    super.retrieveVideos(userID, options, callback);
  }

  @Override
  public void retrieveGroups(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Group>> callback) {
    super.retrieveGroups(userID, options, callback);
  }

  @Override
  public void retrieveStatusMessages(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<StatusMessage>> callback) {
    super.retrieveStatusMessages(userID, options, callback);
  }

  @Override
  public void retrieveLinks(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Link>> callback) {
    super.retrieveLinks(userID, options, callback);
  }

  @Override
  public void retrieveNotes(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Note>> callback) {
    super.retrieveNotes(userID, options, callback);
  }

  @Override
  public void retrieveEvents(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Event>> callback) {
    super.retrieveEvents(userID, options, callback);
  }

  @Override
  public void retrieveInbox(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<MessageThread>> callback) {
    super.retrieveInbox(userID, options, callback);
  }

  @Override
  public void retrieveOutbox(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Note>> callback) {
    super.retrieveOutbox(userID, options, callback);
  }

  @Override
  public void retrieveUpdates(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Note>> callback) {
    super.retrieveUpdates(userID, options, callback);
  }

  @Override
  public void retrieveAccounts(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<Account>> callback) {
    super.retrieveAccounts(userID, options, callback);
  }

  @Override
  public void retrieveCheckIns(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<CheckIn>> callback) {
    super.retrieveCheckIns(userID, options, callback);
  }

  @Override
  public void retrieveFriendLists(final String userID, final FBGraphCallOptions options, final AsyncCallback<List<FriendList>> callback) {
    super.retrieveFriendLists(userID, options, callback);
  }

  /**
   * Posts the current user's wall feed.
   *
   * @param options The call options
   * @param callback Called when complete
   */
  public void postToWall(final FeedPostOptions options, final AsyncCallback<Post> callback) {
    postToWall(CurrentUserID, options, callback);
  }

  @Override
  public void postToWall(final String userID, final FeedPostOptions options, final AsyncCallback<Post> callback) {
    super.postToWall(userID, options, callback);
  }

  /**
   * Posts to the item's wall feed.
   *
   * @param userID The user ID
   * @param name The name of the new friend list
   * @param callback Called when complete
   */
  public void createFriendList(final String userID, final String name, final AsyncCallback<FriendList> callback) {
    createFriendList(userID, CreateFriendListOptions.createCreateFriendListOptions().setName(name), callback);
  }

  /**
   * Posts to the item's wall feed.
   *
   * @param userID The user ID
   * @param options The call options
   * @param callback Called when complete
   */
  public void createFriendList(final String userID, final CreateFriendListOptions options, final AsyncCallback<FriendList> callback) {
    post(userID, ConnectionTypes.FriendLists, options, callback);
  }
}
