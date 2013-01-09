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
import javax.annotation.Nullable;
import java.lang.reflect.Field;

import static java.lang.reflect.Modifier.*;

public enum AccessType {
    PUBLIC { @Override public boolean matches(int modifiers) {
        return isPublic(modifiers);
    }},
    PROTECTED { @Override public boolean matches(int modifiers) {
        return isProtected(modifiers);
    }},
    PACKAGE_LOCAL { @Override public boolean matches(int modifiers) {
        return !isProtected(modifiers) && !isPrivate(modifiers) && !isPublic(modifiers);
    }},
    PRIVATE { @Override public boolean matches(int modifiers) {
        return isPrivate(modifiers);
    }};

    public static final AccessType[] DEFAULT_ACCESS_TYPES = new AccessType[]{AccessType.PUBLIC};

    public abstract boolean matches(int modifiers);

    public boolean matches(@Nonnull Field field) {
        return matches(field.getModifiers());
    }

    public static boolean matches(@Nonnull Field field, @Nullable AccessType... accessTypes) {
        final AccessType[] targetAccessTypes = accessTypes != null && accessTypes.length > 0 ? accessTypes : DEFAULT_ACCESS_TYPES;
        boolean result = false;
        for (AccessType accessType : targetAccessTypes) {
            if (accessType.matches(field)) {
                result = true;
                break;
            }
        }
        return result;
    }

}
