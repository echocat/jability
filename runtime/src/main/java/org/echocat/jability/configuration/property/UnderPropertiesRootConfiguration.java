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

package org.echocat.jability.configuration.property;

import org.echocat.jability.configuration.UnderConfiguration.SingleValueConfiguration;

import javax.annotation.Nonnull;

public interface UnderPropertiesRootConfiguration {

    public static class RespectSystemProperties extends SingleValueConfiguration<Boolean> implements UnderPropertiesRootConfiguration {

        public RespectSystemProperties(boolean value) {
            super(value);
        }

        @Override
        @Nonnull
        public Boolean getValue() {
            return super.getValue();
        }
    }

    public static class RespectSystemProvider extends SingleValueConfiguration<Boolean> implements UnderPropertiesRootConfiguration {

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
