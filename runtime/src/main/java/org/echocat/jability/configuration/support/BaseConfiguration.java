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

package org.echocat.jability.configuration.support;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;
import static org.echocat.jability.support.ClassUtils.nameOf;

public abstract class BaseConfiguration {

    @Nonnull
    protected <T> List<T> addOrCreate(@Nonnull T what, @Nullable List<T> to) {
        final List<T> target = to != null ? to : new ArrayList<T>();
        target.add(what);
        return target;
    }

    @Override
    public boolean equals(Object o) {
        final boolean result;
        if (this == o) {
            result = true;
        } else if (o == null || getClass() != o.getClass()) {
            result = false;
        } else {
            result = reflectionEquals(this, o, false);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this, false);
    }

    @Override
    public String toString() {
        return nameOf(this) + "(" + reflectionToString(this, SHORT_PREFIX_STYLE) + ")";
    }

}
