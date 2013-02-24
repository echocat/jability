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

import com.google.common.base.Predicate;
import org.echocat.jomon.runtime.iterators.ChainedIterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.google.common.collect.Iterators.filter;
import static org.echocat.jability.stage.Stages.unknown;
import static org.echocat.jomon.runtime.CollectionUtils.asImmutableSet;

@ThreadSafe
@Immutable
public class CompoundStageProvider implements StageProvider {

    private final Iterable<StageProvider> _delegates;
    private final Set<String> _availableIds;
    private final Stage _current;

    public CompoundStageProvider(@Nullable Iterable<StageProvider> delegates, @Nullable Iterable<String> availableIds, @Nullable Stage current) {
        _delegates = delegates != null ? delegates : Collections.<StageProvider>emptyList();
        _availableIds = availableIds != null ? asImmutableSet(availableIds) : null;
        validateCurrentId(current != null ? current.getId() : null, _availableIds);
        _current = current;
    }

    public CompoundStageProvider(@Nullable Iterable<StageProvider> delegates, @Nullable Iterable<String> availableIds, @Nullable String currentId) {
        _delegates = delegates != null ? delegates : Collections.<StageProvider>emptyList();
        _availableIds = availableIds != null ? asImmutableSet(availableIds) : null;
        validateCurrentId(currentId, _availableIds);
        if (currentId != null) {
            _current = provideBy(currentId);
            if (_current == null) {
                throw new IllegalArgumentException("Current stage '" + currentId + "' could not be resolved.");
            }
        } else {
            _current = null;
        }
    }

    protected void validateCurrentId(@Nullable String currentStageId, @Nullable Set<String> availableIds) {
        if (currentStageId != null && availableIds != null && !availableIds.contains(currentStageId)) {
            throw new IllegalArgumentException("Current stage '" + currentStageId + "' is not allowed by provided availableIds.");
        }
    }

    @Nonnull
    @Override
    public Stage provideBy(@Nonnull String id) {
        Stage result = unknown.getId().equals(id) ? unknown : null;
        if (result == null && isAvailable(id)) {
            for (StageProvider delegate : _delegates) {
                result = delegate.provideBy(id);
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }

    protected boolean isAvailable(@Nullable String id) {
        return id != null && (_availableIds == null || _availableIds.contains(id));
    }

    protected boolean isAvailable(@Nullable Stage stage) {
        return stage != null && isAvailable(stage.getId());
    }

    @Nullable
    @Override
    public Stage provideCurrent() {
        Stage result = _current;
        if (result == null) {
            for (StageProvider delegate : _delegates) {
                final Stage potential = delegate.provideCurrent();
                if (isAvailable(potential)) {
                    result = potential;
                    break;
                }
            }
        }
        return result != null ? result : unknown;
    }

    @Override
    public Iterator<Stage> iterator() {
        final Set<Stage> alreadyReturned = new HashSet<>();
        return new ChainedIterator<StageProvider, Stage>(_delegates.iterator()) { @Nullable @Override protected Iterator<Stage> nextIterator(@Nullable StageProvider input) {
            final Iterator<Stage> i = input.iterator();
            return i != null ? filter(i, new Predicate<Stage>() {
                @Override
                public boolean apply(@Nullable Stage input) {
                    return isAvailable(input) && alreadyReturned.add(input);
                }
            }) : null;
        }};
    }

    @Nonnull
    protected Iterable<StageProvider> getDelegates() {
        return _delegates;
    }

    @Nullable
    protected Set<String> getAvailableIds() {
        return _availableIds;
    }

    @Nullable
    protected Stage getCurrent() {
        return _current;
    }

}
