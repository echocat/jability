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

import org.echocat.jability.configuration.support.IdBasedConfiguration;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "stage")
public class StageConfiguration extends IdBasedConfiguration {

    private int _priority;

    @XmlAttribute(name = "priority")
    public int getPriority() {
        return _priority;
    }

    public void setPriority(int priority) {
        _priority = priority;
    }

}
