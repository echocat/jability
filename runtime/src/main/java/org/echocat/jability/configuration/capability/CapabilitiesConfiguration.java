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

package org.echocat.jability.configuration.capability;

import org.echocat.jability.configuration.support.TypeBasedConfiguration;

import javax.annotation.concurrent.NotThreadSafe;
import javax.xml.bind.annotation.XmlType;

import static org.echocat.jability.configuration.ConfigurationConstants.SCHEMA_NAMESPACE;

@XmlType(name = "capabilities", namespace = SCHEMA_NAMESPACE)
@NotThreadSafe
public class CapabilitiesConfiguration extends TypeBasedConfiguration implements UnderCapabilitiesRootConfiguration {}
