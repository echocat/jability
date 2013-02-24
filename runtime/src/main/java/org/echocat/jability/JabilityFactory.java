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

package org.echocat.jability;

import org.echocat.jability.configuration.Configuration;
import org.echocat.jability.configuration.ConfigurationException;
import org.w3c.dom.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import static org.echocat.jability.configuration.ConfigurationMarshaller.findDefaultConfigurationLocation;
import static org.echocat.jability.configuration.ConfigurationMarshaller.unmarshal;

public class JabilityFactory {

    @Nonnull
    public static Jability createBy(@Nullable Configuration configuration) throws ConfigurationException {
        return createBy(null, configuration);
    }

    @Nonnull
    public static Jability createBy(@Nullable URL configurationLocation) throws ConfigurationException, IOException {
        return createBy(null, configurationLocation);
    }

    @Nonnull
    public static Jability createBy(@Nullable Reader reader) throws ConfigurationException, IOException {
        return createBy(null, reader);
    }

    @Nonnull
    public static Jability createBy(@Nullable String configurationXml) throws ConfigurationException {
        return createBy(null, configurationXml);
    }

    @Nonnull
    public static Jability createBy(@Nullable Node configuration) throws ConfigurationException {
        return createBy(null, configuration);
    }

    @Nonnull
    public static Jability createDefault(@Nullable ClassLoader classLoader) throws ConfigurationException {
        try {
            return createBy(classLoader, findDefaultConfigurationLocation());
        } catch (IOException e) {
            throw new ConfigurationException(e);
        }
    }

    @Nonnull
    public static Jability createBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) throws ConfigurationException {
        final LoadEnabledJability jability = new DefaultJability();
        jability.load(classLoader, configuration);
        return jability;
    }

    @Nonnull
    public static Jability createBy(@Nullable ClassLoader classLoader, @Nullable URL configurationLocation) throws ConfigurationException, IOException {
        return createBy(classLoader, unmarshal(configurationLocation));
    }

    @Nonnull
    public static Jability createBy(@Nullable ClassLoader classLoader, @Nullable Reader reader) throws ConfigurationException, IOException {
        return createBy(classLoader, unmarshal(reader));
    }

    @Nonnull
    public static Jability createBy(@Nullable ClassLoader classLoader, @Nullable String configurationXml) throws ConfigurationException {
        return createBy(classLoader, unmarshal(configurationXml));
    }

    @Nonnull
    public static Jability createBy(@Nullable ClassLoader classLoader, @Nullable Node configuration) throws ConfigurationException {
        return createBy(classLoader, unmarshal(configuration));
    }

    @Nonnull
    public static Jability createDefault() throws ConfigurationException {
        return createDefault(null);
    }

    private JabilityFactory() {}
    protected static final JabilityFactory INSTANCE = new JabilityFactory();

}
