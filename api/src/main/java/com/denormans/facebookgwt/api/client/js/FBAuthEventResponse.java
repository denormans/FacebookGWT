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

package com.denormans.facebookgwt.api.client.js;

import com.denormans.facebookgwt.api.shared.FBExtendedPermission;
import com.denormans.facebookgwt.api.shared.FBUserStatus;

import com.google.gwt.core.client.JavaScriptObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FBAuthEventResponse extends JavaScriptObject {
  protected FBAuthEventResponse() {
  }

  public final FBUserStatus getConvertedStatus() {
    return FBUserStatus.valueFromApiValue(getStatus());
  }

  public final native String getStatus() /*-{
    return this.status;
  }-*/;

  public final native FBSession getSession() /*-{
    return this.session;
  }-*/;

  public final List<FBExtendedPermission> getConvertedPermissions() {
    return convertPermissionsFromApiValues(getPermissions());
  }

  private static List<FBExtendedPermission> convertPermissionsFromApiValues(final List<String> permissionApiValues) {
    List<FBExtendedPermission> permissions = new ArrayList<FBExtendedPermission>(permissionApiValues.size());

    for (final String permissionApiValue : permissionApiValues) {
      permissions.add(FBExtendedPermission.valueFromApiValue(permissionApiValue));
    }

    return permissions;
  }

  public final List<String> getPermissions() {
    return splitPermissionValues(getPermissionsJS());
  }

  private static List<String> splitPermissionValues(final String permissionApiValues) {
    return Arrays.asList(permissionApiValues.split(","));
  }

  private native String getPermissionsJS() /*-{
    return this.perms;
  }-*/;
}
