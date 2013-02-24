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

import org.echocat.jability.value.CompoundValues;
import org.echocat.jomon.runtime.iterators.ChainedIterator;
import org.echocat.jomon.runtime.util.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Iterator;

@ThreadSafe
public class CompoundCapabilities extends CompoundValues<Capabilities> implements Capabilities {

    public CompoundCapabilities(@Nullable Iterable<Capabilities> delegates) {
        super(delegates);
    }

    public CompoundCapabilities(@Nullable Capabilities... delegates) {
        super(delegates);
    }

    @Nullable
    @Override
    public <V> V get(@Nonnull Capability<V> capability, @Nullable V defaultValue) {
        V result = null;
        for (Capabilities capabilities : getDelegates()) {
            result = capabilities.get(capability, null);
            if (result != null) {
                break;
            }
        }
        return result != null ? result : defaultValue;
    }

    @Nullable
    @Override
    public <V> V get(@Nonnull Capability<V> capability) {
        return get(capability, capability.getDefaultValue());
    }

    @Override
    public boolean isEnabled(@Nonnull Capability<Boolean> capability, @Nullable Boolean defaultValue) {
        final Boolean result = get(capability, defaultValue);
        return result != null && result;
    }

    @Override
    public boolean isEnabled(@Nonnull Capability<Boolean> capability) {
        return isEnabled(capability, capability.getDefaultValue());
    }

    @Override
    public <V> void set(@Nonnull Capability<V> capability, @Nullable V value) throws UnsupportedOperationException {
        boolean atLeastOneSupportsUpdate = false;
        for (Capabilities capabilities : getDelegates()) {
            if (capabilities.isModifiable(capability)) {
                atLeastOneSupportsUpdate = true;
                capabilities.set(capability, value);
            }
        }
        if (!atLeastOneSupportsUpdate) {
            throw new UnsupportedOperationException("Update of " + capability + " is not supported.");
        }
    }

    @Override
    public void remove(@Nonnull Capability<?> capability) throws UnsupportedOperationException {
        boolean atLeastOneSupportsRemove = false;
        for (Capabilities capabilities : getDelegates()) {
            if (capabilities.isModifiable(capability)) {
                atLeastOneSupportsRemove = true;
                capabilities.remove(capability);
            }
        }
        if (!atLeastOneSupportsRemove) {
            throw new UnsupportedOperationException("Remove of " + capability + " is not supported.");
        }
    }

    @Override
    public boolean isModifiable(@Nonnull Capability<?> capability) {
        boolean atLeastOneSupportsModification = false;
        for (Capabilities capabilities : getDelegates()) {
            if (capabilities.isModifiable(capability)) {
                atLeastOneSupportsModification = true;
                break;
            }
        }
        return atLeastOneSupportsModification;
    }

    @Override
    public Iterator<Entry<Capability<?>, Object>> iterator() {
        return new ChainedIterator<Capabilities, Entry<Capability<?>, Object>>(getDelegates()) {
            @Nullable
            @Override
            protected Iterator<Entry<Capability<?>, Object>> nextIterator(@Nullable Capabilities input) {
                return input.iterator();
            }
        };
    }

}
