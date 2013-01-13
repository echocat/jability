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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Collections;

import static org.echocat.jomon.runtime.CollectionUtils.asList;

@ThreadSafe
public abstract class CompoundValues<V extends Values> implements Values {

    private final Iterable<V> _delegates;

    protected CompoundValues(@Nullable Iterable<V> delegates) {
        _delegates = delegates != null ? delegates : Collections.<V>emptyList();
    }

    protected CompoundValues(@Nullable V... delegates) {
        this(delegates != null ? asList(delegates) : null);
    }

    @Nonnull
    protected Iterable<V> getDelegates() {
        return _delegates;
    }

}
