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
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.echocat.jability.stage.support.StageUtils.newStage;
import static org.echocat.jability.stage.support.StageUtils.stageComparator;
import static org.echocat.jomon.runtime.CollectionUtils.asList;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.CollectionMatchers.isEqualTo;

public class StageUtilsUnitTest {

    @Test
    public void testNewStage() throws Exception {
        final Stage stageA = newStage("a", 1);
        assertThat(stageA.getId(), is("a"));
        assertThat(stageA.getPriority(), is(1));

        final Stage stageB = newStage("b", -2);
        assertThat(stageB.getId(), is("b"));
        assertThat(stageB.getPriority(), is(-2));
    }

    @Test
    public void testStageComparator() throws Exception {
        final Stage stageA = newStage("a", -666);
        final Stage stageB = newStage("b", 0);
        final Stage stageC = newStage("c", 7);
        final Stage stageD = newStage("d", 1);

        final List<Stage> stages = asList(stageA, null, stageC, stageD, stageB, null);
        Collections.sort(stages, stageComparator());
        assertThat(stages, isEqualTo(stageA, stageB, stageD, stageC, null, null));
    }

}
