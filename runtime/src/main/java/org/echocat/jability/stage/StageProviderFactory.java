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

import org.echocat.jability.configuration.Configuration;
import org.echocat.jability.configuration.stage.StageConfiguration;
import org.echocat.jability.configuration.stage.StageReferenceConfiguration;
import org.echocat.jability.configuration.stage.StageProviderConfiguration;
import org.echocat.jability.configuration.stage.StagesRootConfiguration;
import org.echocat.jability.stage.Stage.Impl;
import org.echocat.jability.stage.support.BaseStageProvider;
import org.echocat.jability.stage.support.FieldBasedStageProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static java.util.ServiceLoader.load;
import static org.echocat.jability.configuration.stage.StagesRootConfiguration.getAvailableStageIdsBasedOn;
import static org.echocat.jability.configuration.stage.StagesRootConfiguration.getCurrentStageIdBasedOn;
import static org.echocat.jability.support.AccessType.PUBLIC;
import static org.echocat.jability.support.DiscoverUtils.*;
import static org.echocat.jomon.runtime.CollectionUtils.addAll;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;

public class StageProviderFactory {

    @Nonnull
    public static StageProvider createBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        return createBy(classLoader, configuration != null ? configuration.getStages() : null);
    }

    @Nonnull
    public static StageProvider createBy(@Nullable ClassLoader classLoader, @Nullable StagesRootConfiguration configuration) {
        final List<StageProvider> delegates = createAllBy(classLoader, configuration);
        final String currentId = getCurrentStageIdBasedOn(configuration);
        final Iterable<String> availableStages = getAvailableStageIdsBasedOn(configuration);
        return new CompoundStageProvider(delegates, availableStages, currentId);
    }

    @Nonnull
    public static List<StageProvider> createAllBy(@Nullable ClassLoader classLoader, @Nullable StagesRootConfiguration configuration) {
        final List<StageProvider> providers = new ArrayList<>();
        if (configuration != null) {
            providers.addAll(createAllSpecificBy(configuration.getStages()));
            providers.addAll(createAllReferencedBy(classLoader, configuration.getReferences()));
            providers.addAll(createAllBy(classLoader, configuration.getProviders()));
        }
        if (configuration == null || configuration.isRespectSystemProviders()) {
            providers.addAll(createAllSystemsBy(classLoader));
        }
        return asImmutableList(providers);
    }

    @Nonnull
    public static List<StageProvider> createAllSystemsBy(@Nullable ClassLoader classLoader) {
        final List<StageProvider> providers = new ArrayList<>();
        final ClassLoader targetClassLoader = selectClassLoader(classLoader);
        addAll(providers, load(StageProvider.class, targetClassLoader));
        for (Class<?> currentType : discoverTypesOf(Stage.class, null, targetClassLoader)) {
            // noinspection unchecked
            providers.add(new FieldBasedStageProvider(Stage.class, currentType, null, null, PUBLIC));
        }
        return asImmutableList(providers);
    }

    @Nonnull
    public static List<StageProvider> createAllSpecificBy(@Nullable Iterable<StageConfiguration> configurations) {
        final List<Stage> stages = new ArrayList<>();
        if (configurations != null) {
            for (StageConfiguration configuration : configurations) {
                stages.add(new Impl(configuration));
            }
        }
        final List<StageProvider> providers = new ArrayList<>();
        if (!stages.isEmpty()) {
            providers.add(new BaseStageProvider<>(stages));
        }
        return asImmutableList(providers);
    }

    @Nonnull
    public static List<StageProvider> createAllReferencedBy(@Nullable ClassLoader classLoader, @Nullable Iterable<StageReferenceConfiguration> configurations) {
        final List<StageProvider> providers = new ArrayList<>();
        if (configurations != null) {
            for (StageReferenceConfiguration configuration : configurations) {
                providers.add(new FieldBasedStageProvider<>(classLoader, configuration));
            }
        }
        return asImmutableList(providers);
    }

    @Nonnull
    public static List<StageProvider> createAllBy(@Nullable ClassLoader classLoader, @Nullable Iterable<StageProviderConfiguration> configurations) {
        final List<StageProvider> providers = new ArrayList<>();
        if (configurations != null) {
            for (StageProviderConfiguration configuration : configurations) {
                final String typeName = configuration.getType();
                providers.add(createInstanceBy(classLoader, typeName, StageProvider.class));
            }
        }
        return asImmutableList(providers);
    }

    private StageProviderFactory() {}

}
