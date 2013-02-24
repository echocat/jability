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

import org.echocat.jability.Capability.Impl;
import org.echocat.jomon.testing.BaseMatchers;
import org.junit.Test;

import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.BaseMatchers.not;

public class CapabilityUnitTest {

    @Test
    public void testWithIdAndDefaultValue() throws Exception {
        final Capability<String> capabilityA = new Impl<>(String.class, "a", "myDefault");
        assertThat(capabilityA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(capabilityA.getId(), is("a"));
        assertThat(capabilityA.getDefaultValue(), is("myDefault"));

        final Capability<Integer> capabilityB = new Impl<>(Integer.class, "b", null);
        assertThat(capabilityB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(capabilityB.getId(), is("b"));
        assertThat(capabilityB.getDefaultValue(), is(null));
    }

    @Test
    public void testWithBasedOnSubIdAndDefaultValue() throws Exception {
        final Capability<String> capabilityA = new Impl<>(String.class, getClass(), "a", "myDefault");
        assertThat(capabilityA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(capabilityA.getId(), is(getClass().getPackage().getName() + ".a"));
        assertThat(capabilityA.getDefaultValue(), is("myDefault"));

        final Capability<Integer> capabilityB = new Impl<>(Integer.class, getClass(), "b", null);
        assertThat(capabilityB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(capabilityB.getId(), is(getClass().getPackage().getName() + ".b"));
        assertThat(capabilityB.getDefaultValue(), is(null));
    }

    @Test
    public void testEquals() throws Exception {
        final Capability<String> capabilityA = new Impl<>(String.class, getClass(), "a", "myDefault");
        final Capability<Integer> capabilityA2 = new Impl<>(Integer.class, getClass(), "a", null);
        final Capability<Integer> capabilityB = new Impl<>(Integer.class, getClass(), "b", null);

        assertThat(capabilityA.equals(capabilityA), is(true));
        assertThat(capabilityA.equals(capabilityA2), is(true));
        assertThat(capabilityA.equals(capabilityB), is(false));
        assertThat(capabilityA.equals(new Object()), is(false));
    }

    @Test
    public void testHashCode() throws Exception {
        final Capability<String> capabilityA = new Impl<>(String.class, getClass(), "a", "myDefault");
        final Capability<Integer> capabilityA2 = new Impl<>(Integer.class, getClass(), "a", null);
        final Capability<Integer> capabilityB = new Impl<>(Integer.class, getClass(), "b", null);

        assertThat(capabilityA.hashCode(), is(capabilityA2.hashCode()));
        assertThat(capabilityA.hashCode(), not(is(capabilityB.hashCode())));
    }

    @Test
    public void testToString() throws Exception {
        final Capability<String> capabilityA = new Impl<>(String.class, getClass(), "a", "myDefault");
        final Capability<Integer> capabilityA2 = new Impl<>(Integer.class, getClass(), "a", null);
        final Capability<Integer> capabilityB = new Impl<>(Integer.class, getClass(), "b", null);

        assertThat(capabilityA.toString(), is(getClass().getPackage().getName() + ".a(" + String.class.getName() + ")"));
        assertThat(capabilityA2.toString(), is(getClass().getPackage().getName() + ".a(" + Integer.class.getName() + ")"));
        assertThat(capabilityB.toString(), is(getClass().getPackage().getName() + ".b(" + Integer.class.getName() + ")"));
    }


}
