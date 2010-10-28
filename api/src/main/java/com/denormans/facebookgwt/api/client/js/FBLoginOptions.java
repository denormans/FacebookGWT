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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FBLoginOptions extends EnhancedJavaScriptObject {
  public static FBLoginOptions create() {
    return FBLoginOptions.createObject().cast();
  }

  public static FBLoginOptions create(final Collection<FBExtendedPermission> permissions) {
    FBLoginOptions loginOptions = create();

    loginOptions.setPermissions(permissions);

    return loginOptions;
  }

  public static FBLoginOptions create(final FBExtendedPermission... permissions) {
    FBLoginOptions loginOptions = create();

    loginOptions.setPermissions(permissions);

    return loginOptions;
  }

  protected FBLoginOptions() {
  }

  public final List<FBExtendedPermission> getPermissions() {
    return FBExtendedPermission.valuesFromApiValues(getApiPermissions());
  }

  public final List<String> getApiPermissions() {
    return FBExtendedPermission.splitApiValues(getPermissionsJS());
  }

  private native String getPermissionsJS() /*-{
    return this.perms || "";
  }-*/;

  public final void setPermissions(final FBExtendedPermission... permissions) {
    setPermissions(Arrays.asList(permissions));
  }

  public final void setPermissions(final Collection<FBExtendedPermission> permissions) {
    setApiPermissions(FBExtendedPermission.toApiValues(permissions));
  }

  public final void setApiPermissions(final String... permissions) {
    setApiPermissions(Arrays.asList(permissions));
  }

  public final void setApiPermissions(final Collection<String> permissions) {
    String permissionsText = FBExtendedPermission.joinApiValues(permissions);
    setPermissionsJS(permissionsText);
  }

  private native void setPermissionsJS(final String permissions) /*-{
    this.perms = permissions;
  }-*/;
}
