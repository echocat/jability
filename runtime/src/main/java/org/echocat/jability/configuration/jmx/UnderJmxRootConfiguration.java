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

package org.echocat.jability.configuration.jmx;

import org.echocat.jability.configuration.UnderConfiguration.SingleValueConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

public interface UnderJmxRootConfiguration {

    @ThreadSafe
    @Immutable
    public static class Propagate extends SingleValueConfiguration<Boolean> implements UnderJmxRootConfiguration {

        public Propagate(boolean value) {
            super(value);
        }

        @Override
        @Nonnull
        public Boolean getValue() {
            return super.getValue();
        }
    }

}
