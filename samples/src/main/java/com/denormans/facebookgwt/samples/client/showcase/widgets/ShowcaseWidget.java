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

import com.denormans.facebookgwt.api.client.common.events.FBEvent;
import com.denormans.facebookgwt.samples.client.FacebookGWTSamples;
import com.denormans.gwtutil.client.js.EnhancedJSObject;

import com.google.gwt.user.client.ui.Composite;

public abstract class ShowcaseWidget extends Composite {
  protected void addApiEventMessage(final String message, final FBEvent<?, ?> event) {
    FacebookGWTSamples.get().getShowcase().addApiEventMessage(message, event);
  }

  protected void addApiEventMessage(final String message, final EnhancedJSObject apiResponse) {
    FacebookGWTSamples.get().getShowcase().addApiEventMessage(message, apiResponse);
  }

  protected void addApiEventMessage(final String message, final Object apiResponse) {
    FacebookGWTSamples.get().getShowcase().addApiEventMessage(message, apiResponse);
  }

  protected void handleError(final String message) {
    FacebookGWTSamples.get().handleError(message);
  }

  protected void handleError(final String message, final Throwable error) {
    FacebookGWTSamples.get().handleError(message, error);
  }
}
