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

import org.echocat.jability.Capability;
import org.echocat.jability.value.Values;
import org.echocat.jomon.runtime.util.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

public interface Properties extends Values {

    @Nullable
    public <V> V get(@Nonnull Capability<?> capability, @Nonnull Property<V> property, @Nullable V defaultValue);

    @Nullable
    public <V> V get(@Nonnull Capability<?> capability, @Nonnull Property<V> property);

    public boolean isEnabled(@Nonnull Capability<?> capability, @Nonnull Property<Boolean> property, @Nullable Boolean defaultValue);

    public boolean isEnabled(@Nonnull Capability<?> capability, @Nonnull Property<Boolean> property);

    public <V> void set(@Nonnull Capability<?> capability, @Nonnull Property<V> property, @Nullable V value) throws UnsupportedOperationException;

    public void remove(@Nonnull Capability<?> capability, @Nonnull Property<?> property) throws UnsupportedOperationException;

    public boolean isModifiable(@Nonnull Capability<?> capability, @Nonnull Property<?> property);

    @Nonnull
    public Iterator<Entry<Property<?>, Object>> iterator(@Nonnull Capability<?> capability);

}
