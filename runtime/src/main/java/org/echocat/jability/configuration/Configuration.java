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

package org.echocat.jability.configuration;

import org.echocat.jability.configuration.capability.CapabilitiesConfiguration;
import org.echocat.jability.configuration.jmx.JmxConfiguration;
import org.echocat.jability.configuration.property.PropertiesConfiguration;
import org.echocat.jability.configuration.stage.StagesConfiguration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "configuration")
@XmlType(name = "configuration", propOrder = {"capabilities", "stages", "properties", "jmx"})
public class Configuration {

    private CapabilitiesConfiguration _capabilities;
    private StagesConfiguration _stages;
    private PropertiesConfiguration _properties;
    private JmxConfiguration _jmx;

    @XmlElement(name = "capabilities")
    public CapabilitiesConfiguration getCapabilities() {
        return _capabilities;
    }

    public void setCapabilities(CapabilitiesConfiguration capabilities) {
        _capabilities = capabilities;
    }

    @XmlElement(name = "stages")
    public StagesConfiguration getStages() {
        return _stages;
    }

    public void setStages(StagesConfiguration stages) {
        _stages = stages;
    }

    @XmlElement(name = "properties")
    public PropertiesConfiguration getProperties() {
        return _properties;
    }

    public void setProperties(PropertiesConfiguration properties) {
        _properties = properties;
    }

    @XmlElement(name = "jmx")
    public JmxConfiguration getJmx() {
        return _jmx;
    }

    public void setJmx(JmxConfiguration jmx) {
        _jmx = jmx;
    }

}
