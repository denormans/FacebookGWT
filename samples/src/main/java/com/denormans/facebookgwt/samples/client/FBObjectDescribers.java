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

package com.denormans.facebookgwt.samples.client;

import com.denormans.facebookgwt.api.client.graph.js.Education;
import com.denormans.facebookgwt.api.client.graph.js.EducationYear;
import com.denormans.facebookgwt.api.client.graph.js.FBGraphObject;
import com.denormans.facebookgwt.api.client.graph.js.Location;
import com.denormans.facebookgwt.api.client.graph.js.Post;
import com.denormans.facebookgwt.api.client.graph.js.School;
import com.denormans.facebookgwt.api.client.graph.js.User;
import com.denormans.facebookgwt.api.client.graph.js.Work;
import com.denormans.facebookgwt.api.client.graph.js.WorkPosition;
import com.denormans.facebookgwt.samples.client.describe.AbstractObjectDescriber;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescriber;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescription;

public final class FBObjectDescribers {
  private static FBObjectDescribers sInstance;

  private ObjectDescriber<? extends FBGraphObject> genericGraphObjectDescriber;

  private ObjectDescriber<User> userDescriber;
  private ObjectDescriber<Work> workDescriber;
  private ObjectDescriber<Education> educationDescriber;
  private ObjectDescriber<Post> postDescriber;

  public static FBObjectDescribers get() {
    if (sInstance == null) {
      sInstance = new FBObjectDescribers();
    }
    return sInstance;
  }

  private FBObjectDescribers() {
  }

  @SuppressWarnings ( { "unchecked" })
  public <T extends FBGraphObject> ObjectDescriber<T> getGenericGraphObjectDescriber() {
    if (genericGraphObjectDescriber == null) {
      genericGraphObjectDescriber = new FBGraphObjectDescriber<FBGraphObject>();
    }

    return (ObjectDescriber<T>) genericGraphObjectDescriber;
  }

  public ObjectDescriber<Education> getEducationDescriber() {
    if (educationDescriber == null) {
      educationDescriber = new AbstractObjectDescriber<Education>() {
          @Override
          public ObjectDescription describe(final Education obj) {
            if (obj == null) {
              return null;
            }

            return new ObjectDescription().addValue("School", getSchoolDescriber().describe(obj.getSchool())).addValue("Year", getEducationYearDescriber().describe(obj.getYear())).addValue("Type", obj.getType());
          }
        };
    }

    return educationDescriber;
  }

  public ObjectDescriber<EducationYear> getEducationYearDescriber() {
    return getGenericGraphObjectDescriber();
  }

  public ObjectDescriber<Location> getLocationDescriber() {
    return getGenericGraphObjectDescriber();
  }

  public ObjectDescriber<Post> getPostDescriber() {
    if (postDescriber == null) {
      postDescriber = new FBGraphObjectDescriber<Post>() {
        @Override
        public ObjectDescription describe(final Post obj) {
          if (obj == null) {
            return null;
          }

          return super.describe(obj);
        }
      };
    }

    return postDescriber;
  }

  public ObjectDescriber<School> getSchoolDescriber() {
    return getGenericGraphObjectDescriber();
  }

  public ObjectDescriber<User> getUserDescriber() {
    if (userDescriber == null) {
      userDescriber = new FBGraphObjectDescriber<User>() {
        @Override
        public ObjectDescription describe(final User obj) {
          if (obj == null) {
            return null;
          }

          return super.describe(obj).addValue("First Name", obj.getFirstName()).addValue("Last Name", obj.getLastName()).addValue("Link", obj.getLink()). addValue("About", obj.getAbout()).addValue("Birthday", obj.getBirthday()).
              addValue("Work", getWorkDescriber().describeList(obj.getWork())).addValue("Education", getEducationDescriber().describeList(obj.getEducation())).addValue("Email", obj.getEmail()).addValue("Website", obj.getWebsite()).
              addValue("Location", getLocationDescriber().describe(obj.getLocation())).addValue("Biography", obj.getBiography()).addValue("Quotes", obj.getQuotes()).addValue("Gender", obj.getGender()).
              addValue("Interested in", obj.getInterestedIn()).addValue("Seeking", obj.getSeeking());
        }
      };
    }

    return userDescriber;
  }

  public ObjectDescriber<Work> getWorkDescriber() {
    if (workDescriber == null) {
      workDescriber = new AbstractObjectDescriber<Work>() {
        @Override
        public ObjectDescription describe(final Work obj) {
          if (obj == null) {
            return null;
          }

          ObjectDescriber<FBGraphObject> genericGraphObjectDescriber = getGenericGraphObjectDescriber();
          return new ObjectDescription().addValue("Employer", genericGraphObjectDescriber.describe(obj.getEmployer())).addValue("Location", getLocationDescriber().describe(obj.getLocation())).
              addValue("Position", getWorkPositionDescriber().describe(obj.getPosition())).addValue("Start Date", obj.getStartDate()).addValue("End Date", obj.getEndDate());
        }
      };
    }

    return workDescriber;
  }

  public ObjectDescriber<WorkPosition> getWorkPositionDescriber() {
    return getGenericGraphObjectDescriber();
  }

  private static class FBGraphObjectDescriber<T extends FBGraphObject> extends AbstractObjectDescriber<T> {
    @Override
    public ObjectDescription describe(final T obj) {
      if (obj == null) {
        return null;
      }

      return new ObjectDescription().addValue("ID", obj.getID()).addValue("Name", obj.getName());
    }
  }
}
