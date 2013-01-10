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

public abstract class IdBasedConfiguration extends BaseConfiguration {

    private String _id;

    @XmlAttribute(name = "id")
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    @Override
    public String toString() {
        return nameOf(this) + "(" + getId() + ")";
    }

}
