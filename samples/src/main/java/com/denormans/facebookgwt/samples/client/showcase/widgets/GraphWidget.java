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

package com.denormans.facebookgwt.samples.client.showcase.widgets;

import com.denormans.facebookgwt.api.client.FBGWT;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphObject;
import com.denormans.facebookgwt.api.client.graph.js.model.Post;
import com.denormans.facebookgwt.api.client.graph.js.model.User;
import com.denormans.facebookgwt.api.client.graph.js.options.FeedPostOptions;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessHandler;
import com.denormans.facebookgwt.samples.client.FBObjectDescribers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;

import java.util.List;
import java.util.logging.Logger;

public class GraphWidget extends ShowcaseWidget {
  private static final Logger Log = Logger.getLogger(GraphWidget.class.getName());

  interface GraphWidgetUIBinder extends UiBinder<HTMLPanel, GraphWidget> {}
  private static GraphWidgetUIBinder sUIBinder = GWT.create(GraphWidgetUIBinder.class);

  @UiField Button retrieveCurrentUserButton;
  @UiField Button retrieveHomeFeedButton;
  @UiField Button retrieveWallFeedButton;
  @UiField Button retrieveFriendsButton;

  @UiField Button createFriendListButton;
  @UiField TextBox createFriendListNameTextBox;

  @UiField Button postToWallButton;
  @UiField TextBox postToWallMessageTextBox;

  @UiField Button deletePostButton;
  @UiField TextBox deletePostIDTextBox;

  @UiField Button likeItemButton;
  @UiField TextBox likeItemIDTextBox;

  @UiField Button unlikeItemButton;
  @UiField TextBox unlikeItemIDTextBox;

  @UiField Button testButton;
  @UiField TextBox testTextBox;

  public GraphWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    FBGWT.Init.addFBInitSuccessHandler(new FBInitSuccessHandler() {
      @Override
      public void onFBInitSuccess(final FBInitSuccessEvent event) {
        retrieveCurrentUserButton.setEnabled(FBGWT.Init.isInitialized());
        retrieveHomeFeedButton.setEnabled(FBGWT.Init.isInitialized());
        retrieveWallFeedButton.setEnabled(FBGWT.Init.isInitialized());
        retrieveFriendsButton.setEnabled(FBGWT.Init.isInitialized());

        postToWallButton.setEnabled(FBGWT.Init.isInitialized());
        postToWallMessageTextBox.setEnabled(FBGWT.Init.isInitialized());

        deletePostButton.setEnabled(FBGWT.Init.isInitialized());
        deletePostIDTextBox.setEnabled(FBGWT.Init.isInitialized());

        likeItemButton.setEnabled(FBGWT.Init.isInitialized());
        likeItemIDTextBox.setEnabled(FBGWT.Init.isInitialized());

        unlikeItemButton.setEnabled(FBGWT.Init.isInitialized());
        unlikeItemIDTextBox.setEnabled(FBGWT.Init.isInitialized());

        testButton.setEnabled(FBGWT.Init.isInitialized());
        testTextBox.setEnabled(FBGWT.Init.isInitialized());
      }
    });
  }

  @UiHandler ("retrieveCurrentUserButton")
  public void handleRetrieveCurrentUserButtonClick(final ClickEvent event) {
    FBGWT.Graph.User.retrieveCurrentUser(null, new AsyncCallback<User>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error retrieving current user", caught);
      }

      @Override
      public void onSuccess(final User result) {
        addApiEventMessage("Retrieve current user result (firstName=" + result.getFirstName() + ", lastName=" + result.getLastName() + ")", result);

        setItemDisplayDescription(FBObjectDescribers.Graph.getUserDescriber(), result);
      }
    });
  }

  @UiHandler ("retrieveHomeFeedButton")
  public void handleRetrieveHomeFeedButtonClick(final ClickEvent event) {
    FBGWT.Graph.User.retrieveHomeFeed(null, new AsyncCallback<List<Post>>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error retrieving home feed", caught);
      }

      @Override
      public void onSuccess(final List<Post> result) {
        addApiEventMessage("Retrieve home feed result", result);
        setItemDisplayDescription(FBObjectDescribers.Graph.getPostDescriber(), result);
      }
    });
  }

  @UiHandler ("retrieveWallFeedButton")
  public void handleRetrieveWallFeedButtonClick(final ClickEvent event) {
    FBGWT.Graph.User.retrieveWallFeed(null, new AsyncCallback<List<Post>>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error retrieving wall feed", caught);
      }

      @Override
      public void onSuccess(final List<Post> result) {
        addApiEventMessage("Retrieve wall feed result", result);
        setItemDisplayDescription(FBObjectDescribers.Graph.getPostDescriber(), result);
      }
    });
  }

  @UiHandler ("retrieveFriendsButton")
  public void handleRetrieveFriendsButtonClick(final ClickEvent event) {
    FBGWT.Graph.User.retrieveFriends(null, new AsyncCallback<List<User>>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error retrieving friends", caught);
      }

      @Override
      public void onSuccess(final List<User> result) {
        addApiEventMessage("Retrieve friends result", result);
        setItemDisplayDescription(FBObjectDescribers.Graph.getUserDescriber(), result);
      }
    });
  }

  @UiHandler ("postToWallButton")
  public void handlePostToWallClick(final ClickEvent event) {
    FeedPostOptions postOptions = FeedPostOptions.createFeedPostOptions().setMessage(postToWallMessageTextBox.getText());

    Log.info("Post options: " + postOptions.toJSONString());

    FBGWT.Graph.User.postToWall(postOptions, new AsyncCallback<Post>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error posting to wall", caught);
      }

      @Override
      public void onSuccess(final Post result) {
        addApiEventMessage("Post to wall result", result);
        setItemDisplayDescription(FBObjectDescribers.Graph.getPostDescriber(), result);
      }
    });
  }

  @UiHandler ("deletePostButton")
  public void handleDeletePostButtonClick(final ClickEvent event) {
    FBGWT.Graph.deleteItem(deletePostIDTextBox.getText(), null, new AsyncCallback<Boolean>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error deleting post", caught);
      }

      @Override
      public void onSuccess(final Boolean result) {
        addApiEventMessage("Delete post result", result);
      }
    });
  }

  @UiHandler ("likeItemButton")
  public void handleLikeItemButtonClick(final ClickEvent event) {
    FBGWT.Graph.likeItem(likeItemIDTextBox.getText(), new AsyncCallback<Boolean>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error liking item", caught);
      }

      @Override
      public void onSuccess(final Boolean result) {
        addApiEventMessage("Like item result", result);
      }
    });
  }

  @UiHandler ("unlikeItemButton")
  public void handleUnlikeItemButtonClick(final ClickEvent event) {
    FBGWT.Graph.unlikeItem(unlikeItemIDTextBox.getText(), new AsyncCallback<Boolean>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error unliking item", caught);
      }

      @Override
      public void onSuccess(final Boolean result) {
        addApiEventMessage("Unlike item result", result);
      }
    });
  }

  @UiHandler ("testButton")
  public void handleTestButtonClick(final ClickEvent event) {
    String testValue = testTextBox.getText();

    FBGWT.Graph.retrieveItem(testValue, null, new AsyncCallback<FBGraphObject>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error during test", caught);
      }

      @Override
      public void onSuccess(final FBGraphObject result) {
        addApiEventMessage("Test result", result);
        setItemDisplay("Test Results", result);
      }
    });
  }
}