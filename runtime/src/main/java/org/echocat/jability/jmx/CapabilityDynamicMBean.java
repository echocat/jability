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

import org.echocat.jability.Capabilities;
import org.echocat.jability.Capability;

import javax.annotation.Nonnull;
import javax.management.*;
import java.util.ArrayList;
import java.util.List;

public class CapabilityDynamicMBean implements DynamicMBean {

    private final Capability<Object> _capability;
    private final Capabilities _capabilities;

    public CapabilityDynamicMBean(@Nonnull Capability<?> capability, @Nonnull Capabilities capabilities) {
        // noinspection unchecked
        _capability = (Capability<Object>) capability;
        _capabilities = capabilities;
    }

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        final Object result;
        if ("id".equals(attribute)) {
            result = _capability.getId();
        } else if ("valueType".equals(attribute)) {
            result = _capability.getValueType().getName();
        } else if ("isDefaultValue".equals(attribute)) {
            final Object value = _capabilities.get(_capability);
            result = value != null ? value.equals(_capability.getDefaultValue()) : _capability.getDefaultValue() == null;
        } else if ("value".equals(attribute)) {
            result = _capabilities.get(_capability, null);
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
            if (_capabilities.isModifiable(_capability)) {
                final Class<?> valueType = _capability.getValueType();
                if (valueType.isInstance(value)) {
                    _capabilities.set(_capability, value);
                } else if (value == null) {
                    _capabilities.remove(null);
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
        final List<MBeanAttributeInfo> result = new ArrayList<>();
        result.add(new MBeanAttributeInfo("id", String.class.getName(), null, true, false, false));
        result.add(new MBeanAttributeInfo("valueType", String.class.getName(), null, true, false, false));
        result.add(new MBeanAttributeInfo("isDefaultValue", Boolean.class.getName(), null, true, false, false));
        result.add(new MBeanAttributeInfo("value", _capability.getValueType().getName(), null, true, _capabilities.isModifiable(_capability), false));
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
