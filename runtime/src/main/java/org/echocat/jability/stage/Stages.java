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

import javax.annotation.Nonnull;

import static org.echocat.jability.stage.CompoundStageProvider.stageProvider;
import static org.echocat.jability.stage.Stage.Impl;

@SuppressWarnings("ConstantNamingConvention")
public class Stages {

    public static final Stage development = newStage("development", 100000);
    public static final Stage integration = newStage("integration", 200000);
    public static final Stage alpha = newStage("alpha", 300000);
    public static final Stage beta = newStage("beta", 400000);
    public static final Stage productive = newStage("productive", 500000);

    @Nonnull
    public static Stage newStage(@Nonnull String id, int priority) {
        return new Impl(id, priority);
    }

    @Nonnull
    public static Stage stage(@Nonnull String id) throws IllegalArgumentException {
        final Stage stage = stageProvider().provideBy(id);
        if (stage == null) {
            throw new IllegalArgumentException("There is no stage with id '" + id + "'.");
        }
        return stage;
    }

    private Stages() {}

}
