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

package com.denormans.facebookgwt.api.client.graph.model;

import com.denormans.facebookgwt.api.client.common.FBDateTimeFormats;
import com.denormans.facebookgwt.api.shared.graph.EventRSVPStatus;
import com.denormans.facebookgwt.api.shared.graph.EventRSVPStatuses;
import com.denormans.facebookgwt.api.shared.graph.PrivacyType;
import com.denormans.facebookgwt.api.shared.graph.PrivacyTypes;

import java.util.Date;

public class Event extends FBGraphObject {
  protected Event() {
  }

  public final native User getOwner() /*-{
    return this.owner;
  }-*/;

  public final native String getDescription() /*-{
    return this.description;
  }-*/;

  public final Date getStartTime() {
    return FBDateTimeFormats.parseDateTime(FBDateTimeFormats.RFC3339Format, getStartTimeJS());
  }

  private native String getStartTimeJS() /*-{
    return this.start_time;
  }-*/;

  public final Date getEndTime() {
    return FBDateTimeFormats.parseDateTime(FBDateTimeFormats.RFC3339Format, getEndTimeJS());
  }

  private native String getEndTimeJS() /*-{
    return this.end_time;
  }-*/;

  public final native String getLocation() /*-{
    return this.location;
  }-*/;

  public final native Address getVenue() /*-{
    return this.venue;
  }-*/;

  public final PrivacyType getPrivacy() {
    return PrivacyTypes.valueFromApiValue(getPrivacyJS());
  }

  private native String getPrivacyJS() /*-{
    return this.privacy;
  }-*/;

  public final EventRSVPStatus getRSVPStatus() {
    return EventRSVPStatuses.valueFromApiValue(getRSVPStatusJS());
  }

  private native String getRSVPStatusJS() /*-{
    return this.rsvp_status;
  }-*/;
}
