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

package org.echocat.jability.configuration.capability;

import org.echocat.jability.configuration.UnderConfiguration.SingleValueConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

public interface UnderCapabilitiesRootConfiguration {

    @ThreadSafe
    @Immutable
    public static class RespectSystemCapabilities extends SingleValueConfiguration<Boolean> implements UnderCapabilitiesRootConfiguration {

        public RespectSystemCapabilities(boolean value) {
            super(value);
        }

        @Override
        @Nonnull
        public Boolean getValue() {
            return super.getValue();
        }
    }

    @ThreadSafe
    @Immutable
    public static class RespectSystemProvider extends SingleValueConfiguration<Boolean> implements UnderCapabilitiesRootConfiguration {

        public RespectSystemProvider(boolean value) {
            super(value);
        }

        @Override
        @Nonnull
        public Boolean getValue() {
            return super.getValue();
        }
    }

}
