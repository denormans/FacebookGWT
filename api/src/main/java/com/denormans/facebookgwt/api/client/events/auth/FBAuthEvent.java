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

package com.denormans.facebookgwt.api.client.events.auth;

import com.denormans.facebookgwt.api.client.js.FBAuthEventResponse;
import com.denormans.facebookgwt.api.client.js.FBSession;
import com.denormans.facebookgwt.api.shared.FBExtendedPermission;
import com.denormans.facebookgwt.api.shared.FBUserStatus;

import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

public abstract class FBAuthEvent<H extends FBAuthHandler> extends GwtEvent<H> {
  private FBUserStatus userStatus;
  private FBSession session;
  private List<FBExtendedPermission> permissions;
  private FBAuthEventResponse apiResponse;

  protected FBAuthEvent(final FBAuthEventResponse apiResponse) {
    this(apiResponse.getStatus(), apiResponse.getSession(), apiResponse.getPermissions());
    this.apiResponse = apiResponse;
  }

  protected FBAuthEvent(final FBUserStatus userStatus, final FBSession session, final List<FBExtendedPermission> permissions) {
    this.userStatus = userStatus;
    this.session = session;
    this.permissions = permissions;
  }

  public FBUserStatus getUserStatus() {
    return userStatus;
  }

  public FBSession getSession() {
    return session;
  }

  public List<FBExtendedPermission> getPermissions() {
    return permissions;
  }

  public FBAuthEventResponse getApiResponse() {
    return apiResponse;
  }
}
