/*****************************************************************************************
 * *** BEGIN LICENSE BLOCK *****
 *
 * Version: MPL 2.0
 *
 * echocat Jability, Copyright (c) 2013 echocat
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * *** END LICENSE BLOCK *****
 ****************************************************************************************/

package org.echocat.jability.impl;

import org.echocat.jability.property.Property;

import static org.echocat.jability.property.support.PropertyUtils.newProperty;

@SuppressWarnings("ConstantNamingConvention")
public class TestPropertyReferenceA {

    public static final Property<Boolean> testPropA1 = newProperty(Boolean.class, TestPropertyReferenceA.class, "testPropA1");
    public static final Property<String> testPropA2 = newProperty(String.class, TestPropertyReferenceA.class, "testPropA2", "hello world! a");
    public static final Property<String> bestPropA3 = newProperty(String.class, TestPropertyReferenceA.class, "bestPropA3");

    private TestPropertyReferenceA() {}
}
