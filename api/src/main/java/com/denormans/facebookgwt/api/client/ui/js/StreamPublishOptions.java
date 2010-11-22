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

package com.denormans.facebookgwt.api.client.ui.js;

import com.google.gwt.core.client.JsArray;

import java.util.Arrays;
import java.util.List;

public class StreamPublishOptions extends FBUIMethodOptions {
  public static StreamPublishOptions createStreamPublishOptions() {
    return createEnhancedObject();
  }

  public static StreamPublishOptions createStreamPublishOptions(final String message) {
    return createStreamPublishOptions().setMessage(message);
  }

  protected StreamPublishOptions() {
  }

  public final native String getMessage() /*-{
    return this.message;
  }-*/;

  public final native StreamPublishOptions setMessage(final String message) /*-{
    this.message = message;
    return this;
  }-*/;

  public final native Attachment getAttachment() /*-{
    return this.attachment;
  }-*/;

  public final native StreamPublishOptions setAttachment(final Attachment attachment) /*-{
    this.attachment = attachment;
    return this;
  }-*/;

  public final List<Link> getActionLinks() {
    return convertJsArrayToList(getActionLinksJS());
  }

  private native JsArray<Link> getActionLinksJS() /*-{
    return this.action_links;
  }-*/;

  public final StreamPublishOptions setActionLinks(final Link... actionLinks) {
    setActionLinksJS(convertListToJsArray(Arrays.asList(actionLinks)));
    return this;
  }

  public final StreamPublishOptions setActionLinks(final List<Link> actionLinks) {
    setActionLinksJS(convertListToJsArray(actionLinks));
    return this;
  }

  private native void setActionLinksJS(final JsArray<Link> actionLinks) /*-{
    this.action_links = actionLinks;
  }-*/;

  public final native String getUserMessagePrompt() /*-{
    return this.user_message_prompt;
  }-*/;

  public final native StreamPublishOptions setUserMessagePrompt(final String userMessagePrompt) /*-{
    this.user_message_prompt = userMessagePrompt;
    return this;
  }-*/;
}
