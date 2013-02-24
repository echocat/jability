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

import org.echocat.jability.property.Property;
import org.echocat.jability.property.PropertyProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

@ThreadSafe
@Immutable
public class DefaultPropertyProvider<T extends Property<?>> implements PropertyProvider {

    private final Map<String, T> _idToProperty;

    public DefaultPropertyProvider(@Nullable Iterable<T> definitions) {
        _idToProperty = toIdToProperty(definitions);
    }

    @Override
    @Nullable
    public <V> Property<V> provideBy(@Nonnull Class<V> valueType, @Nonnull String id) throws IllegalArgumentException {
        final T definition = _idToProperty.get(id);
        if (definition != null && !valueType.isAssignableFrom(definition.getValueType())) {
            throw new IllegalArgumentException("Found a definition with id '" + id + "' but it is of value type " + definition.getValueType().getName() + " not of expected " + valueType.getName() + ".");
        }
        // noinspection unchecked
        return (Property<V>) definition;
    }

    @Override
    public Iterator<Property<?>> iterator() {
        // noinspection unchecked
        return (Iterator<Property<?>>) _idToProperty.values().iterator();
    }

    @Nonnull
    protected Map<String, T> toIdToProperty(@Nullable Iterable<T> definitions) {
        final Map<String, T> result = new LinkedHashMap<>();
        if (definitions != null) {
            for (T definition : definitions) {
                result.put(definition.getId(), definition);
            }
        }
        return unmodifiableMap(result);
    }
}
