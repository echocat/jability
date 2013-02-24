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

package org.echocat.jability.direct;

import org.echocat.jability.Capability;
import org.echocat.jability.property.Property;
import org.echocat.jomon.runtime.util.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

import static org.echocat.jability.direct.DirectJability.properties;

public class DirectProperties {

    @Nullable
    public static <V> V get(@Nonnull Capability<?> capability, @Nonnull Property<V> property, @Nullable V defaultValue) {
        return properties().get(capability, property, defaultValue);
    }

    public static boolean isEnabled(@Nonnull Capability<?> capability, @Nonnull Property<Boolean> property) {
        return properties().isEnabled(capability, property);
    }

    public static <V> void set(@Nonnull Capability<?> capability, @Nonnull Property<V> property, @Nullable V value) throws UnsupportedOperationException {
        properties().set(capability, property, value);
    }

    public static boolean isEnabled(@Nonnull Capability<?> capability, @Nonnull Property<Boolean> property, @Nullable Boolean defaultValue) {
        return properties().isEnabled(capability, property, defaultValue);
    }

    @Nonnull
    public static Iterator<Entry<Property<?>, Object>> iterator(@Nonnull Capability<?> capability) {
        return properties().iterator(capability);
    }

    @Nullable
    public static <V> V get(@Nonnull Capability<?> capability, @Nonnull Property<V> property) {
        return properties().get(capability, property);
    }

    public static boolean isModifiable(@Nonnull Capability<?> capability, @Nonnull Property<?> property) {
        return properties().isModifiable(capability, property);
    }

    public static void remove(@Nonnull Capability<?> capability, @Nonnull Property<?> property) throws UnsupportedOperationException {
        properties().remove(capability, property);
    }

    private DirectProperties() {}
    protected static final DirectProperties INSTANCE = new DirectProperties();

}
