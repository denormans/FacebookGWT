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

package com.denormans.facebookgwt.api.client.graph.js;

import com.denormans.facebookgwt.api.client.common.FBDateTimeFormats;
import com.denormans.facebookgwt.api.shared.graph.Gender;
import com.denormans.facebookgwt.api.shared.graph.PoliticalAffiliation;
import com.denormans.facebookgwt.api.shared.graph.PoliticalAffiliations;
import com.denormans.facebookgwt.api.shared.graph.RelationshipType;
import com.denormans.facebookgwt.api.shared.graph.RelationshipTypes;
import com.denormans.facebookgwt.api.shared.graph.Religion;
import com.denormans.facebookgwt.api.shared.graph.Religions;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.i18n.client.TimeZone;

import java.util.Date;
import java.util.List;

public class User extends FBGraphObject {
  protected User() {
  }

  public final native String getFirstName() /*-{
    return this.first_name;
  }-*/;

  public final native String getLastName() /*-{
    return this.last_name;
  }-*/;

  public final native String getLink() /*-{
    return this.link;
  }-*/;

  public final native String getAbout() /*-{
    return this.about;
  }-*/;

  public final Date getBirthday() {
    return FBDateTimeFormats.parseDateTime(FBDateTimeFormats.BirthdayFormat, getBirthdayJS());
  }

  private native String getBirthdayJS() /*-{
    return this.birthday;
  }-*/;

  public final List<Work> getWork() {
    return convertJsArrayToList(getWorkJS());
  }

  private native JsArray<Work> getWorkJS() /*-{
    return this.work;
  }-*/;

  public final List<Education> getEducation() {
    return convertJsArrayToList(getEducationJS());
  }

  private native JsArray<Education> getEducationJS() /*-{
    return this.education;
  }-*/;

  public final native String getEmail() /*-{
    return this.email;
  }-*/;

  public final native String getWebsite() /*-{
    return this.website;
  }-*/;

  public final native Location getLocation() /*-{
    return this.location;
  }-*/;

  public final native String getBiography() /*-{
    return this.bio;
  }-*/;

  public final native String getQuotes() /*-{
    return this.quotes;
  }-*/;

  public final Gender getGender() {
    return Gender.valueFromApiValue(getGenderJS());
  }

  private native String getGenderJS() /*-{
    return this.gender;
  }-*/;

  public final List<Gender> getInterestedIn() {
    return Gender.valuesFromApiValues(convertJsArrayStringToList(getInterestedInJS()));
  }

  private native JsArrayString getInterestedInJS() /*-{
    return this.interested_in;
  }-*/;

  public final List<RelationshipType> getSeeking() {
    return RelationshipTypes.valuesFromApiValues(convertJsArrayStringToList(getSeekingJS()));
  }

  private native JsArrayString getSeekingJS() /*-{
    return this.meeting_for;
  }-*/;

  public final Religion getReligion() {
    return Religions.valueFromApiValue(getReligionJS());
  }

  private native String getReligionJS() /*-{
    return this.religion;
  }-*/;

  public final PoliticalAffiliation getPoliticalAffiliation() {
    return PoliticalAffiliations.valueFromApiValue(getPoliticalAffiliationJS());
  }

  private native String getPoliticalAffiliationJS() /*-{
    return this.political;
  }-*/;

  public final native boolean isVerified() /*-{
    return this.verified == true;
  }-*/;

  public final native User getSignificantOther() /*-{
    return this.significant_other;
  }-*/;

  public final TimeZone getTimeZone() {
    return TimeZone.createTimeZone(getTimeZoneInHoursJS() * 60);
  }

  private native int getTimeZoneInHoursJS() /*-{
    return this.timezone || 0;
  }-*/;

  public final native String getThirdPartyID() /*-{
    return this.third_party_id;
  }-*/;

  public final native String getLocale() /*-{
    return this.locale;
  }-*/;
}
