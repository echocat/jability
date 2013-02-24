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

package org.echocat.jability.support;

import javax.annotation.Nonnull;

public class IdUtils {

    @Nonnull
    public static String buildIdFrom(@Nonnull Class<?> basedOn, @Nonnull String subId) {
        return buildIdFrom(basedOn.getPackage(), subId);
    }

    @Nonnull
    public static String buildIdFrom(@Nonnull Package basedOn, @Nonnull String subId) {
        return buildIdFrom(basedOn.getName(), subId);
    }

    @Nonnull
    public static String buildIdFrom(@Nonnull String basedOn, @Nonnull String subId) {
        return basedOn + "." + subId;
    }

    private IdUtils() {}
    protected static final IdUtils INSTANCE = new IdUtils();

}
