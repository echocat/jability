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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.fail;
import static org.echocat.jability.stage.Stages.unknown;
import static org.echocat.jability.stage.support.StageUtils.newStage;
import static org.echocat.jomon.runtime.CollectionUtils.*;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.*;
import static org.echocat.jomon.testing.BaseMatchers.isEmpty;
import static org.echocat.jomon.testing.CollectionMatchers.containsAllItemsOf;
import static org.echocat.jomon.testing.CollectionMatchers.isEqualTo;
import static org.echocat.jomon.testing.IteratorMatchers.returnsItems;
import static org.echocat.jomon.testing.IteratorMatchers.returnsNothing;
import static org.echocat.jomon.testing.StringMatchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class CompoundStageProviderUnitTest {

    protected static final Stage SA = newStage("sa", 1);
    protected static final Stage SB = newStage("sb", 2);
    protected static final Stage SC = newStage("sc", 3);

    private final StageProvider _delegateA = mock(StageProvider.class, "a");
    private final StageProvider _delegateB = mock(StageProvider.class, "b");
    private final Collection<StageProvider> _delegates = asImmutableList(_delegateA, _delegateB);

    @Test
    public void testProvideBy() throws Exception {
        assertThat(stageProvider(null, null).provideBy("sa"), is(null));
        assertThat(stageProvider(_delegates, null).provideBy("sa"), is(null));

        final Stage anotherSa = mock(Stage.class, "anotherSa");
        doReturn(anotherSa).when(_delegateB).provideBy(SA.getId());
        assertThat(stageProvider(_delegates, null).provideBy(SA.getId()), is(null));
        assertThat(stageProvider(_delegates, null, SA).provideBy(SA.getId()), isSameAs(anotherSa));

        doReturn(SA).when(_delegateA).provideBy(SA.getId());
        assertThat(stageProvider(_delegates, null, SA).provideBy(SA.getId()), isSameAs(SA));
    }

    @Test
    public void testIterator() throws Exception {
        assertThat(stageProvider(null, null).iterator(), returnsNothing());
        assertThat(stageProvider(_delegates, null).iterator(), returnsNothing());

        doReturn(asIterator(SA, SC, SB)).when(_delegateB).iterator();
        assertThat(stageProvider(_delegates, null).iterator(), returnsNothing());

        doReturn(asIterator(SA, SC, SB)).when(_delegateB).iterator();
        assertThat(stageProvider(_delegates, null, SA, SB).iterator(), returnsItems(SA, SB));

        doReturn(asIterator(SA, SC, SB)).when(_delegateB).iterator();
        assertThat(stageProvider(_delegates, null, SA, SB, SC).iterator(), returnsItems(SA, SC, SB));

        doReturn(asIterator(SA, SB)).when(_delegateA).iterator();
        doReturn(asIterator(SA, SC, SB)).when(_delegateB).iterator();
        assertThat(asList(stageProvider(_delegates, null, SA, SB, SC).iterator()), isEqualTo(SA, SB, SC));
    }

    @Test
    public void testProvideCurrent() throws Exception {
        assertThat(stageProvider(null, null).provideCurrent(), is(unknown));
        assertThat(stageProvider(_delegates, null).provideCurrent(), is(unknown));
        assertThat(stageProvider(_delegates, null, SA, SB, SC).provideCurrent(), is(unknown));

        doReturn(SB).when(_delegateB).provideCurrent();
        assertThat(stageProvider(_delegates, null, SA, SC).provideCurrent(), is(unknown));
        assertThat(stageProvider(_delegates, null, SA, SB, SC).provideCurrent(), is(SB));

        doReturn(SA).when(_delegateA).provideCurrent();
        assertThat(stageProvider(_delegates, null, SC).provideCurrent(), is(unknown));
        assertThat(stageProvider(_delegates, null, SB, SC).provideCurrent(), is(SB));
        assertThat(stageProvider(_delegates, null, SA, SB, SC).provideCurrent(), is(SA));

        assertThat(stageProvider(_delegates, SA, SA, SB, SC).provideCurrent(), is(SA));
    }

    @Test
    public void testCreation() throws Exception {
        final CompoundStageProvider providerA = new CompoundStageProvider(null, null, (String) null);
        assertThat(providerA.getDelegates(), isEmpty());
        assertThat(providerA.getAvailableIds(), is(null));
        assertThat(providerA.getCurrent(), is(null));

        final CompoundStageProvider providerB = new CompoundStageProvider(_delegates, null, (String) null);
        assertThat(providerB.getDelegates(), isEqualTo(_delegates));
        assertThat(providerB.getAvailableIds(), is(null));
        assertThat(providerB.getCurrent(), is(null));

        final CompoundStageProvider providerC = new CompoundStageProvider(_delegates, null, SA);
        assertThat(providerC.getDelegates(), isEqualTo(_delegates));
        assertThat(providerC.getAvailableIds(), is(null));
        assertThat(providerC.getCurrent(), is(SA));

        doReturn(SA).when(_delegateA).provideBy(SA.getId());
        final CompoundStageProvider providerD = new CompoundStageProvider(_delegates, null, SA.getId());
        assertThat(providerD.getDelegates(), isEqualTo(_delegates));
        assertThat(providerD.getAvailableIds(), is(null));
        assertThat(providerD.getCurrent(), is(SA));

        doReturn(SA).when(_delegateA).provideBy(SA.getId());
        final CompoundStageProvider providerE = new CompoundStageProvider(_delegates, asList(SA.getId(), SB.getId()), SA.getId());
        assertThat(providerE.getDelegates(), isEqualTo(_delegates));
        assertThat(providerE.getAvailableIds(), containsAllItemsOf(SA.getId(), SA.getId()));
        assertThat(providerE.getCurrent(), is(SA));
    }

    @Test
    public void testThatCreationWithIllegalCurrent() throws Exception {
        try {
            new CompoundStageProvider(null, null, SA.getId());
            fail("Expected exception missing.");
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(), contains("could not be resolved"));
        }

        doReturn(SA).when(_delegateA).provideBy(SA.getId());
        try {
            new CompoundStageProvider(_delegates, Collections.<String>emptySet(), SA.getId());
            fail("Expected exception missing.");
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(), contains("is not allowed"));
        }

        try {
            new CompoundStageProvider(_delegates, Collections.<String>emptySet(), SA);
            fail("Expected exception missing.");
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(), contains("is not allowed"));
        }
    }

    @Nonnull
    protected static StageProvider stageProvider(@Nullable Iterable<StageProvider> delegates, @Nullable Stage current, @Nullable Stage... availableStages) {
        final Set<String> availableStageIds = availableStages != null ? new HashSet<String>(availableStages.length) : null;
        if (availableStages != null) {
            for (Stage availableStage : availableStages) {
                availableStageIds.add(availableStage.getId());
            }
        }
        return new CompoundStageProvider(delegates, availableStageIds, current);
    }

}
