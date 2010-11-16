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
import com.denormans.facebookgwt.api.client.graph.js.FBGraphObject;
import com.denormans.facebookgwt.api.client.graph.js.FBUser;
import com.denormans.facebookgwt.api.client.graph.js.Work;
import com.denormans.facebookgwt.samples.client.describe.AbstractObjectDescriber;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescriber;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescription;

public class FBObjectDescribers {
  private static ObjectDescriber<FBGraphObject> sGenericGraphObjectDescriber = new FBGraphObjectDescriber<FBGraphObject>();
  private static ObjectDescriber<FBUser> sFBUserDescriber;
  private static ObjectDescriber<Work> sWorkDescriber;
  private static ObjectDescriber<Education> sEducationDescriber;

  public static ObjectDescriber<FBGraphObject> getGenericGraphObjectDescriber() {
    if (sGenericGraphObjectDescriber == null) {
      sGenericGraphObjectDescriber = new FBGraphObjectDescriber<FBGraphObject>();
    }

    return sGenericGraphObjectDescriber;
  }

  public static ObjectDescriber<FBUser> getFBUserDescriber() {
    if (sFBUserDescriber == null) {
      sFBUserDescriber = new FBGraphObjectDescriber<FBUser>() {
        @Override
        public ObjectDescription describe(final FBUser obj) {
          if (obj == null) {
            return null;
          }

          return super.describe(obj).addValue("First Name", obj.getFirstName()).addValue("Last Name", obj.getLastName()).addValue("Link", obj.getLink()).
              addValue("About", obj.getAbout()).addValue("Birthday", obj.getBirthday()).addValue("Work", getWorkDescriber().describeList(obj.getWork())).addValue("Education", getEducationDescriber().describeList(obj.getEducation())).addValue("Email", obj.getEmail());
        }
      };
    }

    return sFBUserDescriber;
  }

  public static ObjectDescriber<Work> getWorkDescriber() {
    if (sWorkDescriber == null) {
      sWorkDescriber = new AbstractObjectDescriber<Work>() {
        @Override
        public ObjectDescription describe(final Work obj) {
          if (obj == null) {
            return null;
          }

          ObjectDescriber<FBGraphObject> genericGraphObjectDescriber = getGenericGraphObjectDescriber();
          return new ObjectDescription().addValue("Employer", genericGraphObjectDescriber.describe(obj.getEmployer())).addValue("Location", genericGraphObjectDescriber.describe(obj.getLocation())).
              addValue("Position", genericGraphObjectDescriber.describe(obj.getPosition())).addValue("Start Date", obj.getStartDate()).addValue("End Date", obj.getEndDate());
        }
      };
    }

    return sWorkDescriber;
  }

  public static ObjectDescriber<Education> getEducationDescriber() {
    if (sEducationDescriber == null) {
      sEducationDescriber = new AbstractObjectDescriber<Education>() {
          @Override
          public ObjectDescription describe(final Education obj) {
            if (obj == null) {
              return null;
            }

            ObjectDescriber<FBGraphObject> genericGraphObjectDescriber = getGenericGraphObjectDescriber();
            return new ObjectDescription().addValue("School", genericGraphObjectDescriber.describe(obj.getSchool())).addValue("Year", genericGraphObjectDescriber.describe(obj.getYear())).addValue("Type", obj.getType());
          }
        };
    }

    return sEducationDescriber;
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
