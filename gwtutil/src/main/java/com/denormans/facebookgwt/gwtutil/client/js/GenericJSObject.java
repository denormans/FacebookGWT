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

package com.denormans.facebookgwt.gwtutil.client.js;

import com.google.gwt.core.client.JavaScriptObject;

public class GenericJSObject extends EnhancedJSObject {
  public static GenericJSObject createGenericJSObject() {
    return EnhancedJSObject.createEnhancedObject();
  }

  protected GenericJSObject() {
  }

  public final native GenericJSObject setProperty(final String name, final JavaScriptObject value) /*-{
    this[name] = value;
    return this;
  }-*/;

  public final native GenericJSObject setProperty(final String name, final double value) /*-{
    this[name] = value;
    return this;
  }-*/;

  public final native GenericJSObject setProperty(final String name, final int value) /*-{
    this[name] = value;
    return this;
  }-*/;

  public final native GenericJSObject setProperty(final String name, final boolean value) /*-{
    this[name] = value;
    return this;
  }-*/;

  public final native GenericJSObject setProperty(final String name, final String value) /*-{
    this[name] = value;
    return this;
  }-*/;

  public final native <T extends JavaScriptObject> T getObjectProperty(final String name) /*-{
    return this[name];
  }-*/;

  public final native double getDoubleProperty(final String name) /*-{
    return this[name];
  }-*/;

  public final native int getIntegerProperty(final String name) /*-{
    return this[name];
  }-*/;

  public final native boolean getBooleanProperty(final String name) /*-{
    return this[name];
  }-*/;

  public final native String getStringProperty(final String name) /*-{
    return this[name];
  }-*/;
}
