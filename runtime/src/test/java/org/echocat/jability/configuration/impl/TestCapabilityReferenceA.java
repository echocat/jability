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

package org.echocat.jability.configuration.impl;

import org.echocat.jability.Capability;

import static org.echocat.jability.support.CapabilityUtils.newCapability;

@SuppressWarnings("ConstantNamingConvention")
public class TestCapabilityReferenceA {

    public static final Capability<Boolean> testCapA1 = newCapability(Boolean.class, TestCapabilityReferenceA.class, "testCapA1");
    public static final Capability<String> testCapA2 = newCapability(String.class, TestCapabilityReferenceA.class, "testCapA2", "hello world! a");
    public static final Capability<String> bestCapA3 = newCapability(String.class, TestCapabilityReferenceA.class, "bestCapA3");

    private TestCapabilityReferenceA() {}
}
