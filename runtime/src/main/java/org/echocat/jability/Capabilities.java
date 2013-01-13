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

import org.echocat.jability.value.Values;
import org.echocat.jomon.runtime.util.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Capabilities extends Values, Iterable<Entry<Capability<?>, Object>> {

    @Nullable
    public <V> V get(@Nonnull Capability<V> capability, @Nullable V defaultValue);

    @Nullable
    public <V> V get(@Nonnull Capability<V> capability);

    public boolean isEnabled(@Nonnull Capability<Boolean> capability, @Nullable Boolean defaultValue);

    public boolean isEnabled(@Nonnull Capability<Boolean> capability);

    public <V> void set(@Nonnull Capability<V> capability, @Nullable V value) throws UnsupportedOperationException;

    public void remove(@Nonnull Capability<?> capability) throws UnsupportedOperationException;

    public boolean isModifiable(@Nonnull Capability<?> capability);

}
