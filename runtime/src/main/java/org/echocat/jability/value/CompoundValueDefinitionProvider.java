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

public abstract class CompoundValueDefinitionProvider<D extends ValueDefinition<?>, P extends ValueDefinitionProvider<D>> implements ValueDefinitionProvider<D> {

    private final Iterable<P> _delegates;

    protected CompoundValueDefinitionProvider(@Nullable Iterable<P> delegates) {
        _delegates = delegates != null ? delegates : Collections.<P>emptyList();
    }

    protected CompoundValueDefinitionProvider(@Nullable P... delegates) {
        this(delegates != null ? asList(delegates) : null);
    }

    @Nullable
    @Override
    public <V> ValueDefinition<V> provideBy(@Nonnull Class<V> valueType, @Nonnull String id) {
        ValueDefinition<V> result = null;
        for (P delegate : _delegates) {
            result = delegate.provideBy(valueType, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<D> iterator() {
        return new ChainedIterator<P, D>(_delegates.iterator()) { @Nullable @Override protected Iterator<D> nextIterator(@Nullable P input) {
            return input.iterator();
        }};
    }

}
