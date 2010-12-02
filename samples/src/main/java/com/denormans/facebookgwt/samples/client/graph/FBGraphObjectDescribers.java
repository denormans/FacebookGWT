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

package com.denormans.facebookgwt.samples.client.graph;

import com.denormans.facebookgwt.api.client.FBGWT;
import com.denormans.facebookgwt.api.client.common.js.FBJSObject;
import com.denormans.facebookgwt.api.client.graph.js.Company;
import com.denormans.facebookgwt.api.client.graph.js.Education;
import com.denormans.facebookgwt.api.client.graph.js.EducationYear;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphDataListResult;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphObject;
import com.denormans.facebookgwt.api.client.graph.js.Location;
import com.denormans.facebookgwt.api.client.graph.js.Post;
import com.denormans.facebookgwt.api.client.graph.js.School;
import com.denormans.facebookgwt.api.client.graph.js.User;
import com.denormans.facebookgwt.api.client.graph.js.Work;
import com.denormans.facebookgwt.api.client.graph.js.WorkPosition;
import com.denormans.facebookgwt.api.shared.graph.ObjectType;
import com.denormans.facebookgwt.samples.client.describe.AbstractObjectDescriber;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescriber;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescription;
import com.denormans.facebookgwt.samples.client.showcase.Action;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FBGraphObjectDescribers {
  private Map<ObjectType, ObjectDescriber<? extends FBGraphObject>> objectDescribers = new HashMap<ObjectType, ObjectDescriber<? extends FBGraphObject>>();

  private ObjectDescriber<Work> workDescriber;
  private ObjectDescriber<Education> educationDescriber;

  public FBGraphObjectDescribers() {
    registerDescribers();
  }

  private void registerDescribers() {
    objectDescribers.put(ObjectType.Post, new FBGraphObjectDescriber<Post>(ObjectType.Post) {
      @Override
      protected ObjectDescription<Post> describeObject(final Post obj) {
        return super.describeObject(obj);
      }
    });

    objectDescribers.put(ObjectType.User, new FBGraphObjectDescriber<User>(ObjectType.User) {
      @Override
      protected ObjectDescription<User> describeObject(final User obj) {
        return super.describeObject(obj).addValue("First Name", obj.getFirstName()).addValue("Last Name", obj.getLastName()).addValue("Link", obj.getLink()).addValue("About", obj.getAbout()).addValue("Birthday", obj.getBirthday()).
            addValue("Work", getWorkDescriber().describeList(obj.getWork())).addValue("Education", getEducationDescriber().describeList(obj.getEducation())).addValue("Email", obj.getEmail()).addValue("Website", obj.getWebsite()).
            addValue("Location", getLocationDescriber().describe(obj.getLocation())).addValue("Biography", obj.getBiography()).addValue("Quotes", obj.getQuotes()).addValue("Gender", obj.getGender()).
            addValue("Interested in", obj.getInterestedIn()).addValue("Seeking", obj.getSeeking()).addValue("Religion", obj.getReligion()).addValue("Political Affiliation", obj.getPoliticalAffiliation()).
            addValue("Verified", obj.isVerified()).addValue("Significant Other", describe(obj.getSignificantOther())).addValue("Time Zone", obj.getTimeZone().getID()).addValue("Third-Party ID", obj.getThirdPartyID()).
            addValue("Locale", obj.getLocale()).addValue("Updated Time", obj.getUpdatedTime()).
            addAction("User Details", new Action<User, ObjectDescription<User>>() {
              @Override
              public void execute(final User obj, final AsyncCallback<ObjectDescription<User>> callback) {
                FBGWT.Graph.retrieveUser(obj.getID(), null, new AsyncCallback<User>() {
                  @Override
                  public void onFailure(final Throwable caught) {
                    callback.onFailure(caught);
                  }

                  @Override
                  public void onSuccess(final User result) {
                    callback.onSuccess(describe(result));
                  }
                });
              }
            }).
            addAction("Home Feed", new Action<User, List<ObjectDescription<Post>>>() {
              @Override
              public void execute(final User obj, final AsyncCallback<List<ObjectDescription<Post>>> callback) {
                FBGWT.Graph.retrieveUserHomeFeed(obj.getID(), null, new ListTransformingCallback<Post>(getPostDescriber(), callback));
              }
            }).
            addAction("Wall Feed", new Action<User, List<ObjectDescription<Post>>>() {
              @Override
              public void execute(final User obj, final AsyncCallback<List<ObjectDescription<Post>>> callback) {
                FBGWT.Graph.retrieveUserWallFeed(obj.getID(), null, new ListTransformingCallback<Post>(getPostDescriber(), callback));
              }
            });
      }
    });

    objectDescribers.put(ObjectType.Company, new FBGraphObjectDescriber<Company>(ObjectType.Company));
    objectDescribers.put(ObjectType.EducationYear, new FBGraphObjectDescriber<EducationYear>(ObjectType.EducationYear));
    objectDescribers.put(ObjectType.Location, new FBGraphObjectDescriber<Location>(ObjectType.Location));
    objectDescribers.put(ObjectType.School, new FBGraphObjectDescriber<School>(ObjectType.School));
    objectDescribers.put(ObjectType.WorkPosition, new FBGraphObjectDescriber<WorkPosition>(ObjectType.WorkPosition));
  }

  private ObjectDescriber<Company> getCompanyDescriber() {
    return getObjectDescriber(ObjectType.Company);
  }

  private ObjectDescriber<Education> getEducationDescriber() {
    if(educationDescriber == null) {
      educationDescriber = new AbstractObjectDescriber<Education>() {
        @Override
        public String getObjectTypeName() {
          return "Education";
        }

        @Override
        public ObjectDescription<Education> describeObject(final Education obj) {
          return new ObjectDescription<Education>(obj, this).addValue("School", getSchoolDescriber().describe(obj.getSchool())).addValue("Year", getEducationYearDescriber().describe(obj.getYear())).addValue("Type", obj.getType());
        }
      };
    }

    return educationDescriber;
  }

  private ObjectDescriber<EducationYear> getEducationYearDescriber() {
    return getObjectDescriber(ObjectType.EducationYear);
  }

  private ObjectDescriber<Location> getLocationDescriber() {
    return getObjectDescriber(ObjectType.Location);
  }

  public ObjectDescriber<Post> getPostDescriber() {
    return getObjectDescriber(ObjectType.Post);
  }

  private ObjectDescriber<School> getSchoolDescriber() {
    return getObjectDescriber(ObjectType.School);
  }

  public ObjectDescriber<User> getUserDescriber() {
    return getObjectDescriber(ObjectType.User);
  }

  private ObjectDescriber<Work> getWorkDescriber() {
    if(workDescriber == null) {
      workDescriber = new AbstractObjectDescriber<Work>() {
        @Override
        public String getObjectTypeName() {
          return "Work";
        }

        @Override
        public ObjectDescription<Work> describeObject(final Work obj) {
          return new ObjectDescription<Work>(obj, this).addValue("Employer", getCompanyDescriber().describe(obj.getEmployer())).addValue("Location", getLocationDescriber().describe(obj.getLocation())).
              addValue("Position", getWorkPositionDescriber().describe(obj.getPosition())).addValue("Start Date", obj.getStartDate()).addValue("End Date", obj.getEndDate());
        }
      };
    }

    return workDescriber;
  }

  private ObjectDescriber<WorkPosition> getWorkPositionDescriber() {
    return getObjectDescriber(ObjectType.WorkPosition);
  }

  @SuppressWarnings({"unchecked"})
  public <T extends FBGraphObject> ObjectDescriber<T> getObjectDescriber(final ObjectType objectType) {
    return (ObjectDescriber<T>)objectDescribers.get(objectType);
  }

  private static class FBGraphObjectDescriber<T extends FBGraphObject> extends AbstractGraphObjectDescriber<T> {
    private ObjectType objectType;

    public FBGraphObjectDescriber(final ObjectType objectType) {
      this.objectType = objectType;
    }

    @Override
    protected ObjectType getObjectType() {
      return objectType;
    }
  }

  private static abstract class AbstractGraphObjectDescriber<T extends FBGraphObject> extends AbstractObjectDescriber<T> {
    @Override
    public String getObjectTypeName() {
      return getObjectType().name();
    }

    @Override
    protected ObjectDescription<T> describeObject(final T obj) {
      return new GraphObjectDescription<T>(obj, this, getObjectType()).addValue("ID", obj.getID()).addValue("Name", obj.getName());
    }

    protected abstract ObjectType getObjectType();
  }

  private class ListTransformingCallback<T extends FBJSObject> implements AsyncCallback<FBGraphDataListResult<T>> {
    private final ObjectDescriber<T> describer;
    private final AsyncCallback<List<ObjectDescription<T>>> callback;

    private ListTransformingCallback(final ObjectDescriber<T> describer, final AsyncCallback<List<ObjectDescription<T>>> callback) {
      this.describer = describer;
      this.callback = callback;
    }

    @Override
    public void onFailure(final Throwable caught) {
      callback.onFailure(caught);
    }

    @Override
    public void onSuccess(final FBGraphDataListResult<T> result) {
      callback.onSuccess(describer.describeList(result.getData()));
    }
  }
}
