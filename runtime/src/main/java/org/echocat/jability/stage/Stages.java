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

import org.echocat.jability.stage.support.StageUtils;

@SuppressWarnings("ConstantNamingConvention")
public interface Stages {

    public static final Stage unknown = StageUtils.newStage("unknown", 0);
    public static final Stage development = StageUtils.newStage("development", 100000);
    public static final Stage integration = StageUtils.newStage("integration", 200000);
    public static final Stage alpha = StageUtils.newStage("alpha", 300000);
    public static final Stage beta = StageUtils.newStage("beta", 400000);
    public static final Stage productive = StageUtils.newStage("productive", 500000);

}
