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

import org.echocat.jability.Capabilities;
import org.echocat.jability.CapabilityProvider;
import org.echocat.jability.configuration.capability.*;
import org.echocat.jability.configuration.capability.UnderCapabilitiesRootConfiguration.RespectSystemCapabilities;
import org.echocat.jability.configuration.capability.UnderCapabilitiesRootConfiguration.RespectSystemProvider;
import org.echocat.jability.configuration.jmx.JmxRootConfiguration;
import org.echocat.jability.configuration.jmx.UnderJmxRootConfiguration;
import org.echocat.jability.configuration.jmx.UnderJmxRootConfiguration.Propagate;
import org.echocat.jability.configuration.property.*;
import org.echocat.jability.configuration.property.UnderPropertiesRootConfiguration.RespectSystemProperties;
import org.echocat.jability.configuration.stage.*;
import org.echocat.jability.configuration.stage.UnderStagesRootConfiguration.CurrentId;
import org.echocat.jability.property.Properties;
import org.echocat.jability.property.PropertyProvider;
import org.echocat.jability.stage.StageProvider;
import org.echocat.jability.support.AccessType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static org.echocat.jomon.runtime.CollectionUtils.asList;

public class Configurations {

    @Nonnull
    public static Configuration configuration(@Nullable UnderConfiguration... what) {
        return new Configuration().with(what);
    }

    @Nonnull
    public static CapabilitiesRootConfiguration capabilitiesConfiguration(@Nullable UnderCapabilitiesRootConfiguration... what) {
        return new CapabilitiesRootConfiguration().with(what);
    }

    @Nonnull
    public static PropertiesRootConfiguration propertiesConfiguration(@Nullable UnderPropertiesRootConfiguration... what) {
        return new PropertiesRootConfiguration().with(what);
    }

    @Nonnull
    public static StagesRootConfiguration stagesConfiguration(@Nullable UnderStagesRootConfiguration... what) {
        return new StagesRootConfiguration().with(what);
    }

    @Nonnull
    public static JmxRootConfiguration jmxConfiguration(@Nullable UnderJmxRootConfiguration... what) {
        return new JmxRootConfiguration().with(what);
    }

    @Nonnull
    public static CapabilitiesConfiguration capabilities(@Nonnull Class<? extends Capabilities> type) {
        return capabilities(type.getName());
    }

    @Nonnull
    public static CapabilitiesConfiguration capabilities(@Nonnull String type) {
        final CapabilitiesConfiguration configuration = new CapabilitiesConfiguration();
        configuration.setType(type);
        return configuration;
    }

    @Nonnull
    public static CapabilityProviderConfiguration capabilityProvider(@Nonnull Class<? extends CapabilityProvider> type) {
        return capabilityProvider(type.getName());
    }

    @Nonnull
    public static CapabilityProviderConfiguration capabilityProvider(@Nonnull String type) {
        final CapabilityProviderConfiguration configuration = new CapabilityProviderConfiguration();
        configuration.setType(type);
        return configuration;
    }

    @Nonnull
    public static CapabilityReferenceConfiguration capabilityReference(@Nonnull Class<?> fromType, @Nullable AccessType... accessTypes) {
        return capabilityReference(fromType.getName(), (Pattern) null, accessTypes);
    }

    @Nonnull
    public static CapabilityReferenceConfiguration capabilityReference(@Nonnull Class<?> fromType, @Nullable Pattern fromField, @Nullable AccessType... accessTypes) {
        return capabilityReference(fromType.getName(), fromField, accessTypes);
    }

    @Nonnull
    public static CapabilityReferenceConfiguration capabilityReference(@Nonnull Class<?> fromType, @Nullable String fromField, @Nullable AccessType... accessTypes) {
        return capabilityReference(fromType, fromField != null ? compile(fromField) : null, accessTypes);
    }

    @Nonnull
    public static CapabilityReferenceConfiguration capabilityReference(@Nonnull String fromType, @Nullable AccessType... accessTypes) {
        return capabilityReference(fromType, (Pattern) null, accessTypes);
    }

