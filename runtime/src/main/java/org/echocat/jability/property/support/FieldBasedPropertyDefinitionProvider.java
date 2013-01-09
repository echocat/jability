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

package org.echocat.jability.property.support;

import org.echocat.jability.property.PropertyDefinition;
import org.echocat.jability.property.PropertyDefinitionProvider;
import org.echocat.jability.support.AccessType;
import org.echocat.jability.support.DiscoverUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class FieldBasedPropertyDefinitionProvider<T extends PropertyDefinition<?>> implements PropertyDefinitionProvider {

    private final Map<String, T> _idToDefinition;

    public FieldBasedPropertyDefinitionProvider(@Nonnull Class<? extends T> definitionType, @Nonnull Class<?> startFrom, @Nullable Class<?> stopAt, @Nullable AccessType... accessTypes) {
        this(DiscoverUtils.discoverStaticFieldValuesBy(definitionType, startFrom, stopAt, accessTypes));
    }

    public FieldBasedPropertyDefinitionProvider(@Nonnull Class<? extends T> definitionType, @Nonnull Class<?> startFrom, @Nullable AccessType... accessTypes) {
        this(DiscoverUtils.discoverStaticFieldValuesBy(definitionType, startFrom, accessTypes));
    }

    public FieldBasedPropertyDefinitionProvider(@Nonnull Class<? extends T> definitionType, @Nullable AccessType... accessTypes) {
        this(DiscoverUtils.discoverStaticFieldValuesBy(definitionType, accessTypes));
    }

    protected FieldBasedPropertyDefinitionProvider(@Nullable Iterable<T> definitions) {
        _idToDefinition = toIdToStage(definitions);
    }

    @Override
    @Nullable
    public <V> PropertyDefinition<V> provideBy(@Nonnull Class<V> valueType, @Nonnull String id) throws IllegalArgumentException {
        final T definition = _idToDefinition.get(id);
        if (definition != null && !valueType.isAssignableFrom(definition.getValueType())) {
            throw new IllegalArgumentException("Found a definition with id '" + id + "' but it is of value type " + definition.getValueType().getName() + " not of expected " + valueType.getName() + ".");
        }
        // noinspection unchecked
        return (PropertyDefinition<V>) definition;
    }

    @Override
    public Iterator<PropertyDefinition<?>> iterator() {
        // noinspection unchecked
        return (Iterator<PropertyDefinition<?>>) _idToDefinition.values().iterator();
    }

    @Nonnull
    protected Map<String, T> toIdToStage(@Nullable Iterable<T> definitions) {
        final Map<String, T> result = new LinkedHashMap<>();
        if (definitions != null) {
            for (T definition : definitions) {
                result.put(definition.getId(), definition);
            }
        }
        return unmodifiableMap(result);
    }

}
