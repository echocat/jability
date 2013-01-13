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

package org.echocat.jability.configuration.capability;

import org.echocat.jability.configuration.UnderConfiguration;
import org.echocat.jability.configuration.capability.UnderCapabilitiesRootConfiguration.RespectSystemCapabilities;
import org.echocat.jability.configuration.capability.UnderCapabilitiesRootConfiguration.RespectSystemProvider;
import org.echocat.jability.configuration.support.BaseConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

import static org.echocat.jomon.runtime.CollectionUtils.asList;

@XmlType(name = "capabilitiesRoot", propOrder = {"respectSystemCapabilities", "respectSystemProviders", "capabilities", "references", "providers"})
public class CapabilitiesRootConfiguration extends BaseConfiguration implements UnderConfiguration {

    private boolean _respectSystemCapabilities = true;
    private boolean _respectSystemProviders = true;
    private List<CapabilitiesConfiguration> _capabilities;
    private List<CapabilityReferenceConfiguration> _references;
    private List<CapabilityProviderConfiguration> _providers;

    @XmlAttribute(name = "respectSystemCapabilities")
    public boolean isRespectSystemCapabilities() {
        return _respectSystemCapabilities;
    }

    public void setRespectSystemCapabilities(boolean respectSystemCapabilities) {
        _respectSystemCapabilities = respectSystemCapabilities;
    }

    @XmlAttribute(name = "respectSystemProviders")
    public boolean isRespectSystemProviders() {
        return _respectSystemProviders;
    }

    public void setRespectSystemProviders(boolean respectSystemProviders) {
        _respectSystemProviders = respectSystemProviders;
    }

    @XmlElement(name = "capabilities")
    public List<CapabilitiesConfiguration> getCapabilities() {
        return _capabilities;
    }

    public void setCapabilities(List<CapabilitiesConfiguration> capabilities) {
        _capabilities = capabilities;
    }

    @XmlElement(name = "reference")
    public List<CapabilityReferenceConfiguration> getReferences() {
        return _references;
    }

    public void setReferences(List<CapabilityReferenceConfiguration> references) {
        _references = references;
    }

    @XmlElement(name = "provider")
    public List<CapabilityProviderConfiguration> getProviders() {
        return _providers;
    }

    public void setProviders(List<CapabilityProviderConfiguration> providers) {
        _providers = providers;
    }

    @Nonnull
    public CapabilitiesRootConfiguration with(@Nullable UnderCapabilitiesRootConfiguration... what) {
        return with(asList(what));
    }

    @Nonnull
    public CapabilitiesRootConfiguration with(@Nullable Iterable<UnderCapabilitiesRootConfiguration> what) {
        if (what != null) {
            for (UnderCapabilitiesRootConfiguration configuration : what) {
                if (configuration instanceof CapabilitiesConfiguration) {
                    _capabilities = addOrCreate((CapabilitiesConfiguration) configuration, _capabilities);
                } else if (configuration instanceof CapabilityReferenceConfiguration) {
                    _references = addOrCreate((CapabilityReferenceConfiguration) configuration, _references);
                } else if (configuration instanceof CapabilityProviderConfiguration) {
                    _providers = addOrCreate((CapabilityProviderConfiguration) configuration, _providers);
                } else if (configuration instanceof RespectSystemCapabilities ) {
                    final RespectSystemCapabilities respect = (RespectSystemCapabilities) configuration;
                    _respectSystemCapabilities = respect.getValue();
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
