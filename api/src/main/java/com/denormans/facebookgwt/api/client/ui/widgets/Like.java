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

package com.denormans.facebookgwt.api.client.ui.widgets;

import com.denormans.facebookgwt.api.shared.ui.widgets.LikeAction;
import com.denormans.facebookgwt.api.shared.ui.widgets.ColorScheme;
import com.denormans.facebookgwt.api.shared.ui.widgets.LikeLayout;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;


public class Like extends Widget {
  private LikeLayout layout;
  private LikeAction action;
  private ColorScheme colorScheme;

  public Like() {
    Element likeElement = DOM.createElement("fb:like");
    setElement(likeElement);
  }

  public Like(final String href) {
    this();
    
    setHref(href);
  }

  public String getHref() {
    return getElement().getAttribute("href");
  }

  public void setHref(final String href) {
    getElement().setAttribute("href", href);
  }

  public LikeLayout getLayout() {
    return layout;
  }

  public void setLayout(final LikeLayout layout) {
    this.layout = layout;
    getElement().setAttribute("layout", layout.getApiValue());
  }

  public boolean getShowFaces() {
    return Boolean.parseBoolean(getElement().getAttribute("show_faces"));
  }

  public void setShowFaces(final boolean showFaces) {
    getElement().setAttribute("show_faces", Boolean.toString(showFaces));
  }

  public Integer getWidth() {
    String widthText = getElement().getAttribute("width");
    return widthText != null ? Integer.parseInt(widthText) : null;
  }

  public void setWidth(final int width) {
    getElement().setAttribute("width", Integer.toString(width));
  }

  public LikeAction getAction() {
    return action;
  }

  public void setAction(final LikeAction action) {
    this.action = action;
    getElement().setAttribute("action", action.getApiValue());
  }

  public String getFont() {
    return getElement().getAttribute("font");
  }

  public void setFont(final String font) {
    getElement().setAttribute("font", font);
  }

  public ColorScheme getColorScheme() {
    return colorScheme;
  }

  public void setColorScheme(final ColorScheme colorScheme) {
    this.colorScheme = colorScheme;
    getElement().setAttribute("color_scheme", colorScheme.getApiValue());
  }

  public String getRef() {
    return getElement().getAttribute("ref");
  }

  public void setRef(final String ref) {
    getElement().setAttribute("ref", ref);
  }

}
