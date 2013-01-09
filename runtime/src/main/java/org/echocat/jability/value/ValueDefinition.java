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

package org.echocat.jability.value;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

public interface ValueDefinition<V> {

    @Nonnull
    public String getId();

    @Nonnull
    public Class<? extends V> getValueType();

    @Nullable
    public V getDefaultValue();

    @ThreadSafe
    @Immutable
    public abstract static class Impl<V> implements ValueDefinition<V> {

        private final Class<? extends V> _valueType;
        private final String _id;
        private final V _defaultValue;

        protected Impl(@Nonnull Class<? extends V> valueType, @Nonnull String id, @Nullable V defaultValue) {
            _valueType = valueType;
            _id = id;
            _defaultValue = defaultValue;
        }

        @Override
        @Nonnull
        public String getId() {
            return _id;
        }

        @Override
        @Nonnull
        public Class<? extends V> getValueType() {
            return _valueType;
        }

        @Override
        @Nullable
        public V getDefaultValue() {
            return _defaultValue;
        }

        @Override
        public boolean equals(Object o) {
            final boolean result;
            if (this == o) {
                result = true;
            } else if (!(o instanceof ValueDefinition)) {
                result = false;
            } else {
                final ValueDefinition<?> that = (ValueDefinition) o;
                result = getId().equals(that.getId());
            }
            return result;
        }

        @Override
        public int hashCode() {
            return getId().hashCode();
        }

        @Override
        public String toString() {
            return getId() + "(" + getValueType().getName() +  ")";
        }

    }

}
