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

import java.util.EnumSet;
import java.util.Set;

public enum ObjectType {
  Application(EnumSet.of(ConnectionTypes.WallFeed, ConnectionTypes.Posts, ConnectionTypes.Picture, ConnectionTypes.Tagged, ConnectionTypes.Links, ConnectionTypes.Photos, ConnectionTypes.PhotoAlbums,
                  ConnectionTypes.StatusMessages, ConnectionTypes.Videos, ConnectionTypes.VideoUploads, ConnectionTypes.Notes, ConnectionTypes.Events, ConnectionTypes.Subscriptions, ConnectionTypes.Insights),
              EnumSet.of(ObjectFields.ID, ObjectFields.Name, ObjectFields.Description, ObjectFields.Category, ObjectFields.Link)),
  Attachment(EnumSet.noneOf(ConnectionTypes.class),
              EnumSet.of(ObjectFields.ID, ObjectFields.Name, ObjectFields.MimeType, ObjectFields.Size)),
  CheckIn(EnumSet.noneOf(ConnectionTypes.class),
          EnumSet.of(ObjectFields.ID, ObjectFields.From, ObjectFields.Tags, ObjectFields.Place, ObjectFields.Message, ObjectFields.Application, ObjectFields.CreatedTime)),
  Comment(EnumSet.of(ConnectionTypes.Likes),
          EnumSet.of(ObjectFields.ID, ObjectFields.CreatedTime, ObjectFields.Message, ObjectFields.Likes, ObjectFields.From)),
  Event(EnumSet.of(ConnectionTypes.WallFeed, ConnectionTypes.NoReply, ConnectionTypes.Maybe, ConnectionTypes.Invited, ConnectionTypes.Attending, ConnectionTypes.Declined, ConnectionTypes.Picture),
        EnumSet.of(ObjectFields.ID, ObjectFields.Owner, ObjectFields.Name, ObjectFields.Description, ObjectFields.StartTime, ObjectFields.EndTime, ObjectFields.Location, ObjectFields.Venue, ObjectFields.Privacy, ObjectFields.UpdatedTime)),
  FriendList(EnumSet.of(ConnectionTypes.Members),
      ObjectFields.BasicFields),
  Group(EnumSet.of(ConnectionTypes.WallFeed, ConnectionTypes.Members, ConnectionTypes.Picture),
        EnumSet.of(ObjectFields.ID, ObjectFields.Icon, ObjectFields.Owner, ObjectFields.Name, ObjectFields.Description, ObjectFields.Link, ObjectFields.Privacy, ObjectFields.UpdatedTime)),
  Insights(EnumSet.noneOf(ConnectionTypes.class),
           EnumSet.of(ObjectFields.ID, ObjectFields.Name, ObjectFields.Period, ObjectFields.Values)),
  Link(EnumSet.of(ConnectionTypes.Comments),
       EnumSet.of(ObjectFields.ID, ObjectFields.From, ObjectFields.Link, ObjectFields.Name, ObjectFields.Caption, ObjectFields.Description, ObjectFields.Icon, ObjectFields.Picture, ObjectFields.Message, ObjectFields.CreatedTime)),
  Note(EnumSet.of(ConnectionTypes.Comments, ConnectionTypes.Likes),
       EnumSet.of(ObjectFields.ID, ObjectFields.From, ObjectFields.Subject, ObjectFields.Message, ObjectFields.CreatedTime, ObjectFields.UpdatedTime, ObjectFields.Icon)),
  Message(EnumSet.of(ConnectionTypes.Attachments, ConnectionTypes.Shares),
       EnumSet.of(ObjectFields.ID, ObjectFields.From, ObjectFields.To, ObjectFields.Message, ObjectFields.CreatedTime)),
  MessageThread(EnumSet.of(ConnectionTypes.Participants, ConnectionTypes.FormerParticipants, ConnectionTypes.Senders, ConnectionTypes.Messages),
               EnumSet.of(ObjectFields.Object, ObjectFields.Snippet, ObjectFields.UpdatedTime, ObjectFields.MessageCount, ObjectFields.UnreadCount, ObjectFields.Tags)),
  Page(EnumSet.of(ConnectionTypes.WallFeed, ConnectionTypes.Picture, ConnectionTypes.Tagged, ConnectionTypes.Links, ConnectionTypes.Photos, ConnectionTypes.PhotoAlbums, ConnectionTypes.StatusMessages,
           ConnectionTypes.Videos, ConnectionTypes.VideoUploads, ConnectionTypes.Notes, ConnectionTypes.Posts, ConnectionTypes.Events, ConnectionTypes.CheckIns),
       EnumSet.of(ObjectFields.ID, ObjectFields.Name, ObjectFields.Category, ObjectFields.FanCount)),
  Photo(EnumSet.of(ConnectionTypes.Comments, ConnectionTypes.Likes),
        EnumSet.of(ObjectFields.ID, ObjectFields.From, ObjectFields.Tags, ObjectFields.Name, ObjectFields.Picture, ObjectFields.Icon, ObjectFields.Source, ObjectFields.Height, ObjectFields.Width, ObjectFields.Link,
            ObjectFields.CreatedTime, ObjectFields.UpdatedTime)),
  PhotoAlbum(EnumSet.of(ConnectionTypes.Photos, ConnectionTypes.Comments, ConnectionTypes.Picture),
      EnumSet.of(ObjectFields.ID, ObjectFields.From, ObjectFields.Name, ObjectFields.Description, ObjectFields.Location, ObjectFields.Link, ObjectFields.Privacy, ObjectFields.Count, ObjectFields.CreatedTime, ObjectFields.UpdatedTime)),
  Post(EnumSet.of(ConnectionTypes.Comments),
       EnumSet.of(ObjectFields.ID, ObjectFields.From, ObjectFields.To, ObjectFields.Message, ObjectFields.Picture, ObjectFields.Link, ObjectFields.Name, ObjectFields.Caption, ObjectFields.Description, ObjectFields.Source,
           ObjectFields.Icon, ObjectFields.Attribution, ObjectFields.Actions, ObjectFields.Privacy, ObjectFields.Likes, ObjectFields.CreatedTime, ObjectFields.UpdatedTime)),
  Share(EnumSet.noneOf(ConnectionTypes.class),
      EnumSet.of(ObjectFields.ID, ObjectFields.Name, ObjectFields.Link, ObjectFields.Description, ObjectFields.Picture)),
  StatusMessage(EnumSet.of(ConnectionTypes.Comments),
                EnumSet.of(ObjectFields.ID, ObjectFields.From, ObjectFields.Message, ObjectFields.UpdatedTime)),
  Subscription(EnumSet.noneOf(ConnectionTypes.class),
               EnumSet.of(ObjectFields.Object, ObjectFields.Fields, ObjectFields.CallbackURL, ObjectFields.Active)),
  User(EnumSet.of(ConnectionTypes.HomeFeed, ConnectionTypes.WallFeed, ConnectionTypes.Tagged, ConnectionTypes.Posts, ConnectionTypes.Picture, ConnectionTypes.Friends, ConnectionTypes.Activities,
           ConnectionTypes.Interests, ConnectionTypes.Music, ConnectionTypes.Books, ConnectionTypes.Movies, ConnectionTypes.Television, ConnectionTypes.Likes, ConnectionTypes.Photos,
           ConnectionTypes.PhotoAlbums, ConnectionTypes.Videos, ConnectionTypes.VideoUploads, ConnectionTypes.Groups, ConnectionTypes.StatusMessages, ConnectionTypes.Links, ConnectionTypes.Notes, ConnectionTypes.Events,
           ConnectionTypes.Inbox, ConnectionTypes.Outbox, ConnectionTypes.Updates, ConnectionTypes.Accounts, ConnectionTypes.CheckIns),
       EnumSet.of(ObjectFields.ID, ObjectFields.FirstName, ObjectFields.LastName, ObjectFields.Name, ObjectFields.Link, ObjectFields.About, ObjectFields.Birthday, ObjectFields.Work, ObjectFields.Education, ObjectFields.Email,
           ObjectFields.Website, ObjectFields.Hometown, ObjectFields.Location, ObjectFields.Bio, ObjectFields.Quotes, ObjectFields.Gender, ObjectFields.InterestedIn, ObjectFields.MeetingFor, ObjectFields.RelationshipStatus,
           ObjectFields.Religion, ObjectFields.Political, ObjectFields.Verified, ObjectFields.SignificantOther, ObjectFields.Timezone, ObjectFields.ThirdPartyID, ObjectFields.LastUpdated, ObjectFields.Locale, ObjectFields.Address,
           ObjectFields.MobilePhone, ObjectFields.Languages)),
  Video(EnumSet.of(ConnectionTypes.Comments),
        EnumSet.of(ObjectFields.ID, ObjectFields.From, ObjectFields.Tags, ObjectFields.Name, ObjectFields.Picture, ObjectFields.EmbedHTML, ObjectFields.Icon, ObjectFields.Source, ObjectFields.CreatedTime, ObjectFields.UpdatedTime)),

  // Other types
  Account(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  Activity(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  Book(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  Company(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  EducationYear(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  Game(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  Interest(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  Language(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  Location(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  Movie(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  Music(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  Place(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  School(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  TelevisionShow(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  WorkPosition(EnumSet.noneOf(ConnectionTypes.class), ObjectFields.SimpleObjectFields),
  ;

  private Set<ConnectionTypes> connectionTypes;
  private Set<ObjectFields> objectFields;

  private ObjectType(final EnumSet<ConnectionTypes> connectionTypes, final EnumSet<ObjectFields> objectFields) {
    this.connectionTypes = connectionTypes;
    this.objectFields = objectFields;
  }

  public Set<ConnectionTypes> getConnectionTypes() {
    return connectionTypes;
  }

  public Set<ObjectFields> getObjectFields() {
    return objectFields;
  }
}
