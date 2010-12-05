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
import com.denormans.facebookgwt.api.client.graph.js.Postable;
import com.denormans.facebookgwt.api.client.graph.js.School;
import com.denormans.facebookgwt.api.client.graph.js.SimpleGraphObject;
import com.denormans.facebookgwt.api.client.graph.js.User;
import com.denormans.facebookgwt.api.client.graph.js.Work;
import com.denormans.facebookgwt.api.client.graph.js.WorkPosition;
import com.denormans.facebookgwt.api.shared.graph.ObjectType;
import com.denormans.facebookgwt.samples.client.describe.AbstractObjectDescriber;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescriber;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescription;
import com.denormans.facebookgwt.samples.client.showcase.Action;

import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FBGraphObjectDescribers {
  private Map<ObjectType, ObjectDescriber<? extends FBGraphObject>> objectDescribers = new HashMap<ObjectType, ObjectDescriber<? extends FBGraphObject>>();

  private ObjectDescriber<Work> workDescriber;
  private ObjectDescriber<Education> educationDescriber;
  private ObjectDescriber<Postable> postableDescriber;

  public FBGraphObjectDescribers() {
    registerDescribers();
  }

  private void registerDescribers() {
    objectDescribers.put(ObjectType.Post, new PostDescriber());
    objectDescribers.put(ObjectType.User, new UserDescriber());

    objectDescribers.put(ObjectType.Company, new SimpleGraphObjectDescriber<Company>(ObjectType.Company));
    objectDescribers.put(ObjectType.EducationYear, new SimpleGraphObjectDescriber<EducationYear>(ObjectType.EducationYear));
    objectDescribers.put(ObjectType.Location, new SimpleGraphObjectDescriber<Location>(ObjectType.Location));
    objectDescribers.put(ObjectType.School, new SimpleGraphObjectDescriber<School>(ObjectType.School));
    objectDescribers.put(ObjectType.WorkPosition, new SimpleGraphObjectDescriber<WorkPosition>(ObjectType.WorkPosition));

    educationDescriber = new EducationDescriber();
    postableDescriber = new PostableDescriber();
    workDescriber = new WorkDescriber();
  }

  private ObjectDescriber<Company> getCompanyDescriber() {
    return getObjectDescriber(ObjectType.Company);
  }

  private ObjectDescriber<Education> getEducationDescriber() {
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

  public ObjectDescriber<Postable> getPostableDescriber() {
    return postableDescriber;
  }

  private ObjectDescriber<School> getSchoolDescriber() {
    return getObjectDescriber(ObjectType.School);
  }

  public ObjectDescriber<User> getUserDescriber() {
    return getObjectDescriber(ObjectType.User);
  }

  private ObjectDescriber<Work> getWorkDescriber() {
    return workDescriber;
  }

  private ObjectDescriber<WorkPosition> getWorkPositionDescriber() {
    return getObjectDescriber(ObjectType.WorkPosition);
  }

  @SuppressWarnings({"unchecked"})
  public <T extends FBGraphObject> ObjectDescriber<T> getObjectDescriber(final ObjectType objectType) {
    return (ObjectDescriber<T>)objectDescribers.get(objectType);
  }

  private static class SimpleGraphObjectDescriber<T extends SimpleGraphObject> extends FBGraphObjectDescriber<T> {
    public SimpleGraphObjectDescriber(final ObjectType objectType) {
      super(objectType);
    }

    @Override
    protected ObjectDescription<T> describeObject(final T obj) {
      return super.describeObject(obj).addValue("Picture URL", obj.getPictureURL()).addValue("Page URL", obj.getPageURL()).addValue("Category", obj.getCategory()).addValue("Is Community Page?", obj.isCommunityPage()).
          addValue("Fan Count", obj.getFanCount());
    }
  }

  private static abstract class FBGraphObjectDescriber<T extends FBGraphObject> extends AbstractGraphObjectDescriber<T> {
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
    public String getObjectTypeName(final T obj) {
      return getObjectType().name();
    }

    @Override
    protected ObjectDescription<T> describeObject(final T obj) {
      return new GraphObjectDescription<T>(obj, this, getObjectType()).addValue("ID", obj.getID()).addValue("Name", obj.getName()).
          addAction("Get " + getObjectTypeName(obj), new Action<T, ObjectDescription<T>>() {
            @Override
            public void execute(final T obj, final AsyncCallback<ObjectDescription<T>> callback) {
              retrieveItem(obj.getID(), new ObjectTransformingCallback<T>(AbstractGraphObjectDescriber.this, callback));
            }
          });
    }

    protected abstract ObjectType getObjectType();

    protected void retrieveItem(final String itemID, final AsyncCallback<T> callback) {
      FBGWT.Graph.retrieveItem(itemID, null, callback);
    }
  }

  private static class ObjectTransformingCallback<T extends FBJSObject> implements AsyncCallback<T> {
    private final ObjectDescriber<T> describer;
    private final AsyncCallback<ObjectDescription<T>> callback;

    public ObjectTransformingCallback(final ObjectDescriber<T> describer, final AsyncCallback<ObjectDescription<T>> callback) {
      this.describer = describer;
      this.callback = callback;
    }

    @Override
    public void onFailure(final Throwable caught) {
      callback.onFailure(caught);
    }

    @Override
    public void onSuccess(final T result) {
      callback.onSuccess(describer.describe(result));
    }
  }

  private static class ListTransformingCallback<T extends FBJSObject> implements AsyncCallback<FBGraphDataListResult<T>> {
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

  private static class PostDescriber extends FBGraphObjectDescriber<Post> {
    public PostDescriber() {
      super(ObjectType.Post);
    }

    @Override
    protected ObjectDescription<Post> describeObject(final Post obj) {
      return super.describeObject(obj);
    }
  }

  private static class PostableDescriber extends AbstractObjectDescriber<Postable> {
    @Override
    protected ObjectDescription<Postable> describeObject(final Postable obj) {
      // todo: get postables by type
      return null;
    }

    @Override
    public String getObjectTypeName(final Postable obj) {
      // todo: get the exact type
      return "Postable";
    }
  }

  private class UserDescriber extends FBGraphObjectDescriber<User> {
    public UserDescriber() {
      super(ObjectType.User);
    }

    @Override
    protected ObjectDescription<User> describeObject(final User obj) {
      final ObjectDescriber<User> describer = this;
      TimeZone timeZone = obj.getTimeZone();
      return super.describeObject(obj).addValue("First Name", obj.getFirstName()).addValue("Last Name", obj.getLastName()).addValue("Link", obj.getLink()).addValue("About", obj.getAbout()).addValue("Birthday", obj.getBirthday()).
          addValue("Work", getWorkDescriber().describeList(obj.getWork())).addValue("Education", getEducationDescriber().describeList(obj.getEducation())).addValue("Email", obj.getEmail()).addValue("Website", obj.getWebsite()).
          addValue("Location", getLocationDescriber().describe(obj.getLocation())).addValue("Biography", obj.getBiography()).addValue("Quotes", obj.getQuotes()).addValue("Gender", obj.getGender()).
          addValue("Interested in", obj.getInterestedIn()).addValue("Seeking", obj.getSeeking()).addValue("Religion", obj.getReligion()).addValue("Political Affiliation", obj.getPoliticalAffiliation()).
          addValue("Verified", obj.isVerified()).addValue("Significant Other", describe(obj.getSignificantOther())).addValue("Time Zone", timeZone != null ? timeZone.getID() : null).addValue("Third-Party ID", obj.getThirdPartyID()).
          addValue("Locale", obj.getLocale()).addValue("Updated Time", obj.getUpdatedTime()).
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
          }).
          addAction("Tagged In", new Action<User, List<ObjectDescription<Postable>>>() {
            @Override
            public void execute(final User obj, final AsyncCallback<List<ObjectDescription<Postable>>> callback) {
              FBGWT.Graph.retrieveUserTaggedIn(obj.getID(), null, new ListTransformingCallback<Postable>(getPostableDescriber(), callback));
            }
          }).
          addAction("Posts", new Action<User, List<ObjectDescription<Post>>>() {
            @Override
            public void execute(final User obj, final AsyncCallback<List<ObjectDescription<Post>>> callback) {
              FBGWT.Graph.retrieveUserPosts(obj.getID(), null, new ListTransformingCallback<Post>(getPostDescriber(), callback));
            }
          }).
          addAction("Friends", new Action<User, List<ObjectDescription<User>>>() {
            @Override
            public void execute(final User obj, final AsyncCallback<List<ObjectDescription<User>>> callback) {
              FBGWT.Graph.retrieveUserFriends(obj.getID(), null, new ListTransformingCallback<User>(describer, callback));
            }
          });
    }

    @Override
    protected void retrieveItem(final String itemID, final AsyncCallback<User> callback) {
      FBGWT.Graph.retrieveUser(itemID, null, callback);
    }
  }

  private class EducationDescriber extends AbstractObjectDescriber<Education> {
    @Override
    public String getObjectTypeName(final Education obj) {
      return "Education";
    }
    @Override
    public ObjectDescription<Education> describeObject(final Education obj) {
      return new ObjectDescription<Education>(obj, this).addValue("School", getSchoolDescriber().describe(obj.getSchool())).addValue("Year", getEducationYearDescriber().describe(obj.getYear())).addValue("Type", obj.getType());
    }

  }

  private class WorkDescriber extends AbstractObjectDescriber<Work> {
    @Override
    public String getObjectTypeName(final Work obj) {
      return "Work";
    }

    @Override
    public ObjectDescription<Work> describeObject(final Work obj) {
      return new ObjectDescription<Work>(obj, this).addValue("Employer", getCompanyDescriber().describe(obj.getEmployer())).addValue("Location", getLocationDescriber().describe(obj.getLocation())).
          addValue("Position", getWorkPositionDescriber().describe(obj.getPosition())).addValue("Start Date", obj.getStartDate()).addValue("End Date", obj.getEndDate());
    }
  }
}
