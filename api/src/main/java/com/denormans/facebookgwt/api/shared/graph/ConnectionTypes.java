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

package com.denormans.facebookgwt.api.shared.graph;

public enum ConnectionTypes implements ConnectionType {
  Accounts("accounts"),
  Activities("activities"),
  Attending("attending"),
  Books("books"),
  CheckIns("checkins"),
  Comments("comments"),
  Declined("declined"),
  Events("events"),
  WallFeed("feed"),
  Friends("friends"),
  FriendLists("friendLists"),
  Groups("groups"),
  HomeFeed("home"),
  Inbox("inbox"),
  Insights("insights"),
  Interests("interests"),
  Invited("invited"),
  Likes("likes"),
  Links("links"),
  Maybe("maybe"),
  Members("members"),
  Movies("movies"),
  Music("music"),
  NoReply("noreply"),
  Notes("notes"),
  Outbox("outbox"),
  Picture("picture"),
  Photos("photos"),
  PhotoAlbums("albums"),
  PlatformRequests("platformrequests"),
  Posts("posts"),
  Statuses("statuses"),
  Subscriptions("subscriptions"),
  Tagged("tagged"),
  Television("television"),
  Updates("updates"),
  Videos("videos"),
  VideoUploads("videos/uploaded"),
  ;

  private String apiValue;

  private ConnectionTypes(final String apiValue) {
    this.apiValue = apiValue;
  }

  @Override
  public String getApiValue() {
    return apiValue;
  }
}
