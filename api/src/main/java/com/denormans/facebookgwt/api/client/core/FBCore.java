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

package com.denormans.facebookgwt.api.client.core;

import com.denormans.facebookgwt.api.client.FBIntegration;
import com.denormans.facebookgwt.api.client.common.events.FBEventType;
import com.denormans.facebookgwt.api.client.core.events.FBLogEvent;
import com.denormans.facebookgwt.api.client.common.events.FBLogHandler;
import com.denormans.facebookgwt.api.client.common.events.HasFBLogHandler;
import com.denormans.facebookgwt.api.client.common.js.FBEventResponse;
import com.denormans.facebookgwt.api.client.core.js.FBLogEventResponse;

import com.google.gwt.event.shared.HandlerRegistration;

public final class FBCore extends FBIntegration implements HasFBLogHandler {
  @Override
  protected void handleFBEvent(final FBEventType eventType, final FBEventResponse apiResponse) {
    switch (eventType) {
      case Log:
        FBLogEvent.fire(this, apiResponse.<FBLogEventResponse>cast());
        break;

      default:
        super.handleFBEvent(eventType, apiResponse);
    }
  }

  @Override
  public HandlerRegistration addFBLogHandler(final FBLogHandler handler) {
    return addFBEventHandler(handler, FBLogEvent.getType(), FBEventType.Log);
  }
}
