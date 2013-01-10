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

package org.echocat.jability.configuration.stage;

import org.echocat.jability.configuration.support.BaseConfiguration;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "stages", propOrder = {"current", "stages", "definitions", "availables", "providers"})
public class StagesConfiguration extends BaseConfiguration {

    private String _current;
    private List<StageConfiguration> _stages;
    private List<StageDefinitionConfiguration> _definitions;
    private List<AvailableStageConfiguration> _availables;
    private List<StageProviderConfiguration> _providers;

    @XmlAttribute(name = "current", required = false)
    public String getCurrent() {
        return _current;
    }

    public void setCurrent(String current) {
        _current = current;
    }

    @XmlElement(name = "stage")
    public List<StageConfiguration> getStages() {
        return _stages;
    }

    public void setStages(List<StageConfiguration> stages) {
        _stages = stages;
    }

    @XmlElement(name = "definition")
    public List<StageDefinitionConfiguration> getDefinitions() {
        return _definitions;
    }

    public void setDefinitions(List<StageDefinitionConfiguration> definitions) {
        _definitions = definitions;
    }

    @XmlElement(name = "available", required = false)
    public List<AvailableStageConfiguration> getAvailables() {
        return _availables;
    }

    public void setAvailables(List<AvailableStageConfiguration> availables) {
        _availables = availables;
    }

    @XmlElement(name = "provider", required = false)
    public List<StageProviderConfiguration> getProviders() {
        return _providers;
    }

    public void setProviders(List<StageProviderConfiguration> providers) {
        _providers = providers;
    }

}
