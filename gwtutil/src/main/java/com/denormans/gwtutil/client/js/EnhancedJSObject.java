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

import com.denormans.gwtutil.shared.Transformer;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.json.client.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EnhancedJSObject extends JavaScriptObject {
  public static <T extends EnhancedJSObject> T createEnhancedObject() {
    return createObject().<T>cast();
  }

  protected EnhancedJSObject() {
  }

  public static List<String> convertJSArrayStringToList(final JsArrayString jsArray) {
    return convertJSArrayStringToList(jsArray, Transformer.IdentityTransformer.<String>get());
  }

  public static <T> List<T> convertJSArrayStringToList(final JsArrayString jsArray, final Transformer<String, T> transformer) {
    int stackSize = jsArray.length();
    List<T> list = new ArrayList<T>(stackSize);
    for (int index = 0; index < stackSize; index++) {
      final String originalValue = jsArray.get(index);
      T value = transformer.transform(originalValue);
      list.add(value);
    }
    return list;
  }

  public final String getJSONString() {
    return new JSONObject(this).toString();
  }
}
