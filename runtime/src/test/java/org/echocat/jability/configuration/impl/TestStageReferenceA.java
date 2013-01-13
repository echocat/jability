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

package org.echocat.jability.configuration.impl;

import org.echocat.jability.stage.Stage;

import static org.echocat.jability.stage.support.StageUtils.newStage;

@SuppressWarnings("ConstantNamingConvention")
public class TestStageReferenceA {

    public static final Stage testStageA1 = newStage("testStageA1", 110);
    public static final Stage testStageA2 = newStage("testStageA2", 210);
    public static final Stage bestStageA3 = newStage("bestStageA3", 310);

    private TestStageReferenceA() {}
}
