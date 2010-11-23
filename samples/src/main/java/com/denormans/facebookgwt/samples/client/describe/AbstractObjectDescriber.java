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

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObjectDescriber<T> implements ObjectDescriber<T> {
  @Override
  public ObjectDescription<T> describe(final T obj) {
    if (obj == null) {
      return null;
    }

    return describeObject(obj);
  }

  protected abstract ObjectDescription<T> describeObject(final T obj);

  @Override
  public List<ObjectDescription<T>> describeList(final List<T> list) {
    if (list == null) {
      return null;
    }

    List<ObjectDescription<T>> descriptions = new ArrayList<ObjectDescription<T>>();

    for (final T obj : list) {
      ObjectDescription<T> description = describe(obj);
      if (description != null) {
        descriptions.add(description);
      }
    }

    return descriptions;
  }
}
