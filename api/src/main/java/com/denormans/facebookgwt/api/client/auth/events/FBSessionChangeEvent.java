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

import com.denormans.facebookgwt.api.client.auth.js.FBAuthEventResponse;

public class FBSessionChangeEvent extends FBAuthEvent<FBSessionChangeHandler> {
  private static Type<FBSessionChangeHandler> sType;

  /**
   * Fires a {@link FBSessionChangeEvent} on all registered handlers in the handler
   * manager. If no such handlers exist, this method will do nothing.
   *
   * @param source the source of the handlers
   * @param apiResponse the Facebook JS API response
   */
  public static void fire(HasFBSessionChangeHandler source, final FBAuthEventResponse apiResponse) {
    if (sType != null) {
      FBSessionChangeEvent event = new FBSessionChangeEvent(apiResponse);
      source.fireEvent(event);
    }
  }

  /**
   * Gets the type associated with this event.
   *
   * @return returns the handler type
   */
  public static Type<FBSessionChangeHandler> getType() {
    if (sType == null) {
      sType = new Type<FBSessionChangeHandler>();
    }

    return sType;
  }

  protected FBSessionChangeEvent(final FBAuthEventResponse apiResponse) {
    super(apiResponse);
  }

  @Override
  public Type<FBSessionChangeHandler> getAssociatedType() {
    return sType;
  }

  @Override
  protected void dispatch(final FBSessionChangeHandler handler) {
    handler.onFBSessionChange(this);
  }
}
