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

package org.echocat.jability.stage.support;

import org.echocat.jability.stage.Stage;
import org.echocat.jability.stage.StageProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class BaseStageProvider<T extends Stage> implements StageProvider {

    private final Map<String, T> _idToStage;

    public BaseStageProvider(@Nullable Iterable<T> stages) {
        _idToStage = toIdToStage(stages);
    }

    @Override
    public Stage provideBy(@Nonnull String id) {
        return _idToStage.get(id);
    }

    @Override
    public Stage provideCurrent() {
        return null;
    }

    @Override
    public Iterator<Stage> iterator() {
        // noinspection unchecked
        return (Iterator<Stage>) _idToStage.values().iterator();
    }

    @Nonnull
    protected Map<String, T> toIdToStage(@Nullable Iterable<T> stages) {
        final Map<String, T> result = new LinkedHashMap<>();
        if (stages != null) {
            for (T stage : stages) {
                result.put(stage.getId(), stage);
            }
        }
        return unmodifiableMap(result);
    }
}
