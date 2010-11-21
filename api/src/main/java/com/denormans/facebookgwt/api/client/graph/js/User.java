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

import com.google.gwt.core.client.JsArray;

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
    String birthdayText = getBirthdayJS();
    return birthdayText != null ? FBDateTimeFormats.sBirthdayFormat.parse(birthdayText) : null;
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
}
