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

package org.echocat.jability.stage;

import org.junit.Test;

import static org.echocat.jability.stage.CompoundStageProvider.stageProvider;
import static org.echocat.jability.stage.Stages.*;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.BaseMatchers.isSameAs;

public class CompoundStageProviderUnitTest {

    @Test
    public void test() throws Exception {
        assertThat(stageProvider().provideBy(development.getId()), isSameAs(development));
        assertThat(stageProvider().provideBy(integration.getId()), isSameAs(integration));
        assertThat(stageProvider().provideBy(alpha.getId()), isSameAs(alpha));
        assertThat(stageProvider().provideBy(beta.getId()), isSameAs(beta));
        assertThat(stageProvider().provideBy(productive.getId()), isSameAs(productive));
    }

    @Test
    public void testNotExisting() throws Exception {
        assertThat(stageProvider().provideBy(development.getId() + "x"), is(null));
    }

}
