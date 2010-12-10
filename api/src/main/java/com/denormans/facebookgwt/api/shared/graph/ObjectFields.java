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

import com.denormans.facebookgwt.api.shared.FBEnum;
import com.denormans.facebookgwt.api.shared.FBEnumCreator;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public enum ObjectFields implements ObjectField {
  Application("application"),
  About("about"),
  Actions("actions"),
  Active("active"),
  Attribution("attribution"),
  Bio("bio"),
  Birthday("birthday"),
  CallbackURL("callback_url"),
  Caption("caption"),
  Category("category"),
  Count("count"),
  CreatedTime("created_time"),
  Description("description"),
  Education("education"),
  Email("email"),
  EmbedHTML("embed_html"),
  EndTime("end_time"),
  FanCount("fan_count"),
  Fields("fields"),
  From("from"),
  FirstName("first_name"),
  Gender("gender"),
  Height("height"),
  Hometown("hometown"),
  Icon("icon"),
  ID("id"),
  InterestedIn("interested_in"),
  LastName("last_name"),
  LastUpdated("last_updated"),
  Likes("likes"),
  Link("link"),
  Locale("locale"),
  Location("location"),
  MeetingFor("meeting_for"),
  Message("message"),
  MessageCount("message_count"),
  MimeType("mime_type"),
  Name("name"),
  Object("object"),
  Owner("owner"),
  Period("period"),
  Picture("picture"),
  Place("place"),
  Political("political"),
  Privacy("privacy"),
  Quotes("quotes"),
  RelationshipStatus("relationship_status"),
  Religion("religion"),
  SignificantOther("significant_other"),
  Size("size"),
  Source("source"),
  Snippet("snippet"),
  StartTime("start_time"),
  Subject("subject"),
  Tags("tags"),
  ThirdPartyID("third_party_id"),
  To("to"),
  Timezone("timezone"),
  UnreadCount("unread_count"),
  UpdatedTime("update_time"),
  Values("values"),
  Venue("venue"),
  Verified("verified"),
  Website("website"),
  Width("width"),
  Work("work")
  ;

  private static final Map<String, ObjectFields> sObjectFieldByApiValue = FBEnum.Util.createFBEnumByApiValueMap(ObjectFields.class);
  private static final FBEnumCreator<ObjectField> sObjectFieldCreator = new ObjectFieldCreator();

  public static final EnumSet<ObjectFields> BasicFields = EnumSet.of(ObjectFields.ID, ObjectFields.Name);
  public static final EnumSet<ObjectFields> SimpleObjectFields = EnumSet.of(ObjectFields.ID, ObjectFields.Name, ObjectFields.Picture, ObjectFields.Link, ObjectFields.Category, ObjectFields.FanCount);

  private String apiValue;

  private ObjectFields(final String apiValue) {
    this.apiValue = apiValue;
  }

  public static List<ObjectField> valuesFromApiValues(final List<String> apiValues) {
    return FBEnum.Util.valuesFromApiValues(sObjectFieldByApiValue, apiValues, sObjectFieldCreator);
  }

  public static ObjectField valueFromApiValue(final String apiValue) {
    return FBEnum.Util.valueFromApiValue(sObjectFieldByApiValue, apiValue, sObjectFieldCreator);
  }

  public static List<ObjectField> parseApiValues(final String apiValues) {
    return valuesFromApiValues(FBEnum.Util.splitApiValues(apiValues));
  }

  @Override
  public String getApiValue() {
    return apiValue;
  }

  private static class ObjectFieldCreator implements FBEnumCreator<ObjectField> {
    @Override
    public ObjectField create(final String apiValue) {
      return new ObjectField() {
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
