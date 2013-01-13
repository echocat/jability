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

package org.echocat.jability.configuration.support;

import javax.xml.bind.annotation.XmlAttribute;

import static org.echocat.jability.support.DiscoverUtils.nameOf;

public abstract class TypeBasedConfiguration extends BaseConfiguration {

    private String _type;

    @XmlAttribute(name = "type", required = true)
    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    @Override
    public String toString() {
        return nameOf(this) + "(" + getType() + ")";
    }

}
