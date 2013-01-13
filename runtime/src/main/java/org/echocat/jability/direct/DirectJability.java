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

package org.echocat.jability.direct;

import org.echocat.jability.Capabilities;
import org.echocat.jability.CapabilityProvider;
import org.echocat.jability.Jability;
import org.echocat.jability.LoadEnabledJability;
import org.echocat.jability.configuration.Configuration;
import org.echocat.jability.property.Properties;
import org.echocat.jability.property.PropertyProvider;
import org.echocat.jability.stage.StageProvider;
import org.w3c.dom.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import static java.lang.Runtime.getRuntime;
import static org.echocat.jability.JabilityFactory.createDefault;
import static org.echocat.jability.configuration.ConfigurationMarshaller.unmarshal;
import static org.echocat.jomon.runtime.util.ResourceUtils.closeQuietly;

public class DirectJability {

    private static volatile Jability c_instance = new CloseOnShutdownJability(createDefault());

    @Nonnull
    public static CapabilityProvider capabilityProvider() {
        return c_instance.getCapabilityProvider();
    }

    @Nonnull
    public static Capabilities capabilities() {
        return c_instance.getCapabilities();
    }

    @Nonnull
    public static StageProvider stageProvider() {
        return c_instance.getStageProvider();
    }

    @Nonnull
    public static PropertyProvider propertyProvider() {
        return c_instance.getPropertyProvider();
    }

    @Nonnull
    public static Properties properties() {
        return c_instance.getProperties();
    }

    public static void configureWith(@Nullable URL configurationLocation) throws IOException {
        configureWith(null, configurationLocation);
    }

    public static void configureWith(@Nullable Reader configuration) throws IOException {
        configureWith(null, configuration);
    }

    public static void configureWith(@Nullable String xmlConfiguration) {
        configureWith(null, xmlConfiguration);
    }

    public static void configureWith(@Nullable Node configuration) {
        configureWith(null, configuration);
    }

    public static void configureWith(@Nullable Configuration configuration) {
        configureWith(null, configuration);
    }

    public static void configureWith(@Nullable ClassLoader classLoader, @Nullable URL configurationLocation) throws IOException {
        configureWith(classLoader, unmarshal(configurationLocation));
    }

    public static void configureWith(@Nullable ClassLoader classLoader, @Nullable Reader configuration) throws IOException {
        configureWith(classLoader, unmarshal(configuration));
    }

    public static void configureWith(@Nullable ClassLoader classLoader, @Nullable String xmlConfiguration) {
        configureWith(classLoader, unmarshal(xmlConfiguration));
    }

    public static void configureWith(@Nullable ClassLoader classLoader, @Nullable Node configuration) {
        configureWith(classLoader, unmarshal(configuration));
    }

    public static void configureWith(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        final Jability instance = c_instance;
        if (!(instance instanceof LoadEnabledJability)) {
            throw new UnsupportedOperationException(instance + " does not support loading of a configuration.");
        }
        ((LoadEnabledJability)instance).load(classLoader, configuration);
    }

    @Nonnull
    public static Jability getInstance() {
        return c_instance;
    }

    @Nonnull
    public static void setInstance(@Nullable Jability jability) {
        synchronized (DirectJability.class) {
            // noinspection ObjectEquality
            if (jability != c_instance) {
                closeQuietly(c_instance);
            }
            final Jability target = jability != null ? jability : createDefault();
            c_instance = target instanceof CloseOnShutdownJability ? target : new CloseOnShutdownJability(target);
        }
    }

    public static class CloseOnShutdownJability implements LoadEnabledJability {

        private final Jability _delegate;
        private final Thread _shutdownHook;

        public CloseOnShutdownJability(@Nonnull Jability delegate) {
            _delegate = delegate;
            _shutdownHook = new Thread("Shutdown Jability") { @Override public void run() {
                closeQuietly(CloseOnShutdownJability.this);
            }};
            getRuntime().addShutdownHook(_shutdownHook);
        }

        @Override
        @Nonnull
        public CapabilityProvider getCapabilityProvider() {
            return _delegate.getCapabilityProvider();
        }

        @Override
        @Nonnull
        public Capabilities getCapabilities() {
            return _delegate.getCapabilities();
        }

        @Override
        @Nonnull
        public PropertyProvider getPropertyProvider() {
            return _delegate.getPropertyProvider();
        }

        @Override
        @Nonnull
        public Properties getProperties() {
            return _delegate.getProperties();
        }

        @Override
        @Nonnull
        public StageProvider getStageProvider() {
            return _delegate.getStageProvider();
        }

        @Override
        public void load(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
            if (!(_delegate instanceof LoadEnabledJability)) {
                throw new UnsupportedOperationException(_delegate + " does not support loading of a configuration.");
            }
            ((LoadEnabledJability)_delegate).load(classLoader, configuration);
        }

        @Override
        public void close() throws Exception {
            try {
                _delegate.close();
            } finally {
                getRuntime().removeShutdownHook(_shutdownHook);
            }
        }

        @Nonnull
        public Jability getDelegate() {
            return _delegate;
        }
    }

    private DirectJability() {}

}
