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

package com.denormans.facebookgwt.api.client.graph.options;

/**
 * Options for <a href="http://developers.facebook.com/docs/reference/api/post">Post API</a>
 */
public class FeedPostOptions extends FBGraphCallOptions {
  public static FeedPostOptions createFeedPostOptions() {
    return createEnhancedObject();
  }

  protected FeedPostOptions() {
  }

  /**
   * @return the message for the post
   */
  public final native String getMessage() /*-{
    return this.message;
  }-*/;

  /**
   * Sets the message for the post
   * @param message message string
   * @return a reference to this object
   */
  public final native FeedPostOptions setMessage(final String message) /*-{
    this.message = message;
    return this;
  }-*/;

  /**
   * @return If available, a link to the picture included with this post
   */
  public final native String getPictureURL() /*-{
    return this.picture;
  }-*/;

  /**
   * Sets a link to the picture included with this post
   * @param picture string containing the image URL
   * @return a reference to this object
   */
  public final native FeedPostOptions setPictureURL(final String picture) /*-{
    this.picture = picture;
    return this;
  }-*/;

  /**
   * @return the link attached to this post
   */
  public final native String getLink() /*-{
    return this.link;
  }-*/;

  /**
   * Sets the link attached to this post
   * @param link string containing the URL
   * @return a reference to this object
   */
  public final native FeedPostOptions setLink(final String link) /*-{
    this.link = link;
    return this;
  }-*/;

  /**
   * @return the name of the link
   */
  public final native String getName() /*-{
    return this.link;
  }-*/;

  /**
   * Sets the name of the link
   * @param name name of the link
   * @return a reference to this object
   */
  public final native FeedPostOptions setName(final String name) /*-{
    this.name = name;
    return this;
  }-*/;

  /**
   * @return the caption of the link (appears beneath the link name)
   */
  public final native String getCaption() /*-{
    return this.caption;
  }-*/;

  /**
   * Sets the caption of the link (appears beneath the link name)
   * @param caption caption of the link
   * @return 
   */
  public final native FeedPostOptions setCaption(final String caption) /*-{
    this.caption = caption;
    return this;
  }-*/;

  /**
   * @return a description of the link (appears beneath the link caption)
   */
  public final native String getDescription() /*-{
    return this.description;
  }-*/;

  /**
   * Sets a description of the link (appears beneath the link caption)
   * @param description description of the link
   * @return 
   */
  public final native FeedPostOptions setDescription(final String description) /*-{
    this.description = description;
    return this;
  }-*/;}
