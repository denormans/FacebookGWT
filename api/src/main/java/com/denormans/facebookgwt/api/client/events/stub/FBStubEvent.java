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

package com.denormans.facebookgwt.api.client.events.stub;

import com.denormans.facebookgwt.api.client.js.FBAuthEventResponse;
import com.denormans.facebookgwt.api.client.js.FBSession;
import com.denormans.facebookgwt.api.client.events.auth.FBAuthEvent;
import com.denormans.facebookgwt.api.shared.FBExtendedPermission;
import com.denormans.facebookgwt.api.shared.FBUserStatus;

import java.util.List;

public class FBStubEvent extends FBAuthEvent<FBStubHandler> {
  private static Type<FBStubHandler> sType;

  /**
   * Fires a {@link FBStubEvent} on all registered handlers in the handler
   * manager. If no such handlers exist, this method will do nothing.
   *
   * @param source the source of the handlers
   * @param apiResponse the Facebook JS API response
   */
  public static void fire(HasFBStubHandler source, final FBAuthEventResponse apiResponse) {
    if (sType != null) {
      FBStubEvent event = new FBStubEvent(apiResponse);
      source.fireEvent(event);
    }
  }

  /**
   * Fires a {@link FBStubEvent} on all registered handlers in the handler
   * manager. If no such handlers exist, this method will do nothing.
   *
   * @param source the source of the handlers
   * @param userStatus the user status
   * @param session the session
   * @param permissions the extended permissions
   */
  public static void fire(HasFBStubHandler source, final FBUserStatus userStatus, final FBSession session, final List<FBExtendedPermission> permissions) {
    if (sType != null) {
      FBStubEvent event = new FBStubEvent(userStatus, session, permissions);
      source.fireEvent(event);
    }
  }

  /**
   * Gets the type associated with this event.
   *
   * @return returns the handler type
   */
  public static Type<FBStubHandler> getType() {
    if (sType == null) {
      sType = new Type<FBStubHandler>();
    }

    return sType;
  }

  protected FBStubEvent(final FBAuthEventResponse apiResponse) {
    super(apiResponse);
  }

  protected FBStubEvent(final FBUserStatus userStatus, final FBSession session, final List<FBExtendedPermission> permissions) {
    super(userStatus, session, permissions);
  }

  @Override
  public Type<FBStubHandler> getAssociatedType() {
    return sType;
  }

  @Override
  protected void dispatch(final FBStubHandler handler) {
    handler.onFBStub(this);
  }
}
