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

import org.echocat.jability.Capabilities;
import org.echocat.jability.Capability;
import org.echocat.jomon.runtime.util.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Iterator;

import static org.echocat.jomon.runtime.CollectionUtils.emptyIterator;

@ThreadSafe
@Immutable
public class NoopCapabilities implements Capabilities {

    @Nullable
    @Override
    public <V> V get(@Nonnull Capability<V> capability, @Nullable V defaultValue) {
        return defaultValue;
    }

    @Nullable
    @Override
    public <V> V get(@Nonnull Capability<V> capability) {
        return get(capability, capability.getDefaultValue());
    }

    @Override
    public boolean isEnabled(@Nonnull Capability<Boolean> capability, @Nullable Boolean defaultValue) {
        return defaultValue != null && defaultValue;
    }

    @Override
    public boolean isEnabled(@Nonnull Capability<Boolean> capability) {
        return isEnabled(capability, capability.getDefaultValue());
    }

    @Override
    public <V> void set(@Nonnull Capability<V> capability, @Nullable V value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Update of " + capability + " is not supported.");
    }

    @Override
    public void remove(@Nonnull Capability<?> capability) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Remove of " + capability + " is not supported.");
    }

    @Override
    public boolean isModifiable(@Nonnull Capability<?> capability) {
        return false;
    }

    @Override
    public Iterator<Entry<Capability<?>, Object>> iterator() {
        return emptyIterator();
    }

}
