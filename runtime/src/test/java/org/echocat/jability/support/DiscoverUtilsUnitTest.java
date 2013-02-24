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

import org.echocat.jability.support.TypeIteratorUnitTest.*;
import org.echocat.jomon.testing.CollectionMatchers;
import org.junit.Test;

import static java.util.regex.Pattern.compile;
import static org.echocat.jability.support.AccessType.*;
import static org.echocat.jability.support.DiscoverUtils.discoverStaticFieldValuesBy;
import static org.echocat.jability.support.DiscoverUtils.discoverTypesOf;
import static org.echocat.jability.support.DiscoverUtilsUnitTest.Test1.E1;
import static org.echocat.jability.support.DiscoverUtilsUnitTest.Test1.IA1;
import static org.echocat.jability.support.DiscoverUtilsUnitTest.Test1.IB1;
import static org.echocat.jability.support.DiscoverUtilsUnitTest.Test1.IC1;
import static org.echocat.jability.support.DiscoverUtilsUnitTest.Test1.N1;
import static org.echocat.jability.support.DiscoverUtilsUnitTest.Test2.*;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.CollectionMatchers.isEqualTo;

public class DiscoverUtilsUnitTest {

    @Test
    public void testDiscoverStaticFieldValuesByBasics() throws Exception {
        assertThat(discoverStaticFieldValuesBy(Object.class, Test2.class, null, null, PUBLIC, PROTECTED, PACKAGE_LOCAL, PRIVATE), CollectionMatchers.<Object>isEqualTo(IA2, IB2, IC2, Test2.ID2, N2, E2, IA1, IB1, IC1, Test1.ID1, N1, E1));
        assertThat(discoverStaticFieldValuesBy(String.class, Test2.class, null, null, PUBLIC, PROTECTED, PACKAGE_LOCAL, PRIVATE), CollectionMatchers.<String>isEqualTo(IA2, IB2, IC2, Test2.ID2, E2, IA1, IB1, IC1, Test1.ID1, E1));
    }

    @Test
    public void testDiscoverStaticFieldValuesByWithStopAt() throws Exception {
        assertThat(discoverStaticFieldValuesBy(Object.class, Test2.class, Test1.class, null, PUBLIC, PROTECTED, PACKAGE_LOCAL, PRIVATE), CollectionMatchers.<Object>isEqualTo(IA2, IB2, IC2, Test2.ID2, N2, E2));
    }

    @Test
    public void testDiscoverStaticFieldValuesByWithFieldNamePattern() throws Exception {
        assertThat(discoverStaticFieldValuesBy(Object.class, Test2.class, null, compile("[^E].*"), PUBLIC, PROTECTED, PACKAGE_LOCAL, PRIVATE), CollectionMatchers.<Object>isEqualTo(IA2, IB2, IC2, Test2.ID2, N2, IA1, IB1, IC1, Test1.ID1, N1));
    }

    @Test
    public void testDiscoverStaticFieldValuesByWithVisibilityLimitation() throws Exception {
        assertThat(discoverStaticFieldValuesBy(Object.class, Test2.class, null, compile("I.*"), PUBLIC), CollectionMatchers.<Object>isEqualTo(IA2, IA1));
        assertThat(discoverStaticFieldValuesBy(Object.class, Test2.class, null, compile("I.*"), PROTECTED), CollectionMatchers.<Object>isEqualTo(IB2, IB1));
        assertThat(discoverStaticFieldValuesBy(Object.class, Test2.class, null, compile("I.*"), PACKAGE_LOCAL), CollectionMatchers.<Object>isEqualTo(IC2, IC1));
        assertThat(discoverStaticFieldValuesBy(Object.class, Test2.class, null, compile("I.*"), PRIVATE), CollectionMatchers.<Object>isEqualTo(Test2.ID2, Test1.ID1));
        assertThat(discoverStaticFieldValuesBy(Object.class, Test2.class, null, compile("I.*"), PUBLIC, PACKAGE_LOCAL), CollectionMatchers.<Object>isEqualTo(IA2, IC2, IA1, IC1));
    }

    @Test
    public void testDiscoverTypesOf() throws Exception {
        assertThat(discoverTypesOf(TestType1.class, null), isEqualTo(TestType1A.class, TestType1B.class, TestType1C.class));
        assertThat(discoverTypesOf(TestType1.class, SuperTestType.class, null), CollectionMatchers.<Class<? extends SuperTestType>>isEqualTo(TestType1A.class, TestType1B.class, TestType1C.class));
        assertThat(discoverTypesOf(TestType2.class, SuperTestType.class, null), isEqualTo(TestType2A.class, TestType2B.class, TestType2C.class));
    }

    public static class Test1 {

        public static final String IA1 = "a1";
        protected static final String IB1 = "b1";
        static final String IC1 = "c1";
        private static final String ID1 = "d1";

        public static final Integer N1 = 1;
        public static final String E1 = "e1";

        private Test1() {}

    }

    public static class Test2 extends Test1 {

        public static final String IA2 = "a2";
        protected static final String IB2 = "b2";
        static final String IC2 = "c2";
        private static final String ID2 = "d2";

        public static final Integer N2 = 1;
        public static final String E2 = "e2";

        private Test2() {}

    }

}