    @Nonnull
    public static CapabilityReferenceConfiguration capabilityReference(@Nonnull String fromType, @Nullable String fromField, @Nullable AccessType... accessTypes) {
        return capabilityReference(fromType, fromField != null ? compile(fromField) : null, accessTypes);
    }

    @Nonnull
    public static CapabilityReferenceConfiguration capabilityReference(@Nonnull String fromType, @Nullable Pattern fromField, @Nullable AccessType... accessTypes) {
        final CapabilityReferenceConfiguration configuration = new CapabilityReferenceConfiguration();
        configuration.setFromType(fromType);
        configuration.setFromField(fromField);
        configuration.setAccessTypes(accessTypes != null && accessTypes.length > 0 ? asList(accessTypes) : null);
        return configuration;
    }

    @Nonnull
    public static RespectSystemCapabilities respectSystemCapabilities(boolean respect) {
        return new RespectSystemCapabilities(respect);
    }

    @Nonnull
    public static RespectSystemCapabilities respectSystemCapabilities() {
        return respectSystemCapabilities(true);
    }

    @Nonnull
    public static RespectSystemProvider respectSystemCapabilityProviders(boolean respect) {
        return new RespectSystemProvider(respect);
    }

    @Nonnull
    public static RespectSystemProvider respectSystemCapabilityProviders() {
        return respectSystemCapabilityProviders(true);
    }

    @Nonnull
    public static Propagate propagateToJmx(boolean propagate) {
        return new Propagate(propagate);
    }

    @Nonnull
    public static Propagate propagateToJmx() {
        return propagateToJmx(true);
    }

    private Configurations() {}

    @Nonnull
    public static RespectSystemProperties respectSystemProperties(boolean respect) {
        return new RespectSystemProperties(respect);
    }

    @Nonnull
    public static RespectSystemProperties respectSystemProperties() {
        return respectSystemProperties(true);
    }

    @Nonnull
    public static UnderPropertiesRootConfiguration.RespectSystemProvider respectSystemPropertyProviders(boolean respect) {
        return new UnderPropertiesRootConfiguration.RespectSystemProvider(respect);
    }

    @Nonnull
    public static UnderPropertiesRootConfiguration.RespectSystemProvider respectSystemPropertyProviders() {
        return respectSystemPropertyProviders(true);
    }

    @Nonnull
    public static PropertiesConfiguration properties(@Nonnull Class<? extends Properties> type) {
        return properties(type.getName());
    }

    @Nonnull
    public static PropertiesConfiguration properties(@Nonnull String type) {
        final PropertiesConfiguration configuration = new PropertiesConfiguration();
        configuration.setType(type);
        return configuration;
    }

    @Nonnull
    public static PropertyProviderConfiguration propertyProvider(@Nonnull Class<? extends PropertyProvider> type) {
        return propertyProvider(type.getName());
    }

    @Nonnull
    public static PropertyProviderConfiguration propertyProvider(@Nonnull String type) {
        final PropertyProviderConfiguration configuration = new PropertyProviderConfiguration();
        configuration.setType(type);
        return configuration;
    }

    @Nonnull
    public static PropertyReferenceConfiguration propertyReference(@Nonnull Class<?> fromType, @Nullable Pattern fromField, @Nullable AccessType... accessTypes) {
        return propertyReference(fromType.getName(), fromField, accessTypes);
    }

    @Nonnull
    public static PropertyReferenceConfiguration propertyReference(@Nonnull Class<?> fromType, @Nullable String fromField, @Nullable AccessType... accessTypes) {
        return propertyReference(fromType, fromField != null ? compile(fromField) : null, accessTypes);
    }

    @Nonnull
    public static PropertyReferenceConfiguration propertyReference(@Nonnull Class<?> fromType, @Nullable AccessType... accessTypes) {
        return propertyReference(fromType, (Pattern) null, accessTypes);
    }

