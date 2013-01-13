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

import org.echocat.jability.Capability;
import org.echocat.jability.configuration.capability.CapabilityReferenceConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.regex.Pattern;

import static org.echocat.jability.support.DiscoverUtils.discoverStaticFieldValuesBy;
import static org.echocat.jability.support.DiscoverUtils.loadClassBy;

public class FieldBasedCapabilityProvider<T extends Capability<?>> extends BaseCapabilityProvider<T> {

    public FieldBasedCapabilityProvider(@Nonnull Class<? extends T> definitionType, @Nonnull Class<?> startFrom, @Nullable Class<?> stopAt, @Nullable Pattern fieldPattern, @Nullable AccessType... accessTypes) {
        super(discoverStaticFieldValuesBy(definitionType, startFrom, stopAt, fieldPattern, accessTypes));
    }

    public FieldBasedCapabilityProvider(@Nonnull Class<? extends T> definitionType, @Nonnull Class<?> startFrom, @Nullable Class<?> stopAt, @Nullable Pattern fieldPattern, @Nullable Collection<AccessType> accessTypes) {
        super(discoverStaticFieldValuesBy(definitionType, startFrom, stopAt, fieldPattern, accessTypes));
    }

    public FieldBasedCapabilityProvider(@Nullable ClassLoader classLoader, @Nonnull CapabilityReferenceConfiguration configuration) {
        // noinspection unchecked, RedundantCast
        this((Class<? extends T>) (Object) Capability.class, loadClassBy(classLoader, configuration.getFromType()), null, configuration.getFromField(), configuration.getAccessTypes());
    }

}
