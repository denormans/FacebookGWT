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
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FBEnum {
  public String getApiValue();

  public static class Util {
    /**
     * Returns a list of API values from the given collection of enum values.
     */
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

    /**
     * Splits the given comma-separated string into a list of strings.
     */
    public static List<String> splitApiValues(final String apiValues) {
      return splitApiValues(apiValues, ",");
    }

    /**
     * Splits the given string into a list of strings with the given token.
     */
    public static List<String> splitApiValues(final String apiValues, final String token) {
      return Arrays.asList(apiValues.split(token));
    }

    /**
     * Join the given API values with a comma (,).
     */
    public static String joinApiValues(final Iterable<String> apiValues) {
      return joinApiValues(apiValues, ",");
    }

    /**
     * Join the given API values with the given token.
     */
    public static String joinApiValues(final Iterable<String> apiValues, final String token) {
      if (apiValues == null) {
        return "";
      }

      StringBuilder builder = new StringBuilder();
      boolean isFirst = true;
      for (final String apiValue : apiValues) {
        if (!isFirst) {
          builder.append(token);
        } else {
          isFirst = false;
        }
        builder.append(apiValue);
      }

      return builder.toString();
    }

    /**
     * Create a map of API values to FB enums.
     */
    @SuppressWarnings ( { "unchecked" })
    public static <T extends Enum & FBEnum> Map<String, T> createFBEnumByApiValueMap(final Class<T> fbEnumClass) {
      HashMap<String, T> fbEnumValues = new HashMap<String, T>();

      for (final Object e : EnumSet.allOf(fbEnumClass)) {
        T fbEnum = (T)e;
        fbEnumValues.put(fbEnum.getApiValue(), fbEnum);
      }

      return fbEnumValues;
    }

    /**
     * Get a value from the given map of FB enums by API values, using the optional enum creator to create a non-null API value that doesn't have a corresponding enum value.
     */
    public static <T extends FBEnum> T valueFromApiValue(final Map<String, ? extends T> fbEnumValuesByApiValue, final String apiValue, final FBEnumCreator<? extends T> fbEnumCreator) {
      if (apiValue == null) {
        return null;
      }

      T fbEnum = fbEnumValuesByApiValue.get(apiValue);
      if (fbEnum != null) {
        return fbEnum;
      }

      if (fbEnumCreator == null) {
        return null;
      }

      // Unknown non-null API value
      return fbEnumCreator.create(apiValue);
    }

    /**
     * Converts the values from the API values, using the optional enum creator to create any non-null API values that don't have corresponding enum values.
     */
    public static <T extends FBEnum> List<T> valuesFromApiValues(final Map<String, ? extends T> fbEnumValuesByApiValue, final List<String> apiValues, final FBEnumCreator<? extends T> fbEnumCreator) {
      List<T> fbEnums = new ArrayList<T>(apiValues.size());

      for (final String apiValue : apiValues) {
        T fbEnum = valueFromApiValue(fbEnumValuesByApiValue, apiValue, fbEnumCreator);
        if (fbEnum != null) {
          fbEnums.add(fbEnum);
        }
      }

      return fbEnums;
    }
  }

}
