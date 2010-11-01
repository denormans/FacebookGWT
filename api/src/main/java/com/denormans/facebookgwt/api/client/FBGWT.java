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

package com.denormans.facebookgwt.api.client;

import com.denormans.facebookgwt.api.client.js.FBJSException;
import com.denormans.gwtutil.client.js.JSError;

import com.google.gwt.core.client.GWT;

import java.util.logging.Logger;

public final class FBGWT {
  public static final Logger Log = Logger.getLogger(FBGWT.class.getName());

  public static final FBCore Core = new FBCore();
  public static final FBInitialization Init = new FBInitialization();
  public static final FBAuthentication Auth = new FBAuthentication();
  public static final FBUserInterface UI = new FBUserInterface();

  @SuppressWarnings ( { "ThrowableResultOfMethodCallIgnored" })
  public static void raiseException(final String message) {
    GWT.UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
    if (uncaughtExceptionHandler == null) {
      throw new FBGWTException(message);
    }

    uncaughtExceptionHandler.onUncaughtException(createException(message));
  }

  public static FBGWTException createException(final String message) {
    FBGWTException fbgwtException = new FBGWTException(message);
    fbgwtException.fillInStackTrace();
    return fbgwtException;
  }

  @SuppressWarnings ( { "UnusedDeclaration", "ThrowableResultOfMethodCallIgnored" })
  public static void raiseException(final JSError error) {
    GWT.UncaughtExceptionHandler uncaughtExceptionHandler = GWT.getUncaughtExceptionHandler();
    if (uncaughtExceptionHandler == null) {
      throw new FBJSException(error);
    }

    uncaughtExceptionHandler.onUncaughtException(createException(error));
  }

  public static FBJSException createException(final JSError error) {
    FBJSException fbjsException = new FBJSException(error);
    fbjsException.fillInStackTrace();
    return fbjsException;
  }
}
