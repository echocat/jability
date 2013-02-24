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
import org.echocat.jability.configuration.capability.CapabilityReferenceConfiguration;
import org.echocat.jability.configuration.capability.CapabilityProviderConfiguration;
import org.echocat.jability.configuration.jmx.JmxRootConfiguration;
import org.echocat.jability.configuration.property.PropertiesRootConfiguration;
import org.echocat.jability.configuration.property.PropertyReferenceConfiguration;
import org.echocat.jability.configuration.property.PropertyProviderConfiguration;
import org.echocat.jability.configuration.stage.*;
import org.echocat.jability.direct.DirectJability;
import org.w3c.dom.Node;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

import static java.lang.System.getProperty;
import static javax.xml.bind.JAXBContext.newInstance;
import static javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.echocat.jability.configuration.ConfigurationConstants.SCHEMA;

public class ConfigurationMarshaller {

    public static final String CONFIGURATION_PROPERTY_NAME = "org.echocat.jability.configuration";
    public static final String DEFAULT_CONFIGURATION_LOCATION = "classpath:META-INF/jability.xml";

    private static final JAXBContext JAXB_CONTEXT;
    private static final Object NAMESPACE_PREFIX_MAPPER;

    static {
        try {
            JAXB_CONTEXT = newInstance(
                Configuration.class,

                CapabilitiesRootConfiguration.class,
                CapabilityReferenceConfiguration.class,
                CapabilityProviderConfiguration.class,

                JmxRootConfiguration.class,

                PropertiesRootConfiguration.class,
                PropertyReferenceConfiguration.class,
                PropertyProviderConfiguration.class,

                AvailableStageConfiguration.class,
                StageConfiguration.class,
                StageReferenceConfiguration.class,
                StageProviderConfiguration.class,
                StagesRootConfiguration.class
            );
        } catch (Exception e) {
            throw new ConfigurationError("Could not create jaxb context.", e);
        }
        Object namespacePrefixMapper;
        try {
            namespacePrefixMapper = ConfigurationMarshaller.class.getClassLoader().loadClass(ConfigurationMarshaller.class.getPackage().getName() + ".ConfigurationNamespacePrefixMapper").newInstance();
        } catch (Throwable ignored) {
            namespacePrefixMapper = null;
        }
        NAMESPACE_PREFIX_MAPPER = namespacePrefixMapper;
    }

    private static final String CLASSPATH_PREFIX = "classpath:";

    @Nullable
    public static Configuration unmarshal(@Nullable String content) {
        try {
            return isEmpty(content) ? null :  (Configuration) unmarshallerFor(content).unmarshal(new StringReader(content));
        } catch (JAXBException e) {
            throw new ConfigurationException("Could not unmarshall: " + content, e);
        }
    }

    @Nonnull
    public static Configuration unmarshal(@Nullable URL url) throws IOException {
        final Configuration result;
        if (url != null) {
            final Unmarshaller unmarshaller = unmarshallerFor(url);
            try (final InputStream is = url.openStream()) {
                try (final Reader reader = new InputStreamReader(is)) {
                    try {
                        result = (Configuration) unmarshaller.unmarshal(reader);
                    } catch (JAXBException e) {
                        throw new IOException("Could not unmarshal " + url + ".", e);
                    }
                }
            }
        } else {
            result = null;
        }
        return result;
    }

    @Nullable
    public static Configuration unmarshal(@Nullable Reader reader) throws IOException {
        try {
            return reader != null ? (Configuration) unmarshallerFor(reader).unmarshal(reader) : null;
        } catch (JAXBException e) {
            throw new IOException("Could not unmarshal " + reader + ".", e);
        }
    }

    @Nullable
    public static Configuration unmarshal(@Nullable Node rulesElement) {
        try {
            final JAXBElement<Configuration> jaxbElement = rulesElement != null ? unmarshallerFor(rulesElement).unmarshal(rulesElement, Configuration.class) : null;
            return jaxbElement != null ? jaxbElement.getValue() : null;
        } catch (JAXBException e) {
            throw new ConfigurationException("Could not unmarshal " + rulesElement + ".", e);
        }
    }

