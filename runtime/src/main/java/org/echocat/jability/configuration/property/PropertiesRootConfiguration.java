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

package org.echocat.jability.configuration.property;

import org.echocat.jability.configuration.UnderConfiguration;
import org.echocat.jability.configuration.property.UnderPropertiesRootConfiguration.RespectSystemProperties;
import org.echocat.jability.configuration.property.UnderPropertiesRootConfiguration.RespectSystemProvider;
import org.echocat.jability.configuration.support.BaseConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

import static org.echocat.jability.configuration.ConfigurationConstants.SCHEMA_NAMESPACE;
import static org.echocat.jomon.runtime.CollectionUtils.asList;

@XmlType(name = "propertiesRoot", propOrder = {"respectSystemProperties", "respectSystemProviders", "properties", "references", "providers"}, namespace = SCHEMA_NAMESPACE)
@NotThreadSafe
public class PropertiesRootConfiguration extends BaseConfiguration implements UnderConfiguration {

    @Nonnull
    public static PropertiesRootConfiguration propertiesConfiguration() {
        return new PropertiesRootConfiguration();
    }

    private boolean _respectSystemProperties = true;
    private boolean _respectSystemProviders = true;
    private List<PropertiesConfiguration> _properties;
    private List<PropertyReferenceConfiguration> _references;
    private List<PropertyProviderConfiguration> _providers;

    @XmlAttribute(name = "respectSystemProperties")
    public boolean isRespectSystemProperties() {
        return _respectSystemProperties;
    }

    public void setRespectSystemProperties(boolean respectSystemProperties) {
        _respectSystemProperties = respectSystemProperties;
    }

    @XmlAttribute(name = "respectSystemProviders")
    public boolean isRespectSystemProviders() {
        return _respectSystemProviders;
    }

    public void setRespectSystemProviders(boolean respectSystemProviders) {
        _respectSystemProviders = respectSystemProviders;
    }

    @XmlElement(name = "properties", namespace = SCHEMA_NAMESPACE)
    public List<PropertiesConfiguration> getProperties() {
        return _properties;
    }

    public void setProperties(List<PropertiesConfiguration> properties) {
        _properties = properties;
    }

    @XmlElement(name = "reference", namespace = SCHEMA_NAMESPACE)
    public List<PropertyReferenceConfiguration> getReferences() {
        return _references;
    }

    public void setReferences(List<PropertyReferenceConfiguration> references) {
        _references = references;
    }

    @XmlElement(name = "provider", namespace = SCHEMA_NAMESPACE)
    public List<PropertyProviderConfiguration> getProviders() {
        return _providers;
    }

    public void setProviders(List<PropertyProviderConfiguration> providers) {
        _providers = providers;
    }

    @Nonnull
    public PropertiesRootConfiguration with(@Nullable UnderPropertiesRootConfiguration... what) {
        return with(asList(what));
    }

    @Nonnull
    public PropertiesRootConfiguration with(@Nullable Iterable<UnderPropertiesRootConfiguration> what) {
        if (what != null) {
            for (UnderPropertiesRootConfiguration configuration : what) {
                if (configuration instanceof PropertiesConfiguration) {
                    _properties = addOrCreate((PropertiesConfiguration) configuration, _properties);
                } else if (configuration instanceof PropertyReferenceConfiguration) {
                    _references = addOrCreate((PropertyReferenceConfiguration) configuration, _references);
                } else if (configuration instanceof PropertyProviderConfiguration) {
                    _providers = addOrCreate((PropertyProviderConfiguration) configuration, _providers);
                } else if (configuration instanceof RespectSystemProperties) {
                    final RespectSystemProperties respect = (RespectSystemProperties) configuration;
                    _respectSystemProperties = respect.getValue();
                } else if (configuration instanceof RespectSystemProvider) {
                    final RespectSystemProvider respect = (RespectSystemProvider) configuration;
                    _respectSystemProviders = respect.getValue();
                } else {
                    throw new IllegalArgumentException("Could not handle configuration: "  + configuration);
                }
            }
        }
        return this;
    }

}
