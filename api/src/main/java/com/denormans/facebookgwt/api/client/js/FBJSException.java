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

package com.denormans.facebookgwt.api.client.js;

import com.denormans.facebookgwt.api.client.FBGWTException;
import com.denormans.gwtutil.client.js.JSError;

public class FBJSException extends FBGWTException {
  private JSError javaScriptError;

  public FBJSException(final JSError javaScriptError) {
    this.javaScriptError = javaScriptError;
  }

  public FBJSException(final String message, final JSError javaScriptError) {
    super(message);
    this.javaScriptError = javaScriptError;
  }

  public FBJSException(final String message, final Throwable cause, final JSError javaScriptError) {
    super(message, cause);
    this.javaScriptError = javaScriptError;
  }

  public FBJSException(final Throwable cause, final JSError javaScriptError) {
    super(cause);
    this.javaScriptError = javaScriptError;
  }

  public JSError getJavaScriptError() {
    return javaScriptError;
  }

  @Override
  public String getMessage() {
    String message = super.getMessage();
    String jsErrorMessage = getJavaScriptError().getDetailMessage();
    if (message != null && message.length() > 0) {
      return message + " - " + jsErrorMessage;
    } else {
      return jsErrorMessage;
    }
  }
}
