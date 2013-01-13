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

package org.echocat.jability.stage;

import org.echocat.jomon.runtime.iterators.ChainedIterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import static org.echocat.jability.stage.Stages.unknown;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableSet;

public class CompoundStageProvider implements StageProvider {

    private final Iterable<StageProvider> _delegates;

    private final Set<String> _availableIds;
    private final Stage _current;

    public CompoundStageProvider(@Nullable Iterable<StageProvider> delegates, @Nullable Iterable<String> availableIds, @Nullable Stage current) {
        _delegates = delegates != null ? delegates : Collections.<StageProvider>emptyList();
        _availableIds = availableIds != null ? asImmutableSet(availableIds) : null;
        _current = current;
    }

    public CompoundStageProvider(@Nullable Iterable<StageProvider> delegates, @Nullable Iterable<String> availableIds, @Nullable String currentId) {
        _delegates = delegates != null ? delegates : Collections.<StageProvider>emptyList();
        _availableIds = availableIds != null ? asImmutableSet(availableIds) : null;
        if (currentId != null) {
            _current = provideBy(currentId);
            if (_current == null) {
                throw new IllegalArgumentException("Current stage '" + currentId + "' could not be resolved.");
            }
        } else {
            _current = null;
        }
    }

    @Nullable
    @Override
    public Stage provideBy(@Nonnull String id) {
        Stage result = unknown.getId().equals(id) ? unknown : null;
        if (result == null && (_availableIds == null || _availableIds.contains(id))) {
            for (StageProvider delegate : _delegates) {
                result = delegate.provideBy(id);
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }

    @Nullable
    @Override
    public Stage provideCurrent() {
        Stage result = _current;
        if (result != null) {
            for (StageProvider delegate : _delegates) {
                result = delegate.provideCurrent();
                if (result != null) {
                    break;
                }
            }
        }
        return result != null ? result : unknown;
    }

    @Override
    public Iterator<Stage> iterator() {
        return new ChainedIterator<StageProvider, Stage>(_delegates.iterator()) { @Nullable @Override protected Iterator<Stage> nextIterator(@Nullable StageProvider input) {
            return input.iterator();
        }};
    }

}
