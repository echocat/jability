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

import org.echocat.jability.property.support.FieldBasedPropertyDefinitionProvider;
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

public class CompoundPropertyDefinitionProvider extends CompoundValueDefinitionProvider<PropertyDefinition<?>, PropertyDefinitionProvider>  implements PropertyDefinitionProvider {

    private static final CompoundPropertyDefinitionProvider INSTANCE = new CompoundPropertyDefinitionProvider();

    @Nonnull
    public static CompoundPropertyDefinitionProvider propertyDefinitionProvider() {
        return INSTANCE;
    }

    public CompoundPropertyDefinitionProvider(@Nullable Iterable<PropertyDefinitionProvider> delegates) {
        super(delegates);
    }

    public CompoundPropertyDefinitionProvider(@Nullable PropertyDefinitionProvider... delegates) {
        super(delegates);
    }

    public CompoundPropertyDefinitionProvider(@Nullable ClassLoader classLoader) {
        this(loadSystemProviderBy(classLoader));
    }

    public CompoundPropertyDefinitionProvider() {
        this((ClassLoader) null);
    }

    @Nullable
    @Override
    public <V> PropertyDefinition<V> provideBy(@Nonnull Class<V> valueType, @Nonnull String id) {
        return (PropertyDefinition<V>) super.provideBy(valueType, id);
    }

    @Nonnull
    public static Iterable<PropertyDefinitionProvider> loadSystemProviderBy(@Nullable ClassLoader classLoader) {
        final ClassLoader targetClassLoader = classLoader != null ? classLoader : currentThread().getContextClassLoader();
        final List<PropertyDefinitionProvider> providers = new ArrayList<>();
        addAll(providers, load(PropertyDefinitionProvider.class, targetClassLoader));
        // noinspection rawtypes
        for (Class<?> currentType : discoverTypesOf(PropertyDefinition.class, null, targetClassLoader)) {
            providers.add(new FieldBasedPropertyDefinitionProvider<>(PropertyDefinition.class, currentType, PUBLIC));
        }
        return asImmutableList(providers);
    }

}
