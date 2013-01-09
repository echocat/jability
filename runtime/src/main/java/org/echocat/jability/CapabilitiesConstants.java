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

public interface CapabilitiesConstants {

    public static final String FALL_BACK_PROVIDER_ENABLED_NAME = CapabilitiesConstants.class.getPackage().getName() + ".fallBackProviderEnabled";
    public static final boolean FALL_BACK_PROVIDER_ENABLED_DEFAULT = false;

    public static final String AUTO_PROPAGATE_NAME = CapabilitiesConstants.class.getPackage().getName() + ".autoPropagate";
    public static final boolean AUTO_PROPAGATE_DEFAULT = true;

}
