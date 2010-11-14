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

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public enum ObjectTypes {
  Application(ConnectionTypes.Feed, ConnectionTypes.Posts, ConnectionTypes.Picture, ConnectionTypes.Tagged, ConnectionTypes.Links, ConnectionTypes.Photos, ConnectionTypes.PhotoAlbums,
              ConnectionTypes.Statuses, ConnectionTypes.Videos, ConnectionTypes.VideoUploads, ConnectionTypes.Notes, ConnectionTypes.Events, ConnectionTypes.Subscriptions, ConnectionTypes.Insights),
  CheckIn(),
  Event(ConnectionTypes.Feed, ConnectionTypes.NoReply, ConnectionTypes.Maybe, ConnectionTypes.Invited, ConnectionTypes.Attending, ConnectionTypes.Declined, ConnectionTypes.Picture),
  Group(ConnectionTypes.Feed, ConnectionTypes.Members, ConnectionTypes.Picture),
  Insights(),
  Link(ConnectionTypes.Comments),
  Note(ConnectionTypes.Comments, ConnectionTypes.Likes),
  Page(ConnectionTypes.Feed, ConnectionTypes.Picture, ConnectionTypes.Tagged, ConnectionTypes.Links, ConnectionTypes.Photos, ConnectionTypes.PhotoAlbums, ConnectionTypes.Statuses,
       ConnectionTypes.Videos, ConnectionTypes.VideoUploads, ConnectionTypes.Notes, ConnectionTypes.Posts, ConnectionTypes.Events, ConnectionTypes.CheckIns),
  Photo(ConnectionTypes.Comments, ConnectionTypes.Likes),
  PhotoAlbum(ConnectionTypes.Photos, ConnectionTypes.Comments, ConnectionTypes.Picture),
  Post(ConnectionTypes.Comments),
  StatusMessage(ConnectionTypes.Comments),
  Subscription(),
  User(ConnectionTypes.Home, ConnectionTypes.Feed, ConnectionTypes.Tagged, ConnectionTypes.Posts, ConnectionTypes.Picture, ConnectionTypes.Friends, ConnectionTypes.Activities,
       ConnectionTypes.Interests, ConnectionTypes.Music, ConnectionTypes.Books, ConnectionTypes.Movies, ConnectionTypes.Television, ConnectionTypes.Likes, ConnectionTypes.Photos,
      ConnectionTypes.PhotoAlbums, ConnectionTypes.Videos, ConnectionTypes.VideoUploads, ConnectionTypes.Groups, ConnectionTypes.Statuses, ConnectionTypes.Links, ConnectionTypes.Notes, ConnectionTypes.Events,
      ConnectionTypes.Inbox, ConnectionTypes.Outbox, ConnectionTypes.Updates, ConnectionTypes.Accounts, ConnectionTypes.CheckIns, ConnectionTypes.PlatformRequests),
  Video(ConnectionTypes.Comments);

  private Set<ConnectionTypes> connectionTypes;

  private ObjectTypes(final ConnectionTypes... connectionTypes) {
    this.connectionTypes = EnumSet.copyOf(Arrays.asList(connectionTypes));
  }

  public Set<ConnectionTypes> getConnectionTypes() {
    return connectionTypes;
  }
}
