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

import org.echocat.jability.stage.Stage;
import org.junit.Test;

import java.util.Date;

import static org.echocat.jability.property.CompoundPropertyDefinitionProvider.propertyDefinitionProvider;
import static org.echocat.jability.property.Properties.*;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.BaseMatchers.isSameAs;
import static org.junit.Assert.fail;

public class CompoundPropertyDefinitionProviderUnitTest {

    @Test
    public void test() throws Exception {
        assertThat(propertyDefinitionProvider().provideBy(Date.class, validFrom.getId()), isSameAs(validFrom));
        assertThat(propertyDefinitionProvider().provideBy(Date.class, validTo.getId()), isSameAs(validTo));
        assertThat(propertyDefinitionProvider().provideBy(Stage.class, minimumRequiredStage.getId()), isSameAs(minimumRequiredStage));
    }

    @Test
    public void testWrongType() throws Exception {
        try {
            propertyDefinitionProvider().provideBy(String.class, validFrom.getId());
            fail("expected exception missing");
        } catch (IllegalArgumentException expected) {}
    }

    @Test
    public void testNotExisting() throws Exception {
        assertThat(propertyDefinitionProvider().provideBy(String.class, validFrom.getId() + "x"), is(null));
    }

}
