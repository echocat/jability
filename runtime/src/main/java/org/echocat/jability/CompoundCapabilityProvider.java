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

package org.echocat.jability;

import org.echocat.jomon.runtime.iterators.ChainedIterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;

import static java.lang.Thread.currentThread;
import static java.util.ServiceLoader.load;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;
import static org.echocat.jomon.runtime.CollectionUtils.asList;

public class CompoundCapabilityProvider implements CapabilityProvider {

    private static final CompoundCapabilityProvider INSTANCE = new CompoundCapabilityProvider();

    @Nonnull
    public static CapabilityProvider capabilityProvider() {
        return INSTANCE;
    }

    private final Iterable<CapabilityProvider> _delegates;

    public CompoundCapabilityProvider(@Nullable Iterable<CapabilityProvider> delegates) {
        _delegates = delegates != null ? delegates : Collections.<CapabilityProvider>emptyList();
    }

    public CompoundCapabilityProvider(@Nullable CapabilityProvider... delegates) {
        this(delegates != null ? asList(delegates) : null);
    }

    public CompoundCapabilityProvider(@Nullable ClassLoader classLoader) {
        this(loadSystemProviderBy(classLoader));
    }

    public CompoundCapabilityProvider() {
        this((ClassLoader) null);
    }

    @Nullable
    @Override
    public <ID extends CapabilityDefinition<V>, V, C extends Capability<ID, V>> C provideBy(@Nonnull ID id) {
        C result = null;
        for (CapabilityProvider delegate : _delegates) {
            result = delegate.provideBy(id);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<Capability<?, ?>> iterator() {
        return new ChainedIterator<CapabilityProvider, Capability<?, ?>>(_delegates.iterator()) { @Nullable @Override protected Iterator<Capability<?, ?>> nextIterator(@Nullable CapabilityProvider input) {
            return input.iterator();
        }};
    }

    @Nonnull
    public static Iterable<CapabilityProvider> loadSystemProviderBy() {
        return loadSystemProviderBy(null);
    }

    @Nonnull
    public static Iterable<CapabilityProvider> loadSystemProviderBy(@Nullable ClassLoader classLoader) {
        final ClassLoader targetClassLoader = classLoader != null ? classLoader : currentThread().getContextClassLoader();
        return asImmutableList(load(CapabilityProvider.class, targetClassLoader));
    }
}
