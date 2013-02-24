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

import org.echocat.jomon.runtime.repository.Repository;
import org.echocat.jomon.testing.BaseMatchers;
import org.echocat.jomon.testing.IteratorMatchers;
import org.echocat.jomon.testing.StringMatchers;
import org.junit.Test;

import java.util.NoSuchElementException;

import static junit.framework.Assert.fail;
import static org.echocat.jomon.runtime.CollectionUtils.asList;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.is;

public class TypeIteratorUnitTest {

    @Test
    public void test() throws Exception {
        assertThat(new TypeIterator<>(TestType1.class, TestType1.class, null), IteratorMatchers.<Class<? extends TestType1>>returnsItems(TestType1A.class, TestType1B.class, TestType1C.class));
        assertThat(new TypeIterator<>(TestType1.class, SuperTestType.class, null), IteratorMatchers.<Class<? extends SuperTestType>>returnsItems(TestType1A.class, TestType1B.class, TestType1C.class));
        assertThat(new TypeIterator<>(TestType2.class, SuperTestType.class, null), IteratorMatchers.<Class<? extends SuperTestType>>returnsItems(TestType2A.class, TestType2B.class, TestType2C.class));
    }

    @Test
    public void testNotMatchingExpectedType() throws Exception {
        try {
            asList(new TypeIterator<>(TestType2.class, TestType2.class, null));
            fail("Expected exception missing.");
        } catch (IllegalStateException expected) {
            assertThat(expected.getMessage(), StringMatchers.contains("is not of expected type " + TestType2.class.getName()));
        }
        try {
            asList(new TypeIterator<>(TestType2.class, Repository.class, null));
            fail("Expected exception missing.");
        } catch (IllegalStateException expected) {
            assertThat(expected.getMessage(), StringMatchers.contains("is not of expected type " + Repository.class.getName()));
        }
    }

    @Test
    public void testIllegalSpecifiedType() throws Exception {
        try {
            asList(new TypeIterator<>(TestType3.class, TestType3.class, null));
            fail("Expected exception missing.");
        } catch (IllegalStateException expected) {
            assertThat(expected.getMessage(), StringMatchers.contains("Could not find a class named"));
        }
    }

    @Test
    public void testRightHasNextCall() throws Exception {
        final TypeIterator<TestType1> i = new TypeIterator<>(TestType1.class, TestType1.class, null);
        assertThat(i.hasNext(), is(true));
        assertThat(i.next(), BaseMatchers.<Class<?>>is(TestType1A.class));
        assertThat(i.hasNext(), is(true));
        assertThat(i.hasNext(), is(true));
        assertThat(i.hasNext(), is(true));
        assertThat(i.next(), BaseMatchers.<Class<?>>is(TestType1B.class));
        assertThat(i.hasNext(), is(true));
        assertThat(i.next(), BaseMatchers.<Class<?>>is(TestType1C.class));
        assertThat(i.hasNext(), is(false));
        try {
            i.next();
            fail("Expected exception missing.");
        } catch (NoSuchElementException expected) {}
    }

    @Test
    public void testRemove() throws Exception {
        final TypeIterator<TestType1> i = new TypeIterator<>(TestType1.class, TestType1.class, null);
        assertThat(i.hasNext(), is(true));
        assertThat(i.next(), BaseMatchers.<Class<?>>is(TestType1A.class));
        try {
            i.remove();
            fail("Expected exception missing.");
        } catch (UnsupportedOperationException expected) {}

    }

    public static interface SuperTestType {}

    public static interface TestType1 extends SuperTestType {}
    public static class TestType1A implements TestType1 {}
    public static class TestType1B implements TestType1 {}
    public static class TestType1C implements TestType1 {}

    public static interface TestType2 extends SuperTestType {}
    public static class TestType2A implements TestType2 {}
    public static class TestType2B implements TestType2 {}
    public static class TestType2C implements TestType1 {}

    public static interface TestType3 extends SuperTestType {}
}
