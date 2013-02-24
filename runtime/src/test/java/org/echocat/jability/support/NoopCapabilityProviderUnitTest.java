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

import org.echocat.jability.CapabilityProvider;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.IteratorMatchers.returnsNothing;

public class NoopCapabilityProviderUnitTest {

    @Test
    public void testProvideBy() throws Exception {
        assertThat(capabilityProvider().provideBy(String.class, "a"), is(null));
        assertThat(capabilityProvider().provideBy(Integer.class, "c"), is(null));
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(capabilityProvider().iterator(), returnsNothing());
    }

    @Nonnull
    protected static CapabilityProvider capabilityProvider() {
        return new NoopCapabilityProvider();
    }
}
