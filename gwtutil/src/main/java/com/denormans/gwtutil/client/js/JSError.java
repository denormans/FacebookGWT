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

package com.denormans.gwtutil.client.js;

import com.google.gwt.core.client.JsArrayString;

import java.util.List;

public class JSError extends EnhancedJSObject {
  protected JSError() {
  }

  public final String getDetailMessage() {
    String detailMessage = getMessage();

    String filename = getFileName();
    int lineNumber = getLineNumber();

    if (filename != null && filename.length() > 0) {
      return detailMessage + " (" + filename + ", line " + lineNumber + ")";
    } else {
      return detailMessage;
    }
  }

  public final native String getMessage() /*-{
    return this.message || "";
  }-*/;

  public final native String getFileName() /*-{
    return this.fileName || "";
  }-*/;

  public final native int getLineNumber() /*-{
    return this.lineNumber || 0;
  }-*/;

  public final List<String> getStack() {
    return convertJSArrayStringToList(getStackJS());
  }

  private final native JsArrayString getStackJS() /*-{
    return this.stack || [];
  }-*/;
}
