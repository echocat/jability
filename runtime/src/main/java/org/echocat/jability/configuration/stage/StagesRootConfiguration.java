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

package org.echocat.jability.configuration.stage;

import org.echocat.jability.configuration.ConfigurationException;
import org.echocat.jability.configuration.UnderConfiguration;
import org.echocat.jability.configuration.stage.UnderStagesRootConfiguration.CurrentId;
import org.echocat.jability.configuration.stage.UnderStagesRootConfiguration.RespectSystemProvider;
import org.echocat.jability.configuration.support.BaseConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.lang.System.getProperty;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.split;
import static org.echocat.jability.Jability.AVAILABLE_STAGES;
import static org.echocat.jability.Jability.CURRENT_STAGE;
import static org.echocat.jomon.runtime.CollectionUtils.asList;

@XmlType(name = "stagesRoot", propOrder = {"current", "respectSystemProviders", "stages", "references", "availables", "providers"})
public class StagesRootConfiguration extends BaseConfiguration implements UnderConfiguration {

    @Nullable
    public static String getDefaultCurrentStageId() {
        final String value = getProperty(CURRENT_STAGE, "").trim();
        return !isEmpty(value) ? value : null;
    }

    @Nullable
    public static String getCurrentStageIdBasedOn(@Nullable StagesRootConfiguration configuration) {
        final String defaultId = getDefaultCurrentStageId();
        final String result;
        if (defaultId != null) {
            result = defaultId;
        } else {
            result = configuration != null ? configuration.getCurrent() : null;
        }
        return result;
    }

    @Nullable
    public static Set<String> getDefaultAvailableStageIds() {
        final String value = getProperty(AVAILABLE_STAGES);
        final Set<String> result;
        if (!isEmpty(value)) {
            final String[] ids = split(value, ",;\n ");
            result = new LinkedHashSet<>(ids.length);
            for (String available : ids) {
                final String trimmedAvailable = available.trim();
                if (!isEmpty(trimmedAvailable)) {
                    result.add(trimmedAvailable);
                }
            }
        } else {
            result = null;
        }
        return result;
    }

    @Nullable
    public static Set<String> getAvailableStageIdsBasedOn(@Nullable StagesRootConfiguration configuration) {
        final Set<String> defaultAvailableIds = getDefaultAvailableStageIds();
        final Set<String> result;
        if (defaultAvailableIds != null) {
            result = defaultAvailableIds;
        } else if (configuration != null) {
            final List<AvailableStageConfiguration> configured = configuration.getAvailables();
            if (configured != null) {
                result = new LinkedHashSet<>(configured.size());
                for (AvailableStageConfiguration current : configured) {
                    if (current != null) {
                        final String id = current.getId();
                        if (id == null) {
                            throw new ConfigurationException("null id");
                        }
                        result.add(id);
                    }
                }
            } else {
                result = null;
            }
        } else {
            result = null;
        }
        return result;
    }

    private String _current;
    private boolean _respectSystemProviders = true;
    private List<StageConfiguration> _stages;
    private List<StageReferenceConfiguration> _references;
    private List<AvailableStageConfiguration> _availables;
    private List<StageProviderConfiguration> _providers;

    @XmlAttribute(name = "current", required = false)
    public String getCurrent() {
        return _current;
    }

    public void setCurrent(String current) {
        _current = current;
    }

    @XmlAttribute(name = "respectSystemProviders")
    public boolean isRespectSystemProviders() {
        return _respectSystemProviders;
    }

    public void setRespectSystemProviders(boolean respectSystemProviders) {
        _respectSystemProviders = respectSystemProviders;
    }

    @XmlElement(name = "stage")
    public List<StageConfiguration> getStages() {
        return _stages;
    }

    public void setStages(List<StageConfiguration> stages) {
        _stages = stages;
    }

    @XmlElement(name = "reference")
    public List<StageReferenceConfiguration> getReferences() {
        return _references;
    }

    public void setReferences(List<StageReferenceConfiguration> references) {
        _references = references;
    }

    @XmlElement(name = "available", required = false)
    public List<AvailableStageConfiguration> getAvailables() {
        return _availables;
    }

    public void setAvailables(List<AvailableStageConfiguration> availables) {
        _availables = availables;
    }

    @XmlElement(name = "provider", required = false)
    public List<StageProviderConfiguration> getProviders() {
        return _providers;
    }

    public void setProviders(List<StageProviderConfiguration> providers) {
        _providers = providers;
    }

    @Nonnull
    public StagesRootConfiguration whichCurrentId(@Nullable String currentId) {
        _current = currentId;
        return this;
    }

    @Nonnull
    public StagesRootConfiguration with(@Nullable UnderStagesRootConfiguration... what) {
        return with(asList(what));
    }

    @Nonnull
    public StagesRootConfiguration with(@Nullable Iterable<UnderStagesRootConfiguration> what) {
        if (what != null) {
            for (UnderStagesRootConfiguration configuration : what) {
                if (configuration instanceof StageConfiguration) {
                    _stages = addOrCreate((StageConfiguration) configuration, _stages);
                } else if (configuration instanceof StageReferenceConfiguration) {
                    _references = addOrCreate((StageReferenceConfiguration) configuration, _references);
                } else if (configuration instanceof AvailableStageConfiguration) {
                    _availables = addOrCreate((AvailableStageConfiguration) configuration, _availables);
                } else if (configuration instanceof StageProviderConfiguration) {
                    _providers = addOrCreate((StageProviderConfiguration) configuration, _providers);
                } else if (configuration instanceof RespectSystemProvider) {
                    final RespectSystemProvider respectSystemProvider = (RespectSystemProvider) configuration;
                    _respectSystemProviders = respectSystemProvider.getValue();
                } else if (configuration instanceof CurrentId) {
                    final CurrentId currentId = (CurrentId) configuration;
                    _current = currentId.getValue();
                } else {
                    throw new IllegalArgumentException("Could not handle configuration: "  + configuration);
                }
            }
        }
        return this;
    }

}
