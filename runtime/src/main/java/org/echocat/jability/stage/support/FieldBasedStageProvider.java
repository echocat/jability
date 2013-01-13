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

import org.echocat.jability.configuration.stage.StageReferenceConfiguration;
import org.echocat.jability.stage.Stage;
import org.echocat.jability.support.AccessType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.regex.Pattern;

import static org.echocat.jability.support.DiscoverUtils.discoverStaticFieldValuesBy;
import static org.echocat.jability.support.DiscoverUtils.loadClassBy;

public class FieldBasedStageProvider<T extends Stage> extends BaseStageProvider<T> {

    public FieldBasedStageProvider(@Nonnull Class<? extends T> stageType, @Nonnull Class<?> startFrom, @Nullable Class<?> stopAt, @Nullable Pattern fieldPattern, @Nullable AccessType... accessTypes) {
        super(discoverStaticFieldValuesBy(stageType, startFrom, stopAt, fieldPattern, accessTypes));
    }

    public FieldBasedStageProvider(@Nonnull Class<? extends T> stageType, @Nonnull Class<?> startFrom, @Nullable Class<?> stopAt, @Nullable Pattern fieldPattern, @Nullable Collection<AccessType> accessTypes) {
        super(discoverStaticFieldValuesBy(stageType, startFrom, stopAt, fieldPattern, accessTypes));
    }

    public FieldBasedStageProvider(@Nullable ClassLoader classLoader, @Nonnull StageReferenceConfiguration configuration) {
        // noinspection unchecked
        this((Class<? extends T>) Stage.class, loadClassBy(classLoader, configuration.getFromType()), null, configuration.getFromField(), configuration.getAccessTypes());
    }

}
