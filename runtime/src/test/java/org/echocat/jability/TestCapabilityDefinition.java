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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum TestCapabilityDefinition implements CapabilityDefinition<String> {
    capA(null),
    capB("defaultValueB");

    private final String _id;
    private final String _defaultValue;

    private TestCapabilityDefinition(@Nullable String defaultValue) {
        _id = getClass().getPackage().getName() + "." + name();
        _defaultValue = defaultValue;
    }

    @Nonnull
    @Override
    public String get() {
        return _id;
    }

    @Nonnull
    @Override
    public Class<? extends String> getValueType() {
        return String.class;
    }

    @Nullable
    @Override
    public String getDefaultValue() {
        return _defaultValue;
    }
}
