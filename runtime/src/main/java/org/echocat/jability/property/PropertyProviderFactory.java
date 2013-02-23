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
import org.echocat.jability.configuration.property.PropertiesRootConfiguration;
import org.echocat.jability.configuration.property.PropertyProviderConfiguration;
import org.echocat.jability.configuration.property.PropertyReferenceConfiguration;
import org.echocat.jability.property.support.DefaultPropertyProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.ServiceLoader.load;
import static org.echocat.jability.support.AccessType.PUBLIC;
import static org.echocat.jability.support.DiscoverUtils.*;
import static org.echocat.jomon.runtime.CollectionUtils.addAll;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableList;

public class PropertyProviderFactory {

    @Nonnull
    public static PropertyProvider createBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        return createBy(classLoader, configuration != null ? configuration.getProperties() : null);
    }

    @Nonnull
    public static PropertyProvider createBy(@Nullable ClassLoader classLoader, @Nullable PropertiesRootConfiguration configuration) {
        return new CompoundPropertyProvider(createAllBy(classLoader, configuration));
    }

    @Nonnull
    public static List<PropertyProvider> createAllBy(@Nullable ClassLoader classLoader, @Nullable PropertiesRootConfiguration configuration) {
        final List<PropertyProvider> providers = new ArrayList<>();
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
    public static List<PropertyProvider> createAllSystemsBy(@Nullable ClassLoader classLoader) {
        final List<PropertyProvider> providers = new ArrayList<>();
        final ClassLoader targetClassLoader = selectClassLoader(classLoader);
        addAll(providers, load(PropertyProvider.class, targetClassLoader));
        for (Class<?> currentType : discoverTypesOf(Property.class, null, targetClassLoader)) {
            providers.add(getPropertyProviderBy(currentType));
        }
        return asImmutableList(providers);
    }

    @Nonnull
    public static List<PropertyProvider> createAllReferencedBy(@Nullable ClassLoader classLoader, @Nullable Iterable<PropertyReferenceConfiguration> configurations) {
        final List<PropertyProvider> providers = new ArrayList<>();
        if (configurations != null) {
            for (PropertyReferenceConfiguration configuration : configurations) {
                providers.add(getPropertyProviderBy(classLoader, configuration));
            }
        }
        return asImmutableList(providers);
    }

    @Nonnull
    protected static Collection<Property<?>> getPropertiesBy(@Nonnull Class<?> type) {
        //noinspection rawtypes,unchecked
        return (Collection<Property<?>>) (Collection) discoverStaticFieldValuesBy(Property.class, type, null, null, PUBLIC);
    }

    @Nonnull
    protected static PropertyProvider getPropertyProviderBy(@Nonnull Class<?> type) {
        return new DefaultPropertyProvider<>(getPropertiesBy(type));
    }

    @Nonnull
    protected static Collection<Property<?>> getPropertiesBy(@Nullable ClassLoader classLoader, @Nonnull PropertyReferenceConfiguration configuration) {
        final Class<?> startFrom = loadClassBy(classLoader, configuration.getFromType());
        //noinspection rawtypes,unchecked
        return (Collection<Property<?>>) (Collection) discoverStaticFieldValuesBy(Property.class, startFrom, null, configuration.getFromField(), configuration.getAccessTypes());
    }

    @Nonnull
    protected static PropertyProvider getPropertyProviderBy(@Nullable ClassLoader classLoader, @Nonnull PropertyReferenceConfiguration configuration) {
        return new DefaultPropertyProvider<>(getPropertiesBy(classLoader, configuration));
    }

    @Nonnull
    public static List<PropertyProvider> createAllBy(@Nullable ClassLoader classLoader, @Nullable Iterable<PropertyProviderConfiguration> configurations) {
        final List<PropertyProvider> providers = new ArrayList<>();
        if (configurations != null) {
            for (PropertyProviderConfiguration configuration : configurations) {
                final String typeName = configuration.getType();
                providers.add(createInstanceBy(classLoader, typeName, PropertyProvider.class));
            }
        }
        return asImmutableList(providers);
    }

    private PropertyProviderFactory() {}

}
