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

import org.echocat.jability.configuration.support.BaseConfiguration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "capabilities", propOrder = {"definitions", "providers", "definitionProviders"})
public class CapabilitiesConfiguration extends BaseConfiguration {

    private List<CapabilityDefinitionConfiguration> _definitions;
    private List<CapabilityProviderConfiguration> _providers;
    private List<CapabilityDefinitionProviderConfiguration> _definitionProviders;

    @XmlElement(name = "definition")
    public List<CapabilityDefinitionConfiguration> getDefinitions() {
        return _definitions;
    }

    public void setDefinitions(List<CapabilityDefinitionConfiguration> definitions) {
        _definitions = definitions;
    }

    @XmlElement(name = "provider")
    public List<CapabilityProviderConfiguration> getProviders() {
        return _providers;
    }

    public void setProviders(List<CapabilityProviderConfiguration> providers) {
        _providers = providers;
    }

    @XmlElement(name = "definitionProvider")
    public List<CapabilityDefinitionProviderConfiguration> getDefinitionProviders() {
        return _definitionProviders;
    }

    public void setDefinitionProviders(List<CapabilityDefinitionProviderConfiguration> definitionProviders) {
        _definitionProviders = definitionProviders;
    }

}
