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

import com.denormans.facebookgwt.api.client.auth.FBAuthentication;
import com.denormans.facebookgwt.api.client.core.FBCore;
import com.denormans.facebookgwt.api.client.graph.FBGraph;
import com.denormans.facebookgwt.api.client.init.FBInitialization;
import com.denormans.facebookgwt.api.client.legacy.FBLegacy;
import com.denormans.facebookgwt.api.client.ui.FBUserInterface;

public final class FBGWT {
  public static final FBCore Core = new FBCore();
  public static final FBInitialization Init = new FBInitialization();
  public static final FBAuthentication Auth = new FBAuthentication();
  public static final FBGraph Graph = new FBGraph();
  public static final FBUserInterface UI = new FBUserInterface();
  public static final FBLegacy Legacy = new FBLegacy();

  private FBGWT() {
  }
}
