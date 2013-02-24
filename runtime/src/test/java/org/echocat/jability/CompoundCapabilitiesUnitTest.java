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

import org.echocat.jomon.runtime.util.Entry;
import org.echocat.jomon.runtime.util.Entry.Impl;
import org.echocat.jomon.testing.IteratorMatchers;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static junit.framework.Assert.fail;
import static org.echocat.jability.support.CapabilityUtils.newCapability;
import static org.echocat.jomon.runtime.CollectionUtils.asIterator;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.IteratorMatchers.returnsItems;
import static org.mockito.Mockito.*;

public class CompoundCapabilitiesUnitTest {

    protected static final Capability<Boolean> CA = newCapability(Boolean.class, CompoundCapabilitiesUnitTest.class, "ca");
    protected static final Capability<Integer> CB = newCapability(Integer.class, CompoundCapabilitiesUnitTest.class, "cb", 666);
    protected static final Capability<Boolean> CC = newCapability(Boolean.class, CompoundCapabilitiesUnitTest.class, "cc", true);

    private final Capabilities _delegateA = mock(Capabilities.class, "a");
    private final Capabilities _delegateB = mock(Capabilities.class, "b");

    @Test
    public void testGetWithoutDefaultValue() throws Exception {
        assertThat(capabilities().get(CA), is(null));
        assertThat(capabilities(_delegateA, _delegateB).get(CA), is(null));
        doReturn(true).when(_delegateA).get(CA, null);
        assertThat(capabilities(_delegateA, _delegateB).get(CA), is(true));
        doReturn(null).when(_delegateA).get(CA, null);
        doReturn(false).when(_delegateB).get(CA, null);
        assertThat(capabilities(_delegateA, _delegateB).get(CA), is(false));
        
        assertThat(capabilities().get(CB), is(666));
        assertThat(capabilities(_delegateA, _delegateB).get(CB), is(666));
        doReturn(1).when(_delegateA).get(CB, null);
        assertThat(capabilities(_delegateA, _delegateB).get(CB), is(1));
        doReturn(null).when(_delegateA).get(CB, null);
        doReturn(2).when(_delegateB).get(CB, null);
        assertThat(capabilities(_delegateA, _delegateB).get(CB), is(2));
    }

    @Test
    public void testGetWithDefaultValue() throws Exception {
        assertThat(capabilities().get(CB, 777), is(777));
        assertThat(capabilities(_delegateA, _delegateB).get(CB, 777), is(777));
        doReturn(1).when(_delegateA).get(CB, null);
        assertThat(capabilities(_delegateA, _delegateB).get(CB, 777), is(1));
        doReturn(null).when(_delegateA).get(CB, null);
        doReturn(2).when(_delegateB).get(CB, null);
        assertThat(capabilities(_delegateA, _delegateB).get(CB, 777), is(2));
    }

    @Test
    public void testIsEnabledWithoutDefaultValue() throws Exception {
        assertThat(capabilities().isEnabled(CA), is(false));
        assertThat(capabilities(_delegateA, _delegateB).isEnabled(CA), is(false));
        doReturn(true).when(_delegateA).get(CA, null);
        assertThat(capabilities(_delegateA, _delegateB).isEnabled(CA), is(true));
        doReturn(null).when(_delegateA).get(CA, null);
        doReturn(true).when(_delegateB).get(CA, null);
        assertThat(capabilities(_delegateA, _delegateB).isEnabled(CA), is(true));

        assertThat(capabilities().isEnabled(CC), is(true));
        assertThat(capabilities(_delegateA, _delegateB).isEnabled(CC), is(true));
        doReturn(false).when(_delegateA).get(CC, null);
        assertThat(capabilities(_delegateA, _delegateB).isEnabled(CC), is(false));
        doReturn(null).when(_delegateA).get(CC, null);
        doReturn(false).when(_delegateB).get(CC, null);
        assertThat(capabilities(_delegateA, _delegateB).isEnabled(CC), is(false));
    }

