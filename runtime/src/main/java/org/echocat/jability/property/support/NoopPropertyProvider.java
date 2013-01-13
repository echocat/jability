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

package org.echocat.jability.property.support;

import org.echocat.jability.property.Property;
import org.echocat.jability.property.PropertyProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

import static org.echocat.jomon.runtime.CollectionUtils.emptyIterator;

public class NoopPropertyProvider implements PropertyProvider {

    @Nullable
    @Override
    public <V> Property<V> provideBy(@Nonnull Class<V> valueType, @Nonnull String id) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Iterator<Property<?>> iterator() {
        return emptyIterator();
    }

}
