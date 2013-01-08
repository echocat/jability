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

package org.echocat.jability;

import org.junit.Test;

import static org.echocat.jability.CompoundCapabilityDefinitionProvider.capabilityDefinitionBy;
import static org.echocat.jability.TestCapabilityDefinition.capA;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.isSameAs;

public class CompoundCapabilityDefinitionProviderUnitTest {

    @Test
    public void test() throws Exception {
        final TestCapabilityDefinition definitionA = capabilityDefinitionBy(TestCapabilityDefinition.class, TestCapabilityDefinition.class.getPackage().getName() + "." + capA);
        assertThat(definitionA, isSameAs(capA));
    }

}
