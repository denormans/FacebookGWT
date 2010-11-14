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

package com.denormans.facebookgwt.samples.client.showcase.widgets;

import com.denormans.gwtutil.client.js.EnhancedJSObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class FBObjectDisplay<T> extends Composite implements TakesValue<T> {
  interface FBObjectDisplayUIBinder extends UiBinder<HTMLPanel, FBObjectDisplay> {}
  private static FBObjectDisplayUIBinder sUIBinder = GWT.create(FBObjectDisplayUIBinder.class);

  @UiField SpanElement label;
  @UiField SpanElement objectDetails;

  private T value;

  public FBObjectDisplay() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);
  }

  public FBObjectDisplay(final String labelText) {
    this();
    setLabel(labelText);
  }

  @Override
  public void setValue(final T value) {
    this.value = value;

    if (value instanceof EnhancedJSObject) {
      objectDetails.setInnerText(((EnhancedJSObject) value).toJSONString());
    } else if (value != null) {
      objectDetails.setInnerText(value.toString());
    } else {
      objectDetails.setInnerText("");
    }
  }

  @Override
  public T getValue() {
    return value;
  }

  public void setLabel(final String labelText) {
    label.setInnerText(labelText + ":");
  }
}