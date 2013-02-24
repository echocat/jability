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

import org.echocat.jomon.testing.BaseMatchers;
import org.junit.Test;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlType;

import static java.lang.Thread.currentThread;
import static junit.framework.Assert.fail;
import static org.echocat.jability.support.ClassUtils.*;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.*;
import static org.echocat.jomon.testing.StringMatchers.contains;

public class ClassUtilsUnitTest {

    @Test
    public void testSelectClassLoader() throws Exception {
        assertThat(selectClassLoader(null), isSameAs(currentThread().getContextClassLoader()));
        final ClassLoader specialClassLoader = new TestClassLoader(null);
        assertThat(selectClassLoader(specialClassLoader), isSameAs(specialClassLoader));
    }

    @Test
    public void testLoadClassBy() throws Exception {
        assertThat(loadClassBy(null, TestA.class.getName()), BaseMatchers.<Class<?>>isSameAs(TestA.class));
        assertThat(loadClassBy(null, TestB.class.getName()), BaseMatchers.<Class<?>>isSameAs(TestB.class));
        assertThat(loadClassBy(null, TestC.class.getName()), BaseMatchers.<Class<?>>isSameAs(TestC.class));

        final ClassLoader specialClassLoader = new TestClassLoader(currentThread().getContextClassLoader());
        assertThat(loadClassBy(specialClassLoader, TestA.class.getName()), BaseMatchers.<Class<?>>isSameAs(TestA.class));
        assertThat(loadClassBy(specialClassLoader, TestB.class.getName()), BaseMatchers.<Class<?>>isSameAs(TestB.class));
        assertThat(loadClassBy(specialClassLoader, TestC.class.getName()), BaseMatchers.<Class<?>>isSameAs(TestC.class));
    }

    @Test
    public void testLoadClassByWithIllegalClassLoader() throws Exception {
        try {
            assertThat(loadClassBy(new TestClassLoader(null), TestA.class.getName()), BaseMatchers.<Class<?>>isSameAs(TestA.class));
            fail("Expected exception missing.");
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getCause(), isInstanceOf(ClassNotFoundException.class));
        }
    }

    @Test
    public void testLoadClassByWithIllegalClassName() throws Exception {
        try {
            assertThat(loadClassBy(null, TestA.class.getName() + "XXX"), BaseMatchers.<Class<?>>isSameAs(TestA.class));
            fail("Expected exception missing.");
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getCause(), isInstanceOf(ClassNotFoundException.class));
        }
    }

    @Test
    public void testLoadClassByWithTypeCheck() throws Exception {
        assertThat(loadClassBy(null, TestA.class.getName(), TestA.class), BaseMatchers.<Class<?>>isSameAs(TestA.class));
        assertThat(loadClassBy(null, TestB.class.getName(), TestA.class), BaseMatchers.<Class<?>>isSameAs(TestB.class));
        assertThat(loadClassBy(null, TestC.class.getName(), TestC.class), BaseMatchers.<Class<?>>isSameAs(TestC.class));
    }

    @Test
    public void testLoadClassByWithMismatchingTypeCheck() throws Exception {
        try {
            loadClassBy(null, TestA.class.getName(), TestB.class);
            fail("Expected exception missing.");
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(), contains("is not of type"));
        }
    }

    @Test
    public void testCreateInstanceBy() throws Exception {
        assertThat(createInstanceBy(null, TestA.class.getName(), TestA.class), isInstanceOf(TestA.class));
        assertThat(createInstanceBy(null, TestB.class.getName(), TestA.class), isInstanceOf(TestB.class));
        assertThat(createInstanceBy(null, TestC.class.getName(), TestC.class), isInstanceOf(TestC.class));
    }

    @Test
    public void testCreateInstanceByWithExceptions() throws Exception {
        try {
            createInstanceBy(null, TestWithRuntimeExceptionInConstructor.class.getName(), TestWithRuntimeExceptionInConstructor.class);
            fail("Expected exception missing.");
        } catch (RuntimeException expected) {
            assertThat(expected.getClass(), BaseMatchers.<Class<?>>is(RuntimeException.class));
            assertThat(expected.getMessage(), is("expected"));
        }
        try {
            createInstanceBy(null, TestWithCheckedExceptionInConstructor.class.getName(), TestWithCheckedExceptionInConstructor.class);
            fail("Expected exception missing.");
        } catch (Exception expected) {
            assertThat(expected.getClass(), BaseMatchers.<Class<?>>is(RuntimeException.class));
            assertThat(expected.getCause().getClass(), BaseMatchers.<Class<?>>is(Exception.class));
            assertThat(expected.getCause().getMessage(), is("expected"));
        }
        try {
            createInstanceBy(null, TestWithErrorInConstructor.class.getName(), TestWithErrorInConstructor.class);
            fail("Expected exception missing.");
        } catch (Error expected) {
            assertThat(expected.getClass(), BaseMatchers.<Class<?>>is(Error.class));
            assertThat(expected.getMessage(), is("expected"));
        }
    }

    @Test
    public void testCreateInstanceByWithMissingConstructor() throws Exception {
        try {
            createInstanceBy(null, TestWithMissingConstructor.class.getName(), TestWithMissingConstructor.class);
            fail("Expected exception missing.");
        } catch (IllegalArgumentException expected) {
            assertThat(expected.getMessage(), contains("find a default constructor"));
        }
    }

    @Test
    public void testNameOf() throws Exception {
        assertThat(nameOf(new TestA()), is(TestA.class.getSimpleName()));
        assertThat(nameOf(new TestB()), is(TestB.class.getSimpleName()));
        assertThat(nameOf(new TestC()), is("anotherWayToDefineNames"));
    }

    public static class TestA {}

    public static class TestB extends TestA {}

    @XmlType(name = "anotherWayToDefineNames")
    public static class TestC extends TestB {}

    public static class TestWithRuntimeExceptionInConstructor {

        public TestWithRuntimeExceptionInConstructor() {
            throw new RuntimeException("expected");
        }

    }

    public static class TestWithCheckedExceptionInConstructor {

        public TestWithCheckedExceptionInConstructor() throws Exception {
            throw new Exception("expected");
        }

    }

    public static class TestWithErrorInConstructor {

        public TestWithErrorInConstructor() {
            throw new Error("expected");
        }

    }

    public static class TestWithMissingConstructor {

        public TestWithMissingConstructor(int hiddenParameter) {
            throw new RuntimeException("You found this constructor and provide the hidden parameter " + hiddenParameter + "?");
        }

    }

    public static class TestClassLoader extends ClassLoader {

        public TestClassLoader(@Nullable ClassLoader parent) {
            super(parent);
        }

    }

}
