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
import org.echocat.jomon.testing.BaseMatchers;
import org.junit.Test;

import static org.echocat.jability.support.CapabilityUtils.newCapability;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;

public class CapabilityUtilsUnitTest {

    @Test
    public void testWithIdAndDefaultValue() throws Exception {
        final Capability<String> capabilityA = newCapability(String.class, "a", "myDefault");
        assertThat(capabilityA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(capabilityA.getId(), is("a"));
        assertThat(capabilityA.getDefaultValue(), is("myDefault"));

        final Capability<Integer> capabilityB = newCapability(Integer.class, "b", null);
        assertThat(capabilityB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(capabilityB.getId(), is("b"));
        assertThat(capabilityB.getDefaultValue(), is(null));
    }

    @Test
    public void testWithId() throws Exception {
        final Capability<String> capabilityA = newCapability(String.class, "a");
        assertThat(capabilityA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(capabilityA.getId(), is("a"));
        assertThat(capabilityA.getDefaultValue(), is(null));

        final Capability<Integer> capabilityB = newCapability(Integer.class, "b");
        assertThat(capabilityB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(capabilityB.getId(), is("b"));
        assertThat(capabilityB.getDefaultValue(), is(null));
    }


    @Test
    public void testWithBasedOnSubIdAndDefaultValue() throws Exception {
        final Capability<String> capabilityA = newCapability(String.class, getClass(), "a", "myDefault");
        assertThat(capabilityA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(capabilityA.getId(), is(getClass().getPackage().getName() + ".a"));
        assertThat(capabilityA.getDefaultValue(), is("myDefault"));

        final Capability<Integer> capabilityB = newCapability(Integer.class, getClass(), "b", null);
        assertThat(capabilityB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(capabilityB.getId(), is(getClass().getPackage().getName() + ".b"));
        assertThat(capabilityB.getDefaultValue(), is(null));
    }

    @Test
    public void testWithBasedOnAndSubId() throws Exception {
        final Capability<String> capabilityA = newCapability(String.class, getClass(), "a");
        assertThat(capabilityA.getValueType(), BaseMatchers.<Class<?>>is(String.class));
        assertThat(capabilityA.getId(), is(getClass().getPackage().getName() + ".a"));
        assertThat(capabilityA.getDefaultValue(), is(null));

        final Capability<Integer> capabilityB = newCapability(Integer.class, getClass(), "b");
        assertThat(capabilityB.getValueType(), BaseMatchers.<Class<?>>is(Integer.class));
        assertThat(capabilityB.getId(), is(getClass().getPackage().getName() + ".b"));
        assertThat(capabilityB.getDefaultValue(), is(null));
    }


}
