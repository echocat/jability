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

import org.echocat.jability.configuration.stage.StagesRootConfiguration;
import org.echocat.jability.impl.*;
import org.echocat.jomon.testing.IteratorMatchers;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.List;

import static org.echocat.jability.configuration.Configurations.*;
import static org.echocat.jability.impl.TestStageReferenceA.testStageA1;
import static org.echocat.jability.impl.TestStageReferenceA.testStageA2;
import static org.echocat.jability.impl.TestStageReferenceB.testStageB1;
import static org.echocat.jability.impl.TestStageReferenceB.testStageB2;
import static org.echocat.jability.stage.Stages.*;
import static org.echocat.jability.stage.support.StageUtils.newStage;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.isInstanceOf;
import static org.echocat.jomon.testing.CollectionMatchers.hasSize;

public class StageProviderFactoryUnitTest {

    protected static final StageProviderFactory FACTORY = new StageProviderFactory();

    @Test
    public void testWithSystemProviders() throws Exception {
        final StageProvider compoundProvider = FACTORY.createBy(null, configuration(exampleStageConfiguration(true)));
        assertThat(compoundProvider, isInstanceOf(CompoundStageProvider.class));
        final List<StageProvider> delegates = asImmutableList(((CompoundStageProvider) compoundProvider).getDelegates());
        assertThat(delegates, hasSize(7));
        assertThat(delegates.get(0).iterator(), IteratorMatchers.<Stage>returnsItems(newStage("a", 1), newStage("b", 2), newStage("c", 3)));
        assertThat(delegates.get(1).iterator(), IteratorMatchers.<Stage>returnsItems(testStageA1, testStageA2));
        assertThat(delegates.get(2).iterator(), IteratorMatchers.<Stage>returnsItems(testStageB1, testStageB2));
        assertThat(delegates.get(3), isInstanceOf(TestStageProviderA.class));
        assertThat(delegates.get(4), isInstanceOf(TestStageProviderB.class));
        assertThat(delegates.get(5), isInstanceOf(TestSystemStageProvider.class));
        assertThat(delegates.get(6).iterator(), IteratorMatchers.<Stage>returnsItems(unknown, development, integration, alpha, beta, productive));
    }

    @Test
    public void testWithoutSystemProviders() throws Exception {
        final StageProvider compoundProvider = FACTORY.createBy(null, configuration(exampleStageConfiguration(false)));
        assertThat(compoundProvider, isInstanceOf(CompoundStageProvider.class));
        final List<StageProvider> delegates = asImmutableList(((CompoundStageProvider) compoundProvider).getDelegates());
        assertThat(delegates, hasSize(5));
        assertThat(delegates.get(0).iterator(), IteratorMatchers.<Stage>returnsItems(newStage("a", 1), newStage("b", 2), newStage("c", 3)));
        assertThat(delegates.get(1).iterator(), IteratorMatchers.<Stage>returnsItems(testStageA1, testStageA2));
        assertThat(delegates.get(2).iterator(), IteratorMatchers.<Stage>returnsItems(testStageB1, testStageB2));
        assertThat(delegates.get(3), isInstanceOf(TestStageProviderA.class));
        assertThat(delegates.get(4), isInstanceOf(TestStageProviderB.class));
    }

    @Nonnull
    protected static StagesRootConfiguration exampleStageConfiguration(boolean respectSystemProviders) {
        return stagesConfiguration().with(
            respectSystemStageProviders(respectSystemProviders),
            stage("a", 1),
            stage("b", 2),
            stage("c", 3),
            stageProvider(TestStageProviderA.class),
            stageProvider(TestStageProviderB.class),
            stageReference(TestStageReferenceA.class, "test.*"),
            stageReference(TestStageReferenceB.class, "test.*")
        );
    }

}
