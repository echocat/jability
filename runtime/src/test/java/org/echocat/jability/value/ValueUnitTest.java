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

package org.echocat.jability.value;

import org.echocat.jability.value.Value.Impl;
import org.echocat.jomon.testing.BaseMatchers;
import org.junit.Test;

import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.BaseMatchers.not;

public class ValueUnitTest {

    @Test
    public void testWithIdAndDefaultValue() throws Exception {
        final Value<String> valueA = new Impl<>(String.class, "a", "myDefault");
        assertThat(valueA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(valueA.getId(), is("a"));
        assertThat(valueA.getDefaultValue(), is("myDefault"));

        final Value<Integer> valueB = new Impl<>(Integer.class, "b", null);
        assertThat(valueB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(valueB.getId(), is("b"));
        assertThat(valueB.getDefaultValue(), is(null));
    }

    @Test
    public void testEquals() throws Exception {
        final Value<String> valueA = new Impl<>(String.class, "a", "myDefault");
        final Value<Integer> valueA2 = new Impl<>(Integer.class, "a", null);
        final Value<Integer> valueB = new Impl<>(Integer.class, "b", null);

        assertThat(valueA.equals(valueA), is(true));
        assertThat(valueA.equals(valueA2), is(true));
        assertThat(valueA.equals(valueB), is(false));
        assertThat(valueA.equals(new Object()), is(false));
    }

    @Test
    public void testHashCode() throws Exception {
        final Value<String> valueA = new Impl<>(String.class, "a", "myDefault");
        final Value<Integer> valueA2 = new Impl<>(Integer.class, "a", null);
        final Value<Integer> valueB = new Impl<>(Integer.class, "b", null);

        assertThat(valueA.hashCode(), is(valueA2.hashCode()));
        assertThat(valueA.hashCode(), not(is(valueB.hashCode())));
    }

    @Test
    public void testToString() throws Exception {
        final Value<String> valueA = new Impl<>(String.class, "a", "myDefault");
        final Value<Integer> valueA2 = new Impl<>(Integer.class, "a", null);
        final Value<Integer> valueB = new Impl<>(Integer.class, "b", null);

        assertThat(valueA.toString(), is("a(" + String.class.getName() + ")"));
        assertThat(valueA2.toString(), is("a(" + Integer.class.getName() + ")"));
        assertThat(valueB.toString(), is("b(" + Integer.class.getName() + ")"));
    }


}
