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

import org.echocat.jability.property.Property.Impl;
import org.echocat.jomon.testing.BaseMatchers;
import org.junit.Test;

import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.BaseMatchers.not;

public class PropertyUnitTest {

    @Test
    public void testWithIdAndDefaultValue() throws Exception {
        final Property<String> propertyA = new Impl<>(String.class, "a", "myDefault");
        assertThat(propertyA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(propertyA.getId(), is("a"));
        assertThat(propertyA.getDefaultValue(), is("myDefault"));

        final Property<Integer> propertyB = new Impl<>(Integer.class, "b", null);
        assertThat(propertyB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(propertyB.getId(), is("b"));
        assertThat(propertyB.getDefaultValue(), is(null));
    }

    @Test
    public void testWithBasedOnSubIdAndDefaultValue() throws Exception {
        final Property<String> propertyA = new Impl<>(String.class, getClass(), "a", "myDefault");
        assertThat(propertyA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(propertyA.getId(), is(getClass().getPackage().getName() + ".a"));
        assertThat(propertyA.getDefaultValue(), is("myDefault"));

        final Property<Integer> propertyB = new Impl<>(Integer.class, getClass(), "b", null);
        assertThat(propertyB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(propertyB.getId(), is(getClass().getPackage().getName() + ".b"));
        assertThat(propertyB.getDefaultValue(), is(null));
    }

    @Test
    public void testEquals() throws Exception {
        final Property<String> propertyA = new Impl<>(String.class, getClass(), "a", "myDefault");
        final Property<Integer> propertyA2 = new Impl<>(Integer.class, getClass(), "a", null);
        final Property<Integer> propertyB = new Impl<>(Integer.class, getClass(), "b", null);

        assertThat(propertyA.equals(propertyA), is(true));
        assertThat(propertyA.equals(propertyA2), is(true));
        assertThat(propertyA.equals(propertyB), is(false));
        assertThat(propertyA.equals(new Object()), is(false));
    }

    @Test
    public void testHashCode() throws Exception {
        final Property<String> propertyA = new Impl<>(String.class, getClass(), "a", "myDefault");
        final Property<Integer> propertyA2 = new Impl<>(Integer.class, getClass(), "a", null);
        final Property<Integer> propertyB = new Impl<>(Integer.class, getClass(), "b", null);

        assertThat(propertyA.hashCode(), is(propertyA2.hashCode()));
        assertThat(propertyA.hashCode(), not(is(propertyB.hashCode())));
    }

    @Test
    public void testToString() throws Exception {
        final Property<String> propertyA = new Impl<>(String.class, getClass(), "a", "myDefault");
        final Property<Integer> propertyA2 = new Impl<>(Integer.class, getClass(), "a", null);
        final Property<Integer> propertyB = new Impl<>(Integer.class, getClass(), "b", null);

        assertThat(propertyA.toString(), is(getClass().getPackage().getName() + ".a(" + String.class.getName() + ")"));
        assertThat(propertyA2.toString(), is(getClass().getPackage().getName() + ".a(" + Integer.class.getName() + ")"));
        assertThat(propertyB.toString(), is(getClass().getPackage().getName() + ".b(" + Integer.class.getName() + ")"));
    }


}
