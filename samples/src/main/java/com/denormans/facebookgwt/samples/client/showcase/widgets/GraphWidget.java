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
import com.denormans.facebookgwt.api.client.graph.js.FBFeedPostOptions;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphDataListResult;
import com.denormans.facebookgwt.api.client.graph.js.Post;
import com.denormans.facebookgwt.api.client.graph.js.User;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessEvent;
import com.denormans.facebookgwt.api.client.init.events.FBInitSuccessHandler;
import com.denormans.facebookgwt.samples.client.FBObjectDescribers;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescription;

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
  @UiField FBObjectDisplay<ObjectDescription> retrieveCurrentUserDisplay;

  @UiField Button retrieveCurrentUserHomeFeedButton;
  @UiField FBObjectDisplay<List<ObjectDescription>> retrieveCurrentUserHomeFeedDisplay;

  @UiField Button postToCurrentUserWallButton;
  @UiField TextBox postToCurrentUserWallMessageTextBox;

  @UiField Button deletePostButton;
  @UiField TextBox deletePostIDTextBox;
  @UiField FBObjectDisplay<Post> postToCurrentUserWallDisplay;

  public GraphWidget() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);

    FBGWT.Init.addFBInitSuccessHandler(new FBInitSuccessHandler() {
      @Override
      public void onFBInitSuccess(final FBInitSuccessEvent event) {
        retrieveCurrentUserButton.setEnabled(FBGWT.Init.isInitialized());
        retrieveCurrentUserHomeFeedButton.setEnabled(FBGWT.Init.isInitialized());

        postToCurrentUserWallButton.setEnabled(FBGWT.Init.isInitialized());
        postToCurrentUserWallMessageTextBox.setEnabled(FBGWT.Init.isInitialized());

        deletePostButton.setEnabled(FBGWT.Init.isInitialized());
        deletePostIDTextBox.setEnabled(FBGWT.Init.isInitialized());
      }
    });
  }

  @UiHandler ("retrieveCurrentUserButton")
  public void handleRetrieveCurrentUserButtonClick(final ClickEvent event) {
    FBGWT.Graph.retrieveCurrentUser(null, new AsyncCallback<User>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error retrieving current user", caught);
      }

      @Override
      public void onSuccess(final User result) {
        addApiEventMessage("Retrieve current user result (firstName=" + result.getFirstName() + ", lastName=" + result.getLastName() + ")", result);
        retrieveCurrentUserDisplay.setValue(FBObjectDescribers.getUserDescriber().describe(result));
        retrieveCurrentUserDisplay.setVisible(true);
      }
    });
  }

  @UiHandler ("retrieveCurrentUserHomeFeedButton")
  public void handleRetrieveCurrentUserHomeFeedButtonClick(final ClickEvent event) {
    FBGWT.Graph.retrieveCurrentUserHomeFeed(null, new AsyncCallback<FBGraphDataListResult<Post>>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error retrieving current user home feed", caught);
      }

      @Override
      public void onSuccess(final FBGraphDataListResult<Post> result) {
        addApiEventMessage("Retrieve current user home feed result", result);
        retrieveCurrentUserHomeFeedDisplay.setValue(FBObjectDescribers.getPostDescriber().describeList(result.getData()));
        retrieveCurrentUserHomeFeedDisplay.setVisible(true);
      }
    });
  }

  @UiHandler ("postToCurrentUserWallButton")
  public void handlePostToCurrentUserWallClick(final ClickEvent event) {
    FBFeedPostOptions postOptions = FBFeedPostOptions.createFeedPostOptions().setMessage(postToCurrentUserWallMessageTextBox.getText());

    Log.info("Post options: " + postOptions.toJSONString());

    FBGWT.Graph.postToCurrentUserWall(postOptions, new AsyncCallback<Post>() {
      @Override
      public void onFailure(final Throwable caught) {
        handleError("Error posting to current user wall", caught);
      }

      @Override
      public void onSuccess(final Post result) {
        addApiEventMessage("Post to current user wall result", result);
        postToCurrentUserWallDisplay.setValue(result);
        postToCurrentUserWallDisplay.setVisible(true);
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
}