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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;

import static org.echocat.jability.value.ValueUtils.valueDefinitionComparator;

public class PropertyUtils {

    @Nonnull
    public static <V> Property<V> newPropertyDefinition(@Nonnull Class<? extends V> valueType, @Nonnull String id, @Nullable V defaultValue) {
        return new Property.Impl<>(valueType, id, defaultValue);
    }

    @Nonnull
    public static <V> Property<V> newPropertyDefinition(@Nonnull Class<? extends V> valueType, @Nonnull String id) {
        return newPropertyDefinition(valueType, id, null);
    }

    @Nonnull
    public static <V> Property<V> newPropertyDefinition(@Nonnull Class<? extends V> valueType, @Nonnull Class<?> basedOn, @Nonnull String subId, @Nullable V defaultValue) {
        return new Property.Impl<>(valueType, basedOn, subId, defaultValue);
    }

    @Nonnull
    public static <V> Property<V> newPropertyDefinition(@Nonnull Class<? extends V> valueType, @Nonnull Class<?> basedOn, @Nonnull String subId) {
        return newPropertyDefinition(valueType, basedOn, subId, null);
    }

    @Nonnull
    public static Comparator<Property<?>> propertyDefinitionComparator() {
        return valueDefinitionComparator();
    }

    private PropertyUtils() {}
}
