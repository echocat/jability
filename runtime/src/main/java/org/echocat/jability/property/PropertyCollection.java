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

package org.echocat.jability.property;

import org.echocat.jomon.runtime.util.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface PropertyCollection extends Iterable<Entry<PropertyDefinition<?>, Object>> {

    @Nullable
    public <V> V get(@Nonnull PropertyDefinition<V> definition, @Nullable V defaultValue);

    @Nullable
    public <V> V get(@Nonnull PropertyDefinition<V> definition);

    public boolean isEnabled(@Nonnull PropertyDefinition<Boolean> definition, @Nullable Boolean defaultValue);

    public boolean isEnabled(@Nonnull PropertyDefinition<Boolean> definition);

    public <V> void set(@Nonnull PropertyDefinition<V> definition, @Nullable V value) throws UnsupportedOperationException;

    public void remove(@Nonnull PropertyDefinition<?> definition) throws IllegalArgumentException, UnsupportedOperationException;

    public boolean isModifiable();

}
