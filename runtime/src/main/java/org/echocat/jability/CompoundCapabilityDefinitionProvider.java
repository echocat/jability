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

import org.echocat.jability.support.FieldBasedCapabilityDefinitionProvider;
import org.echocat.jability.value.CompoundValueDefinitionProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.currentThread;
import static java.util.ServiceLoader.load;
import static org.echocat.jability.support.AccessType.PUBLIC;
import static org.echocat.jability.support.DiscoverUtils.discoverTypesOf;
import static org.echocat.jomon.runtime.CollectionUtils.addAll;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;

public class CompoundCapabilityDefinitionProvider extends CompoundValueDefinitionProvider<CapabilityDefinition<?>, CapabilityDefinitionProvider>  implements CapabilityDefinitionProvider {

    private static final CompoundCapabilityDefinitionProvider INSTANCE = new CompoundCapabilityDefinitionProvider();

    @Nonnull
    public static CompoundCapabilityDefinitionProvider capabilityDefinitionProvider() {
        return INSTANCE;
    }

    public CompoundCapabilityDefinitionProvider(@Nullable Iterable<CapabilityDefinitionProvider> delegates) {
        super(delegates);
    }

    public CompoundCapabilityDefinitionProvider(@Nullable CapabilityDefinitionProvider... delegates) {
        super(delegates);
    }

    public CompoundCapabilityDefinitionProvider(@Nullable ClassLoader classLoader) {
        this(loadSystemProviderBy(classLoader));
    }

    public CompoundCapabilityDefinitionProvider() {
        this((ClassLoader) null);
    }

    @Nullable
    @Override
    public <V> CapabilityDefinition<V> provideBy(@Nonnull Class<V> valueType, @Nonnull String id) {
        return (CapabilityDefinition<V>) super.provideBy(valueType, id);
    }

    @Nonnull
    public static Iterable<CapabilityDefinitionProvider> loadSystemProviderBy(@Nullable ClassLoader classLoader) {
        final ClassLoader targetClassLoader = classLoader != null ? classLoader : currentThread().getContextClassLoader();
        final List<CapabilityDefinitionProvider> providers = new ArrayList<>();
        addAll(providers, load(CapabilityDefinitionProvider.class, targetClassLoader));
        // noinspection rawtypes
        for (Class<?> currentType : discoverTypesOf(CapabilityDefinition.class, null, targetClassLoader)) {
            providers.add(new FieldBasedCapabilityDefinitionProvider<>(CapabilityDefinition.class, currentType, PUBLIC));
        }
        return asImmutableList(providers);
    }

}