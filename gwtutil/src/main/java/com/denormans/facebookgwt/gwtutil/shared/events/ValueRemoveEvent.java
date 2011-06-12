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

package com.denormans.facebookgwt.gwtutil.shared.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Represents a value remove event.
 * 
 * @param <I> the value about to be removed
 */
public class ValueRemoveEvent<I> extends GwtEvent<ValueRemoveHandler<I>> {

  /**
   * Handler type.
   */
  private static Type<ValueRemoveHandler<?>> TYPE;

  /**
   * Fires a value remove event on all registered handlers in the handler
   * manager. If no such handlers exist, this method will do nothing.
   * 
   * @param <I> the value type
   * @param source the source of the handlers
   * @param value the value
   */
  public static <I> void fire(HasValueRemoveHandlers<I> source, I value) {
    if (TYPE != null) {
      ValueRemoveEvent<I> event = new ValueRemoveEvent<I>(value);
      source.fireEvent(event);
    }
  }

  /**
   * Gets the type associated with this event.
   * 
   * @return returns the handler type
   */
  public static Type<ValueRemoveHandler<?>> getType() {
    if (TYPE == null) {
      TYPE = new Type<ValueRemoveHandler<?>>();
    }
    return TYPE;
  }

  private final I value;

  /**
   * Creates a value remove event.
   * 
   * @param value the value
   */
  protected ValueRemoveEvent(I value) {
    this.value = value;
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public final Type<ValueRemoveHandler<I>> getAssociatedType() {
    return (Type) TYPE;
  }

  /**
   * Gets the value.
   * 
   * @return the value
   */
  public I getValue() {
    return value;
  }
 
  @Override
  public String toDebugString() {
    return super.toDebugString() + getValue();
  }

  @Override
  protected void dispatch(ValueRemoveHandler<I> handler) {
    handler.onValueRemove(this);
  }
}
