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

package org.echocat.jability.support;

import org.echocat.jability.CapabilityDefinition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.echocat.jability.support.AccessType.PUBLIC;

public class EnumBasedCapabilityDefinitionProvider<T extends Enum<T> & CapabilityDefinition<?>> extends FieldBasedCapabilityDefinitionProvider<T> {

    public EnumBasedCapabilityDefinitionProvider(@Nonnull Class<T> definitionType) {
        super(definitionType, PUBLIC);
    }

    public EnumBasedCapabilityDefinitionProvider(@Nullable Iterable<T> definitions) {
        super(definitions);
    }

}
