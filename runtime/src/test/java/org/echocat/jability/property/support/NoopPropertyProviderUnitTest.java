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

import org.echocat.jability.property.PropertyProvider;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.IteratorMatchers.returnsNothing;

public class NoopPropertyProviderUnitTest {

    @Test
    public void testProvideBy() throws Exception {
        assertThat(propertyProvider().provideBy(String.class, "a"), is(null));
        assertThat(propertyProvider().provideBy(Integer.class, "c"), is(null));
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(propertyProvider().iterator(), returnsNothing());
    }

    @Nonnull
    protected static PropertyProvider propertyProvider() {
        return new NoopPropertyProvider();
    }
}
