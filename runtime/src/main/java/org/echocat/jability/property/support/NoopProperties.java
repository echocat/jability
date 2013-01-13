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

import org.echocat.jability.Capability;
import org.echocat.jability.property.Properties;
import org.echocat.jability.property.Property;
import org.echocat.jomon.runtime.util.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

import static org.echocat.jomon.runtime.CollectionUtils.emptyIterator;

public class NoopProperties implements Properties {

    @Nullable
    @Override
    public <V> V get(@Nonnull Capability<?> capability, @Nonnull Property<V> property, @Nullable V defaultValue) {
        return defaultValue;
    }

    @Nullable
    @Override
    public <V> V get(@Nonnull Capability<?> capability, @Nonnull Property<V> property) {
        return get(capability, property, property.getDefaultValue());
    }

    @Override
    public boolean isEnabled(@Nonnull Capability<?> capability, @Nonnull Property<Boolean> property, @Nullable Boolean defaultValue) {
        return defaultValue != null && defaultValue;
    }

    @Override
    public boolean isEnabled(@Nonnull Capability<?> capability, @Nonnull Property<Boolean> property) {
        return isEnabled(capability, property, property.getDefaultValue());
    }

    @Override
    public <V> void set(@Nonnull Capability<?> capability, @Nonnull Property<V> property, @Nullable V value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Update of " + capability + ":" + property + " is not supported.");
    }

    @Override
    public void remove(@Nonnull Capability<?> capability, @Nonnull Property<?> property) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Remove of " + capability + ":" + property + " is not supported.");
    }

    @Override
    public boolean isModifiable(@Nonnull Capability<?> capability, @Nonnull Property<?> property) {
        return false;
    }

    @Nonnull
    @Override
    public Iterator<Entry<Property<?>, Object>> iterator(@Nonnull Capability<?> capability) {
        return emptyIterator();
    }

}