    @Nonnull
    public static PropertyReferenceConfiguration propertyReference(@Nonnull String fromType, @Nullable AccessType... accessTypes) {
        return propertyReference(fromType, (Pattern) null, accessTypes);
    }

    @Nonnull
    public static PropertyReferenceConfiguration propertyReference(@Nonnull String fromType, @Nullable String fromField, @Nullable AccessType... accessTypes) {
        return propertyReference(fromType, fromField != null ? compile(fromField) : null, accessTypes);
    }

    @Nonnull
    public static PropertyReferenceConfiguration propertyReference(@Nonnull String fromType, @Nullable Pattern fromField, @Nullable AccessType... accessTypes) {
        final PropertyReferenceConfiguration configuration = new PropertyReferenceConfiguration();
        configuration.setFromType(fromType);
        configuration.setFromField(fromField);
        configuration.setAccessTypes(accessTypes != null && accessTypes.length > 0 ? asList(accessTypes) : null);
        return configuration;
    }

    @Nonnull
    public static UnderStagesRootConfiguration.RespectSystemProvider respectSystemStageProviders(boolean respect) {
        return new UnderStagesRootConfiguration.RespectSystemProvider(respect);
    }

    @Nonnull
    public static UnderStagesRootConfiguration.RespectSystemProvider respectSystemStageProviders() {
        return respectSystemStageProviders(true);
    }

    @Nonnull
    public static CurrentId currentStageId(@Nullable String value) {
        return new CurrentId(value);
    }

    @Nonnull
    public static StageConfiguration stage(@Nonnull String id, int priority) {
        final StageConfiguration configuration = new StageConfiguration();
        configuration.setId(id);
        configuration.setPriority(priority);
        return configuration;
    }

    @Nonnull
    public static StageReferenceConfiguration stageReference(@Nonnull Class<?> fromType, @Nullable AccessType... accessTypes) {
        return stageReference(fromType, (Pattern) null, accessTypes);
    }

    @Nonnull
    public static StageReferenceConfiguration stageReference(@Nonnull Class<?> fromType, @Nullable String fromField, @Nullable AccessType... accessTypes) {
        return stageReference(fromType, fromField != null ? compile(fromField) : null, accessTypes);
    }

    @Nonnull
    public static StageReferenceConfiguration stageReference(@Nonnull Class<?> fromType, @Nullable Pattern fromField, @Nullable AccessType... accessTypes) {
        return stageReference(fromType.getName(), fromField, accessTypes);
    }

    @Nonnull
    public static StageReferenceConfiguration stageReference(@Nonnull String fromType, @Nullable String fromField, @Nullable AccessType... accessTypes) {
        return stageReference(fromType, fromField != null ? compile(fromField) : null, accessTypes);
    }

    @Nonnull
    public static StageReferenceConfiguration stageReference(@Nonnull String fromType, @Nullable AccessType... accessTypes) {
        return stageReference(fromType, (Pattern) null, accessTypes);
    }

    @Nonnull
    public static StageReferenceConfiguration stageReference(@Nonnull String fromType, @Nullable Pattern fromField, @Nullable AccessType... accessTypes) {
        final StageReferenceConfiguration configuration = new StageReferenceConfiguration();
        configuration.setFromType(fromType);
        configuration.setFromField(fromField);
        configuration.setAccessTypes(accessTypes != null && accessTypes.length > 0 ? asList(accessTypes) : null);
        return configuration;
    }

    @Nonnull
    public static AvailableStageConfiguration availableStage(@Nonnull String id) {
        final AvailableStageConfiguration configuration = new AvailableStageConfiguration();
        configuration.setId(id);
        return configuration;
    }

    @Nonnull
    public static StageProviderConfiguration stageProvider(@Nonnull Class<? extends StageProvider> type) {
        return stageProvider(type.getName());
    }

    @Nonnull
    public static StageProviderConfiguration stageProvider(@Nonnull String type) {
        final StageProviderConfiguration configuration = new StageProviderConfiguration();
        configuration.setType(type);
        return configuration;
    }

}
