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

import org.echocat.jability.configuration.support.BaseConfiguration;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import static java.lang.Boolean.TRUE;
import static java.lang.System.getProperty;
import static org.echocat.jability.CapabilitiesConstants.AUTO_PROPAGATE_DEFAULT;
import static org.echocat.jability.CapabilitiesConstants.AUTO_PROPAGATE_NAME;

@XmlType(name = "jmx")
public class JmxConfiguration extends BaseConfiguration {

    private static boolean isAutoPropagateEnabled() {
        final String value = getProperty(AUTO_PROPAGATE_NAME, Boolean.toString(AUTO_PROPAGATE_DEFAULT));
        return TRUE.toString().equalsIgnoreCase(value);
    }

    private boolean _propagate = isAutoPropagateEnabled();

    @XmlAttribute(name = "propagate")
    public boolean isPropagate() {
        return _propagate;
    }

    public void setPropagate(boolean propagate) {
        _propagate = propagate;
    }

}
