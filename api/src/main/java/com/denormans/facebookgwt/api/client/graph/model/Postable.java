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

import com.google.gwt.core.client.JsArray;

import java.util.List;

public abstract class Postable extends FBGraphObject {
  protected Postable() {
  }

  public final native User getFrom() /*-{
    return this.from;
  }-*/;

  /**
   * Returns an ID for the postable that can be used in likes/unlikes and responses.
   * This is necessary because the ID returned from this.id doesn't work in other API calls.
   *
   * @return The fully-qualified ID
   */
  public final String getFullPostableID() {
    User user = getFrom();
    String fromID = user != null ? user.getID() : null;
    return getIDScopedByUserID(fromID);
  }

  public final List<Comment> getComments() {
    return convertJsArrayToList(getCommentsJS());
  }

  private native JsArray<Comment> getCommentsJS() /*-{
    return this.comments != null ? this.comments.data : null;
  }-*/;

  public final List<User> getLikes() {
    return convertJsArrayToList(getLikesJS());
  }

  private native JsArray<User> getLikesJS() /*-{
    return this.likes != null ? this.likes.data : null;
  }-*/;

  public final int getNumLikes() {
    return getNumLikesJS();
  }
}
