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

public class ConfigurationError extends Error {

    public ConfigurationError() {}

    public ConfigurationError(String message) {
        super(message);
    }

    public ConfigurationError(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationError(Throwable cause) {
        super(cause);
    }
}
