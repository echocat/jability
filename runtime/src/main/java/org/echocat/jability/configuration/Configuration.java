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

import org.echocat.jability.configuration.capability.CapabilitiesRootConfiguration;
import org.echocat.jability.configuration.jmx.JmxRootConfiguration;
import org.echocat.jability.configuration.property.PropertiesRootConfiguration;
import org.echocat.jability.configuration.stage.StagesRootConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static org.echocat.jomon.runtime.CollectionUtils.asList;

@XmlRootElement(name = "configuration")
@XmlType(name = "configuration", propOrder = {"capabilities", "stages", "properties", "jmx"})
public class Configuration {

    private CapabilitiesRootConfiguration _capabilities;
    private StagesRootConfiguration _stages;
    private PropertiesRootConfiguration _properties;
    private JmxRootConfiguration _jmx;

    @XmlElement(name = "capabilities")
    public CapabilitiesRootConfiguration getCapabilities() {
        return _capabilities;
    }

    public void setCapabilities(CapabilitiesRootConfiguration capabilities) {
        _capabilities = capabilities;
    }

    @XmlElement(name = "stages")
    public StagesRootConfiguration getStages() {
        return _stages;
    }

    public void setStages(StagesRootConfiguration stages) {
        _stages = stages;
    }

    @XmlElement(name = "properties")
    public PropertiesRootConfiguration getProperties() {
        return _properties;
    }

    public void setProperties(PropertiesRootConfiguration properties) {
        _properties = properties;
    }

    @XmlElement(name = "jmx")
    public JmxRootConfiguration getJmx() {
        return _jmx;
    }

    public void setJmx(JmxRootConfiguration jmx) {
        _jmx = jmx;
    }

    @Nonnull
    public Configuration with(@Nullable UnderConfiguration... what) {
        return with(asList(what));
    }

    @Nonnull
    public Configuration with(@Nullable Iterable<UnderConfiguration> what) {
        if (what != null) {
            for (UnderConfiguration configuration : what) {
                if (configuration instanceof CapabilitiesRootConfiguration) {
                    _capabilities = (CapabilitiesRootConfiguration) configuration;
                } else if (configuration instanceof StagesRootConfiguration) {
                    _stages = (StagesRootConfiguration) configuration;
                } else if (configuration instanceof PropertiesRootConfiguration) {
                    _properties = (PropertiesRootConfiguration) configuration;
                } else if (configuration instanceof JmxRootConfiguration) {
                    _jmx = (JmxRootConfiguration) configuration;
                } else {
                    throw new IllegalArgumentException("Could not handle configuration: "  + configuration);
                }
            }
        }
        return this;
    }

}
