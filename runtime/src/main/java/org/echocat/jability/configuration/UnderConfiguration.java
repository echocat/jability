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

package org.echocat.jability.configuration;

import javax.annotation.Nullable;

public interface UnderConfiguration {

    public static class SingleValueConfiguration<T> {

        private final T _value;

        public SingleValueConfiguration(@Nullable T value) {
            _value = value;
        }

        @Nullable
        public T getValue() {
            return _value;
        }

    }

}
