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
 * Represents a value add event.
 * 
 * @param <I> the value about to be added
 */
public class ValueAddEvent<I> extends GwtEvent<ValueAddHandler<I>> {

  /**
   * Handler type.
   */
  private static Type<ValueAddHandler<?>> TYPE;

  /**
   * Fires a value add event on all registered handlers in the handler
   * manager. If no such handlers exist, this method will do nothing.
   * 
   * @param <I> the value type
   * @param source the source of the handlers
   * @param value the value
   */
  public static <I> void fire(HasValueAddHandlers<I> source, I value) {
    if (TYPE != null) {
      ValueAddEvent<I> event = new ValueAddEvent<I>(value);
      source.fireEvent(event);
    }
  }

  /**
   * Gets the type associated with this event.
   * 
   * @return returns the handler type
   */
  public static Type<ValueAddHandler<?>> getType() {
    if (TYPE == null) {
      TYPE = new Type<ValueAddHandler<?>>();
    }
    return TYPE;
  }

  private final I value;

  /**
   * Creates a value add event.
   * 
   * @param value the value
   */
  protected ValueAddEvent(I value) {
    this.value = value;
  }

  @SuppressWarnings({"unchecked"})
  @Override
  public final Type<ValueAddHandler<I>> getAssociatedType() {
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
  protected void dispatch(ValueAddHandler<I> handler) {
    handler.onValueAdd(this);
  }
}
