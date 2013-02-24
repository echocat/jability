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
import org.echocat.jability.property.Property.Impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PropertyUtils {

    @Nonnull
    public static <V> Property<V> newProperty(@Nonnull Class<? extends V> valueType, @Nonnull String id, @Nullable V defaultValue) {
        return new Impl<>(valueType, id, defaultValue);
    }

    @Nonnull
    public static <V> Property<V> newProperty(@Nonnull Class<? extends V> valueType, @Nonnull String id) {
        return newProperty(valueType, id, null);
    }

    @Nonnull
    public static <V> Property<V> newProperty(@Nonnull Class<? extends V> valueType, @Nonnull Class<?> basedOn, @Nonnull String subId, @Nullable V defaultValue) {
        return new Impl<>(valueType, basedOn, subId, defaultValue);
    }

    @Nonnull
    public static <V> Property<V> newProperty(@Nonnull Class<? extends V> valueType, @Nonnull Class<?> basedOn, @Nonnull String subId) {
        return newProperty(valueType, basedOn, subId, null);
    }

    private PropertyUtils() {}
    public static final PropertyUtils INSTANCE = new PropertyUtils();

}
