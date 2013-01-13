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

import static org.echocat.jability.stage.Stages.unknown;
import static org.echocat.jomon.runtime.CollectionUtils.emptyIterator;

public class NoopStageProvider implements StageProvider {

    @Nullable
    @Override
    public Stage provideBy(@Nonnull String id) {
        return null;
    }

    @Nonnull
    @Override
    public Stage provideCurrent() {
        return unknown;
    }

    @Override
    public Iterator<Stage> iterator() {
        return emptyIterator();
    }

}
