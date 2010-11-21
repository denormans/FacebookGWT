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

package com.denormans.facebookgwt.api.shared.auth;

import com.denormans.facebookgwt.api.client.auth.js.FBPermissionsJS;
import com.denormans.facebookgwt.api.shared.FBEnum;
import com.denormans.facebookgwt.api.shared.FBEnumCreator;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public enum FBPermissions implements FBPermission {
  // Publishing Permissions
  PublishStream("publish_stream"),
  CreateEvent("create_event"),
  RSVPEvent("rsvp_event"),
  SMS("sms"),
  OfflineAccess("offline_access"),

  // Data Permissions
  Email("email"),
  ContactEmail("contact_email", false),
  ReadFriendlists("read_friendlists"),
  ReadInsights("read_insights"),
  ReadMailbox("read_mailbox"),
  ReadRequests("read_requests"),
  ReadStream("read_stream"),
  XMPPLogin("xmpp_login"),
  ADSManagement("ads_management"),

  UserAboutMe("user_about_me"),
  UserActivities("user_activities"),
  UserBirthday("user_birthday"),
  UserCheckins("user_checkins"),
  UserEducationHistory("user_education_history"),
  UserEvents("user_events"),
  UserGroups("user_groups"),
  UserHometown("user_hometown"),
  UserInterests("user_interests"),
  UserLikes("user_likes"),
  UserLocation("user_location"),
  UserNotes("user_notes"),
  UserOnlinePresence("user_online_presence"),
  UserPhotoVideoTags("user_photo_video_tags"),
  UserPhotos("user_photos"),
  UserRelationships("user_relationships"),
  UserRelationshipDetails("user_relationship_details"),
  UserReligionPolitics("user_religion_politics"),
  UserStatus("user_status"),
  UserVideos("user_videos"),
  UserWebsite("user_website"),
  UserWorkHistory("user_work_history"),

  FriendsAboutMe("friends_about_me"),
  FriendsActivities("friends_activities"),
  FriendsBirthday("friends_birthday"),
  FriendsCheckins("friends_checkins"),
  FriendsEducationHistory("friends_education_history"),
  FriendsEvents("friends_events"),
  FriendsGroups("friends_groups"),
  FriendsHometown("friends_hometown"),
  FriendsInterests("friends_interests"),
  FriendsLikes("friends_likes"),
  FriendsLocation("friends_location"),
  FriendsNotes("friends_notes"),
  FriendsOnlinePresence("friends_online_presence"),
  FriendsPhotoVideoTags("friends_photo_video_tags"),
  FriendsPhotos("friends_photos"),
  FriendsRelationships("friends_relationships"),
  FriendsRelationshipDetails("friends_relationship_details"),
  FriendsReligionPolitics("friends_religion_politics"),
  FriendsStatus("friends_status"),
  FriendsVideos("friends_videos"),
  FriendsWebsite("friends_website"),
  FriendsWorkHistory("friends_work_history"),

  // Page Permissions
  ManagePages("manage_pages");

  public static final EnumSet<FBPermissions> PublishingPermissions = EnumSet.range(PublishStream, OfflineAccess);

  public static final EnumSet<FBPermissions> UserDataPermissions = EnumSet.range(UserAboutMe, UserWorkHistory);
  public static final EnumSet<FBPermissions> FriendsDataPermissions = EnumSet.range(FriendsAboutMe, FriendsWorkHistory);

  public static final EnumSet<FBPermissions> DataPermissions = EnumSet.range(Email, FriendsWorkHistory);

  public static final EnumSet<FBPermissions> PagePermissions = EnumSet.of(ManagePages);

  private static final Map<String, FBPermissions> sPermissionByApiValue = FBEnum.Util.createFBEnumByApiValueMap(FBPermissions.class);
  private static final FBEnumCreator<FBPermission> sPermissionCreator = new FBPermissionCreator();

  private String apiValue;
  private boolean isRequestable;

  FBPermissions(final String apiValue) {
    this(apiValue, true);
  }

  FBPermissions(final String apiValue, final boolean isRequestable) {
    this.apiValue = apiValue;
    this.isRequestable = isRequestable;
  }

  public static List<FBPermission> valuesFromApiValues(final List<String> permissionApiValues) {
    return FBEnum.Util.valuesFromApiValues(sPermissionByApiValue, permissionApiValues, sPermissionCreator);
  }

  public static FBPermission valueFromApiValue(final String apiValue) {
    return FBEnum.Util.valueFromApiValue(sPermissionByApiValue, apiValue, sPermissionCreator);
  }

  public static List<FBPermission> parseApiValues(final String permissionApiValues) {
    if (permissionApiValues == null || permissionApiValues.trim().length() == 0) {
      return Collections.emptyList();
    }

    if (permissionApiValues.trim().charAt(0) != '{') {
      return valuesFromApiValues(FBEnum.Util.splitApiValues(permissionApiValues));
    }

    FBPermissionsJS permissions = FBPermissionsJS.fromJSONString(permissionApiValues);
    if (permissions == null) {
      return Collections.emptyList();
    }

    return permissions.getAllPermissions();
  }

  @Override
  public String getApiValue() {
    return apiValue;
  }

  @Override
  public boolean isRequestable() {
    return isRequestable;
  }

  private static class FBPermissionCreator implements FBEnumCreator<FBPermission> {
    @Override
    public FBPermission create(final String apiValue) {
      return new FBPermission() {
        @Override
        public boolean isRequestable() {
          return false;
        }

        @Override
        public String getApiValue() {
          return apiValue;
        }

        @Override
        public String toString() {
          return apiValue;
        }
      };
    }
  }
}
