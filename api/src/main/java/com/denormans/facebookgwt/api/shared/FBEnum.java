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

package com.denormans.facebookgwt.api.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface FBEnum {
  public String getApiValue();

  public static class Util {

    public static List<String> toApiValues(final Collection<? extends FBEnum> enumValues) {
      if (enumValues == null) {
        return null;
      }
      List<String> apiValues = new ArrayList<String>(enumValues.size());
      for (final FBEnum enumValue : enumValues) {
        apiValues.add(enumValue.getApiValue());
      }
      return apiValues;
    }

    public static String joinApiValues(final Collection<String> apiValues) {
      if (apiValues == null) {
        return "";
      }

      StringBuilder builder = new StringBuilder();
      boolean isFirst = true;
      for (final String apiValue : apiValues) {
        if (!isFirst) {
          builder.append(",");
        } else {
          isFirst = false;
        }
        builder.append(apiValue);
      }

      return builder.toString();
    }
  }
}
