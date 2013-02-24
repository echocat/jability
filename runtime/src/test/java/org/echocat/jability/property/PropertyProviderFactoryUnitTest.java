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
import org.echocat.jability.impl.*;
import org.echocat.jomon.testing.IteratorMatchers;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.List;

import static org.echocat.jability.configuration.Configurations.*;
import static org.echocat.jability.configuration.property.PropertiesRootConfiguration.propertiesConfiguration;
import static org.echocat.jability.impl.TestPropertyReferenceA.testPropA1;
import static org.echocat.jability.impl.TestPropertyReferenceA.testPropA2;
import static org.echocat.jability.impl.TestPropertyReferenceB.testPropB1;
import static org.echocat.jability.impl.TestPropertyReferenceB.testPropB2;
import static org.echocat.jability.property.Property.*;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.isInstanceOf;
import static org.echocat.jomon.testing.CollectionMatchers.hasSize;

public class PropertyProviderFactoryUnitTest {

    protected static final PropertyProviderFactory FACTORY = new PropertyProviderFactory();

    @Test
    public void testWithSystemProviders() throws Exception {
        final PropertyProvider compoundProvider = FACTORY.createBy(null, configuration(examplePropertyConfiguration(true)));
        assertThat(compoundProvider, isInstanceOf(CompoundPropertyProvider.class));
        final List<PropertyProvider> delegates = asImmutableList(((CompoundPropertyProvider) compoundProvider).getDelegates());
        assertThat(delegates, hasSize(6));
        assertThat(delegates.get(0).iterator(), IteratorMatchers.<Property<?>>returnsItems(testPropA1, testPropA2));
        assertThat(delegates.get(1).iterator(), IteratorMatchers.<Property<?>>returnsItems(testPropB1, testPropB2));
        assertThat(delegates.get(2), isInstanceOf(TestPropertyProviderA.class));
        assertThat(delegates.get(3), isInstanceOf(TestPropertyProviderB.class));
        assertThat(delegates.get(4), isInstanceOf(TestSystemPropertyProvider.class));
        assertThat(delegates.get(5).iterator(), IteratorMatchers.<Property<?>>returnsItems(validFrom, validTo, minimumRequiredStage));
    }

    @Test
    public void testWithoutSystemProviders() throws Exception {
        final PropertyProvider compoundProvider = FACTORY.createBy(null, configuration(examplePropertyConfiguration(false)));
        assertThat(compoundProvider, isInstanceOf(CompoundPropertyProvider.class));
        final List<PropertyProvider> delegates = asImmutableList(((CompoundPropertyProvider) compoundProvider).getDelegates());
        assertThat(delegates, hasSize(4));
        assertThat(delegates.get(0).iterator(), IteratorMatchers.<Property<?>>returnsItems(testPropA1, testPropA2));
        assertThat(delegates.get(1).iterator(), IteratorMatchers.<Property<?>>returnsItems(testPropB1, testPropB2));
        assertThat(delegates.get(2), isInstanceOf(TestPropertyProviderA.class));
        assertThat(delegates.get(3), isInstanceOf(TestPropertyProviderB.class));
    }

    @Nonnull
    protected static PropertiesRootConfiguration examplePropertyConfiguration(boolean respectSystemProviders) {
        return propertiesConfiguration().with(
            respectSystemPropertyProviders(respectSystemProviders),
            propertyProvider(TestPropertyProviderA.class),
            propertyProvider(TestPropertyProviderB.class),
            propertyReference(TestPropertyReferenceA.class, "test.*"),
            propertyReference(TestPropertyReferenceB.class, "test.*")
        );
    }

}
