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

package org.echocat.jability.property;

import org.echocat.jability.Capability;
import org.echocat.jability.support.CapabilityUtils;
import org.echocat.jomon.runtime.util.Entry;
import org.echocat.jomon.runtime.util.Entry.Impl;
import org.echocat.jomon.testing.IteratorMatchers;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static junit.framework.Assert.fail;
import static org.echocat.jability.property.support.PropertyUtils.newProperty;
import static org.echocat.jomon.runtime.CollectionUtils.asIterator;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.IteratorMatchers.returnsItems;
import static org.mockito.Mockito.*;

public class CompoundPropertiesUnitTest {

    protected static final Capability<Boolean> CA = CapabilityUtils.newCapability(Boolean.class, CompoundPropertiesUnitTest.class, "ca");
    protected static final Property<Boolean> PA = newProperty(Boolean.class, CompoundPropertiesUnitTest.class, "pa");
    protected static final Property<Integer> PB = newProperty(Integer.class, CompoundPropertiesUnitTest.class, "pb", 666);
    protected static final Property<Boolean> PC = newProperty(Boolean.class, CompoundPropertiesUnitTest.class, "pc", true);

    private final Properties _delegateA = mock(Properties.class, "a");
    private final Properties _delegateB = mock(Properties.class, "b");

    @Test
    public void testGetWithoutDefaultValue() throws Exception {
        assertThat(properties().get(CA, PA), is(null));
        assertThat(properties(_delegateA, _delegateB).get(CA, PA), is(null));
        doReturn(true).when(_delegateA).get(CA, PA, null);
        assertThat(properties(_delegateA, _delegateB).get(CA, PA), is(true));
        doReturn(null).when(_delegateA).get(CA, PA, null);
        doReturn(false).when(_delegateB).get(CA, PA, null);
        assertThat(properties(_delegateA, _delegateB).get(CA, PA), is(false));
        
        assertThat(properties().get(CA, PB), is(666));
        assertThat(properties(_delegateA, _delegateB).get(CA, PB), is(666));
        doReturn(1).when(_delegateA).get(CA, PB, null);
        assertThat(properties(_delegateA, _delegateB).get(CA, PB), is(1));
        doReturn(null).when(_delegateA).get(CA, PB, null);
        doReturn(2).when(_delegateB).get(CA, PB, null);
        assertThat(properties(_delegateA, _delegateB).get(CA, PB), is(2));
    }

    @Test
    public void testGetWithDefaultValue() throws Exception {
        assertThat(properties().get(CA, PB, 777), is(777));
        assertThat(properties(_delegateA, _delegateB).get(CA, PB, 777), is(777));
        doReturn(1).when(_delegateA).get(CA, PB, null);
        assertThat(properties(_delegateA, _delegateB).get(CA, PB, 777), is(1));
        doReturn(null).when(_delegateA).get(CA, PB, null);
        doReturn(2).when(_delegateB).get(CA, PB, null);
        assertThat(properties(_delegateA, _delegateB).get(CA, PB, 777), is(2));
    }

    @Test
    public void testIsEnabledWithoutDefaultValue() throws Exception {
        assertThat(properties().isEnabled(CA, PA), is(false));
        assertThat(properties(_delegateA, _delegateB).isEnabled(CA, PA), is(false));
        doReturn(true).when(_delegateA).get(CA, PA, null);
        assertThat(properties(_delegateA, _delegateB).isEnabled(CA, PA), is(true));
        doReturn(null).when(_delegateA).get(CA, PA, null);
        doReturn(true).when(_delegateB).get(CA, PA, null);
        assertThat(properties(_delegateA, _delegateB).isEnabled(CA, PA), is(true));

        assertThat(properties().isEnabled(CA, PC), is(true));
        assertThat(properties(_delegateA, _delegateB).isEnabled(CA, PC), is(true));
        doReturn(false).when(_delegateA).get(CA, PC, null);
        assertThat(properties(_delegateA, _delegateB).isEnabled(CA, PC), is(false));
        doReturn(null).when(_delegateA).get(CA, PC, null);
        doReturn(false).when(_delegateB).get(CA, PC, null);
        assertThat(properties(_delegateA, _delegateB).isEnabled(CA, PC), is(false));
    }

