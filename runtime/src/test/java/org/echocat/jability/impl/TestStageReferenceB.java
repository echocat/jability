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

package org.echocat.jability.impl;

import org.echocat.jability.stage.Stage;

import static org.echocat.jability.stage.support.StageUtils.newStage;

@SuppressWarnings("ConstantNamingConvention")
public class TestStageReferenceB {

    public static final Stage testStageB1 = newStage("testStageB1", 120);
    public static final Stage testStageB2 = newStage("testStageB2", 220);
    public static final Stage bestStageB3 = newStage("bestStageB3", 320);

    private TestStageReferenceB() {}
}
