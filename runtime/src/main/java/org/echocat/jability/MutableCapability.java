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

package org.echocat.jability;

import org.echocat.jability.property.MutablePropertyCollection;
import org.echocat.jability.property.PropertyCollection;
import org.echocat.jability.property.PropertyDefinition;
import org.echocat.jability.support.CapabilitySupport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Map;

@ThreadSafe
public class MutableCapability<V> extends CapabilitySupport<V> {

    private volatile V _value;

    public MutableCapability(@Nonnull CapabilityDefinition<V> definition) {
        this(definition, null);
    }

    public MutableCapability(@Nonnull CapabilityDefinition<V> definition, @Nullable V value) {
        this(definition, value, (PropertyCollection) null);
    }

    public MutableCapability(@Nonnull CapabilityDefinition<V> definition, @Nullable V value, @Nullable Map<PropertyDefinition<?>, Object> propertyToValue) {
        this(definition, value, new MutablePropertyCollection(propertyToValue));
    }

    public MutableCapability(@Nonnull CapabilityDefinition<V> definition, @Nullable V value, @Nullable PropertyCollection propertyCollection) {
        super(definition, propertyCollection != null ? propertyCollection : new MutablePropertyCollection());
        _value = value;
    }

    @Override
    public V get() {
        return _value;
    }

    @Override
    public void set(@Nullable V value) throws UnsupportedOperationException {
        _value = value;
    }

    @Override
    public boolean isModifiable() {
        return true;
    }
}
