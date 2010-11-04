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

package com.denormans.facebookgwt.api.client.auth.js;

import com.denormans.facebookgwt.api.client.js.FBJSObject;
import com.denormans.facebookgwt.api.shared.FBPermission;

import com.google.gwt.core.client.JsArrayString;

import java.util.ArrayList;
import java.util.List;

public class FBPermissions extends FBJSObject {
  protected FBPermissions() {
  }

  public final List<FBPermission> getAllPermissions() {
    List<FBPermission> allPermissions = new ArrayList<FBPermission>();
    allPermissions.addAll(getExtendedPermissions());
    allPermissions.addAll(getUserPermissions());
    allPermissions.addAll(getFriendPermissions());
    return allPermissions;
  }

  public final List<String> getAllApiPermissions() {
    List<String> allPermissions = new ArrayList<String>();
    allPermissions.addAll(getExtendedApiPermissions());
    allPermissions.addAll(getUserApiPermissions());
    allPermissions.addAll(getFriendApiPermissions());
    return allPermissions;
  }

  public final List<FBPermission> getExtendedPermissions() {
    return FBPermission.valuesFromApiValues(getExtendedApiPermissions());
  }

  public final List<String> getExtendedApiPermissions() {
    return convertJSArrayStringToList(getExtendedApiPermissionsJS());
  }

  private native JsArrayString getExtendedApiPermissionsJS() /*-{
    return this.extended || [];
  }-*/;

  public final List<FBPermission> getUserPermissions() {
    return FBPermission.valuesFromApiValues(getUserApiPermissions());
  }

  public final List<String> getUserApiPermissions() {
    return convertJSArrayStringToList(getUserApiPermissionsJS());
  }

  private native JsArrayString getUserApiPermissionsJS() /*-{
    return this.user || [];
  }-*/;

  public final List<FBPermission> getFriendPermissions() {
    return FBPermission.valuesFromApiValues(getFriendApiPermissions());
  }

  public final List<String> getFriendApiPermissions() {
    return convertJSArrayStringToList(getFriendApiPermissionsJS());
  }

  private native JsArrayString getFriendApiPermissionsJS() /*-{
    return this.friends || [];
  }-*/;
}
