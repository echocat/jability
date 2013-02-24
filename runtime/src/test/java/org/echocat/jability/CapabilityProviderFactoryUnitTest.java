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
import org.echocat.jability.impl.*;
import org.echocat.jomon.testing.IteratorMatchers;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.List;

import static org.echocat.jability.Test1Capability.*;
import static org.echocat.jability.configuration.Configurations.*;
import static org.echocat.jability.impl.TestCapabilityReferenceA.testCapA1;
import static org.echocat.jability.impl.TestCapabilityReferenceA.testCapA2;
import static org.echocat.jability.impl.TestCapabilityReferenceB.testCapB1;
import static org.echocat.jability.impl.TestCapabilityReferenceB.testCapB2;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.isInstanceOf;
import static org.echocat.jomon.testing.CollectionMatchers.hasSize;

public class CapabilityProviderFactoryUnitTest {

    protected static final CapabilityProviderFactory FACTORY = new CapabilityProviderFactory();

    @Test
    public void testWithSystemProviders() throws Exception {
        final CapabilityProvider compoundProvider = FACTORY.createBy(null, configuration(exampleCapabilityConfiguration(true)));
        assertThat(compoundProvider, isInstanceOf(CompoundCapabilityProvider.class));
        final List<CapabilityProvider> delegates = asImmutableList(((CompoundCapabilityProvider) compoundProvider).getDelegates());
        assertThat(delegates, hasSize(6));
        assertThat(delegates.get(0).iterator(), IteratorMatchers.<Capability<?>>returnsItems(testCapA1, testCapA2));
        assertThat(delegates.get(1).iterator(), IteratorMatchers.<Capability<?>>returnsItems(testCapB1, testCapB2));
        assertThat(delegates.get(2), isInstanceOf(TestCapabilityProviderA.class));
        assertThat(delegates.get(3), isInstanceOf(TestCapabilityProviderB.class));
        assertThat(delegates.get(4), isInstanceOf(TestSystemCapabilityProvider.class));
        assertThat(delegates.get(5).iterator(), IteratorMatchers.<Capability<?>>returnsItems(capA, capB, capC));
    }

    @Test
    public void testWithoutSystemProviders() throws Exception {
        final CapabilityProvider compoundProvider = FACTORY.createBy(null, configuration(exampleCapabilityConfiguration(false)));
        assertThat(compoundProvider, isInstanceOf(CompoundCapabilityProvider.class));
        final List<CapabilityProvider> delegates = asImmutableList(((CompoundCapabilityProvider) compoundProvider).getDelegates());
        assertThat(delegates, hasSize(4));
        assertThat(delegates.get(0).iterator(), IteratorMatchers.<Capability<?>>returnsItems(testCapA1, testCapA2));
        assertThat(delegates.get(1).iterator(), IteratorMatchers.<Capability<?>>returnsItems(testCapB1, testCapB2));
        assertThat(delegates.get(2), isInstanceOf(TestCapabilityProviderA.class));
        assertThat(delegates.get(3), isInstanceOf(TestCapabilityProviderB.class));
    }

    @Nonnull
    protected static CapabilitiesRootConfiguration exampleCapabilityConfiguration(boolean respectSystemProviders) {
        return capabilitiesConfiguration().with(
            respectSystemCapabilityProviders(respectSystemProviders),
            capabilityProvider(TestCapabilityProviderA.class),
            capabilityProvider(TestCapabilityProviderB.class),
            capabilityReference(TestCapabilityReferenceA.class, "test.*"),
            capabilityReference(TestCapabilityReferenceB.class, "test.*")
        );
    }

}
