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

package com.denormans.facebookgwt.api.shared.graph;

import com.denormans.facebookgwt.api.shared.FBEnum;

import java.util.ArrayList;
import java.util.List;

public enum Gender implements FBEnum {
  Female("female"),
  Male("male");

  private String apiValue;

  private Gender(final String apiValue) {
    this.apiValue = apiValue;
  }

  public static List<Gender> valuesFromApiValues(final List<String> apiValues) {
    List<Gender> genders = new ArrayList<Gender>(apiValues.size());

    for (final String apiValue : apiValues) {
      Gender fbEnum = valueFromApiValue(apiValue);
      if (fbEnum != null) {
        genders.add(fbEnum);
      }
    }

    return genders;
  }

  public static Gender valueFromApiValue(final String apiValue) {
    if (Female.getApiValue().equals(apiValue)) {
      return Female;
    }

    if (Male.getApiValue().equals(apiValue)) {
      return Male;
    }

    return null;
  }

  public static List<Gender> parseApiValues(final String apiValues) {
    return valuesFromApiValues(Util.splitApiValues(apiValues));
  }

  public String getApiValue() {
    return apiValue;
  }
}
