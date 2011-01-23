/*
 * Copyright (C) 2011 deNormans
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

import com.denormans.facebookgwt.api.client.common.FBJSObject;

public class Address extends FBJSObject {
  protected Address() {
  }

  public final native String getStreet() /*-{
    return this.street;
  }-*/;

  public final native String getCity() /*-{
    return this.city;
  }-*/;

  public final native String getState() /*-{
    return this.state;
  }-*/;

  public final native String getCountry() /*-{
    return this.country;
  }-*/;

  public final native String getPostalCode() /*-{
    return this.zip;
  }-*/;

  public final native boolean hasLatitudeAndLongitude() /*-{
    return this.latitude !== null && this.latitude !== undefined && this.longitude !== null && this.longitude !== undefined;
  }-*/;

  public final native double getLatitude() /*-{
    return this.latitude || 0;
  }-*/;

  public final native double getLongitude() /*-{
    return this.longitude || 0;
  }-*/;
}
