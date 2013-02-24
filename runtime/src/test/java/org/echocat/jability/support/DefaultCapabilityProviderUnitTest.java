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

package org.echocat.jability.support;

import org.echocat.jability.Capability;
import org.echocat.jability.CapabilityProvider;
import org.echocat.jomon.runtime.CollectionUtils;
import org.echocat.jomon.testing.IteratorMatchers;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.echocat.jability.support.CapabilityUtils.newCapability;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.BaseMatchers.isSameAs;
import static org.junit.Assert.fail;

public class DefaultCapabilityProviderUnitTest {

    protected static final Capability<String> A = newCapability(String.class, DefaultCapabilityProviderUnitTest.class, "a");
    protected static final Capability<Integer> B = newCapability(Integer.class, DefaultCapabilityProviderUnitTest.class, "b");
    protected static final Capability<String> C = newCapability(String.class, DefaultCapabilityProviderUnitTest.class, "c");

    @Test
    public void test() throws Exception {
        final CapabilityProvider capabilityProvider = capabilityProvider();
        assertThat(capabilityProvider.provideBy(String.class, A.getId()), isSameAs(A));
        assertThat(capabilityProvider.provideBy(Integer.class, B.getId()), isSameAs(B));
        assertThat(capabilityProvider.provideBy(String.class, C.getId()), isSameAs(C));
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(capabilityProvider().iterator(), IteratorMatchers.<Capability<?>>returnsAllItemsOf(A, B, C));
    }

    @Test
    public void testWrongType() throws Exception {
        try {
            capabilityProvider().provideBy(String.class, B.getId());
            fail("expected exception missing");
        } catch (IllegalArgumentException expected) {}
    }

    @Test
    public void testNotExisting() throws Exception {
        assertThat(capabilityProvider().provideBy(String.class, A.getId() + "x"), is(null));
    }

    @Nonnull
    protected static CapabilityProvider capabilityProvider() {
        return new DefaultCapabilityProvider<Capability<?>>(CollectionUtils.<Capability<?>>asImmutableList(A, B, C)) {};
    }

}
