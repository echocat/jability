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

import org.echocat.jability.stage.Stage.Impl;
import org.junit.Test;

import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;
import static org.echocat.jomon.testing.BaseMatchers.not;

public class StageUnitTest {

    @Test
    public void test() throws Exception {
        final Impl stageA = new Impl("a", 1);
        assertThat(stageA.getId(), is("a"));
        assertThat(stageA.getPriority(), is(1));

        final Impl stageB = new Impl("b", 666);
        assertThat(stageB.getId(), is("b"));
        assertThat(stageB.getPriority(), is(666));
    }

    @Test
    public void testEquals() throws Exception {
        final Impl stageA = new Impl("a", 1);
        final Impl stageA2 = new Impl("a", 6);
        final Impl stageB = new Impl("b", 666);

        assertThat(stageA.equals(stageA), is(true));
        assertThat(stageA.equals(stageA2), is(true));
        assertThat(stageA.equals(stageB), is(false));
        assertThat(stageA.equals(new Object()), is(false));
    }

    @Test
    public void testHashCode() throws Exception {
        final Impl stageA = new Impl("a", 1);
        final Impl stageA2 = new Impl("a", 6);
        final Impl stageB = new Impl("b", 666);

        assertThat(stageA.hashCode(), is(stageA2.hashCode()));
        assertThat(stageA.hashCode(), not(is(stageB.hashCode())));
    }

    @Test
    public void testToString() throws Exception {
        final Impl stageA = new Impl("a", 1);
        final Impl stageA2 = new Impl("a", 6);
        final Impl stageB = new Impl("b", 666);

        assertThat(stageA.toString(), is("a"));
        assertThat(stageA2.toString(), is("a"));
        assertThat(stageB.toString(), is("b"));
    }

}
