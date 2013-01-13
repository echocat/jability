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

package org.echocat.jability.stage;

import org.echocat.jability.configuration.stage.StageConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

public interface Stage {

    @Nonnull
    public String getId();

    public int getPriority();

    @ThreadSafe
    @Immutable
    public static class Impl implements Stage {

        private final String _id;
        private final int _priority;

        public Impl(@Nonnull String id, int priority) {
            _id = id;
            _priority = priority;
        }

        public Impl(@Nonnull StageConfiguration configuration) {
            _id = configuration.getId();
            _priority = configuration.getPriority();
        }

        @Override
        @Nonnull
        public String getId() {
            return _id;
        }

        @Override
        public int getPriority() {
            return _priority;
        }

        @Override
        public boolean equals(Object o) {
            final boolean result;
            if (this == o) {
                result = true;
            } else if (!(o instanceof Stage)) {
                result = false;
            } else {
                final Stage that = (Stage) o;
                result = getId().equals(that.getId());
            }
            return result;
        }

        @Override
        public int hashCode() {
            return _id.hashCode();
        }

        @Override
        public String toString() {
            return getId();
        }

    }


}
