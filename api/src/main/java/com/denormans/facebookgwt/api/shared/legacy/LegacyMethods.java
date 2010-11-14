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

package com.denormans.facebookgwt.api.shared.legacy;

public enum LegacyMethods implements LegacyMethod {
  // Administrative Methods
  AdminGetMetrics("admin.getMetrics"),
  AdminSetAppProperties("admin.setAppProperties"),
  AdminGetBannedUsers("admin.getBannedUsers"),
  AdminGetRestrictionInfo("admin.getRestrictionInfo"),
  AdminBanUsers("admin.banUsers"),
  AdminGetAllocation("admin.getAllocation"),
  AdminUnbanUsers("admin.unbanUsers"),
  AdminSetRestrictionInfo("admin.setRestrictionInfo"),
  AdsGetAdreportSchedules("ads.getAdreportSchedules"),
  ApplicationGetPublicInfo("application.getPublicInfo"),
  BatchRun("batch.run"),
  DataSetCookie("data.setCookie"),
  LinksGetStats("links.getStats"),
  IntlGetTranslations("intl.getTranslations"),
  FbmlSetRefHandle("fbml.setRefHandle"),
  IntlUploadNativeStrings("intl.uploadNativeStrings"),
  MigrationsSetStatus("migrations.setStatus"),
  MigrationsGetStatuses("migrations.getStatuses"),
  PagesBlockFan("pages.blockFan"),
  AdminGetAppProperties("admin.getAppProperties"),

  // Login/Auth Methods
  AuthExpireSession("auth.expireSession"),
  AuthGetSession("auth.getSession"),
  AuthPromoteSession("auth.promoteSession"),
  AuthCreateToken("auth.createToken"),
  AuthRevokeAuthorization("auth.revokeAuthorization"),
  AuthRevokeExtendedPermission("auth.revokeExtendedPermission"),

  // Data Retrieval Methods
  CommentsGet("comments.get"),
  DataGetCookies("data.getCookies"),
  FriendsGetAppUsers("friends.getAppUsers"),
  FriendsAreFriends("friends.areFriends"),
  FqlQuery("fql.query"),
  FriendsGet("friends.get"),
  FqlMultiquery("fql.multiquery"),
  FbmlRefreshRefUrl("fbml.refreshRefUrl"),
  FriendsGetMutualFriends("friends.getMutualFriends"),
  FriendsGetLists("friends.getLists"),
  GroupsGetMembers("groups.getMembers"),
  GroupsGet("groups.get"),
  MessageGetThreadsInFolder("message.getThreadsInFolder"),
  LinksGet("links.get"),
  NotesGet("notes.get"),
  NotificationsGet("notifications.get"),
  NotificationsGetList("notifications.getList"),
  PagesIsFan("pages.isFan"),
  PagesIsAdmin("pages.isAdmin"),
  PagesGetinfo("pages.getinfo"),
  PagesIsAppAdded("pages.isAppAdded"),
  ProfileGetInfoOptions("profile.getInfoOptions"),
  PhotosGetAlbums("photos.getAlbums"),
  PhotosGetTags("photos.getTags"),
  PhotosGet("photos.get"),
  PrivacyGet("privacy.get"),
  ProfileGetFBML("profile.getFBML"),
  StatusGet("status.get"),
  StreamGetComments("stream.getComments"),
  StreamGetFilters("stream.getFilters"),
  StreamGet("stream.get"),
  UsersHasAppPermission("users.hasAppPermission"),
  UsersGetInfo("users.getInfo"),
  UsersIsAppUser("users.isAppUser"),
  UsersGetStandardinfo("users.getStandardinfo"),
  UsersGetLoggedInUser("users.getLoggedInUser"),
  UsersIsVerified("users.isVerified"),
  VideoGetUploadLimits("video.getUploadLimits"),
  FbmlRefreshImgSrc("fbml.refreshImgSrc"),
  ProfileGetInfo("profile.getInfo"),

  // Publishing Methods
  CommentsRemove("comments.remove"),
  CommentsAdd("comments.add"),
  LinksPost("links.post"),
  LiveMessageSend("liveMessage.send"),
  LinksPreview("links.preview"),
  NotesEdit("notes.edit"),
  NotesDelete("notes.delete"),
  NotesCreate("notes.create"),
  NotificationsMarkRead("notifications.markRead"),
  PhotosUpload("photos.upload"),
  NotificationsSendEmail("notifications.sendEmail"),
  PhotosAddTag("photos.addTag"),
  ProfileSetInfo("profile.setInfo"),
  ProfileSetFBML("profile.setFBML"),
  ProfileSetInfoOptions("profile.setInfoOptions"),
  StreamPublish("stream.publish"),
  StreamAddLike("stream.addLike"),
  StreamRemoveComment("stream.removeComment"),
  StatusSet("status.set"),
  StreamRemoveLike("stream.removeLike"),
  StreamRemove("stream.remove"),
  UsersSetStatus("users.setStatus"),
  VideoUpload("video.upload"),
  PhotosCreateAlbum("photos.createAlbum"),
  StreamAddComment("stream.addComment"),

