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
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

import static java.lang.reflect.Modifier.isStatic;
import static java.util.Collections.unmodifiableCollection;
import static org.echocat.jability.support.AccessType.matches;
import static org.echocat.jability.support.ClassUtils.selectClassLoader;
import static org.echocat.jomon.runtime.CollectionUtils.asList;

public class DiscoverUtils {

    @Nonnull
    public static <T> Collection<T> discoverStaticFieldValuesBy(@Nonnull Class<? extends T> fieldType, @Nonnull Class<?> startFrom, @Nullable Class<?> stopAt, @Nullable Pattern fieldName, @Nullable AccessType... accessTypes) {
        return discoverStaticFieldValuesBy(fieldType, startFrom, stopAt, fieldName, asList(accessTypes));
    }

    @Nonnull
    public static <T> Collection<T> discoverStaticFieldValuesBy(@Nonnull Class<? extends T> fieldType, @Nonnull Class<?> startFrom, @Nullable Class<?> stopAt, @Nullable Pattern fieldName, @Nullable Collection<AccessType> accessTypes) {
        final Collection<T> result = new ArrayList<>();
        if (!startFrom.equals(stopAt)) {
            for (Field field : startFrom.getDeclaredFields()) {
                if (fieldName == null || fieldName.matcher(field.getName()).matches()) {
                    if (isStatic(field.getModifiers())) {
                        if (matches(field, accessTypes)) {
                            if (fieldType.isAssignableFrom(field.getType())) {
                                field.setAccessible(true);
                                try {
                                    result.add(fieldType.cast(field.get(null)));
                                } catch (Exception e) {
                                    throw new RuntimeException("Could not get the value from " + field + ".", e);
                                }
                            }
                        }
                    }
                }
            }
            final Class<?> superclass = startFrom.getSuperclass();
            if (superclass != null && !superclass.equals(Object.class)) {
                result.addAll(discoverStaticFieldValuesBy(fieldType, superclass, stopAt, fieldName, accessTypes));
            }
        }
        return unmodifiableCollection(result);
    }

    @Nonnull
    public static <T> Iterable<Class<? extends T>> discoverTypesOf(@Nonnull Class<T> baseType, @Nullable ClassLoader classLoader) {
        return discoverTypesOf(baseType, baseType, classLoader);
    }

    @Nonnull
    public static <T> Iterable<Class<? extends T>> discoverTypesOf(@Nonnull final Class<?> baseType, @Nullable final Class<T> expectedType, @Nullable final ClassLoader classLoader) {
        return new Iterable<Class<? extends T>>() { @Override public Iterator<Class<? extends T>> iterator() {
            return new TypeIterator<>(baseType, expectedType, selectClassLoader(classLoader));
        }};
    }

    private DiscoverUtils() {}
    protected static final DiscoverUtils INSTANCE = new DiscoverUtils();

}
