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

import org.echocat.jability.configuration.capability.CapabilitiesRootConfiguration;
import org.echocat.jability.impl.TestCapabilitiesA;
import org.echocat.jability.impl.TestCapabilitiesB;
import org.echocat.jability.impl.TestSystemCapabilities;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.List;

import static org.echocat.jability.configuration.Configurations.*;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.isInstanceOf;
import static org.echocat.jomon.testing.CollectionMatchers.hasSize;

public class CapabilitiesFactoryUnitTest {

    protected static final CapabilitiesFactory FACTORY = new CapabilitiesFactory();

    @Test
    public void testWithSystemCapabilities() throws Exception {
        final Capabilities compoundCapabilities = FACTORY.createBy(null, configuration(exampleCapabilityConfiguration(true)));
        assertThat(compoundCapabilities, isInstanceOf(CompoundCapabilities.class));
        final List<Capabilities> delegates = asImmutableList(((CompoundCapabilities) compoundCapabilities).getDelegates());
        assertThat(delegates, hasSize(3));
        assertThat(delegates.get(0), isInstanceOf(TestCapabilitiesA.class));
        assertThat(delegates.get(1), isInstanceOf(TestCapabilitiesB.class));
        assertThat(delegates.get(2), isInstanceOf(TestSystemCapabilities.class));
    }

    public void testWithoutSystemCapabilities() throws Exception {
        final Capabilities compoundCapabilities = FACTORY.createBy(null, configuration(exampleCapabilityConfiguration(false)));
        assertThat(compoundCapabilities, isInstanceOf(CompoundCapabilities.class));
        final List<Capabilities> delegates = asImmutableList(((CompoundCapabilities) compoundCapabilities).getDelegates());
        assertThat(delegates, hasSize(2));
        assertThat(delegates.get(0), isInstanceOf(TestCapabilitiesA.class));
        assertThat(delegates.get(1), isInstanceOf(TestCapabilitiesB.class));
    }

    @Nonnull
    protected static CapabilitiesRootConfiguration exampleCapabilityConfiguration(boolean respectSystemCapabilities) {
        return capabilitiesConfiguration().with(
            respectSystemCapabilities(respectSystemCapabilities),
            capabilities(TestCapabilitiesA.class),
            capabilities(TestCapabilitiesB.class)
        );
    }
}