    @Test
    public void testIsEnabledWithDefaultValue() throws Exception {
        assertThat(capabilities().isEnabled(CA, true), is(true));
        assertThat(capabilities(_delegateA, _delegateB).isEnabled(CA, true), is(true));
        doReturn(false).when(_delegateA).get(CA, null);
        assertThat(capabilities(_delegateA, _delegateB).isEnabled(CA, true), is(false));
        doReturn(null).when(_delegateA).get(CA, null);
        doReturn(false).when(_delegateB).get(CA, null);
        assertThat(capabilities(_delegateA, _delegateB).isEnabled(CA, true), is(false));
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(capabilities().iterator(), IteratorMatchers.returnsNothing());
        assertThat(capabilities(_delegateA, _delegateB).iterator(), IteratorMatchers.returnsNothing());

        doReturn(asIterator(entry(CA, false), entry(CC, false), entry(CB, 1))).when(_delegateB).iterator();
        assertThat(capabilities(_delegateA, _delegateB).iterator(), returnsItems(entry(CA, false), entry(CC, false), entry(CB, 1)));

        doReturn(asIterator(entry(CA, true), entry(CB, 2))).when(_delegateA).iterator();
        doReturn(asIterator(entry(CA, false), entry(CC, false), entry(CB, 1))).when(_delegateB).iterator();
        assertThat(capabilities(_delegateA, _delegateB).iterator(), returnsItems(entry(CA, true), entry(CB, 2), entry(CC, false)));
    }

    @Test
    public void testSet() throws Exception {
        doReturn(true).when(_delegateA).isModifiable(CA);
        capabilities(_delegateA, _delegateB).set(CA, true);
        verify(_delegateA, times(1)).set(CA, true);
        verify(_delegateB, times(0)).set(CA, true);

        doReturn(true).when(_delegateB).isModifiable(CA);
        capabilities(_delegateA, _delegateB).set(CA, true);
        verify(_delegateA, times(2)).set(CA, true);
        verify(_delegateB, times(1)).set(CA, true);
    }

    @Test
    public void testFailingSet() throws Exception {
        try {
            capabilities().set(CA, true);
            fail("Expected exception missing.");
        } catch (UnsupportedOperationException expected) {}
        try {
            capabilities(_delegateA, _delegateB).set(CA, true);
            fail("Expected exception missing.");
        } catch (UnsupportedOperationException expected) {}
    }

    @Test
    public void testRemove() throws Exception {
        doReturn(true).when(_delegateA).isModifiable(CA);
        capabilities(_delegateA, _delegateB).remove(CA);
        verify(_delegateA, times(1)).remove(CA);
        verify(_delegateB, times(0)).remove(CA);

        doReturn(true).when(_delegateB).isModifiable(CA);
        capabilities(_delegateA, _delegateB).remove(CA);
        verify(_delegateA, times(2)).remove(CA);
        verify(_delegateB, times(1)).remove(CA);
    }

    @Test
    public void testFailingRemove() throws Exception {
        try {
            capabilities().remove(CA);
            fail("Expected exception missing.");
        } catch (UnsupportedOperationException expected) {}
        try {
            capabilities(_delegateA, _delegateB).remove(CA);
            fail("Expected exception missing.");
        } catch (UnsupportedOperationException expected) {}
    }

    @Test
    public void testIsModifiable() throws Exception {
        assertThat(capabilities().isModifiable(CA), is(false));
        assertThat(capabilities(_delegateA, _delegateB).isModifiable(CA), is(false));

        doReturn(true).when(_delegateA).isModifiable(CA);
        assertThat(capabilities(_delegateA, _delegateB).isModifiable(CA), is(true));

        doReturn(false).when(_delegateA).isModifiable(CA);
        doReturn(true).when(_delegateB).isModifiable(CA);
        assertThat(capabilities(_delegateA, _delegateB).isModifiable(CA), is(true));
    }

    @Nonnull
    protected static Capabilities capabilities(@Nullable Capabilities... delegates) {
        return new CompoundCapabilities(delegates);
    }

    @Nonnull
    protected static Entry<Capability<?>, Object> entry(@Nonnull Capability<?> capability, @Nullable Object value) {
        return new Impl<Capability<?>, Object>(capability, value);
    }

}
