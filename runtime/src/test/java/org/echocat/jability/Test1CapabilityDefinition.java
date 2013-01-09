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

public enum Test1CapabilityDefinition implements CapabilityDefinition<String> {
    capA(null, true),
    capB("defaultValueB", false),
    capC(null, false),
    ;

    private final String _id;
    private final String _defaultValue;
    private final boolean _nullable;

    private Test1CapabilityDefinition(@Nullable String defaultValue, boolean nullable) {
        _id = getClass().getPackage().getName() + "." + name();
        _defaultValue = defaultValue;
        _nullable = nullable;
    }

    @Nonnull
    @Override
    public String getId() {
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

    @Override
    public boolean isNullable() {
        return _nullable;
    }

}
