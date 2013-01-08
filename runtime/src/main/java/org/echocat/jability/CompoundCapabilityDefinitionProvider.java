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

public class CompoundCapabilityDefinitionProvider implements CapabilityDefinitionProvider {

    private static final CompoundCapabilityDefinitionProvider INSTANCE = new CompoundCapabilityDefinitionProvider();

    @Nonnull
    public static CapabilityDefinitionProvider capabilityDefinitionProvider() {
        return INSTANCE;
    }

    @Nullable
    public static <ID extends CapabilityDefinition<?>> ID capabilityDefinitionBy(@Nonnull Class<ID> idType, @Nonnull String value) {
        return INSTANCE.provideBy(idType, value);
    }

    private final Iterable<CapabilityDefinitionProvider> _delegates;

    public CompoundCapabilityDefinitionProvider(@Nullable Iterable<CapabilityDefinitionProvider> delegates) {
        _delegates = delegates != null ? delegates : Collections.<CapabilityDefinitionProvider>emptyList();
    }

    public CompoundCapabilityDefinitionProvider(@Nullable CapabilityDefinitionProvider... delegates) {
        this(delegates != null ? asList(delegates) : null);
    }

    public CompoundCapabilityDefinitionProvider(@Nullable ClassLoader classLoader) {
        this(loadSystemProviderBy(classLoader));
    }

    public CompoundCapabilityDefinitionProvider() {
        this((ClassLoader) null);
    }

    @Nullable
    @Override
    public <ID extends CapabilityDefinition<?>> ID provideBy(@Nonnull Class<ID> idType, @Nonnull String value) {
        ID result = null;
        for (CapabilityDefinitionProvider delegate : _delegates) {
            result = delegate.provideBy(idType, value);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<CapabilityDefinition<?>> iterator() {
        return new ChainedIterator<CapabilityDefinitionProvider, CapabilityDefinition<?>>(_delegates.iterator()) { @Nullable @Override protected Iterator<CapabilityDefinition<?>> nextIterator(@Nullable CapabilityDefinitionProvider input) {
            return input.iterator();
        }};
    }

    @Nonnull
    public static Iterable<CapabilityDefinitionProvider> loadSystemProviderBy() {
        return loadSystemProviderBy(null);
    }

    @Nonnull
    public static Iterable<CapabilityDefinitionProvider> loadSystemProviderBy(@Nullable ClassLoader classLoader) {
        final ClassLoader targetClassLoader = classLoader != null ? classLoader : currentThread().getContextClassLoader();
        return asImmutableList(load(CapabilityDefinitionProvider.class, targetClassLoader));
    }

}
