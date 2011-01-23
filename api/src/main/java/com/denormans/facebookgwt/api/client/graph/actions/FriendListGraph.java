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

package com.denormans.facebookgwt.api.client.graph.actions;

import com.denormans.facebookgwt.api.client.graph.options.FBGraphCallOptions;
import com.denormans.facebookgwt.api.client.graph.model.FriendList;
import com.denormans.facebookgwt.api.client.graph.model.User;
import com.denormans.facebookgwt.api.shared.graph.ConnectionTypes;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public class FriendListGraph extends FBItemGraph<FriendList> {
  @Override
  public void retrieveMembers(final String friendListID, final FBGraphCallOptions options, final AsyncCallback<List<User>> callback) {
    super.retrieveMembers(friendListID, options, callback);
  }

  /**
   * Adds a member to the given friend list.
   *
   * @param friendListID The friend list ID
   * @param memberID The member ID
   * @param callback Called when complete
   */
  public void addMember(final String friendListID, final String memberID, final AsyncCallback<Boolean> callback) {
    super.post(friendListID, ConnectionTypes.Members, memberID, null, callback);
  }

  /**
   * Removes a member from the given friend list.
   *
   * @param friendListID The friend list ID
   * @param memberID The member ID
   * @param callback Called when complete
   */
  public void removeMember(final String friendListID, final String memberID, final AsyncCallback<Boolean> callback) {
    super.deleteConnection(friendListID, ConnectionTypes.Members, memberID, null, callback);
  }
}
