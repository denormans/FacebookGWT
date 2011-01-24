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

import com.denormans.facebookgwt.api.shared.graph.PostTarget;
import com.denormans.facebookgwt.api.shared.graph.PostTargets;

import com.google.gwt.core.client.JsArray;

import java.util.List;

public class Post extends Postable {
  protected Post() {
  }
  
  public final List<User> getMentionedUsers() {
    return convertJsArrayToList(getMentionedUsersJS());
  }

  public final native JsArray<User> getMentionedUsersJS() /*-{
    return this.to != null ? this.to.data : null;
  }-*/;

  public final native String getMessage() /*-{
    return this.message;
  }-*/;

  public final native String getPictureURL() /*-{
    return this.picture;
  }-*/;

  public final native String getLinkURL() /*-{
    return this.link;
  }-*/;

  public final native String getCaption() /*-{
    return this.caption;
  }-*/;

  public final native String getDescription() /*-{
    return this.description;
  }-*/;

  public final native String getSourceURL() /*-{
    return this.source;
  }-*/;

  public final native String getIconURL() /*-{
    return this.icon;
  }-*/;

  public final native String getApplicationAttribution() /*-{
    return this.attribution;
  }-*/;

  public final List<PostAction> getActions() {
    return convertJsArrayToList(getActionsJS());
  }

  private native JsArray<PostAction> getActionsJS() /*-{
    return this.actions;
  }-*/;

  // todo: add privacy

  public final List<PostTarget> getTargetRestrictions() {
    return PostTargets.parseApiValues(getTargetRestrictionsJS());
  }

  private native String getTargetRestrictionsJS() /*-{
    return this.targets;
  }-*/;
}
