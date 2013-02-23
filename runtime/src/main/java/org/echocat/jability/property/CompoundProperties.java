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

import com.google.common.base.Predicate;
import org.echocat.jability.Capability;
import org.echocat.jability.value.CompoundValues;
import org.echocat.jomon.runtime.iterators.ChainedIterator;
import org.echocat.jomon.runtime.util.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.google.common.collect.Iterators.filter;

public class CompoundProperties extends CompoundValues<Properties> implements Properties {

    public CompoundProperties(@Nullable Iterable<Properties> delegates) {
        super(delegates);
    }

    public CompoundProperties(@Nullable Properties... delegates) {
        super(delegates);
    }

    @Nullable
    @Override
    public <V> V get(@Nonnull Capability<?> capability, @Nonnull Property<V> property, @Nullable V defaultValue) {
        V result = null;
        for (Properties properties : getDelegates()) {
            result = properties.get(capability, property, null);
            if (result != null) {
                break;
            }
        }
        return result != null ? result : defaultValue;
    }

    @Nullable
    @Override
    public <V> V get(@Nonnull Capability<?> capability, @Nonnull Property<V> property) {
        return get(capability, property, property.getDefaultValue());
    }

    @Override
    public boolean isEnabled(@Nonnull Capability<?> capability, @Nonnull Property<Boolean> property, @Nullable Boolean defaultValue) {
        final Boolean result = get(capability, property, defaultValue);
        return result != null && result;
    }

    @Override
    public boolean isEnabled(@Nonnull Capability<?> capability, @Nonnull Property<Boolean> property) {
        return isEnabled(capability, property, property.getDefaultValue());
    }

    @Override
    public <V> void set(@Nonnull Capability<?> capability, @Nonnull Property<V> property, @Nullable V value) throws UnsupportedOperationException {
        boolean atLeastOneSupportsUpdate = false;
        for (Properties properties : getDelegates()) {
            if (properties.isModifiable(capability, property)) {
                atLeastOneSupportsUpdate = true;
                properties.set(capability, property, value);
            }
        }
        if (!atLeastOneSupportsUpdate) {
            throw new UnsupportedOperationException("Update of " + capability + ":" + property + " is not supported.");
        }
    }

    @Override
    public void remove(@Nonnull Capability<?> capability, @Nonnull Property<?> property) throws UnsupportedOperationException {
        boolean atLeastOneSupportsRemove = false;
        for (Properties properties : getDelegates()) {
            if (properties.isModifiable(capability, property)) {
                atLeastOneSupportsRemove = true;
                properties.remove(capability, property);
            }
        }
        if (!atLeastOneSupportsRemove) {
            throw new UnsupportedOperationException("Remove of " + capability + ":" + property + " is not supported.");
        }
    }

    @Override
    public boolean isModifiable(@Nonnull Capability<?> capability, @Nonnull Property<?> property) {
        boolean atLeastOneSupportsModification = false;
        for (Properties properties : getDelegates()) {
            if (properties.isModifiable(capability, property)) {
                atLeastOneSupportsModification = true;
                break;
            }
        }
        return atLeastOneSupportsModification;
    }

    @Nonnull
    @Override
    public Iterator<Entry<Property<?>, Object>> iterator(@Nonnull final Capability<?> capability) {
        final Set<Property<?>> alreadyReturnedProperties = new HashSet<>();
        return new ChainedIterator<Properties, Entry<Property<?>, Object>>(getDelegates()) {
            @Nullable
            @Override
            protected Iterator<Entry<Property<?>, Object>> nextIterator(@Nullable Properties input) {
                final Iterator<Entry<Property<?>, Object>> i = input.iterator(capability);
                return i != null ? filter(i, new Predicate<Entry<Property<?>, Object>>() {
                    @Override
                    public boolean apply(@Nullable Entry<Property<?>, Object> input) {
                        return alreadyReturnedProperties.add(input.getKey());
                    }
                }) : null;
            }
        };
    }

}
