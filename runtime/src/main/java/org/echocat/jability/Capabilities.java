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

import org.echocat.jability.jmx.CapabilityPropagater;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;

import static java.lang.Boolean.TRUE;
import static java.lang.System.getProperty;
import static org.echocat.jability.CapabilitiesConstants.AUTO_PROPAGATE_DEFAULT;
import static org.echocat.jability.CapabilitiesConstants.AUTO_PROPAGATE_NAME;
import static org.echocat.jability.CompoundCapabilityDefinitionProvider.capabilityDefinitionProvider;
import static org.echocat.jability.CompoundCapabilityProvider.capabilityProvider;
import static org.echocat.jability.value.Values.valueDefinitionComparator;

public class Capabilities {

    private static CapabilityDefinitionProvider c_capabilityDefinitionProvider = capabilityDefinitionProvider();
    private static CapabilityProvider c_capabilityProvider = capabilityProvider();

    static {
        if (isAutoPropagateEnabled()) {
            CapabilityPropagater.registerSystemPropagater();
        }
    }

    /**
     * @throws IllegalArgumentException if the provided <code>valueType</code> does not match the type of the found definition.
     */
    @Nonnull
    public static <V> CapabilityDefinition<V> capabilityDefinition(@Nonnull Class<V> valueType, @Nonnull String id) throws IllegalArgumentException {
        final CapabilityDefinition<V> definition = c_capabilityDefinitionProvider.provideBy(valueType, id);
        return definition != null ? definition : newCapabilityDefinition(valueType, id);
    }

    /**
     * @throws IllegalArgumentException if no capability could be found for <code>definition</code>.
     */
    @Nonnull
    public static <V> Capability<V> capability(@Nonnull CapabilityDefinition<V> definition) throws IllegalArgumentException {
        final Capability<V> capability = c_capabilityProvider.provideBy(definition);
        return capability;
    }

    /**
     * @throws IllegalArgumentException if the provided <code>valueType</code> does not match the type of the definition of the found capability or no
     * capability could be found for the given <code>id</code>.
     */
    @Nonnull
    public static <V> Capability<V> capability(@Nonnull Class<V> valueType, @Nonnull String id) throws IllegalArgumentException {
        final CapabilityDefinition<V> definition = capabilityDefinition(valueType, id);
        return definition != null ? capability(definition) : null;
    }

    /**
     * @throws IllegalArgumentException if no capability could be found for the given <code>definition</code>.
     */
    public static boolean isEnabled(@Nonnull CapabilityDefinition<Boolean> definition) throws IllegalArgumentException {
        final Capability<Boolean> capability = capability(definition);
        final Boolean value = capability.get();
        final boolean result;
        if (value != null) {
            result = value;
        } else {
            final Boolean defaultValue = capability.getDefinition().getDefaultValue();
            result = defaultValue != null ? defaultValue : false;
        }
        return result;
    }

    /**
     * @throws IllegalArgumentException if the <code>valueType</code> of the found definition does not match <code>{@link Boolean}</code> or no
     * capability could be found for the given <code>id</code>.
     */
    public static boolean isEnabled(@Nonnull String id) throws IllegalArgumentException {
        final CapabilityDefinition<Boolean> definition = capabilityDefinition(Boolean.class, id);
        return isEnabled(definition);
    }

    /**
     * @throws IllegalArgumentException if no capability could be found for the given <code>definition</code>.
     */
    @Nullable
    public static <V> V getValueOf(@Nonnull CapabilityDefinition<V> definition) throws IllegalArgumentException {
        return getValueOf(definition, definition.getDefaultValue());
    }

    /**
     * @throws IllegalArgumentException if no capability could be found for the given <code>definition</code>.
     */
    @Nullable
    public static <V> V getValueOf(@Nonnull CapabilityDefinition<V> definition, @Nullable V defaultValue) throws IllegalArgumentException {
        final Capability<V> capability = capability(definition);
        final V value = capability.get();
        final V result;
        if (value != null) {
            result = value;
        } else {
            result = defaultValue;
            if (result == null && !definition.isNullable()) {
                throw new NullPointerException("The capability '" + definition.getId() + "' is currently null.");
            }
        }
        return result;
    }

