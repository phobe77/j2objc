/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.devtools.j2objc.ast;

import com.google.devtools.j2objc.jdt.BindingConverter;
import com.google.devtools.j2objc.types.Types;

import org.eclipse.jdt.core.dom.ITypeBinding;

import javax.lang.model.type.TypeMirror;

/**
 * Node type for string literals.
 */
public class StringLiteral extends Expression {

  private String literalValue = null;
  private final TypeMirror typeMirror;

  public StringLiteral(org.eclipse.jdt.core.dom.StringLiteral jdtNode) {
    super(jdtNode);
    literalValue = jdtNode.getLiteralValue();
    ITypeBinding typeBinding = BindingConverter.wrapBinding(jdtNode.resolveTypeBinding());
    typeMirror = BindingConverter.getType(typeBinding);
  }

  public StringLiteral(StringLiteral other) {
    super(other);
    literalValue = other.getLiteralValue();
    typeMirror = other.getTypeMirror();
  }

  public StringLiteral(String literalValue, Types typeEnv) {
    this.literalValue = literalValue;
    typeMirror = BindingConverter.getType(typeEnv.resolveJavaType("java.lang.String"));
  }

  @Override
  public Kind getKind() {
    return Kind.STRING_LITERAL;
  }

  @Override
  public TypeMirror getTypeMirror() {
    return typeMirror;
  }

  public String getLiteralValue() {
    return literalValue;
  }

  @Override
  protected void acceptInner(TreeVisitor visitor) {
    visitor.visit(this);
    visitor.endVisit(this);
  }

  @Override
  public StringLiteral copy() {
    return new StringLiteral(this);
  }
}
