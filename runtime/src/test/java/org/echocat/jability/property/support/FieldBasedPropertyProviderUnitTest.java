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

package org.echocat.jability.property.support;

import org.echocat.jability.property.Property;
import org.echocat.jability.property.PropertyProvider;
import org.junit.Test;

import javax.annotation.Nonnull;

import static java.util.regex.Pattern.compile;
import static org.echocat.jability.property.PropertyUtils.newPropertyDefinition;
import static org.echocat.jability.support.AccessType.PROTECTED;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.BaseMatchers.isSameAs;
import static org.junit.Assert.fail;

public class FieldBasedPropertyProviderUnitTest {

    protected static final Property<String> A = newPropertyDefinition(String.class, BasePropertyProviderUnitTest.class, "a");
    protected static final Property<Integer> B = newPropertyDefinition(Integer.class, BasePropertyProviderUnitTest.class, "b");
    protected static final Property<String> C = newPropertyDefinition(String.class, BasePropertyProviderUnitTest.class, "c");
    public static final Property<String> D = newPropertyDefinition(String.class, BasePropertyProviderUnitTest.class, "d");
    protected static final Property<String> E = newPropertyDefinition(String.class, BasePropertyProviderUnitTest.class, "e");

    @Test
    public void test() throws Exception {
        final PropertyProvider propertyProvider = propertyProvider();
        assertThat(propertyProvider.provideBy(String.class, A.getId()), isSameAs(A));
        assertThat(propertyProvider.provideBy(Integer.class, B.getId()), isSameAs(B));
        assertThat(propertyProvider.provideBy(String.class, C.getId()), isSameAs(C));
        assertThat(propertyProvider.provideBy(String.class, D.getId()), is(null));
        assertThat(propertyProvider.provideBy(String.class, E.getId()), is(null));
    }

    @Test
    public void testWrongType() throws Exception {
        try {
            propertyProvider().provideBy(String.class, B.getId());
            fail("expected exception missing");
        } catch (IllegalArgumentException expected) {}
    }

    @Test
    public void testNotExisting() throws Exception {
        assertThat(propertyProvider().provideBy(String.class, A.getId() + "x"), is(null));
    }

    @Nonnull
    protected static PropertyProvider propertyProvider() {
        return new FieldBasedPropertyProvider<>(Property.class, FieldBasedPropertyProviderUnitTest.class, null, compile("[A-D]"), PROTECTED);
    }

}
