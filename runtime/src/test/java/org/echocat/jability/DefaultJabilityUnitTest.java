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

import org.echocat.jability.jmx.CapabilityPropagator;
import org.echocat.jability.property.CompoundProperties;
import org.echocat.jability.property.CompoundPropertyProvider;
import org.echocat.jability.property.support.NoopProperties;
import org.echocat.jability.property.support.NoopPropertyProvider;
import org.echocat.jability.stage.CompoundStageProvider;
import org.echocat.jability.stage.support.NoopStageProvider;
import org.echocat.jability.support.NoopCapabilities;
import org.echocat.jability.support.NoopCapabilityProvider;
import org.junit.Test;

import static org.echocat.jability.configuration.ConfigurationUnitTest.configurationReference;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.BaseMatchers.isInstanceOf;
import static org.mockito.Mockito.*;

public class DefaultJabilityUnitTest {

    @Test
    public void testWithoutInitialization() throws Exception {
        try (final DefaultJability jability = new DefaultJability()) {
            assertThat(jability.getCapabilities(), isInstanceOf(NoopCapabilities.class));
            assertThat(jability.getCapabilityProvider(), isInstanceOf(NoopCapabilityProvider.class));
            assertThat(jability.getProperties(), isInstanceOf(NoopProperties.class));
            assertThat(jability.getPropertyProvider(), isInstanceOf(NoopPropertyProvider.class));
            assertThat(jability.getStageProvider(), isInstanceOf(NoopStageProvider.class));
            assertThat(jability.getCapabilityPropagator(), is(null));
        }
    }

    @Test
    public void testWithInitialization() throws Exception {
        try (final DefaultJability jability = new DefaultJability()) {
            jability.load(null, configurationReference());
            assertThat(jability.getCapabilities(), isInstanceOf(CompoundCapabilities.class));
            assertThat(jability.getCapabilityProvider(), isInstanceOf(CompoundCapabilityProvider.class));
            assertThat(jability.getProperties(), isInstanceOf(CompoundProperties.class));
            assertThat(jability.getPropertyProvider(), isInstanceOf(CompoundPropertyProvider.class));
            assertThat(jability.getStageProvider(), isInstanceOf(CompoundStageProvider.class));
            assertThat(jability.getCapabilityPropagator(), isInstanceOf(CapabilityPropagator.class));
        }
    }

    @Test
    public void testRightCloseOfPropagatorIfSet() throws Exception {
        try (final DefaultJability jability = new DefaultJability()) {
            final CapabilityPropagator propagator = mock(CapabilityPropagator.class);
            jability.setCapabilityPropagator(propagator);
            verify(propagator, times(0)).close();
            jability.close();
            verify(propagator, times(1)).close();
        }
    }

    @Test
    public void testRightCloseOfPropagatorIfSetOnManualSetOfPropagator() throws Exception {
        try (final DefaultJability jability = new DefaultJability()) {
            final CapabilityPropagator propagatorA = mock(CapabilityPropagator.class);
            final CapabilityPropagator propagatorB = mock(CapabilityPropagator.class);
            jability.setCapabilityPropagator(propagatorA);
            verify(propagatorA, times(0)).close();
            verify(propagatorB, times(0)).close();
            jability.setCapabilityPropagator(propagatorA);
            verify(propagatorA, times(0)).close();
            verify(propagatorB, times(0)).close();
            jability.setCapabilityPropagator(propagatorB);
            verify(propagatorA, times(1)).close();
            verify(propagatorB, times(0)).close();
        }
    }

}
