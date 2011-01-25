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

import com.denormans.facebookgwt.api.shared.graph.ObjectCategories;
import com.denormans.facebookgwt.api.shared.graph.ObjectCategory;

public abstract class SimpleGraphObject extends FBGraphObject {
  protected SimpleGraphObject() {
  }

  public final native String getUsername() /*-{
    return this.username;
  }-*/;

  public final native String getDescription() /*-{
    return this.description;
  }-*/;

  public final native String getPictureURL() /*-{
    return this.picture;
  }-*/;

  public final native String getPageURL() /*-{
    return this.link;
  }-*/;

  public final native String getWebsite() /*-{
    return this.website;
  }-*/;

  public final ObjectCategory getCategory() {
    return ObjectCategories.valueFromApiValue(getCategoryJS());
  }

  private native String getCategoryJS() /*-{
    return this.category;
  }-*/;

  public final native boolean isCommunityPage() /*-{
    return this.is_community_page == true;
  }-*/;

  public final native boolean canPost() /*-{
    return this.can_post == true;
  }-*/;

  public final int getNumLikes() {
    return getNumLikesJS();
  }

  public final native String getHometown() /*-{
    return this.hometown;
  }-*/;

  public final native String getCurrentLocation() /*-{
    return this.current_location;
  }-*/;

  public final native String getBiography() /*-{
    return this.bio;
  }-*/;

  public final native String getReleaseDate() /*-{
    return this.release_date;
  }-*/;

  public final native String getAwards() /*-{
    return this.awards;
  }-*/;
}