  // Facebook Connect Methods
  ConnectGetUnconnectedFriendsCount("connect.getUnconnectedFriendsCount"),
  ConnectUnregisterUsers("connect.unregisterUsers"),
  ConnectRegisterUsers("connect.registerUsers"),

  // Mobile Methods
  SmsCanSend("sms.canSend"),
  SmsSend("sms.send"),

  // Dashboard API Methods
  DashboardGetActivity("dashboard.getActivity"),
  DashboardGetCount("dashboard.getCount"),
  DashboardIncrementCount("dashboard.incrementCount"),
  DashboardAddGlobalNews("dashboard.addGlobalNews"),
  DashboardMultiClearNews("dashboard.multiClearNews"),
  DashboardClearGlobalNews("dashboard.clearGlobalNews"),
  DashboardMultiAddNews("dashboard.multiAddNews"),
  DashboardMultiGetCount("dashboard.multiGetCount"),
  DashboardGetNews("dashboard.getNews"),
  DashboardSetCount("dashboard.setCount"),
  DashboardClearNews("dashboard.clearNews"),
  DashboardDecrementCount("dashboard.decrementCount"),
  DashboardGetGlobalNews("dashboard.getGlobalNews"),
  DashboardMultiDecrementCount("dashboard.multiDecrementCount"),
  DashboardMultiSetCount("dashboard.multiSetCount"),
  DashboardRemoveActivity("dashboard.removeActivity"),
  DashboardPublishActivity("dashboard.publishActivity"),
  DashboardMultiGetNews("dashboard.multiGetNews"),
  DashboardMultiIncrementCount("dashboard.multiIncrementCount"),
  DashboardAddNews("dashboard.addNews"),

  // Events API Methods
  EventsGet("events.get"),
  EventsInvite("events.invite"),
  EventsCancel("events.cancel"),
  EventsGetMembers("events.getMembers"),
  EventsCreate("events.create"),
  EventsRsvp("events.rsvp"),
  EventsEdit("events.edit"),

  // Custom Tags API Methods
  FbmlGetCustomTags("fbml.getCustomTags"),
  FbmlRegisterCustomTags("fbml.registerCustomTags"),
  FbmlDeleteCustomTags("fbml.deleteCustomTags"),

  // Ads Methods
  AdsCreateAdGroups("ads.createAdGroups"),
  AdsGetAdGroupCreatives("ads.getAdGroupCreatives"),
  AdsEstimateTargetingStats("ads.estimateTargetingStats"),
  AdsAddAccountUsers("ads.addAccountUsers"),
  AdsCreateCampaigns("ads.createCampaigns"),
  AdsGetAdGroups("ads.getAdGroups"),
  AdsGetCampaigns("ads.getCampaigns"),
  AdsGetAccounts("ads.getAccounts"),
  AdsGetAdGroupTargeting("ads.getAdGroupTargeting"),
  AdsGetCampaignStats("ads.getCampaignStats"),
  AdsGetAdreportJobs("ads.getAdreportJobs"),
  AdsGetConnectionObjectIds("ads.getConnectionObjectIds"),
  AdsGetValidKeywords("ads.getValidKeywords"),
  AdsGetKeywordAutocomplete("ads.getKeywordAutocomplete"),
  AdsGetKeywordSuggestions("ads.getKeywordSuggestions"),
  AdsGetAdGroupStats("ads.getAdGroupStats"),
  AdsGetAutoCompleteData("ads.getAutoCompleteData"),
  AdsUpdateAdreportSchedules("ads.updateAdreportSchedules"),
  AdsUpdateAdGroups("ads.updateAdGroups"),
  AdsRemoveAccountUsers("ads.removeAccountUsers"),
  AdsSetAccountUsersRole("ads.setAccountUsersRole"),
  AdsUpdateCampaigns("ads.updateCampaigns"),
  AdsCreateAdreportSchedules("ads.createAdreportSchedules"),
  ;

  private String apiValue;

  private LegacyMethods(final String apiValue) {
    this.apiValue = apiValue;
  }

  @Override
  public String getApiValue() {
    return apiValue;
  }
}
