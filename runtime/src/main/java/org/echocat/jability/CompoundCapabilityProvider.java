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

import org.echocat.jability.value.CompoundValueProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import static org.echocat.jomon.runtime.CollectionUtils.asList;

@ThreadSafe
@Immutable
public class CompoundCapabilityProvider extends CompoundValueProvider<Capability<?>, CapabilityProvider> implements CapabilityProvider {

    public CompoundCapabilityProvider(@Nullable Iterable<CapabilityProvider> delegates) {
        super(delegates);
    }

    public CompoundCapabilityProvider(@Nullable CapabilityProvider... delegates) {
        super(delegates != null ? asList(delegates) : null);
    }

    @Nullable
    @Override
    public <V> Capability<V> provideBy(@Nonnull Class<V> valueType, @Nonnull String id) {
        return (Capability<V>) super.provideBy(valueType, id);
    }

    @Nonnull
    @Override
    protected Iterable<CapabilityProvider> getDelegates() {
        return super.getDelegates();
    }

}
