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
import org.echocat.jability.configuration.capability.CapabilitiesConfiguration;
import org.echocat.jability.configuration.capability.CapabilitiesRootConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.ArrayList;
import java.util.List;

import static java.util.ServiceLoader.load;
import static org.echocat.jability.support.DiscoverUtils.createInstanceBy;
import static org.echocat.jability.support.DiscoverUtils.selectClassLoader;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;

@ThreadSafe
@Immutable
public class CapabilitiesFactory {

    @Nonnull
    public Capabilities createBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        return createBy(classLoader, configuration != null ? configuration.getCapabilities() : null);
    }

    @Nonnull
    public Capabilities createBy(@Nullable ClassLoader classLoader, @Nullable CapabilitiesRootConfiguration configuration) {
        return new CompoundCapabilities(createAllBy(classLoader, configuration));
    }

    @Nonnull
    public List<Capabilities> createAllBy(@Nullable ClassLoader classLoader, @Nullable CapabilitiesRootConfiguration configuration) {
        final List<Capabilities> allCapabilities = new ArrayList<>();
        if (configuration != null) {
            allCapabilities.addAll(createAllBy(classLoader, configuration.getCapabilities()));
        }
        if (configuration == null || configuration.isRespectSystemCapabilities()) {
            allCapabilities.addAll(createAllSystemsBy(classLoader));
        }
        return asImmutableList(allCapabilities);
    }

    @Nonnull
    public List<Capabilities> createAllSystemsBy(@Nullable ClassLoader classLoader) {
        return asImmutableList(load(Capabilities.class, selectClassLoader(classLoader)));
    }

    @Nonnull
    public List<Capabilities> createAllBy(@Nullable ClassLoader classLoader, @Nullable Iterable<CapabilitiesConfiguration> configurations) {
        final List<Capabilities> allCapabilities = new ArrayList<>();
        if (configurations != null) {
            for (CapabilitiesConfiguration configuration : configurations) {
                final String typeName = configuration.getType();
                allCapabilities.add(createInstanceBy(classLoader, typeName, Capabilities.class));
            }
        }
        return asImmutableList(allCapabilities);
    }

}
