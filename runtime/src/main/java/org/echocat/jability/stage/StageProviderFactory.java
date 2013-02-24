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
import org.echocat.jability.configuration.stage.StageProviderConfiguration;
import org.echocat.jability.configuration.stage.StageReferenceConfiguration;
import org.echocat.jability.configuration.stage.StagesRootConfiguration;
import org.echocat.jability.stage.Stage.Impl;
import org.echocat.jability.stage.support.DefaultStageProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.ServiceLoader.load;
import static org.echocat.jability.configuration.stage.StagesRootConfiguration.getAvailableStageIdsBasedOn;
import static org.echocat.jability.configuration.stage.StagesRootConfiguration.getCurrentStageIdBasedOn;
import static org.echocat.jability.support.AccessType.PUBLIC;
import static org.echocat.jability.support.DiscoverUtils.*;
import static org.echocat.jability.support.DiscoverUtils.discoverStaticFieldValuesBy;
import static org.echocat.jomon.runtime.CollectionUtils.addAll;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;

@ThreadSafe
@Immutable
public class StageProviderFactory {

    @Nonnull
    public StageProvider createBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        return createBy(classLoader, configuration != null ? configuration.getStages() : null);
    }

    @Nonnull
    public StageProvider createBy(@Nullable ClassLoader classLoader, @Nullable StagesRootConfiguration configuration) {
        final List<StageProvider> delegates = createAllBy(classLoader, configuration);
        final String currentId = getCurrentStageIdBasedOn(configuration);
        final Iterable<String> availableStages = getAvailableStageIdsBasedOn(configuration);
        return new CompoundStageProvider(delegates, availableStages, currentId);
    }

    @Nonnull
    public List<StageProvider> createAllBy(@Nullable ClassLoader classLoader, @Nullable StagesRootConfiguration configuration) {
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
    public List<StageProvider> createAllSystemsBy(@Nullable ClassLoader classLoader) {
        final List<StageProvider> providers = new ArrayList<>();
        final ClassLoader targetClassLoader = selectClassLoader(classLoader);
        addAll(providers, load(StageProvider.class, targetClassLoader));
        for (Class<?> currentType : discoverTypesOf(Stage.class, null, targetClassLoader)) {
            final Collection<Stage> stages = discoverStaticFieldValuesBy(Stage.class, currentType, null, null, PUBLIC);
            providers.add(new DefaultStageProvider<>(stages));
        }
        return asImmutableList(providers);
    }

    @Nonnull
    public List<StageProvider> createAllSpecificBy(@Nullable Iterable<StageConfiguration> configurations) {
        final List<Stage> stages = new ArrayList<>();
        if (configurations != null) {
            for (StageConfiguration configuration : configurations) {
                stages.add(new Impl(configuration));
            }
        }
        final List<StageProvider> providers = new ArrayList<>();
        if (!stages.isEmpty()) {
            providers.add(new DefaultStageProvider<>(stages));
        }
        return asImmutableList(providers);
    }

    @Nonnull
    public List<StageProvider> createAllReferencedBy(@Nullable ClassLoader classLoader, @Nullable Iterable<StageReferenceConfiguration> configurations) {
        final List<StageProvider> providers = new ArrayList<>();
        if (configurations != null) {
            for (StageReferenceConfiguration configuration : configurations) {
                final Class<?> type = loadClassBy(classLoader, configuration.getFromType());
                final Collection<Stage> stages = discoverStaticFieldValuesBy(Stage.class, type, null, configuration.getFromField(), configuration.getAccessTypes());
                providers.add(new DefaultStageProvider<>(stages));
            }
        }
        return asImmutableList(providers);
    }

    @Nonnull
    public List<StageProvider> createAllBy(@Nullable ClassLoader classLoader, @Nullable Iterable<StageProviderConfiguration> configurations) {
        final List<StageProvider> providers = new ArrayList<>();
        if (configurations != null) {
            for (StageProviderConfiguration configuration : configurations) {
                final String typeName = configuration.getType();
                providers.add(createInstanceBy(classLoader, typeName, StageProvider.class));
            }
        }
        return asImmutableList(providers);
    }

}
