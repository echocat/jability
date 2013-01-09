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
import org.echocat.jability.property.PropertyCollection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class CapabilitySupport<V> implements Capability<V> {

    private final CapabilityDefinition<V> _definition;
    private final PropertyCollection _properties;

    protected CapabilitySupport(@Nonnull CapabilityDefinition<V> definition, @Nonnull PropertyCollection properties) {
        _definition = definition;
        _properties = properties;
    }

    @Nonnull
    @Override
    public CapabilityDefinition<V> getDefinition() {
        return _definition;
    }

    @Override
    public void set(@Nullable V value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Set of value of capability '" + _definition.getId() + "' is not supported.");
    }

    @Override
    public boolean isModifiable() {
        return false;
    }

    @Override
    @Nonnull
    public PropertyCollection getProperties() {
        return _properties;
    }

    @Override
    public boolean equals(Object o) {
        final boolean result;
        if (this == o) {
            result = true;
        } else if (!(o instanceof Capability)) {
            result = false;
        } else {
            final Capability<?> that = (Capability) o;
            result = getDefinition().equals(that.getDefinition());
        }
        return result;
    }

    @Override
    public int hashCode() {
        return getDefinition().hashCode();
    }

    @Override
    public String toString() {
        final CapabilityDefinition<V> definition = getDefinition();
        return definition.getId() + "(" + definition.getValueType().getName() +  ")=" + get();
    }
}
