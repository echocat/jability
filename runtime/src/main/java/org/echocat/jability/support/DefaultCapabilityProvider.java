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

package org.echocat.jability.support;

import org.echocat.jability.Capability;
import org.echocat.jability.CapabilityProvider;

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
public class DefaultCapabilityProvider<T extends Capability<?>> implements CapabilityProvider {

    private final Map<String, T> _idToCapability;

    public DefaultCapabilityProvider(@Nullable Iterable<T> definitions) {
        _idToCapability = toIdToStage(definitions);
    }

    @Override
    @Nullable
    public <V> Capability<V> provideBy(@Nonnull Class<V> valueType, @Nonnull String id) throws IllegalArgumentException {
        final T definition = _idToCapability.get(id);
        if (definition != null && !valueType.isAssignableFrom(definition.getValueType())) {
            throw new IllegalArgumentException("Found a definition with id '" + id + "' but it is of value type " + definition.getValueType().getName() + " not of expected " + valueType.getName() + ".");
        }
        // noinspection unchecked
        return (Capability<V>) definition;
    }

    @Override
    public Iterator<Capability<?>> iterator() {
        // noinspection unchecked
        return (Iterator<Capability<?>>) _idToCapability.values().iterator();
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
