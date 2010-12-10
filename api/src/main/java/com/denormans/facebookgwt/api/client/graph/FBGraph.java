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
import com.denormans.facebookgwt.api.client.graph.js.FBGraphObject;
import com.denormans.facebookgwt.api.client.graph.js.model.Like;
import com.denormans.facebookgwt.api.shared.graph.ConnectionTypes;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public class FBGraph extends FBIntegration {
  public final CommentGraph Comment = new CommentGraph();
  public final FriendListGraph FriendList = new FriendListGraph();
  public final UserGraph User = new UserGraph();
  public final VideoGraph Video = new VideoGraph();

  private final FBItemGraph<FBGraphObject> genericItemGraph = new FBItemGraph<FBGraphObject>() {};

  /**
   * Retrieves the given item's likes.
   *
   * @param itemID The item ID
   * @param options The call options
   * @param callback Called with the result
   */
  public void retrieveLikes(final String itemID, final FBGraphCallOptions options, final AsyncCallback<List<Like>> callback) {
    genericItemGraph.retrieveLikes(itemID, options, callback);
  }

  /**
   * Like an item (e.g. post, comment, etc.)
   *
   * @param itemID The item ID
   * @param callback Called when complete
   */
  public void likeItem(final String itemID, final AsyncCallback<Boolean> callback) {
    genericItemGraph.post(itemID, ConnectionTypes.Likes, null, callback);
  }

  /**
   * Unlike an item (e.g. post, comment, etc.)
   *
   * @param itemID The item ID
   * @param callback Called when complete
   */
  public void unlikeItem(final String itemID, final AsyncCallback<Boolean> callback) {
    genericItemGraph.deleteConnection(itemID, ConnectionTypes.Likes, null, callback);
  }

  // Generic graph methods
  /**
   * Retrieves an item by ID.
   *
   * @param itemID The item ID
   * @param options The call options
   * @param callback Called with the result
   */
  @SuppressWarnings({"unchecked"})
  public void retrieveItem(final String itemID, final FBGraphCallOptions options, final AsyncCallback<? extends FBGraphObject> callback) {
    genericItemGraph.retrieveByID(itemID, options, (AsyncCallback<FBGraphObject>)callback);
  }

  /**
   * Deletes the item.
   *
   * @param itemID The item ID
   * @param options The call options
   * @param callback Called when complete
   */
  public void deleteItem(final String itemID, final FBGraphCallOptions options, final AsyncCallback<Boolean> callback) {
    genericItemGraph.delete(itemID, options, callback);
  }
}
