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
import org.echocat.jability.CapabilityDefinition;
import org.echocat.jability.CapabilityProvider;
import org.echocat.jability.MutableCapability;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import static org.echocat.jability.Capabilities.capabilityDefinitionComparator;

public class FallbackCapabilityProvider implements CapabilityProvider {

    private final Map<CapabilityDefinition<?>, Capability<?>> _definitionToCapability = new TreeMap<>(capabilityDefinitionComparator());

    @Override
    public <V> Capability<V> provideBy(@Nonnull CapabilityDefinition<V> definition) {
        synchronized (this) {
            Capability<?> capability = _definitionToCapability.get(definition);
            if (capability != null) {
                final Class<? extends V> expectedValueType = definition.getValueType();
                final Class<?> foundValueType = capability.getDefinition().getValueType();
                if (expectedValueType.isAssignableFrom(foundValueType)) {
                    throw new IllegalStateException("There was two different definitions for the same id of '" + definition.getId() + "'." +
                        " Request was with type " + expectedValueType.getName() + " but already stored was a definition with type " + foundValueType.getName() + ".");
                }
            } else {
                capability = new MutableCapability<>(definition);
                _definitionToCapability.put(definition, capability);
            }
            // noinspection unchecked
            return (Capability<V>) capability;
        }
    }

    @Override
    public Iterator<Capability<?>> iterator() {
        final Iterable<Capability<?>> values;
        synchronized (this) {
            values = new ArrayList<>(_definitionToCapability.values());
        }
        final Iterator<Capability<?>> delegate = values.iterator();
        return new Iterator<Capability<?>>() {
            @Override public boolean hasNext() { return delegate.hasNext(); }
            @Override public Capability<?> next() { return delegate.next(); }
            @Override public void remove() { throw new UnsupportedOperationException(); }
        };
    }

    public void clear() {
        synchronized (this) {
            _definitionToCapability.clear();
        }
    }

}
