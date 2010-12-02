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

package com.denormans.facebookgwt.samples.client.describe;

import com.denormans.facebookgwt.samples.client.showcase.Action;
import com.denormans.facebookgwt.samples.client.showcase.NamedAction;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectDescription<T> {
  private Map<String, Object> values = new LinkedHashMap<String, Object>();
  private List<NamedAction<? extends T, ?>> actions = new ArrayList<NamedAction<? extends T, ?>>();

  private T value;
  private ObjectDescriber<T> describer;

  public ObjectDescription(final T value, final ObjectDescriber<T> describer) {
    this.value = value;
    this.describer = describer;
  }

  public T getValue() {
    return value;
  }

  public ObjectDescriber<T> getDescriber() {
    return describer;
  }

  public ObjectDescription<T> addValue(final String name, final Object value) {
    values.put(name, value);
    return this;
  }

  public ObjectDescription<T> addValues(final Map<String, ?> values) {
    this.values.putAll(values);
    return this;
  }

  public ObjectDescription<T> addValues(final ObjectDescription<?> description) {
    for (Map.Entry<String, Object> entry : description.getValues()) {
      values.put(entry.getKey(), entry.getValue());
    }
    return this;
  }

  public Set<Map.Entry<String, Object>> getValues() {
    return Collections.unmodifiableSet(values.entrySet());
  }

  public <V extends T, R> ObjectDescription<T> addAction(final String name, final Action<V, R> action) {
    return addAction(new NamedAction<V, R>() {
      @Override
      public String getName() {
        return name;
      }

      @Override
      public void execute(final V obj, final AsyncCallback<R> callback) {
        action.execute(obj, callback);
      }
    });
  }

  public ObjectDescription<T> addAction(final NamedAction<? extends T, ?> action) {
    actions.add(action);
    return this;
  }

  public List<NamedAction<? extends T, ?>> getActions() {
    return Collections.unmodifiableList(actions);
  }

  @Override
  public String toString() {
    return values.toString();
  }
}
