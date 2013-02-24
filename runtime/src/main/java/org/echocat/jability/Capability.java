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

import org.echocat.jability.support.IdUtils;
import org.echocat.jability.value.Value;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

public interface Capability<V> extends Value<V> {

    @ThreadSafe
    @Immutable
    public static class Impl<V> extends Value.Impl<V> implements Capability<V> {

        public Impl(@Nonnull Class<? extends V> valueType, @Nonnull String id, @Nullable V defaultValue) {
            super(valueType, id, defaultValue);
        }

        public Impl(@Nonnull Class<? extends V> valueType, @Nonnull Class<?> basedOn, @Nonnull String subId, @Nullable V defaultValue) {
            this(valueType, IdUtils.buildIdFrom(basedOn, subId), defaultValue);
        }

    }

}
