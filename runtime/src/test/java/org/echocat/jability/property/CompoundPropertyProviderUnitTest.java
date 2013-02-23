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

import org.echocat.jability.value.ValueProvider;
import org.echocat.jomon.testing.BaseMatchers;
import org.echocat.jomon.testing.IteratorMatchers;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.echocat.jability.property.support.PropertyUtils.newProperty;
import static org.echocat.jomon.runtime.CollectionUtils.asIterator;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class CompoundPropertyProviderUnitTest {

    protected static final Property<Boolean> PA = newProperty(Boolean.class, CompoundPropertyProviderUnitTest.class, "pa");
    protected static final Property<Integer> PB = newProperty(Integer.class, CompoundPropertyProviderUnitTest.class, "pb", 666);
    protected static final Property<Boolean> PC = newProperty(Boolean.class, CompoundPropertyProviderUnitTest.class, "pc", true);

    private final PropertyProvider _delegateA = mock(PropertyProvider.class, "a");
    private final PropertyProvider _delegateB = mock(PropertyProvider.class, "b");

    @Test
    public void testProvideBy() throws Exception {
        assertThat(propertyProvider().provideBy(String.class, "a"), is(null));
        assertThat(propertyProvider(_delegateA, _delegateB).provideBy(PA.getClass(), PA.getId()), is(null));

        final Property<?> anotherPa = mock(Property.class, "anotherPa");
        doReturn(anotherPa).when((ValueProvider<?>) _delegateB).provideBy(PA.getValueType(), PA.getId());
        assertThat(propertyProvider(_delegateA, _delegateB).provideBy(PA.getValueType(), PA.getId()), BaseMatchers.<Property<?>>isSameAs(anotherPa));

        doReturn(PA).when((ValueProvider<?>) _delegateA).provideBy(PA.getValueType(), PA.getId());
        assertThat(propertyProvider(_delegateA, _delegateB).provideBy(PA.getValueType(), PA.getId()), BaseMatchers.<Property<?>>isSameAs(PA));
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(propertyProvider().iterator(), IteratorMatchers.returnsNothing());
        assertThat(propertyProvider(_delegateA, _delegateB).iterator(), IteratorMatchers.returnsNothing());

        doReturn(asIterator(PA, PC, PB)).when(_delegateB).iterator();
        assertThat(propertyProvider(_delegateA, _delegateB).iterator(), IteratorMatchers.<Property<?>>returnsItems(PA, PC, PB));

        doReturn(asIterator(PA, PB)).when(_delegateA).iterator();
        doReturn(asIterator(PA, PC, PB)).when(_delegateB).iterator();
        assertThat(propertyProvider(_delegateA, _delegateB).iterator(), IteratorMatchers.<Property<?>>returnsItems(PA, PB, PC));
    }

    @Nonnull
    protected static PropertyProvider propertyProvider(@Nullable PropertyProvider... delegates) {
        return new CompoundPropertyProvider(delegates);
    }

}
