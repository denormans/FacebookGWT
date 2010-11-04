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

package com.denormans.facebookgwt.api.client.common.js;

import com.google.gwt.core.client.JsArray;

import java.util.Arrays;
import java.util.List;

public class Attachment extends FBJSObject {
  public static Attachment createAttachment() {
    return createEnhancedObject();
  }

  protected Attachment() {
  }

  public final native String getName() /*-{
    return this.name;
  }-*/;

  public final native Attachment setName(final String name) /*-{
    this.name = name;
    return this;
  }-*/;

  public final native String getCaption() /*-{
    return this.caption;
  }-*/;

  public final native Attachment setCaption(final String caption) /*-{
    this.caption = caption;
    return this;
  }-*/;

  public final native String getDescription() /*-{
    return this.description;
  }-*/;

  public final native Attachment setDescription(final String description) /*-{
    this.description = description;
    return this;
  }-*/;

  public final native String getHref() /*-{
    return this.href;
  }-*/;

  public final native Attachment setHref(final String href) /*-{
    this.href = href;
    return this;
  }-*/;

  public final List<Media> getMedia() {
    return convertJsArrayToList(getMediaJS());
  }

  private native JsArray<Media> getMediaJS() /*-{
    return this.media;
  }-*/;

  public final Attachment setMedia(final Media... media) {
    return setMedia(Arrays.asList(media));
  }

  public final Attachment setMedia(final List<Media> media) {
    setMediaJS(convertListToJsArray(media));
    return this;
  }

  private native void setMediaJS(final JsArray<Media> media) /*-{
    this.media = media;
  }-*/;
}
