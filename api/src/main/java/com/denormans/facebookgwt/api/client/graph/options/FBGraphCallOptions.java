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

import com.denormans.facebookgwt.api.client.common.FBOptions;
import com.denormans.facebookgwt.api.shared.FBEnum;
import com.denormans.facebookgwt.api.shared.graph.ObjectField;
import com.denormans.facebookgwt.api.shared.graph.ObjectFields;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class FBGraphCallOptions extends FBOptions {
  public static FBGraphCallOptions createGraphCallOptions() {
    return createEnhancedObject();
  }

  protected FBGraphCallOptions() {
  }

  public final List<String> getIDs() {
    return FBEnum.Util.splitApiValues(getIDsJS());
  }

  public final FBGraphCallOptions setIDs(final String... ids) {
    setIDs(Arrays.asList(ids));
    return this;
  }

  public final FBGraphCallOptions setIDs(final Collection<String> ids) {
    setIDsJS(FBEnum.Util.joinApiValues(ids));
    return this;
  }

  private native String getIDsJS() /*-{
    return this.ids;
  }-*/;

  private native void setIDsJS(final String ids) /*-{
    this.ids = ids;
  }-*/;

  public final List<ObjectField> getFields() {
    return ObjectFields.parseApiValues(getFieldsJS());
  }

  public final FBGraphCallOptions setFields(final ObjectField... fields) {
    setFields(Arrays.asList(fields));
    return this;
  }

  public final FBGraphCallOptions setFields(final Collection<ObjectField> fields) {
    setFieldsJS(FBEnum.Util.joinApiValues(FBEnum.Util.toApiValues(fields)));
    return this;
  }

  private native String getFieldsJS() /*-{
    return this.fields;
  }-*/;

  private native void setFieldsJS(final String fields) /*-{
    this.fields = fields;
  }-*/;

  public final native int getQueryLimit() /*-{
    return this.limit;
  }-*/;

  public final native FBGraphCallOptions setQueryLimit(final int limit) /*-{
    this.limit = limit;
    return this;
  }-*/;

  public final native int getQueryOffset() /*-{
    return this.offset;
  }-*/;

  public final native FBGraphCallOptions setQueryOffset(final int offset) /*-{
    this.offset = offset;
    return this;
  }-*/;

  public final native Date getQueryUntil() /*-{
    return this.until;
  }-*/;

  public final native FBGraphCallOptions setQueryUntil(final Date until) /*-{
    this.until = until;
    return this;
  }-*/;

  public final native Date getQuerySince() /*-{
    return this.since;
  }-*/;

  public final native FBGraphCallOptions setQuerySince(final Date since) /*-{
    this.since = since;
    return this;
  }-*/;

  public final native boolean getIncludeMetadata() /*-{
    return this.metadata == 1;
  }-*/;

  public final native FBGraphCallOptions setIncludeMetadata(final boolean includeMetadata) /*-{
    if (includeMetadata) {
      this.metadata = 1;
    } else {
      delete this.metadata;
    }
    return this;
  }-*/;
}
