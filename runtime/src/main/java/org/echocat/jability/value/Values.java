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

package org.echocat.jability.value;

import javax.annotation.Nonnull;
import java.util.Comparator;

public class Values {

    private static final Comparator<ValueDefinition<?>> COMPARATOR = new Comparator<ValueDefinition<?>>() { @Override public int compare(ValueDefinition<?> d1, ValueDefinition<?> d2) {
        final int result;
        if (d1 == null && d2 == null) {
            result = 0;
        } else if (d1 != null) {
            result = 1;
        } else if (d2 != null) {
            result = -1;
        } else {
            result = d1.getId().compareTo(d2.getId());
        }
        return result;
    }};

    @Nonnull
    public static <T extends ValueDefinition<?>> Comparator<T> valueDefinitionComparator() {
        // noinspection unchecked
        return (Comparator<T>) Values.COMPARATOR;
    }

    private Values() {}
}