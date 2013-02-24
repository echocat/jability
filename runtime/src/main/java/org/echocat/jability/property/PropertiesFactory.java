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

package org.echocat.jability.property;

import org.echocat.jability.configuration.Configuration;
import org.echocat.jability.configuration.property.PropertiesConfiguration;
import org.echocat.jability.configuration.property.PropertiesRootConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.ArrayList;
import java.util.List;

import static java.util.ServiceLoader.load;
import static org.echocat.jability.support.ClassUtils.createInstanceBy;
import static org.echocat.jability.support.ClassUtils.selectClassLoader;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;

@ThreadSafe
@Immutable
public class PropertiesFactory {

    @Nonnull
    public Properties createBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        return createBy(classLoader, configuration != null ? configuration.getProperties() : null);
    }

    @Nonnull
    public Properties createBy(@Nullable ClassLoader classLoader, @Nullable PropertiesRootConfiguration configuration) {
        return new CompoundProperties(createAllBy(classLoader, configuration));
    }

    @Nonnull
    public List<Properties> createAllBy(@Nullable ClassLoader classLoader, @Nullable PropertiesRootConfiguration configuration) {
        final List<Properties> allProperties = new ArrayList<>();
        if (configuration != null) {
            allProperties.addAll(createAllBy(classLoader, configuration.getProperties()));
        }
        if (configuration == null || configuration.isRespectSystemProperties()) {
            allProperties.addAll(createAllSystemsBy(classLoader));
        }
        return asImmutableList(allProperties);
    }

    @Nonnull
    public List<Properties> createAllSystemsBy(@Nullable ClassLoader classLoader) {
        return asImmutableList(load(Properties.class, selectClassLoader(classLoader)));
    }

    @Nonnull
    public List<Properties> createAllBy(@Nullable ClassLoader classLoader, @Nullable Iterable<PropertiesConfiguration> configurations) {
        final List<Properties> allProperties = new ArrayList<>();
        if (configurations != null) {
            for (PropertiesConfiguration configuration : configurations) {
                final String typeName = configuration.getType();
                allProperties.add(createInstanceBy(classLoader, typeName, Properties.class));
            }
        }
        return asImmutableList(allProperties);
    }

}
