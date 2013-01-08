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

package org.echocat.jability;

import org.echocat.jomon.runtime.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

import static org.echocat.jability.TestCapabilityDefinition.values;

public class TestCapabilityDefinitionProvider implements CapabilityDefinitionProvider {

    @Nullable
    @Override
    public <ID extends CapabilityDefinition<?>> ID provideBy(@Nonnull Class<ID> idType, @Nonnull String value) {
        ID result;
        if (idType.isAssignableFrom(TestCapabilityDefinition.class)) {
            result = null;
            for (TestCapabilityDefinition capabilityDefinition : values()) {
                if (capabilityDefinition.get().equals(value)) {
                    // noinspection unchecked
                    result = (ID) capabilityDefinition;
                    break;
                }
            }
        } else {
            result = null;
        }
        return result;
    }

    @Override
    public Iterator<CapabilityDefinition<?>> iterator() {
        return CollectionUtils.<CapabilityDefinition<?>>asIterator(values());
    }
}
