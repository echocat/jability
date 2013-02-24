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
import org.echocat.jomon.runtime.jaxb.PatternAdapter;

import javax.annotation.concurrent.NotThreadSafe;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.split;
import static org.echocat.jability.support.AccessType.valueOf;

@NotThreadSafe
public abstract class BaseReferenceConfiguration extends BaseConfiguration {

    private String _fromType;
    private Pattern _fromField;
    private List<AccessType> _accessTypes;

    @XmlAttribute(name = "fromType", required = true)
    public String getFromType() {
        return _fromType;
    }

    public void setFromType(String fromType) {
        _fromType = fromType;
    }

    @XmlAttribute(name = "fromField")
    @XmlJavaTypeAdapter(PatternAdapter.class)
    public Pattern getFromField() {
        return _fromField;
    }

    public void setFromField(Pattern fromField) {
        _fromField = fromField;
    }

    @XmlAttribute(name = "accessTypes")
    @XmlJavaTypeAdapter(AccessTypesAdapter.class)
    public List<AccessType> getAccessTypes() {
        return _accessTypes;
    }

    public void setAccessTypes(List<AccessType> accessTypes) {
        _accessTypes = accessTypes;
    }

    public static class AccessTypesAdapter extends XmlAdapter<String, List<AccessType>> {

        @Override
        public List<AccessType> unmarshal(String v) throws Exception {
            final List<AccessType> result;
            if (v != null) {
                final String[] plains = split(v, ",;\n ");
                result = new ArrayList<>(plains.length);
                for (String plain : plains) {
                    final String trimmedPlain = plain.trim();
                    if (!trimmedPlain.isEmpty()) {
                        final AccessType accessType = valueOf(trimmedPlain);
                        if (!result.contains(accessType)) {
                            result.add(accessType);
                        }
                    }
                }
            } else {
                result = null;
            }
            return result != null && !result.isEmpty() ? result : null;
        }

        @Override
        public String marshal(List<AccessType> v) throws Exception {
            final String result;
            if (v != null && !v.isEmpty()) {
                result = join(v, ",");
            } else {
                result = null;
            }
            return result;
        }

    }

}
