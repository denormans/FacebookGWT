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

import com.denormans.facebookgwt.api.client.auth.js.FBPermissions;
import com.denormans.facebookgwt.api.shared.FBEnum;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FBPermission implements FBEnum {
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

  public static final EnumSet<FBPermission> PublishingPermissions = EnumSet.range(PublishStream, OfflineAccess);

  public static final EnumSet<FBPermission> UserDataPermissions = EnumSet.range(UserAboutMe, UserWorkHistory);
  public static final EnumSet<FBPermission> FriendsDataPermissions = EnumSet.range(FriendsAboutMe, FriendsWorkHistory);

  public static final EnumSet<FBPermission> DataPermissions = EnumSet.range(Email, FriendsWorkHistory);

  public static final EnumSet<FBPermission> PagePermissions = EnumSet.of(ManagePages);

  private static final Map<String, FBPermission> sPermissionByApiValue = createPermissionByApiValueMap();

  private static Map<String, FBPermission> createPermissionByApiValueMap() {
    HashMap<String, FBPermission> permissions = new HashMap<String, FBPermission>();

    for (final FBPermission permission : values()) {
      permissions.put(permission.getApiValue(), permission);
    }

    return permissions;
  }

  private String apiValue;
  private boolean isRequestable;

  FBPermission(final String apiValue) {
    this(apiValue, true);
  }

  FBPermission(final String apiValue, final boolean isRequestable) {
    this.apiValue = apiValue;
    this.isRequestable = isRequestable;
  }

  public static List<FBPermission> valuesFromApiValues(final List<String> permissionApiValues) {
    List<FBPermission> permissions = new ArrayList<FBPermission>(permissionApiValues.size());

    for (final String permissionApiValue : permissionApiValues) {
      permissions.add(valueFromApiValue(permissionApiValue));
    }

    return permissions;
  }

  public static FBPermission valueFromApiValue(final String apiValue) {
    if (apiValue == null) {
      return null;
    }
    return sPermissionByApiValue.get(apiValue);
  }

  public static List<String> parseApiValues(final String permissionApiValues) {
    if (permissionApiValues == null || permissionApiValues.trim().length() == 0) {
      return Collections.emptyList();
    }

    if (permissionApiValues.trim().charAt(0) != '{') {
      return Arrays.asList(permissionApiValues.split(","));
    }

    JSONValue jsonValue = JSONParser.parseStrict(permissionApiValues);
    JSONObject jsonObject = jsonValue.isObject();
    if (jsonObject == null) {
      return Collections.emptyList();
    }

    FBPermissions permissions = jsonObject.getJavaScriptObject().cast();
    return permissions.getAllApiPermissions();
  }

  @Override
  public String getApiValue() {
    return apiValue;
  }

  public boolean isRequestable() {
    return isRequestable;
  }
}
