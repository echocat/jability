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

import org.echocat.jability.value.ValueProvider;
import org.echocat.jomon.testing.BaseMatchers;
import org.echocat.jomon.testing.IteratorMatchers;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.echocat.jability.support.CapabilityUtils.newCapability;
import static org.echocat.jomon.runtime.CollectionUtils.asIterator;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class CompoundCapabilityProviderUnitTest {

    protected static final Capability<Boolean> PA = newCapability(Boolean.class, CompoundCapabilityProviderUnitTest.class, "pa");
    protected static final Capability<Integer> PB = newCapability(Integer.class, CompoundCapabilityProviderUnitTest.class, "pb", 666);
    protected static final Capability<Boolean> PC = newCapability(Boolean.class, CompoundCapabilityProviderUnitTest.class, "pc", true);

    private final CapabilityProvider _delegateA = mock(CapabilityProvider.class, "a");
    private final CapabilityProvider _delegateB = mock(CapabilityProvider.class, "b");

    @Test
    public void testProvideBy() throws Exception {
        assertThat(capabilityProvider().provideBy(String.class, "a"), is(null));
        assertThat(capabilityProvider(_delegateA, _delegateB).provideBy(PA.getClass(), PA.getId()), is(null));

        final Capability<?> anotherPa = mock(Capability.class, "anotherPa");
        doReturn(anotherPa).when((ValueProvider<?>) _delegateB).provideBy(PA.getValueType(), PA.getId());
        assertThat(capabilityProvider(_delegateA, _delegateB).provideBy(PA.getValueType(), PA.getId()), BaseMatchers.<Capability<?>>isSameAs(anotherPa));

        doReturn(PA).when((ValueProvider<?>) _delegateA).provideBy(PA.getValueType(), PA.getId());
        assertThat(capabilityProvider(_delegateA, _delegateB).provideBy(PA.getValueType(), PA.getId()), BaseMatchers.<Capability<?>>isSameAs(PA));
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(capabilityProvider().iterator(), IteratorMatchers.returnsNothing());
        assertThat(capabilityProvider(_delegateA, _delegateB).iterator(), IteratorMatchers.returnsNothing());

        doReturn(asIterator(PA, PC, PB)).when(_delegateB).iterator();
        assertThat(capabilityProvider(_delegateA, _delegateB).iterator(), IteratorMatchers.<Capability<?>>returnsItems(PA, PC, PB));

        doReturn(asIterator(PA, PB)).when(_delegateA).iterator();
        doReturn(asIterator(PA, PC, PB)).when(_delegateB).iterator();
        assertThat(capabilityProvider(_delegateA, _delegateB).iterator(), IteratorMatchers.<Capability<?>>returnsItems(PA, PB, PC));
    }

    @Nonnull
    protected static CapabilityProvider capabilityProvider(@Nullable CapabilityProvider... delegates) {
        return new CompoundCapabilityProvider(delegates);
    }

}
