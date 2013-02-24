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

import org.echocat.jability.jmx.CapabilityPropagater;
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
            assertThat(jability.getCapabilityPropagater(), is(null));
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
            assertThat(jability.getCapabilityPropagater(), isInstanceOf(CapabilityPropagater.class));
        }
    }

    @Test
    public void testRightCloseOfPropergaterIfSet() throws Exception {
        try (final DefaultJability jability = new DefaultJability()) {
            final CapabilityPropagater propagater = mock(CapabilityPropagater.class);
            jability.setCapabilityPropagater(propagater);
            verify(propagater, times(0)).close();
            jability.close();
            verify(propagater, times(1)).close();
        }
    }

    @Test
    public void testRightCloseOfPropergaterIfSetOnManualSetOfPropagater() throws Exception {
        try (final DefaultJability jability = new DefaultJability()) {
            final CapabilityPropagater propagaterA = mock(CapabilityPropagater.class);
            final CapabilityPropagater propagaterB = mock(CapabilityPropagater.class);
            jability.setCapabilityPropagater(propagaterA);
            verify(propagaterA, times(0)).close();
            verify(propagaterB, times(0)).close();
            jability.setCapabilityPropagater(propagaterA);
            verify(propagaterA, times(0)).close();
            verify(propagaterB, times(0)).close();
            jability.setCapabilityPropagater(propagaterB);
            verify(propagaterA, times(1)).close();
            verify(propagaterB, times(0)).close();
        }
    }

}
