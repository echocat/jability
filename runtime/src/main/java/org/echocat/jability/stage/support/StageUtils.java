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
import java.util.Comparator;

public class StageUtils {

    private static final Comparator<Stage> COMPARATOR = new Comparator<Stage>() { @Override public int compare(Stage s1, Stage s2) {
        final int result;
        if (s1 == null && s2 == null) {
            result = 0;
        } else if (s1 != null) {
            result = 1;
        } else if (s2 != null) {
            result = -1;
        } else {
            result = Integer.compare(s1.getPriority(), s2.getPriority());
        }
        return result;
    }};

    @Nonnull
    public static Comparator<Stage> stageComparator() {
        return COMPARATOR;
    }

    private StageUtils() {}
}