    /**
     * @throws IllegalArgumentException if the provided <code>valueType</code> does not match the type of the definition of the found capability or no
     * capability could be found for the given <code>id</code>.
     */
    @Nullable
    public static <V> V getValueOf(@Nonnull Class<V> valueType, @Nonnull String id) throws IllegalArgumentException {
        final CapabilityDefinition<V> definition = capabilityDefinition(valueType, id);
        return getValueOf(definition, definition.getDefaultValue());
    }

    /**
     * @throws IllegalArgumentException if the provided <code>valueType</code> does not match the type of the definition of the found capability or no
     * capability could be found for the given <code>id</code>.
     */
    @Nullable
    public static <V> V getValueOf(@Nonnull Class<V> valueType, @Nonnull String id, @Nullable V defaultValue) throws IllegalArgumentException {
        final CapabilityDefinition<V> definition = capabilityDefinition(valueType, id);
        return getValueOf(definition, defaultValue);
    }

    @Nonnull
    public static CapabilityDefinitionProvider getCapabilityDefinitionProvider() {
        return c_capabilityDefinitionProvider;
    }

    public static void setCapabilityDefinitionProvider(@Nullable CapabilityDefinitionProvider capabilityDefinitionProvider) {
        c_capabilityDefinitionProvider = capabilityDefinitionProvider != null ? capabilityDefinitionProvider : capabilityDefinitionProvider();
    }

    @Nonnull
    public static CapabilityProvider getCapabilityProvider() {
        return c_capabilityProvider;
    }

    public static void setCapabilityProvider(@Nullable CapabilityProvider capabilityProvider) {
        c_capabilityProvider = capabilityProvider != null ? capabilityProvider : capabilityProvider();
    }

    public static boolean isAutoPropagateEnabled() {
        final String value = getProperty(AUTO_PROPAGATE_NAME, Boolean.toString(AUTO_PROPAGATE_DEFAULT));
        return TRUE.toString().equalsIgnoreCase(value);
    }

    @Nonnull
    public static <V> CapabilityDefinition<V> newCapabilityDefinition(@Nonnull Class<? extends V> valueType, @Nonnull String id, boolean nullable, @Nullable V defaultValue) {
        return new CapabilityDefinition.Impl<>(valueType, id, nullable, defaultValue);
    }

    @Nonnull
    public static <V> CapabilityDefinition<V> newCapabilityDefinition(@Nonnull Class<? extends V> valueType, @Nonnull String id, boolean nullable) {
        return newCapabilityDefinition(valueType, id, nullable, null);
    }

    @Nonnull
    public static <V> CapabilityDefinition<V> newCapabilityDefinition(@Nonnull Class<? extends V> valueType, @Nonnull String id) {
        return newCapabilityDefinition(valueType, id, true);
    }

    @Nonnull
    public static <V> CapabilityDefinition<V> newCapabilityDefinition(@Nonnull Class<? extends V> valueType, @Nonnull Class<?> basedOn, @Nonnull String subId, boolean nullable, @Nullable V defaultValue) {
        return new CapabilityDefinition.Impl<>(valueType, basedOn, subId, nullable, defaultValue);
    }

    @Nonnull
    public static <V> CapabilityDefinition<V> newCapabilityDefinition(@Nonnull Class<? extends V> valueType, @Nonnull Class<?> basedOn, @Nonnull String subId, boolean nullable) {
        return newCapabilityDefinition(valueType, basedOn, subId, nullable, null);
    }

    @Nonnull
    public static <V> CapabilityDefinition<V> newCapabilityDefinition(@Nonnull Class<? extends V> valueType, @Nonnull Class<?> basedOn, @Nonnull String subId) {
        return newCapabilityDefinition(valueType, basedOn, subId, true);
    }

    @Nonnull
    public static Comparator<CapabilityDefinition<?>> capabilityDefinitionComparator() {
        return valueDefinitionComparator();
    }

    private Capabilities() {}

}
