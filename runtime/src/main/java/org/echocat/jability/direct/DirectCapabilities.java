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

package org.echocat.jability.direct;

import org.echocat.jability.Capability;
import org.echocat.jomon.runtime.util.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

import static org.echocat.jability.direct.DirectJability.capabilities;

public class DirectCapabilities {

    @Nullable
    public static <V> V get(@Nonnull Capability<V> capability, @Nullable V defaultValue) {
        return capabilities().get(capability, defaultValue);
    }

    public static <V> void set(@Nonnull Capability<V> capability, @Nullable V value) throws UnsupportedOperationException {
        capabilities().set(capability, value);
    }

    public static Iterator<Entry<Capability<?>, Object>> iterator() {
        return capabilities().iterator();
    }

    public static boolean isModifiable(@Nonnull Capability<?> capability) {
        return capabilities().isModifiable(capability);
    }

    public static boolean isEnabled(@Nonnull Capability<Boolean> capability, @Nullable Boolean defaultValue) {
        return capabilities().isEnabled(capability, defaultValue);
    }

    @Nullable
    public static <V> V get(@Nonnull Capability<V> capability) {
        return capabilities().get(capability);
    }

    public static boolean isEnabled(@Nonnull Capability<Boolean> capability) {
        return capabilities().isEnabled(capability);
    }

    public static void remove(@Nonnull Capability<?> capability) throws UnsupportedOperationException {
        capabilities().remove(capability);
    }

    private DirectCapabilities() {}
    protected static final DirectCapabilities INSTANCE = new DirectCapabilities();

}
