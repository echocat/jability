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

import org.echocat.jability.Capability;
import org.echocat.jability.CapabilityDefinition;
import org.echocat.jability.CapabilityProvider;

import javax.annotation.Nonnull;
import javax.management.*;
import java.util.ArrayList;
import java.util.List;

public class CapabilityDynamicMBean implements DynamicMBean {

    private final CapabilityDefinition<?> _definition;
    private final CapabilityProvider _capabilityProvider;

    public CapabilityDynamicMBean(@Nonnull CapabilityDefinition<?> definition, @Nonnull CapabilityProvider capabilityProvider) {
        _definition = definition;
        _capabilityProvider = capabilityProvider;
    }

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        final Object result;
        if ("id".equals(attribute)) {
            result = _definition.getId();
        } else if ("valueType".equals(attribute)) {
            result = _definition.getValueType().getName();
        } else if ("defaultValue".equals(attribute)) {
            result = _definition.getDefaultValue();
        } else if ("nullable".equals(attribute)) {
            result = _definition.isNullable();
        } else if ("value".equals(attribute)) {
            final Capability<?> capability = _capabilityProvider.provideBy(_definition);
            result = capability != null ? capability.get() : null;
        } else {
            throw new AttributeNotFoundException(attribute);
        }
        return result;
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        final String attributeName = attribute.getName();
        final Object value = attribute.getValue();
        if ("value".equals(attributeName)) {
            // noinspection unchecked
            final Capability<Object> capability = (Capability<Object>) _capabilityProvider.provideBy(_definition);
            if (capability != null && capability.isModifiable()) {
                final Class<?> valueType = _definition.getValueType();
                if (valueType.isInstance(value)) {
                    capability.set(value);
                } else if (value == null) {
                    if (_definition.isNullable()) {
                        capability.set(null);
                    } else {
                        throw new InvalidAttributeValueException("Value could not be null");
                    }
                } else {
                    throw new InvalidAttributeValueException("Type " + valueType.getName() + " is expected but got value: " + value);
                }
            } else {
                throw new AttributeNotFoundException(attributeName);
            }
        } else {
            throw new AttributeNotFoundException(attributeName);
        }
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        throw new UnsupportedOperationException(actionName);
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return new MBeanInfo(Capability.class.getName(), null, getAttributes(), null, getOperations(), null);
    }

    @Nonnull
    protected MBeanAttributeInfo[] getAttributes() {
        // noinspection unchecked
        final Capability<Object> capability = (Capability<Object>) _capabilityProvider.provideBy(_definition);
        final List<MBeanAttributeInfo> result = new ArrayList<>();
        result.add(new MBeanAttributeInfo("id", String.class.getName(), null, true, false, false));
        result.add(new MBeanAttributeInfo("valueType", String.class.getName(), null, true, false, false));
        result.add(new MBeanAttributeInfo("defaultValue", _definition.getValueType().getName(), null, true, false, false));
        result.add(new MBeanAttributeInfo("nullable", Boolean.class.getName(), null, true, false, false));
        result.add(new MBeanAttributeInfo("value", _definition.getValueType().getName(), null, true, capability != null && capability.isModifiable(), false));
        return result.toArray(new MBeanAttributeInfo[result.size()]);
    }

    @Nonnull
    protected MBeanOperationInfo[] getOperations() {
        final List<MBeanOperationInfo> result = new ArrayList<>();
        return result.toArray(new MBeanOperationInfo[result.size()]);
    }

    @Override public AttributeList getAttributes(String[] attributes) { throw new UnsupportedOperationException(); }
    @Override public AttributeList setAttributes(AttributeList attributes) { throw new UnsupportedOperationException(); }

}
