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

import org.echocat.jability.property.Properties;
import org.echocat.jability.property.PropertyProvider;
import org.echocat.jability.stage.StageProvider;

import javax.annotation.Nonnull;

public interface Jability extends AutoCloseable {

    public static final String CURRENT_STAGE = "org.echocat.jability.stage.current";
    public static final String AVAILABLE_STAGES = "org.echocat.jability.stage.availables";
    public static final String AUTO_PROPAGATE_NAME = "org.echocat.jability.autoPropagate";

    @Nonnull
    public CapabilityProvider getCapabilityProvider();

    @Nonnull
    public Capabilities getCapabilities();

    @Nonnull
    public PropertyProvider getPropertyProvider();

    @Nonnull
    public Properties getProperties();

    @Nonnull
    public StageProvider getStageProvider();

}
