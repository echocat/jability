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

import org.echocat.jability.Capability;
import org.echocat.jability.property.Properties;
import org.echocat.jability.property.Property;
import org.echocat.jability.support.CapabilityUtils;
import org.junit.Test;

import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.junit.Assert.fail;

public class NoopPropertiesUnitTest {

    protected static final Properties PROPERTIES = new NoopProperties();
    protected static final Capability<Boolean> CA = CapabilityUtils.newCapability(Boolean.class, NoopProperties.class, "ca");
    protected static final Property<Boolean> PA = PropertyUtils.newProperty(Boolean.class, NoopProperties.class, "pa");
    protected static final Property<Integer> PB = PropertyUtils.newProperty(Integer.class, NoopProperties.class, "pb", 666);
    protected static final Property<Boolean> PC = PropertyUtils.newProperty(Boolean.class, NoopProperties.class, "pc", true);

    @Test
    public void testGet() throws Exception {
        assertThat(PROPERTIES.get(CA, PA), is(null));
        assertThat(PROPERTIES.get(CA, PB), is(666));
        assertThat(PROPERTIES.get(CA, PC), is(true));
        assertThat(PROPERTIES.get(CA, PA, true), is(true));
        assertThat(PROPERTIES.get(CA, PB, 777), is(777));
        assertThat(PROPERTIES.get(CA, PC, false), is(false));
    }

    @Test
    public void testIsEnabled() throws Exception {
        assertThat(PROPERTIES.isEnabled(CA, PA), is(false));
        assertThat(PROPERTIES.isEnabled(CA, PC), is(true));
        assertThat(PROPERTIES.isEnabled(CA, PA, true), is(true));
        assertThat(PROPERTIES.isEnabled(CA, PC, false), is(false));
    }

    @Test
    public void testThatModificationIsNotAllowed() throws Exception {
        assertThat(PROPERTIES.isModifiable(CA, PA), is(false));
        try {
            PROPERTIES.set(CA, PA, true);
            fail("Expected exception is missing.");
        } catch (UnsupportedOperationException expected) {}
        try {
            PROPERTIES.remove(CA, PA);
            fail("Expected exception is missing.");
        } catch (UnsupportedOperationException expected) {}
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(PROPERTIES.iterator(CA).hasNext(), is(false));
    }

}
