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
import org.echocat.jability.stage.Stage.Impl;

import javax.annotation.Nonnull;
import java.util.Comparator;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

public class StageUtils {

    private static final Comparator<Stage> COMPARATOR = new Comparator<Stage>() { @Override public int compare(Stage s1, Stage s2) {
        final int result;
        if (s1 == null && s2 == null) {
            result = 0;
        } else if (s1 == null) {
            result = MAX_VALUE;
        } else if (s2 == null) {
            result = MIN_VALUE;
        } else {
            result = Integer.compare(s1.getPriority(), s2.getPriority());
        }
        return result;
    }};

    @Nonnull
    public static Comparator<Stage> stageComparator() {
        return COMPARATOR;
    }

    @Nonnull
    public static Stage newStage(@Nonnull String id, int priority) {
        return new Impl(id, priority);
    }

    private StageUtils() {}
    public static final StageUtils INSTANCE = new StageUtils();

}
