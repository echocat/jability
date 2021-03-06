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
import javax.annotation.concurrent.NotThreadSafe;
import java.io.*;
import java.net.URL;
import java.util.*;

import static org.echocat.jability.support.ClassUtils.selectClassLoader;

@NotThreadSafe
public class TypeIterator<T> implements Iterator<Class<? extends T>> {

    private final Class<?> _baseType;
    private final ClassLoader _classLoader;
    private final Class<T> _expectedType;

    private final Enumeration<URL> _urls;

    private Iterator<Class<? extends T>> _currentIterator;
    private Boolean _hasNext;
    private Class<? extends T> _next;

    public TypeIterator(@Nonnull Class<?> baseType, @Nullable Class<T> expectedType, @Nullable ClassLoader classLoader) {
        _baseType = baseType;
        _classLoader = selectClassLoader(classLoader);
        _expectedType = expectedType;

        final String fileName = "META-INF/types/" + baseType.getName();
        try {
            _urls = _classLoader.getResources(fileName);
        } catch (IOException e) {
            throw new RuntimeException("Could not load files '" + fileName + "' from classpath by " + _classLoader + ".", e);
        }
    }

    @Nonnull
    private Iterator<Class<? extends T>> load(@Nonnull URL url) {
        try {
            try (final InputStream is = url.openStream()) {
                try (final Reader reader = new InputStreamReader(is)) {
                    try (final BufferedReader br = new BufferedReader(reader)) {
                        final Collection<Class<? extends T>> types = new ArrayList<>();
                        String line = br.readLine();
                        while (line != null) {
                            final String trimmedLine = line.trim();
                            if (!trimmedLine.isEmpty()) {
                                final Class<?> plainClass;
                                try {
                                    plainClass = _classLoader.loadClass(trimmedLine);
                                } catch (ClassNotFoundException e) {
                                    throw new IllegalStateException("Could not find a class named " + trimmedLine + " defined in " + url + ".", e);
                                }
                                if (_expectedType != null && !_expectedType.isAssignableFrom(plainClass)) {
                                    throw new IllegalStateException("Class named " + trimmedLine + " defined in " + url + " is not of expected type " + _expectedType.getName() + ".");
                                }
                                // noinspection unchecked
                                types.add((Class<? extends T>) plainClass);
                            }
                            line = br.readLine();
                        }
                        return types.iterator();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load classes of type " + _baseType.getName() + " from " + url + ".", e);
        }
    }

    @Override
    public boolean hasNext() {
        while (_hasNext == null) {
            if (_currentIterator != null && _currentIterator.hasNext()) {
                _hasNext = true;
                _next = _currentIterator.next();
            } else {
                if (_urls.hasMoreElements()) {
                    _currentIterator = load(_urls.nextElement());
                    _hasNext = null;
                } else {
                    _hasNext = false;
                }
            }
        }
        return _hasNext;
    }

    @Override
    public Class<? extends T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        final Class<? extends T> next = _next;
        _next = null;
        _hasNext = null;
        return next;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
