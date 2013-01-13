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

import org.echocat.jability.value.CompoundValueProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CompoundPropertyProvider extends CompoundValueProvider<Property<?>, PropertyProvider> implements PropertyProvider {

    public CompoundPropertyProvider(@Nullable Iterable<PropertyProvider> delegates) {
        super(delegates);
    }

    public CompoundPropertyProvider(@Nullable PropertyProvider... delegates) {
        super(delegates);
    }

    @Nullable
    @Override
    public <V> Property<V> provideBy(@Nonnull Class<V> valueType, @Nonnull String id) {
        return (Property<V>) super.provideBy(valueType, id);
    }

}
