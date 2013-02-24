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

package org.echocat.jability.stage.support;

import org.echocat.jability.stage.StageProvider;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.echocat.jability.stage.Stages.unknown;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.IteratorMatchers.returnsNothing;

public class NoopStageProviderUnitTest {

    @Test
    public void testProvideBy() throws Exception {
        assertThat(stageProvider().provideBy("a"), is(null));
        assertThat(stageProvider().provideBy("b"), is(null));
        assertThat(stageProvider().provideBy("c"), is(null));
        assertThat(stageProvider().provideBy("d"), is(null));
    }

    @Test
    public void testProvideCurrent() throws Exception {
        assertThat(stageProvider().provideCurrent(), is(unknown));
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(stageProvider().iterator(), returnsNothing());
    }

    @Nonnull
    protected static StageProvider stageProvider() {
        return new NoopStageProvider();
    }

}
