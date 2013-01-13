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

import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static org.echocat.jomon.runtime.util.ResourceUtils.closeQuietly;

public class ConfigurationConstants {

    public static final String SCHEMA_NAMESPACE = "https://jability.echocat.org/schemas/jability.xsd";
    public static final String SCHEMA_XSD_LOCATION = "org/echocat/jability/jability-1.0.xsd";
    public static final Schema SCHEMA;

    static {
        final SchemaFactory schemaFactory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
        final InputStream is = ConfigurationConstants.class.getClassLoader().getResourceAsStream(SCHEMA_XSD_LOCATION);
        if (is == null){
            throw new IllegalStateException("There is no '" + SCHEMA_XSD_LOCATION + "' in classpath.");
        }
        try {
            SCHEMA = schemaFactory.newSchema(new StreamSource(is));
        } catch (SAXException e) {
            throw new RuntimeException("Could not load '" + SCHEMA_XSD_LOCATION + "'.", e);
        } finally {
            closeQuietly(is);
        }
    }

    private ConfigurationConstants() {}
}
