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
import org.echocat.jability.jmx.CapabilityPropagator;
import org.echocat.jability.jmx.CapabilityPropagatorFactory;
import org.echocat.jability.property.Properties;
import org.echocat.jability.property.PropertiesFactory;
import org.echocat.jability.property.PropertyProvider;
import org.echocat.jability.property.PropertyProviderFactory;
import org.echocat.jability.property.support.NoopProperties;
import org.echocat.jability.property.support.NoopPropertyProvider;
import org.echocat.jability.stage.StageProvider;
import org.echocat.jability.stage.StageProviderFactory;
import org.echocat.jability.stage.support.NoopStageProvider;
import org.echocat.jability.support.NoopCapabilities;
import org.echocat.jability.support.NoopCapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

import static org.echocat.jomon.runtime.util.ResourceUtils.closeQuietly;

@ThreadSafe
public class DefaultJability implements LoadEnabledJability {

    private volatile CapabilityProvider _capabilityProvider = new NoopCapabilityProvider();
    private volatile Capabilities _capabilities = new NoopCapabilities();
    private volatile StageProvider _stageProvider = new NoopStageProvider();
    private volatile PropertyProvider _propertyProvider = new NoopPropertyProvider();
    private volatile Properties _properties = new NoopProperties();
    private volatile CapabilityPropagator _capabilityPropagator;

    @Override
    public void load(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        synchronized (this) {
            setCapabilityProvider(loadCapabilityProviderBy(classLoader, configuration));
            setCapabilities(loadCapabilitiesBy(classLoader, configuration));
            setStageProvider(loadStageProviderBy(classLoader, configuration));
            setPropertyProvider(loadPropertyProviderBy(classLoader, configuration));
            setProperties(loadPropertiesBy(classLoader, configuration));
            setCapabilityPropagator(loadCapabilityProviderIfPossibleBy(configuration));
        }
    }

    @Nonnull
    protected CapabilityProvider loadCapabilityProviderBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        return new CapabilityProviderFactory().createBy(classLoader, configuration);
    }

    @Nonnull
    protected Capabilities loadCapabilitiesBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        return new CapabilitiesFactory().createBy(classLoader, configuration);
    }

    @Nonnull
    protected StageProvider loadStageProviderBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        return new StageProviderFactory().createBy(classLoader, configuration);
    }

    @Nonnull
    protected PropertyProvider loadPropertyProviderBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        return new PropertyProviderFactory().createBy(classLoader, configuration);
    }

    @Nonnull
    protected Properties loadPropertiesBy(@Nullable ClassLoader classLoader, @Nullable Configuration configuration) {
        return new PropertiesFactory().createBy(classLoader, configuration);
    }

    @Nullable
    protected CapabilityPropagator loadCapabilityProviderIfPossibleBy(@Nullable Configuration configuration) {
        return new CapabilityPropagatorFactory().createIfPossibleBy(this, configuration);
    }

    @Override
    @Nonnull
    public CapabilityProvider getCapabilityProvider() {
        return _capabilityProvider;
    }

    public void setCapabilityProvider(@Nullable CapabilityProvider capabilityProvider) {
        synchronized (this) {
            _capabilityProvider = capabilityProvider != null ? capabilityProvider : new NoopCapabilityProvider();
        }
    }

    @Override
    @Nonnull
    public Capabilities getCapabilities() {
        return _capabilities;
    }

    public void setCapabilities(@Nullable Capabilities capabilities) {
        synchronized (this) {
            _capabilities = capabilities != null ? capabilities : new NoopCapabilities();
        }
    }

    @Override
    @Nonnull
    public StageProvider getStageProvider() {
        return _stageProvider;
    }

    public void setStageProvider(@Nullable StageProvider stageProvider) {
        synchronized (this) {
            _stageProvider = stageProvider != null ? stageProvider : new NoopStageProvider();
        }
    }

    @Override
    @Nonnull
    public PropertyProvider getPropertyProvider() {
        return _propertyProvider;
    }

    public void setPropertyProvider(@Nullable PropertyProvider propertyProvider) {
        synchronized (this) {
            _propertyProvider = propertyProvider != null ? propertyProvider : new NoopPropertyProvider();
        }
    }

    @Override
    @Nonnull
    public Properties getProperties() {
        return _properties;
    }

    public void setProperties(@Nullable Properties properties) {
        synchronized (this) {
            _properties = properties != null ? properties : new NoopProperties();
        }
    }

    public CapabilityPropagator getCapabilityPropagator() {
        return _capabilityPropagator;
    }

    public void setCapabilityPropagator(CapabilityPropagator capabilityPropagator) {
        synchronized (this) {
            // noinspection ObjectEquality
            if (_capabilityPropagator != null && capabilityPropagator != _capabilityPropagator) {
                closeQuietly(_capabilityPropagator);
            }
            _capabilityPropagator = capabilityPropagator;
        }
    }

    @Override
    public void close() throws Exception {
        synchronized (this) {
            try {
                closeQuietly(_capabilityPropagator);
            } finally {
                _capabilityPropagator = null;
            }
        }
    }

}
