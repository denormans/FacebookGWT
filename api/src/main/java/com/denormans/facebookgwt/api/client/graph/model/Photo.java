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

public class Photo extends Postable {
  protected Photo() {
  }

  public final native String getPageURL() /*-{
    return this.link;
  }-*/;

  public final native String getIconURL() /*-{
    return this.icon;
  }-*/;

  public final List<PhotoTag> getTags() {
    return convertJsArrayToList(getTagsJS());
  }

  private native JsArray<PhotoTag> getTagsJS() /*-{
    return this.tags != null ? this.tags.data : null;
  }-*/;

  public final native Image getFullSizeImage() /*-{
    // The full-size image is constructed from the source, height, and width parts
    return this;
  }-*/;

  public final List<Image> getImages() {
    return convertJsArrayToList(getImagesJS());
  }

  private native JsArray<Image> getImagesJS() /*-{
    return this.images;
  }-*/;

  public final native int getAlbumPosition() /*-{
    return this.position || 0;
  }-*/;
}
