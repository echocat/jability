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
import org.echocat.jomon.testing.BaseMatchers;
import org.junit.Test;

import static org.echocat.jability.property.support.PropertyUtils.newProperty;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;

public class PropertyUtilsUnitTest {

    @Test
    public void testWithIdAndDefaultValue() throws Exception {
        final Property<String> propertyA = newProperty(String.class, "a", "myDefault");
        assertThat(propertyA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(propertyA.getId(), is("a"));
        assertThat(propertyA.getDefaultValue(), is("myDefault"));

        final Property<Integer> propertyB = newProperty(Integer.class, "b", null);
        assertThat(propertyB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(propertyB.getId(), is("b"));
        assertThat(propertyB.getDefaultValue(), is(null));
    }

    @Test
    public void testWithId() throws Exception {
        final Property<String> propertyA = newProperty(String.class, "a");
        assertThat(propertyA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(propertyA.getId(), is("a"));
        assertThat(propertyA.getDefaultValue(), is(null));

        final Property<Integer> propertyB = newProperty(Integer.class, "b");
        assertThat(propertyB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(propertyB.getId(), is("b"));
        assertThat(propertyB.getDefaultValue(), is(null));
    }


    @Test
    public void testWithBasedOnSubIdAndDefaultValue() throws Exception {
        final Property<String> propertyA = newProperty(String.class, getClass(), "a", "myDefault");
        assertThat(propertyA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(propertyA.getId(), is(getClass().getPackage().getName() + ".a"));
        assertThat(propertyA.getDefaultValue(), is("myDefault"));

        final Property<Integer> propertyB = newProperty(Integer.class, getClass(), "b", null);
        assertThat(propertyB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(propertyB.getId(), is(getClass().getPackage().getName() + ".b"));
        assertThat(propertyB.getDefaultValue(), is(null));
    }

    @Test
    public void testWithBasedOnAndSubId() throws Exception {
        final Property<String> propertyA = newProperty(String.class, getClass(), "a");
        assertThat(propertyA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(propertyA.getId(), is(getClass().getPackage().getName() + ".a"));
        assertThat(propertyA.getDefaultValue(), is(null));

        final Property<Integer> propertyB = newProperty(Integer.class, getClass(), "b");
        assertThat(propertyB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(propertyB.getId(), is(getClass().getPackage().getName() + ".b"));
        assertThat(propertyB.getDefaultValue(), is(null));
    }

}
