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

package org.echocat.jability.property;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Map;

@ThreadSafe
@Immutable
public class ImmutablePropertyCollection extends PropertyCollectionSupport {

    public ImmutablePropertyCollection(@Nullable Map<PropertyDefinition<?>, Object> propertyToValue) {
        super(propertyToValue);
    }

    public ImmutablePropertyCollection() {}

}
