/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.resources.ext;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.typeinfo.JClassType;

import java.net.URL;

/**
 * Allows ResourceGenerators to indicate how their generated resources may be
 * affected by their execution environment. An instance of this type will be
 * provided to the ResourceGenerator via the {@link ResourceGenerator#prepare}
 * method.
 */
public interface ClientBundleRequirements {

  /**
   * Indicates that the ResourcePrototype implementation generated by a
   * ResourceGenerator is sensitive to the values of the specified
   * configuration property.
   *
   * @param propertyName the name of the configuration property
   * @throws BadPropertyValueException
   */
  void addConfigurationProperty(String propertyName)
      throws BadPropertyValueException;

  /**
   * Indicates that the ResourcePrototype implementation generated by a
   * ResourceGenerator is sensitive to the value of the specified
   * deferred-binding property. This method should be called when the behavior
   * of the ResourcePrototype must differ between permutations of the compiled
   * output. For example, some resource implementations may be sensitive to the
   * <code>user.agent</code> deferred-binding property, and would call this
   * method with the literal string <code>user.agent</code>.
   * <p>
   * If a deferred-binding property does not exist, an attempt is made to check
   * whether a configuration property by the same name exists.
   *
   * @param propertyName the name of the deferred-binding property
   * @throws BadPropertyValueException if <code>propertyName</code> is neither a
   *           valid deferred-binding property nor a valid configuration
   *           property.
   */
  void addPermutationAxis(String propertyName) throws BadPropertyValueException;

  /**
   * Indicates that the ResourcePrototype implementation generated by a
   * ResourceGenerator is sensitive to a dependent resource.  This method takes
   * both an unresolved <param>partialPath</param> and a located
   * <param>resolvedResourceUrl</param>, since the resolved location of a
   * resource can change dynamically at run time.  So, by calling this method,
   * the requirement is being declared for both the resolution of the resource's
   * URL, as well as its content.
   * <p>
   * The implementation for resolving a resource url from a partial path is
   * contained in {@link ResourceGeneratorUtil}, and is based on an ordered set
   * of 'locator' implementations, which are tried in sequence.  Example
   * 'locator' implementations include looking up a resource file by name, which
   * usually amounts to a freshly generated temporary file (see
   * {@link ResourceGeneratorUtil#addNamedFile}), or by using the partial path
   * as a classpath resource used by a class loader, which can be affected by
   * classpath shadowing.
   * <p>
   * The current resolution for a resource partial path can be checked via
   * {@link ResourceGeneratorUtil#tryFindResource}.
   *
   * @param partialPath a partial path representing a dependent resource.
   * @param resolvedResourceUrl a located resolved URL for a dependent resource.
   */
  void addResolvedResource(String partialPath, URL resolvedResourceUrl);

  /**
   * Indicates that the ResourcePrototype implementation generated by a
   * ResourceGenerator is sensitive to structural changes to the given type, as
   * well as any of it's super type hierarchy.
   *
   * @param type a type
   */
  void addTypeHierarchy(JClassType type);
}
