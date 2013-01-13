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

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

import static org.echocat.jability.configuration.ConfigurationConstants.SCHEMA_NAMESPACE;

public class ConfigurationNamespacePrefixMapper extends NamespacePrefixMapper {

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        final String result;
        if (SCHEMA_NAMESPACE.equals(namespaceUri)) {
            result = "";
        } else {
            result = suggestion;
        }
        return result;
    }
}
