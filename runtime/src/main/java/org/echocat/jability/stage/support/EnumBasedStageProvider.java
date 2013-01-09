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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.echocat.jability.support.AccessType.PUBLIC;

public class EnumBasedStageProvider<T extends Enum<T> & Stage> extends FieldBasedStageProvider<T> {

    public EnumBasedStageProvider(@Nonnull Class<T> stageType) {
        super(stageType, PUBLIC);
    }

    public EnumBasedStageProvider(@Nullable Iterable<T> stages) {
        super(stages);
    }

}
