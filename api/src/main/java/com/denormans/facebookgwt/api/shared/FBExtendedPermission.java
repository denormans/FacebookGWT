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

package com.denormans.facebookgwt.api.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FBExtendedPermission {
  // Publishing Permissions
  PublishStream("publish_stream"),
  CreateEvent("create_event"),
  RSVPEvent("rsvp_event"),
  SMS("sms"),
  OfflineAccess("offline_access"),

  // Data Permissions
  Email("email"),
  ReadFriendlists("read_friendlists"),
  ReadInsights("read_insights"),
  ReadMailbox("read_mailbox"),
  ReadRequests("read_requests"),
  ReadStream("read_stream"),
  XMPPLogin("xmpp_Login"),
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

  public static final EnumSet<FBExtendedPermission> PublishingPermissions = EnumSet.range(PublishStream, OfflineAccess);

  public static final EnumSet<FBExtendedPermission> UserDataPermissions = EnumSet.range(UserAboutMe, UserWorkHistory);
  public static final EnumSet<FBExtendedPermission> FriendsDataPermissions = EnumSet.range(FriendsAboutMe, FriendsWorkHistory);

  public static final EnumSet<FBExtendedPermission> DataPermissions = EnumSet.range(Email, FriendsWorkHistory);

  public static final EnumSet<FBExtendedPermission> PagePermissions = EnumSet.of(ManagePages);

  private static final Map<String, FBExtendedPermission> sPermissionByApiValue = createPermissionByApiValueMap();

  private static Map<String, FBExtendedPermission> createPermissionByApiValueMap() {
    HashMap<String, FBExtendedPermission> permissions = new HashMap<String, FBExtendedPermission>();

    for (final FBExtendedPermission permission : values()) {
      permissions.put(permission.getApiValue(), permission);
    }

    return permissions;
  }

  private String apiValue;

  FBExtendedPermission(final String apiValue) {
    this.apiValue = apiValue;
  }

  public static List<FBExtendedPermission> valuesFromApiValues(final List<String> permissionApiValues) {
    List<FBExtendedPermission> permissions = new ArrayList<FBExtendedPermission>(permissionApiValues.size());

    for (final String permissionApiValue : permissionApiValues) {
      permissions.add(valueFromApiValue(permissionApiValue));
    }

    return permissions;
  }

  public static FBExtendedPermission valueFromApiValue(final String apiValue) {
    if (apiValue == null) {
      return null;
    }
    return sPermissionByApiValue.get(apiValue);
  }

  public static List<String> splitApiValues(final String permissionApiValues) {
    return Arrays.asList(permissionApiValues.split(","));
  }

  public static List<String> toApiValues(final Collection<FBExtendedPermission> permissions) {
    List<String> apiPermissions = new ArrayList<String>(permissions.size());
    for (final FBExtendedPermission permission : permissions) {
      apiPermissions.add(permission.getApiValue());
    }
    return apiPermissions;
  }

  public static String joinApiValues(final Collection<String> permissions) {
    StringBuilder builder = new StringBuilder();
    boolean isFirst = true;
    for (final String permission : permissions) {
      if (!isFirst) {
        builder.append(permission);
      } else {
        isFirst = false;
      }
      builder.append(permission);
    }

    return builder.toString();
  }

  public String getApiValue() {
    return apiValue;
  }
}
