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

package com.denormans.facebookgwt.api.client.core;

import com.denormans.facebookgwt.api.client.FBGWTException;

public class FBAPIException extends FBGWTException {
  private int errorCode;

  public FBAPIException(final int errorCode) {
    this.errorCode = errorCode;
  }

  public FBAPIException(final String message, final int errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public FBAPIException(final String message, final Throwable cause, final int errorCode) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public FBAPIException(final Throwable cause, final int errorCode) {
    super(cause);
    this.errorCode = errorCode;
  }

  public int getErrorCode() {
    return errorCode;
  }

  @Override
  public String getMessage() {
    String message = super.getMessage();
    if (message != null && message.length() > 0) {
      return message + " (" + errorCode + ")";
    } else {
      return "Error: " + errorCode;
    }
  }
}
