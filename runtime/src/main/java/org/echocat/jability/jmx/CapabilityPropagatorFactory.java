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

package org.echocat.jability.jmx;

import org.echocat.jability.Jability;
import org.echocat.jability.configuration.Configuration;
import org.echocat.jability.configuration.jmx.JmxRootConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import static org.echocat.jability.configuration.jmx.JmxRootConfiguration.isAutoPropagateEnabledBasedOn;

@ThreadSafe
@Immutable
public class CapabilityPropagatorFactory {

    @Nullable
    public CapabilityPropagator createIfPossibleBy(@Nonnull Jability jability, @Nullable Configuration configuration) {
        return createIfPossibleBy(jability, configuration != null ? configuration.getJmx() : null);
    }

    @Nullable
    public CapabilityPropagator createIfPossibleBy(@Nonnull Jability jability, @Nullable JmxRootConfiguration configuration) {
        return isAutoPropagateEnabledBasedOn(configuration) ? new CapabilityPropagator(jability) : null;
    }

}
