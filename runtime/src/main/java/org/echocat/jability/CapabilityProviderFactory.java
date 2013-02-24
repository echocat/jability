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

import org.echocat.jability.configuration.Configuration;
import org.echocat.jability.configuration.capability.CapabilitiesRootConfiguration;
import org.echocat.jability.configuration.capability.CapabilityReferenceConfiguration;
import org.echocat.jability.configuration.capability.CapabilityProviderConfiguration;
import org.echocat.jability.support.FieldBasedCapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.ArrayList;
import java.util.List;

import static java.util.ServiceLoader.load;
import static org.echocat.jability.support.AccessType.PUBLIC;
import static org.echocat.jability.support.DiscoverUtils.*;
import static org.echocat.jomon.runtime.CollectionUtils.addAll;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;

@ThreadSafe
@Immutable
public class CapabilityProviderFactory {

    @Nonnull
    public CapabilityProvider createBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        return createBy(classLoader, configuration != null ? configuration.getCapabilities() : null);
    }

    @Nonnull
    public CapabilityProvider createBy(@Nullable ClassLoader classLoader, @Nullable CapabilitiesRootConfiguration configuration) {
        return new CompoundCapabilityProvider(createAllBy(classLoader, configuration));
    }

    @Nonnull
    public List<CapabilityProvider> createAllBy(@Nullable ClassLoader classLoader, @Nullable CapabilitiesRootConfiguration configuration) {
        final List<CapabilityProvider> providers = new ArrayList<>();
        if (configuration != null) {
            providers.addAll(createAllReferencedBy(classLoader, configuration.getReferences()));
            providers.addAll(createAllBy(classLoader, configuration.getProviders()));
        }
        if (configuration == null || configuration.isRespectSystemProviders()) {
            providers.addAll(createAllSystemsBy(classLoader));
        }
        return asImmutableList(providers);
    }

    @Nonnull
    public List<CapabilityProvider> createAllSystemsBy(@Nullable ClassLoader classLoader) {
        final List<CapabilityProvider> providers = new ArrayList<>();
        final ClassLoader targetClassLoader = selectClassLoader(classLoader);
        addAll(providers, load(CapabilityProvider.class, targetClassLoader));
        for (Class<?> currentType : discoverTypesOf(Capability.class, null, targetClassLoader)) {
            // noinspection unchecked
            providers.add(new FieldBasedCapabilityProvider(Capability.class, currentType, null, null, PUBLIC));
        }
        return asImmutableList(providers);
    }

    @Nonnull
    public List<CapabilityProvider> createAllReferencedBy(@Nullable ClassLoader classLoader, @Nullable Iterable<CapabilityReferenceConfiguration> configurations) {
        final List<CapabilityProvider> providers = new ArrayList<>();
        if (configurations != null) {
            for (CapabilityReferenceConfiguration configuration : configurations) {
                providers.add(new FieldBasedCapabilityProvider<>(classLoader, configuration));
            }
        }
        return asImmutableList(providers);
    }

    @Nonnull
    public List<CapabilityProvider> createAllBy(@Nullable ClassLoader classLoader, @Nullable Iterable<CapabilityProviderConfiguration> configurations) {
        final List<CapabilityProvider> providers = new ArrayList<>();
        if (configurations != null) {
            for (CapabilityProviderConfiguration configuration : configurations) {
                final String typeName = configuration.getType();
                providers.add(createInstanceBy(classLoader, typeName, CapabilityProvider.class));
            }
        }
        return asImmutableList(providers);
    }

}
