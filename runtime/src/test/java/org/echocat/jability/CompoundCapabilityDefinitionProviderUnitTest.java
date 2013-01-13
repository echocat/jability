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

import static org.echocat.jability.direct.DirectJability.capabilityProvider;
import static org.echocat.jability.Test1Capability.*;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.BaseMatchers.isSameAs;
import static org.junit.Assert.fail;

public class CompoundCapabilityDefinitionProviderUnitTest {

    @Test
    public void test() throws Exception {
        assertThat(capabilityProvider().provideBy(String.class, capA.getId()), isSameAs((Object) capA));
        assertThat(capabilityProvider().provideBy(String.class, capB.getId()), isSameAs((Object) capB));
        assertThat(capabilityProvider().provideBy(String.class, capC.getId()), isSameAs((Object) capC));
    }

    @Test
    public void testWrongType() throws Exception {
        try {
            capabilityProvider().provideBy(Integer.class, capA.getId());
            fail("expected exception missing");
        } catch (IllegalArgumentException expected) {}
    }

    @Test
    public void testNotExisting() throws Exception {
        assertThat(capabilityProvider().provideBy(String.class, capA.getId() + "x"), is(null));
    }

}
