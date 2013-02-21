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

import org.echocat.jability.Capability;

import static org.echocat.jability.support.CapabilityUtils.newCapability;

@SuppressWarnings("ConstantNamingConvention")
public class TestCapabilityReferenceB {

    public static final Capability<Boolean> testCapB1 = newCapability(Boolean.class, TestCapabilityReferenceB.class, "testCapB1");
    public static final Capability<String> testCapB2 = newCapability(String.class, TestCapabilityReferenceB.class, "testCapB2", "hello world! b");
    public static final Capability<String> bestCapB3 = newCapability(String.class, TestCapabilityReferenceA.class, "bestCapB3");

    private TestCapabilityReferenceB() {}
}
