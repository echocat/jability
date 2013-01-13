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

import org.echocat.jability.value.ValueProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface CapabilityProvider extends ValueProvider<Capability<?>> {

    /**
     * @return the found definition if one exists for given <code>id</code>. It not <code>null</code> is returned.
     * @throws IllegalArgumentException if the provided <code>valueType</code> does not match the type of the found definition.
     */
    @Override
    @Nullable
    public <V> Capability<V> provideBy(@Nonnull Class<V> valueType, @Nonnull String id) throws IllegalArgumentException;

}
