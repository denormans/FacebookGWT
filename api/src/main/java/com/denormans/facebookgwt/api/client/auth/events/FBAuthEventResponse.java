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

package com.denormans.facebookgwt.api.client.auth.events;

import com.denormans.facebookgwt.api.client.auth.FBSession;
import com.denormans.facebookgwt.api.client.auth.Permissions;
import com.denormans.facebookgwt.api.client.common.events.FBEventResponse;
import com.denormans.facebookgwt.api.shared.auth.*;

import java.util.List;

public class FBAuthEventResponse extends FBEventResponse {
  protected FBAuthEventResponse() {
  }

  public final FBUserStatus getStatus() {
    return FBUserStatuses.valueFromApiValue(getApiStatus());
  }

  public final native String getApiStatus() /*-{
    return this.status;
  }-*/;

  public final native boolean hasSession() /*-{
    return this.authResponse != null;
  }-*/;

  public final boolean isConnected() {
    return hasSession() && getStatus() == FBUserStatuses.Connected;
  }

  public final native FBSession getSession() /*-{
    return this.authResponse;
  }-*/;

  public final List<FBPermission> getPermissions() {
    return Permissions.parseApiValues(getPermissionsJS());
  }

  private native String getPermissionsJS() /*-{
    return this.scope || "";
  }-*/;
}
