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

public class FBInitOptions extends EnhancedJavaScriptObject {
  /**
   * Create an empty init options, to be filled in.
   *
   * @return the empty init options
   */
  public static FBInitOptions create() {
    return createObject().cast();
  }

  /**
   * Create a basic init options with the defaults.
   *
   * @param applicationID The application ID
   *
   * @return the init options
   */
  public static FBInitOptions create(final String applicationID) {
    FBInitOptions initOptions = create();

    initOptions.setApplicationID(applicationID);

    return initOptions;
  }

  /**
   * Create an init options for use with social widgets.
   *
   * @param applicationID The application ID
   * @param parseXFBMLTags Whether or not to parse XFBML tags
   *
   * @return the init options
   */
  public static FBInitOptions create(final String applicationID, final boolean parseXFBMLTags) {
    FBInitOptions initOptions = create();

    initOptions.setApplicationID(applicationID);
    initOptions.setParseXFBMLTags(parseXFBMLTags);

    return initOptions;
  }

  protected FBInitOptions() {
  }

  public final native String getApplicationID() /*-{
    return this.appId;
  }-*/;

  public final native void setApplicationID(final String applicationID) /*-{
    this.appId = applicationID;
  }-*/;

  public final native boolean getEnableCookieSupport() /*-{
    return this.cookie;
  }-*/;

  public final native void setEnableCookieSupport(final boolean enableCookieSupport) /*-{
    this.cookie = enableCookieSupport;
  }-*/;

  public final native boolean getEnableLogging() /*-{
    return this.logging;
  }-*/;

  public final native void setEnableLogging(final boolean enableLogging) /*-{
    this.logging = enableLogging;
  }-*/;

  public final native FBSession getSession() /*-{
    return this.session;
  }-*/;

  public final native void setSession(final FBSession session) /*-{
    this.session = session;
  }-*/;

  public final native boolean getFetchStatus() /*-{
    return this.status;
  }-*/;

  public final native void setFetchStatus(final boolean fetchStatus) /*-{
    this.status = fetchStatus;
  }-*/;

  public final native boolean getParseXFBMLTags() /*-{
    return this.xfbml;
  }-*/;

  public final native void setParseXFBMLTags(final boolean parseXFBMLTags) /*-{
    this.xfbml = parseXFBMLTags;
  }-*/;
}
