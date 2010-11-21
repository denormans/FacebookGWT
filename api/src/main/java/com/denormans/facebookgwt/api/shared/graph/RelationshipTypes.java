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

package com.denormans.facebookgwt.api.shared.graph;

import com.denormans.facebookgwt.api.shared.FBEnumCreator;

import java.util.List;
import java.util.Map;

public enum RelationshipTypes implements RelationshipType {
  Friendship("Friendship"),
  Dating("Dating"),
  Relationship("A Relationship"),
  Networking("Networking")
  ;

  private static final Map<String, RelationshipTypes> sRelationshipTypesByApiValue = Util.createFBEnumByApiValueMap(RelationshipTypes.class);
  private static final FBEnumCreator<RelationshipType> sRelationshipTypeCreator = new RelationshipTypeCreator();

  private String apiValue;

  private RelationshipTypes(final String apiValue) {
    this.apiValue = apiValue;
  }

  public static List<RelationshipType> valuesFromApiValues(final List<String> apiValues) {
    return Util.valuesFromApiValues(sRelationshipTypesByApiValue, apiValues, sRelationshipTypeCreator);
  }

  public static RelationshipType valueFromApiValue(final String apiValue) {
    return Util.valueFromApiValue(sRelationshipTypesByApiValue, apiValue, sRelationshipTypeCreator);
  }

  public static List<RelationshipType> parseApiValues(final String apiValues) {
    return valuesFromApiValues(Util.splitApiValues(apiValues));
  }

  @Override
  public String getApiValue() {
    return apiValue;
  }

  private static class RelationshipTypeCreator implements FBEnumCreator<RelationshipType> {
    @Override
    public RelationshipType create(final String apiValue) {
      return new RelationshipType() {
        @Override
        public String getApiValue() {
          return apiValue;
        }

        @Override
        public String toString() {
          return apiValue;
        }
      };
    }
  }
}
