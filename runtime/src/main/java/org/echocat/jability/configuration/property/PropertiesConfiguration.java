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

import org.echocat.jability.configuration.support.BaseConfiguration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "properties", propOrder = {"definitions", "definitionProviders"})
public class PropertiesConfiguration extends BaseConfiguration {

    private List<PropertyDefinitionConfiguration> _definitions;
    private List<PropertyDefinitionProviderConfiguration> _definitionProviders;

    @XmlElement(name = "definition")
    public List<PropertyDefinitionConfiguration> getDefinitions() {
        return _definitions;
    }

    public void setDefinitions(List<PropertyDefinitionConfiguration> definitions) {
        _definitions = definitions;
    }

    @XmlElement(name = "definitionProvider")
    public List<PropertyDefinitionProviderConfiguration> getDefinitionProviders() {
        return _definitionProviders;
    }

    public void setDefinitionProviders(List<PropertyDefinitionProviderConfiguration> definitionProviders) {
        _definitionProviders = definitionProviders;
    }

}
