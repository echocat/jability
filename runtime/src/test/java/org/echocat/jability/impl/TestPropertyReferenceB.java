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
public class TestPropertyReferenceB {

    public static final Property<Boolean> testPropB1 = newProperty(Boolean.class, TestPropertyReferenceB.class, "testPropB1");
    public static final Property<String> testPropB2 = newProperty(String.class, TestPropertyReferenceB.class, "testPropB2", "hello world! b");
    public static final Property<String> bestPropB3 = newProperty(String.class, TestPropertyReferenceB.class, "bestPropB3");

    private TestPropertyReferenceB() {}
}