    @Nonnull
    protected static Unmarshaller unmarshallerFor(@Nullable Object element) {
        final Unmarshaller unmarshaller;
        try {
            unmarshaller = JAXB_CONTEXT.createUnmarshaller();
            unmarshaller.setSchema(SCHEMA);
        } catch (JAXBException e) {
            throw new ConfigurationException("Could not create unmarshaller to unmarshal " + element + ".", e);
        }
        return unmarshaller;
    }

    public static void marshal(@Nonnull Configuration configuration, @Nonnull Writer to) throws IOException {
        try {
            marshallerFor(configuration).marshal(configuration, to);
        } catch (JAXBException e) {
            throw new IOException("Could not marshal " + configuration + " to " + to + ".", e);
        }
    }

    @Nullable
    public static String marshal(@Nullable Configuration configuration) {
        final String result;
        if (configuration != null) {
            final StringWriter to = new StringWriter();
            try {
                marshallerFor(configuration).marshal(configuration, to);
            } catch (JAXBException e) {
                throw new ConfigurationException("Could not marshal " + configuration + ".", e);
            }
            result = to.toString();
        } else {
            result = null;
        }
        return result;
    }

    @Nonnull
    private static Marshaller marshallerFor(@Nonnull Configuration configuration) throws ConfigurationException {
        final Marshaller marshaller;
        try {
            marshaller = JAXB_CONTEXT.createMarshaller();
            marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);
            marshaller.setSchema(SCHEMA);
            if (NAMESPACE_PREFIX_MAPPER != null) {
                marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", NAMESPACE_PREFIX_MAPPER);
            }
        } catch (Exception e) {
            throw new ConfigurationException("Could not create marshaller to marshal " + configuration + ".", e);
        }
        return marshaller;
    }

    @Nullable
    public static URL findDefaultConfigurationLocation() throws ConfigurationException {
        final String plain = getProperty(CONFIGURATION_PROPERTY_NAME, DEFAULT_CONFIGURATION_LOCATION);
        URL result;
        if (plain.trim().isEmpty()) {
            result = null;
        } else if (plain.startsWith(CLASSPATH_PREFIX) && plain.length() > CLASSPATH_PREFIX.length() + 1) {
            result = findConfigurationLocationInClasspathBy(plain.substring(CLASSPATH_PREFIX.length()));
            if (!DEFAULT_CONFIGURATION_LOCATION.equals(plain) && result == null) {
                throw new ConfigurationException("The file '" + plain + "' was configured bot could not be found in classpath.");
            }
        } else {
            try {
                result = new URL(plain);
            } catch (MalformedURLException ignored) {
                final File file = new File(plain);
                if (!file.isFile()) {
                    throw new ConfigurationException("The file '" + plain + "' was configured bot could not be found in file system.");
                }
                try {
                    result = file.toURI().toURL();
                } catch (MalformedURLException ignored2) {
                    result = null;
                }
            }
        }
        return result;
    }

    @Nullable
    private static URL findConfigurationLocationInClasspathBy(@Nonnull String plain) throws ConfigurationException {
        final Enumeration<URL> resources;
        try {
            resources = DirectJability.class.getClassLoader().getResources(plain);
        } catch (IOException e) {
            throw new ConfigurationException("Could not load resources '" + plain + "' from classpath.", e);
        }
        final URL result;
        if (resources.hasMoreElements()) {
            result = resources.nextElement();
            if (resources.hasMoreElements()) {
                final StringBuilder sb = new StringBuilder();
                sb.append("WARN: Found more then one files named '").append(plain).append("' in classpath:");
                sb.append("\n\t* ").append(result);
                do {
                    sb.append("\n\t* ").append(resources.nextElement());
                } while (resources.hasMoreElements());
                sb.append("\nJability will take the first one. Fix the classpath to prevent mysterious problems.");
                // noinspection UseOfSystemOutOrSystemErr
                System.err.println(sb);
            }
        } else {
            result = null;
        }
        return result;
    }

    private ConfigurationMarshaller() {}
    protected static final ConfigurationMarshaller INSTANCE = new ConfigurationMarshaller();

}
