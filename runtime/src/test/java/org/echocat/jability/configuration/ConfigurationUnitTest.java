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

package org.echocat.jability.configuration;

import org.echocat.jability.configuration.impl.*;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.echocat.jability.configuration.ConfigurationMarshaller.marshal;
import static org.echocat.jability.configuration.Configurations.*;
import static org.junit.Assert.fail;

public class ConfigurationUnitTest {

    @Test
    public void test() throws Exception {
        final Configuration configuration = configurationReference();
        fail(marshal(configuration));
    }

    @Nonnull
    public static Configuration configurationReference() {
        return configuration(
            capabilitiesConfiguration(
                respectSystemCapabilities(true),
                respectSystemCapabilityProviders(true),
                capabilities(TestCapabilitiesA.class),
                capabilities(TestCapabilitiesA.class),
                capabilityProvider(TestCapabilityProviderA.class),
                capabilityProvider(TestCapabilityProviderB.class),
                capabilityReference(TestCapabilityReferenceA.class, "test.*"),
                capabilityReference(TestCapabilityReferenceB.class, "test.*")
            ),
            propertiesConfiguration(
                respectSystemProperties(true),
                respectSystemPropertyProviders(true),
                properties(TestPropertiesA.class),
                properties(TestPropertiesB.class),
                propertyProvider(TestPropertyProviderA.class),
                propertyProvider(TestPropertyProviderB.class),
                propertyReference(TestPropertyReferenceA.class, "test.*"),
                propertyReference(TestPropertyReferenceB.class, "test.*")
            ),
            stagesConfiguration(
                currentStageId("testCurrent"),
                respectSystemStageProviders(true),
                availableStage("a"),
                availableStage("b"),
                availableStage("c"),
                stage("a", 1),
                stage("b", 2),
                stage("c", 3),
                stage("d", 4),
                stageProvider(TestStageProviderA.class),
                stageProvider(TestStageProviderB.class),
                stageReference(TestStageReferenceA.class, "test.*"),
                stageReference(TestStageReferenceB.class, "test.*")
            ),
            jmxConfiguration(
                propagateToJmx(true)
            )
        );
    }

}
