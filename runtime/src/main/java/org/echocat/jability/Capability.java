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

import org.echocat.jability.property.PropertyCollection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Capability<V> {

    @Nonnull
    public CapabilityDefinition<V> getDefinition();

    @Nullable
    public V get();

    public void set(@Nullable V value) throws UnsupportedOperationException;

    public boolean isModifiable();

    @Nonnull
    public PropertyCollection getProperties();
}
