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

import org.echocat.jability.Capabilities;
import org.echocat.jability.Capability;
import org.junit.Test;

import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.junit.Assert.fail;

public class NoopCapabilitiesUnitTest {

    protected static final Capabilities CAPABILITIES = new NoopCapabilities();
    protected static final Capability<Boolean> CA = CapabilityUtils.newCapability(Boolean.class, NoopCapabilities.class, "ca");
    protected static final Capability<Integer> CB = CapabilityUtils.newCapability(Integer.class, NoopCapabilities.class, "cb", 666);
    protected static final Capability<Boolean> CC = CapabilityUtils.newCapability(Boolean.class, NoopCapabilities.class, "cc", true);

    @Test
    public void testGet() throws Exception {
        assertThat(CAPABILITIES.get(CA), is(null));
        assertThat(CAPABILITIES.get(CB), is(666));
        assertThat(CAPABILITIES.get(CC), is(true));
        assertThat(CAPABILITIES.get(CA, true), is(true));
        assertThat(CAPABILITIES.get(CB, 777), is(777));
        assertThat(CAPABILITIES.get(CC, false), is(false));
    }

    @Test
    public void testIsEnabled() throws Exception {
        assertThat(CAPABILITIES.isEnabled(CA), is(false));
        assertThat(CAPABILITIES.isEnabled(CC), is(true));
        assertThat(CAPABILITIES.isEnabled(CA, true), is(true));
        assertThat(CAPABILITIES.isEnabled(CC, false), is(false));
    }

    @Test
    public void testThatModificationIsNotAllowed() throws Exception {
        assertThat(CAPABILITIES.isModifiable(CA), is(false));
        try {
            CAPABILITIES.set(CA, true);
            fail("Expected exception is missing.");
        } catch (UnsupportedOperationException expected) {}
        try {
            CAPABILITIES.remove(CA);
            fail("Expected exception is missing.");
        } catch (UnsupportedOperationException expected) {}
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(CAPABILITIES.iterator().hasNext(), is(false));
    }

}
