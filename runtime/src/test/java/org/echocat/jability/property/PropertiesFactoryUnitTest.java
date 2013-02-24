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

package org.echocat.jability.property;

import org.echocat.jability.configuration.property.PropertiesRootConfiguration;
import org.echocat.jability.impl.TestPropertiesA;
import org.echocat.jability.impl.TestPropertiesB;
import org.echocat.jability.impl.TestSystemProperties;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.List;

import static org.echocat.jability.configuration.Configurations.*;
import static org.echocat.jability.configuration.property.PropertiesRootConfiguration.propertiesConfiguration;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.isInstanceOf;
import static org.echocat.jomon.testing.CollectionMatchers.hasSize;

public class PropertiesFactoryUnitTest {

    protected static final PropertiesFactory FACTORY = new PropertiesFactory();

    @Test
    public void testWithSystemProperties() throws Exception {
        final Properties compoundProperties = FACTORY.createBy(null, configuration(examplePropertyConfiguration(true)));
        assertThat(compoundProperties, isInstanceOf(CompoundProperties.class));
        final List<Properties> delegates = asImmutableList(((CompoundProperties) compoundProperties).getDelegates());
        assertThat(delegates, hasSize(3));
        assertThat(delegates.get(0), isInstanceOf(TestPropertiesA.class));
        assertThat(delegates.get(1), isInstanceOf(TestPropertiesB.class));
        assertThat(delegates.get(2), isInstanceOf(TestSystemProperties.class));
    }

    public void testWithoutSystemProperties() throws Exception {
        final Properties compoundProperties = FACTORY.createBy(null, configuration(examplePropertyConfiguration(false)));
        assertThat(compoundProperties, isInstanceOf(CompoundProperties.class));
        final List<Properties> delegates = asImmutableList(((CompoundProperties) compoundProperties).getDelegates());
        assertThat(delegates, hasSize(2));
        assertThat(delegates.get(0), isInstanceOf(TestPropertiesA.class));
        assertThat(delegates.get(1), isInstanceOf(TestPropertiesB.class));
    }

    @Nonnull
    protected static PropertiesRootConfiguration examplePropertyConfiguration(boolean respectSystemProperties) {
        return propertiesConfiguration().with(
            respectSystemProperties(respectSystemProperties),
            properties(TestPropertiesA.class),
            properties(TestPropertiesB.class)
        );
    }
}
