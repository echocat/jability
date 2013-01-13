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

package org.echocat.jability.value;

import org.echocat.jomon.runtime.iterators.ChainedIterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;

import static org.echocat.jomon.runtime.CollectionUtils.asList;

public abstract class CompoundValueProvider<V extends Value<?>, P extends ValueProvider<V>> implements ValueProvider<V> {

    private final Iterable<P> _delegates;

    protected CompoundValueProvider(@Nullable Iterable<P> delegates) {
        _delegates = delegates != null ? delegates : Collections.<P>emptyList();
    }

    protected CompoundValueProvider(@Nullable P... delegates) {
        this(delegates != null ? asList(delegates) : null);
    }

    @Nullable
    @Override
    public <V> Value<V> provideBy(@Nonnull Class<V> valueType, @Nonnull String id) {
        Value<V> result = null;
        for (P delegate : _delegates) {
            result = delegate.provideBy(valueType, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<V> iterator() {
        return new ChainedIterator<P, V>(_delegates.iterator()) { @Nullable @Override protected Iterator<V> nextIterator(@Nullable P input) {
            return input.iterator();
        }};
    }

}
