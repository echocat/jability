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

import org.echocat.jability.stage.Stage;
import org.echocat.jability.stage.StageProvider;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.echocat.jability.stage.Stages.unknown;
import static org.echocat.jability.stage.support.StageUtils.newStage;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.IteratorMatchers.returnsItems;

public class DefaultStageProviderUnitTest {

    protected static final Stage A = newStage("a", 1);
    protected static final Stage B = newStage("b", 2);
    protected static final Stage C = newStage("c", 3);

    @Test
    public void testProvideBy() throws Exception {
        assertThat(stageProvider().provideBy("a"), is(A));
        assertThat(stageProvider().provideBy("b"), is(B));
        assertThat(stageProvider().provideBy("c"), is(C));
        assertThat(stageProvider().provideBy("d"), is(null));
    }

    @Test
    public void testProvideCurrent() throws Exception {
        assertThat(stageProvider().provideCurrent(), is(unknown));
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(stageProvider().iterator(), returnsItems(A, B, C));
    }

    @Nonnull
    protected static StageProvider stageProvider() {
        return new DefaultStageProvider<>(asImmutableList(A, B, C));
    }

}
