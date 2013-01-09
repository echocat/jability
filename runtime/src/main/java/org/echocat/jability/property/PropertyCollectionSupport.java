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

import org.echocat.jomon.runtime.iterators.ConvertingIterator;
import org.echocat.jomon.runtime.util.Entry;
import org.echocat.jomon.runtime.util.Entry.Impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

import static org.echocat.jability.property.Properties.propertyDefinitionComparator;

@ThreadSafe
public abstract class PropertyCollectionSupport implements PropertyCollection {

    private final Map<PropertyDefinition<?>, Object> _propertyToValue;

    protected PropertyCollectionSupport(@Nullable Map<PropertyDefinition<?>, Object> propertyToValue) {
        _propertyToValue = propertyToValue != null ? propertyToValue : new TreeMap<>(propertyDefinitionComparator());
    }

    protected PropertyCollectionSupport() {
        this(null);
    }

    @Nonnull
    protected Map<PropertyDefinition<?>, Object> getPropertyToValue() {
        return _propertyToValue;
    }

    @Override
    public <V> V get(@Nonnull PropertyDefinition<V> definition, @Nullable V defaultValue) {
        final Object foundValue = getPropertyToValue().get(definition);
        final V result;
        if (foundValue != null) {
            final Class<? extends V> valueType = definition.getValueType();
            if (!valueType.isInstance(foundValue)) {
                throw new IllegalStateException("The property " + definition.getId() + " does contain a value of type " + foundValue.getClass().getName() + " but this is not the expected type " + valueType.getName() + ".");
            }
            result = valueType.cast(foundValue);
        } else {
            result = defaultValue;
        }
        return result;
    }

    @Override
    public <V> V get(@Nonnull PropertyDefinition<V> definition) {
        return get(definition, definition.getDefaultValue());
    }

    @Override
    public boolean isEnabled(@Nonnull PropertyDefinition<Boolean> definition, @Nullable Boolean defaultValue) {
        final Boolean value = get(definition, defaultValue);
        return value != null && value;
    }

    @Override
    public boolean isEnabled(@Nonnull PropertyDefinition<Boolean> definition) {
        return isEnabled(definition, definition.getDefaultValue());
    }

    @Override
    public <V> void set(@Nonnull PropertyDefinition<V> definition, @Nullable V value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(@Nonnull PropertyDefinition<?> definition) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isModifiable() {
        return false;
    }

    @Override
    public Iterator<Entry<PropertyDefinition<?>, Object>> iterator() {
        final Map<PropertyDefinition<?>, Object> copy = new TreeMap<>(propertyDefinitionComparator());
        synchronized (this) {
            copy.putAll(getPropertyToValue());
        }
        final AtomicReference<PropertyDefinition<?>> lastDefinition = new AtomicReference<>();
        return new ConvertingIterator<Map.Entry<PropertyDefinition<?>, Object>, Entry<PropertyDefinition<?>, Object>>(copy.entrySet().iterator()) {

            @Override
            protected Entry<PropertyDefinition<?>, Object> convert(Map.Entry<PropertyDefinition<?>, Object> input) {
                lastDefinition.set(input.getKey());
                return new Impl<PropertyDefinition<?>, Object>(input.getKey(), input.getValue());
            }

            @Override
            public void remove() {
                final PropertyDefinition<?> definition = lastDefinition.get();
                if (definition != null) {
                    PropertyCollectionSupport.this.remove(definition);
                }
            }
        };
    }
}
