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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static java.lang.Thread.currentThread;

public class ClassUtils {

    @Nonnull
    public static ClassLoader selectClassLoader(@Nullable ClassLoader classLoader) {
        return classLoader != null ? classLoader : currentThread().getContextClassLoader();
    }

    @Nonnull
    public static Class<?> loadClassBy(@Nullable ClassLoader classLoader, @Nonnull String className) throws IllegalArgumentException {
        try {
            return selectClassLoader(classLoader).loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Could not find a class named: " + className, e);
        }
    }

    @Nonnull
    public static <T> Class<? extends T> loadClassBy(@Nullable ClassLoader classLoader, @Nonnull String className, @Nonnull Class<T> expectedType) throws IllegalArgumentException {
        final Class<?> plainClass = loadClassBy(classLoader, className);
        if (!expectedType.isAssignableFrom(plainClass)) {
            throw new IllegalArgumentException("Class " + className + " is not of type " + expectedType.getName() + ".");
        }
        // noinspection unchecked
        return (Class<? extends T>) plainClass;
    }

    @Nonnull
    public static <T> T createInstanceBy(@Nullable ClassLoader classLoader, @Nonnull String className, @Nonnull Class<T> expectedType) throws IllegalArgumentException {
        final Class<? extends T> loadedClass = loadClassBy(classLoader, className, expectedType);
        final Constructor<? extends T> constructor;
        try {
            constructor = loadedClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Could not find a default constructor of class: " + className, e);
        }
        try {
            return constructor.newInstance();
        } catch (Exception e) {
            final Throwable cause = e instanceof InvocationTargetException ? ((InvocationTargetException) e).getTargetException() : null;
            final Throwable target = cause != null ? cause : e;
            if (target instanceof RuntimeException) {
                throw (RuntimeException) target;
            } else if (target instanceof Error) {
                throw (Error) target;
            } else {
                throw new RuntimeException("Could not init " + className + ".", target);
            }
        }
    }

    @Nonnull
    public static String nameOf(@Nonnull Object o) {
        return nameOf(o.getClass());
    }

    @Nonnull
    public static String nameOf(@Nonnull Class<?> type) {
        final XmlType xmlType = type.getAnnotation(XmlType.class);
        return xmlType != null && !"##default".equals(xmlType.name()) ? xmlType.name() : type.getSimpleName();
    }

    private ClassUtils() {}
    protected static final ClassUtils INSTANCE = new ClassUtils();

}
