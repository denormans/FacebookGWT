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

package com.denormans.facebookgwt.samples.client.showcase.widgets;

import com.denormans.facebookgwt.samples.client.FacebookGWTSamples;
import com.denormans.facebookgwt.samples.client.describe.ObjectDescription;
import com.denormans.facebookgwt.samples.client.showcase.NamedAction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FBObjectDisplay<T> extends ShowcaseWidget implements TakesValue<T> {
  interface FBObjectDisplayUIBinder extends UiBinder<HTMLPanel, FBObjectDisplay> {}
  private static FBObjectDisplayUIBinder sUIBinder = GWT.create(FBObjectDisplayUIBinder.class);

  public interface FieldTemplates extends SafeHtmlTemplates {
    @Template("<span><span class='FBGWTLabel'>{0}</span>{1}</span>")
    SafeHtml fieldLabelValue(final String label, final String value);

    @Template("<span>{0}</span>")
    SafeHtml fieldValueOnly(final String value);

    @Template("<span class='FBGWTLabel'>{0}</span>")
    SafeHtml fieldLabelOnly(final String label);
  }

  private static FieldTemplates sFieldTemplates = GWT.create(FieldTemplates.class);

  @UiField SpanElement label;
  @UiField Tree objectTree;
  @UiField SpanElement objectDetails;

  private T value;

  public FBObjectDisplay() {
    HTMLPanel rootElement = sUIBinder.createAndBindUi(this);
    initWidget(rootElement);
  }

  public FBObjectDisplay(final String labelText) {
    this();
    setLabel(labelText);
  }

  @SuppressWarnings ( { "unchecked" })
  @Override
  public void setValue(final T value) {
    this.value = value;

    objectTree.setVisible(false);

    Object obj = value;
    if (value instanceof ObjectDescription) {
      obj = ((ObjectDescription<?>) value).getValue();
      if (obj == null) {
        obj = value;
      }
    }

    if (obj instanceof JavaScriptObject) {
      objectDetails.setInnerText(new JSONObject((JavaScriptObject) obj).toString());
    } else if (obj != null) {
      objectDetails.setInnerText(obj.toString());
    } else {
      objectDetails.setInnerText("");
    }

    objectTree.clear();
    List<TreeItem> treeItems = null;
    if (value instanceof ObjectDescription) {
      treeItems = createTreeItems((ObjectDescription) value);
    } else if (value instanceof List) {
      treeItems = createTreeItems((List<Object>) value);
    } else if (value instanceof JSONObject) {
      treeItems = createTreeItems((JSONObject) value);
    } else if (value instanceof JavaScriptObject) {
      treeItems = createTreeItems((JavaScriptObject) value);
    }

    if (treeItems != null) {
      for (final TreeItem treeItem : treeItems) {
        objectTree.addItem(treeItem);
      }

      objectTree.setVisible(true);
    }
  }

  private List<TreeItem> createTreeItems(final ObjectDescription<?> objectDescription) {
    List<TreeItem> treeItems = new ArrayList<TreeItem>();
    for (final Map.Entry<String, Object> entry : objectDescription.getValues()) {
      treeItems.add(createTreeItem(entry.getKey(), entry.getValue()));
    }

    List<? extends NamedAction<?, ?>> actions = objectDescription.getActions();
    if(!actions.isEmpty()) {
      TreeItem actionsTreeItem = new TreeItem(sFieldTemplates.fieldLabelOnly("Actions..."));
      for (final NamedAction<?, ?> action : actions) {
        actionsTreeItem.addItem(createTreeItem(objectDescription.getValue(), action));
      }
      treeItems.add(actionsTreeItem);
    }
    return treeItems;
  }

  private List<TreeItem> createTreeItems(final JavaScriptObject jsObject) {
    return createTreeItems(new JSONObject(jsObject));
  }

  private List<TreeItem> createTreeItems(final JSONObject jsonObject) {
    List<TreeItem> treeItems = new ArrayList<TreeItem>();
    for (final String key : jsonObject.keySet()) {
      treeItems.add(createTreeItem(key, jsonObject.get(key)));
    }
    return treeItems;
  }

  @SuppressWarnings({"unchecked"})
  private List<TreeItem> createTreeItems(final List<Object> items) {
    List<TreeItem> treeItems = new ArrayList<TreeItem>();
    int itemNumber = 1;
    for (final Object item : items) {
      String name = "Item";
      if (item instanceof ObjectDescription) {
        name = ((ObjectDescription<Object>)item).getDescriber().getObjectTypeName(((ObjectDescription<Object>)item).getValue());
      }
      treeItems.add(createTreeItem(name + " " + (itemNumber++), item));
    }
    return treeItems;
  }

  @SuppressWarnings ( { "unchecked" })
  private TreeItem createTreeItem(final String name, final Object value) {
    if (value == null) {
      return new TreeItem(sFieldTemplates.fieldLabelValue(name + ": ", "null"));
    }

    List<TreeItem> treeItems;
    if (value instanceof ObjectDescription) {
      treeItems = createTreeItems((ObjectDescription) value);
    } else if (value instanceof List) {
      treeItems = createTreeItems((List<Object>) value);
    } else if (value instanceof JSONObject) {
      treeItems = createTreeItems((JSONObject) value);
    } else if (value instanceof JavaScriptObject) {
      treeItems = createTreeItems((JavaScriptObject) value);
    } else {
      return new TreeItem(sFieldTemplates.fieldLabelValue(name + ": ", value.toString()));
    }

    TreeItem treeItem = new TreeItem(sFieldTemplates.fieldLabelOnly((name)));
    for (final TreeItem childTreeItem : treeItems) {
      treeItem.addItem(childTreeItem);
    }
    return treeItem;
  }

  @SuppressWarnings({"unchecked"})
  private TreeItem createTreeItem(final Object obj, final NamedAction action) {
    Button actionButton = new Button(action.getName());
    actionButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        action.execute(obj, new AsyncCallback() {
          @Override
          public void onFailure(final Throwable caught) {
            FacebookGWTSamples.get().handleError("Error executing action", caught);
          }

          @Override
          public void onSuccess(final Object result) {
            setValue((T) result);
            setLabel(action.getName() + " Results");
          }
        });
      }
    });
    return new TreeItem(actionButton);
  }

  @Override
  public T getValue() {
    return value;
  }

  public void setLabel(final String labelText) {
    label.setInnerText(labelText + ":");
  }
}