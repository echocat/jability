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

import org.echocat.jability.support.AccessType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public abstract class BaseValueDefinitionConfiguration extends BaseConfiguration {

    private Class<?> _fromType;
    private String _fromField;
    private List<AccessType> _accessTypes;

    @XmlAttribute(name = "fromType")
    public Class<?> getFromType() {
        return _fromType;
    }

    public void setFromType(Class<?> fromType) {
        _fromType = fromType;
    }

    @XmlAttribute(name = "fromField")
    public String getFromField() {
        return _fromField;
    }

    public void setFromField(String fromField) {
        _fromField = fromField;
    }

    @XmlElement(name = "accessType")
    public List<AccessType> getAccessTypes() {
        return _accessTypes;
    }

    public void setAccessTypes(List<AccessType> accessTypes) {
        _accessTypes = accessTypes;
    }

}