    @Test
    public void testIsEnabledWithDefaultValue() throws Exception {
        assertThat(properties().isEnabled(CA, PA, true), is(true));
        assertThat(properties(_delegateA, _delegateB).isEnabled(CA, PA, true), is(true));
        doReturn(false).when(_delegateA).get(CA, PA, null);
        assertThat(properties(_delegateA, _delegateB).isEnabled(CA, PA, true), is(false));
        doReturn(null).when(_delegateA).get(CA, PA, null);
        doReturn(false).when(_delegateB).get(CA, PA, null);
        assertThat(properties(_delegateA, _delegateB).isEnabled(CA, PA, true), is(false));
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(properties().iterator(CA), IteratorMatchers.returnsNothing());
        assertThat(properties(_delegateA, _delegateB).iterator(CA), IteratorMatchers.returnsNothing());

        doReturn(asIterator(entry(PA, false), entry(PC, false), entry(PB, 1))).when(_delegateB).iterator(CA);
        assertThat(properties(_delegateA, _delegateB).iterator(CA), returnsItems(entry(PA, false), entry(PC, false), entry(PB, 1)));

        doReturn(asIterator(entry(PA, true), entry(PB, 2))).when(_delegateA).iterator(CA);
        doReturn(asIterator(entry(PA, false), entry(PC, false), entry(PB, 1))).when(_delegateB).iterator(CA);
        assertThat(properties(_delegateA, _delegateB).iterator(CA), returnsItems(entry(PA, true), entry(PB, 2), entry(PC, false)));
    }

    @Test
    public void testSet() throws Exception {
        doReturn(true).when(_delegateA).isModifiable(CA, PA);
        properties(_delegateA, _delegateB).set(CA, PA, true);
        verify(_delegateA, times(1)).set(CA, PA, true);
        verify(_delegateB, times(0)).set(CA, PA, true);

        doReturn(true).when(_delegateB).isModifiable(CA, PA);
        properties(_delegateA, _delegateB).set(CA, PA, true);
        verify(_delegateA, times(2)).set(CA, PA, true);
        verify(_delegateB, times(1)).set(CA, PA, true);
    }

    @Test
    public void testFailingSet() throws Exception {
        try {
            properties().set(CA, PA, true);
            fail("Expected exception missing.");
        } catch (UnsupportedOperationException expected) {}
        try {
            properties(_delegateA, _delegateB).set(CA, PA, true);
            fail("Expected exception missing.");
        } catch (UnsupportedOperationException expected) {}
    }

    @Test
    public void testRemove() throws Exception {
        doReturn(true).when(_delegateA).isModifiable(CA, PA);
        properties(_delegateA, _delegateB).remove(CA, PA);
        verify(_delegateA, times(1)).remove(CA, PA);
        verify(_delegateB, times(0)).remove(CA, PA);

        doReturn(true).when(_delegateB).isModifiable(CA, PA);
        properties(_delegateA, _delegateB).remove(CA, PA);
        verify(_delegateA, times(2)).remove(CA, PA);
        verify(_delegateB, times(1)).remove(CA, PA);
    }

    @Test
    public void testFailingRemove() throws Exception {
        try {
            properties().remove(CA, PA);
            fail("Expected exception missing.");
        } catch (UnsupportedOperationException expected) {}
        try {
            properties(_delegateA, _delegateB).remove(CA, PA);
            fail("Expected exception missing.");
        } catch (UnsupportedOperationException expected) {}
    }

    @Test
    public void testIsModifiable() throws Exception {
        assertThat(properties().isModifiable(CA, PA), is(false));
        assertThat(properties(_delegateA, _delegateB).isModifiable(CA, PA), is(false));

        doReturn(true).when(_delegateA).isModifiable(CA, PA);
        assertThat(properties(_delegateA, _delegateB).isModifiable(CA, PA), is(true));

        doReturn(false).when(_delegateA).isModifiable(CA, PA);
        doReturn(true).when(_delegateB).isModifiable(CA, PA);
        assertThat(properties(_delegateA, _delegateB).isModifiable(CA, PA), is(true));
    }

    @Nonnull
    protected static Properties properties(@Nullable Properties... delegates) {
        return new CompoundProperties(delegates);
    }

    @Nonnull
    protected static Entry<Property<?>, Object> entry(@Nonnull Property<?> property, @Nullable Object value) {
        return new Impl<Property<?>, Object>(property, value);
    }

}
