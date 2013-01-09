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

import org.echocat.jability.stage.Stage;
import org.echocat.jomon.runtime.annotations.Excluding;
import org.echocat.jomon.runtime.annotations.Including;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Date;

import static org.echocat.jability.value.Values.valueDefinitionComparator;

@SuppressWarnings("ConstantNamingConvention")
public class Properties {

    @Including
    public static final PropertyDefinition<Date> validFrom = newPropertyDefinition(Date.class, PropertyDefinition.class, "validFrom");
    @Excluding
    public static final PropertyDefinition<Date> validTo = newPropertyDefinition(Date.class, PropertyDefinition.class, "validTo");
    @Including
    public static final PropertyDefinition<Stage> minimumRequiredStage = newPropertyDefinition(Stage.class, PropertyDefinition.class, "minimumRequiredStage");

    @Nonnull
    public static <V> PropertyDefinition<V> newPropertyDefinition(@Nonnull Class<? extends V> valueType, @Nonnull String id, @Nullable V defaultValue) {
        return new PropertyDefinition.Impl<>(valueType, id, defaultValue);
    }

    @Nonnull
    public static <V> PropertyDefinition<V> newPropertyDefinition(@Nonnull Class<? extends V> valueType, @Nonnull String id) {
        return newPropertyDefinition(valueType, id, null);
    }

    @Nonnull
    public static <V> PropertyDefinition<V> newPropertyDefinition(@Nonnull Class<? extends V> valueType, @Nonnull Class<?> basedOn, @Nonnull String subId, @Nullable V defaultValue) {
        return new PropertyDefinition.Impl<>(valueType, basedOn, subId, defaultValue);
    }

    @Nonnull
    public static <V> PropertyDefinition<V> newPropertyDefinition(@Nonnull Class<? extends V> valueType, @Nonnull Class<?> basedOn, @Nonnull String subId) {
        return newPropertyDefinition(valueType, basedOn, subId, null);
    }

    @Nonnull
    public static Comparator<PropertyDefinition<?>> propertyDefinitionComparator() {
        return valueDefinitionComparator();
    }

    private Properties() {}
}
