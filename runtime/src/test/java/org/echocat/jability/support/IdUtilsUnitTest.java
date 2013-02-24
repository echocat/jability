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

package org.echocat.jability.support;

import org.junit.Test;

import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;

public class IdUtilsUnitTest {

    @Test
    public void testBuildIdFrom() throws Exception {
        assertThat(IdUtils.buildIdFrom(IdUtilsUnitTest.class, "a"), is(IdUtilsUnitTest.class.getPackage().getName() + ".a"));
        assertThat(IdUtils.buildIdFrom(IdUtilsUnitTest.class, "xxx"), is(IdUtilsUnitTest.class.getPackage().getName() + ".xxx"));
    }

}
