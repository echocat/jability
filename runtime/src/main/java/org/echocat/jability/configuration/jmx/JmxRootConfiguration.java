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

package org.echocat.jability.configuration.jmx;

import org.echocat.jability.configuration.UnderConfiguration;
import org.echocat.jability.configuration.jmx.UnderJmxRootConfiguration.Propagate;
import org.echocat.jability.configuration.support.BaseConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import static java.lang.Boolean.TRUE;
import static java.lang.System.getProperty;
import static org.echocat.jability.Jability.AUTO_PROPAGATE_OVER_JMX;
import static org.echocat.jability.configuration.ConfigurationConstants.SCHEMA_NAMESPACE;
import static org.echocat.jomon.runtime.CollectionUtils.asList;

@XmlType(name = "jmxRoot", namespace = SCHEMA_NAMESPACE)
public class JmxRootConfiguration extends BaseConfiguration implements UnderConfiguration {

    @Nullable
    public static Boolean getAutoPropagatePerDefaultState() {
        final String value = getProperty(AUTO_PROPAGATE_OVER_JMX);
        return value != null ? TRUE.toString().equalsIgnoreCase(value) : null;
    }

    public static boolean isAutoPropagateEnabledBasedOn(@Nullable JmxRootConfiguration configuration) {
        final Boolean defaultState = getAutoPropagatePerDefaultState();
        final boolean result;
        if (defaultState != null) {
            result = defaultState;
        } else {
            result = configuration == null || configuration.isPropagate();
        }
        return result;
    }

    private boolean _propagate = true;

    @XmlAttribute(name = "propagate")
    public boolean isPropagate() {
        return _propagate;
    }

    public void setPropagate(boolean propagate) {
        _propagate = propagate;
    }

    @Nonnull
    public JmxRootConfiguration with(@Nullable UnderJmxRootConfiguration... what) {
        return with(asList(what));
    }

    @Nonnull
    public JmxRootConfiguration with(@Nullable Iterable<UnderJmxRootConfiguration> what) {
        if (what != null) {
            for (UnderJmxRootConfiguration configuration : what) {
                if (configuration instanceof Propagate) {
                    final Propagate propagate = (Propagate) configuration;
                    _propagate = propagate.getValue();
                } else {
                    throw new IllegalArgumentException("Could not handle configuration: "  + configuration);
                }
            }
        }
        return this;
    }

}
