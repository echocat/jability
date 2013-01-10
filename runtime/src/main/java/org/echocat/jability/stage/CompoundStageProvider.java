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

import org.echocat.jability.stage.support.FieldBasedStageProvider;
import org.echocat.jomon.runtime.iterators.ChainedIterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static java.lang.Thread.currentThread;
import static java.util.ServiceLoader.load;
import static org.echocat.jability.stage.Stages.unknown;
import static org.echocat.jability.support.AccessType.PUBLIC;
import static org.echocat.jability.support.DiscoverUtils.discoverTypesOf;
import static org.echocat.jomon.runtime.CollectionUtils.*;

public class CompoundStageProvider implements StageProvider {

    private static final CompoundStageProvider INSTANCE = new CompoundStageProvider();

    @Nonnull
    public static CompoundStageProvider stageProvider() {
        return INSTANCE;
    }

    private final Iterable<StageProvider> _delegates;

    public CompoundStageProvider(@Nullable Iterable<StageProvider> delegates) {
        _delegates = delegates != null ? delegates : Collections.<StageProvider>emptyList();
    }

    public CompoundStageProvider(@Nullable StageProvider... delegates) {
        this(delegates != null ? asList(delegates) : null);
    }

    public CompoundStageProvider(@Nullable ClassLoader classLoader) {
        this(loadSystemProviderBy(classLoader));
    }

    public CompoundStageProvider() {
        this((ClassLoader) null);
    }

    @Nullable
    @Override
    public Stage provideBy(@Nonnull String id) {
        Stage result = unknown.getId().equals(id) ? unknown : null;
        if (result == null) {
            for (StageProvider delegate : _delegates) {
                result = delegate.provideBy(id);
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }

    @Nullable
    @Override
    public Stage provideCurrent() {
        Stage result = null;
        for (StageProvider delegate : _delegates) {
            result = delegate.provideCurrent();
            if (result != null) {
                break;
            }
        }
        return result != null ? result : unknown;
    }

    @Override
    public Iterator<Stage> iterator() {
        return new ChainedIterator<StageProvider, Stage>(_delegates.iterator()) { @Nullable @Override protected Iterator<Stage> nextIterator(@Nullable StageProvider input) {
            return input.iterator();
        }};
    }

    @Nonnull
    public static Iterable<StageProvider> loadSystemProviderBy() {
        return loadSystemProviderBy(null);
    }

    @Nonnull
    public static Iterable<StageProvider> loadSystemProviderBy(@Nullable ClassLoader classLoader) {
        final ClassLoader targetClassLoader = classLoader != null ? classLoader : currentThread().getContextClassLoader();
        final List<StageProvider> providers = new ArrayList<>();
        addAll(providers, load(StageProvider.class, targetClassLoader));
        for (Class<?> type : discoverTypesOf(Stage.class, null, targetClassLoader)) {
            providers.add(new FieldBasedStageProvider<>(Stage.class, type, PUBLIC));
        }
        return asImmutableList(providers);
    }

}
