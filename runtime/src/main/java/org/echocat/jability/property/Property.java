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
import org.echocat.jability.value.Value;
import org.echocat.jomon.runtime.annotations.Excluding;
import org.echocat.jomon.runtime.annotations.Including;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Date;

import static org.echocat.jability.property.support.PropertyUtils.newProperty;
import static org.echocat.jability.value.support.DefinitionIdUtils.buildCapabilityDefinitionIdFrom;

public interface Property<V> extends Value<V> {

    @Including
    public static final Property<Date> validFrom = newProperty(Date.class, Property.class, "validFrom");
    @Excluding
    public static final Property<Date> validTo = newProperty(Date.class, Property.class, "validTo");
    @Including
    public static final Property<Stage> minimumRequiredStage = newProperty(Stage.class, Property.class, "minimumRequiredStage");

    @ThreadSafe
    @Immutable
    public static class Impl<V> extends Value.Impl<V> implements Property<V> {

        public Impl(@Nonnull Class<? extends V> valueType, @Nonnull String id, @Nullable V defaultValue) {
            super(valueType, id, defaultValue);
        }

        public Impl(@Nonnull Class<? extends V> valueType, @Nonnull Class<?> basedOn, @Nonnull String subId, @Nullable V defaultValue) {
            this(valueType, buildCapabilityDefinitionIdFrom(basedOn, subId), defaultValue);
        }

        @Override
        public boolean equals(Object o) {
            final boolean result;
            if (this == o) {
                result = true;
            } else if (!(o instanceof Property)) {
                result = false;
            } else {
                final Property<?> that = (Property) o;
                result = getId().equals(that.getId());
            }
            return result;
        }

    }

}
