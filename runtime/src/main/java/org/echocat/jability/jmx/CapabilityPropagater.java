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
import org.echocat.jability.CapabilityDefinitionProvider;
import org.echocat.jability.CapabilityProvider;
import org.echocat.jomon.runtime.util.Duration;

import javax.annotation.Nonnull;
import javax.management.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static java.lang.Boolean.TRUE;
import static java.lang.Runtime.getRuntime;
import static java.lang.System.getProperty;
import static java.lang.management.ManagementFactory.getPlatformMBeanServer;
import static org.echocat.jability.CapabilitiesConstants.AUTO_PROPAGATE_DEFAULT;
import static org.echocat.jability.CapabilitiesConstants.AUTO_PROPAGATE_NAME;
import static org.echocat.jability.CompoundCapabilityDefinitionProvider.capabilityDefinitionProvider;
import static org.echocat.jability.CompoundCapabilityProvider.capabilityProvider;
import static org.echocat.jomon.runtime.concurrent.ThreadUtils.stop;
import static org.echocat.jomon.runtime.util.ResourceUtils.closeQuietly;

public class CapabilityPropagater implements AutoCloseable {

    private static CapabilityPropagater c_systemInstance;

    static {
        getRuntime().addShutdownHook(new Thread(CapabilityPropagater.class.getName() + ".Shotdown") { @Override public void run() {
            synchronized (CapabilityProvider.class) {
                if (c_systemInstance != null) {
                    closeQuietly(c_systemInstance);
                }
            }
        }});
    }

    public static void registerSystemPropagater() {
        synchronized (CapabilityPropagater.class) {
            if (c_systemInstance == null) {
                c_systemInstance = new CapabilityPropagater();
            }
        }
    }

    public static void registerSystemPropagaterIfAutoPropagateEnabled() {
        if (isAutoPropagateEnabled()) {
            registerSystemPropagater();
        }
    }

    public static boolean isAutoPropagateEnabled() {
        final String value = getProperty(AUTO_PROPAGATE_NAME, Boolean.toString(AUTO_PROPAGATE_DEFAULT));
        return TRUE.toString().equalsIgnoreCase(value);
    }


    private final MBeanServer _server;
    private final CapabilityDefinitionProvider _capabilityDefinitionProvider;
    private final CapabilityProvider _capabilityProvider;

    private final Refresher _refresher = new Refresher();
    private final Set<CapabilityDefinition<?>> _knownDefinitions = new HashSet<>();

    private Duration _indexRefreshDuration = new Duration("10s");

    public CapabilityPropagater(@Nonnull MBeanServer server, @Nonnull CapabilityDefinitionProvider capabilityDefinitionProvider, @Nonnull CapabilityProvider capabilityProvider) {
        _server = server;
        _capabilityDefinitionProvider = capabilityDefinitionProvider;
        _capabilityProvider = capabilityProvider;
        refresh();
        _refresher.start();
    }

    public CapabilityPropagater(@Nonnull CapabilityDefinitionProvider capabilityDefinitionProvider, @Nonnull CapabilityProvider capabilityProvider) {
        this(getPlatformMBeanServer(), capabilityDefinitionProvider, capabilityProvider);
    }

    public CapabilityPropagater() {
        this(capabilityDefinitionProvider(), capabilityProvider());
    }

    @Nonnull
    public Duration getIndexRefreshDuration() {
        return _indexRefreshDuration;
    }

    public void setIndexRefreshDuration(@Nonnull Duration value) {
        _indexRefreshDuration = value;
    }

    public void refresh() {
        synchronized (this) {
            final Set<CapabilityDefinition<?>> newDefinitions = new HashSet<>();
            boolean success = false;
            try {
                for (CapabilityDefinition<?> definition : _capabilityDefinitionProvider) {
                    if (_knownDefinitions.contains(definition)) {
                        register(definition);
                    }
                    newDefinitions.add(definition);
                }
                success = true;
            } finally {
                if (!success) {
                    for (CapabilityDefinition<?> definition : newDefinitions) {
                        try {
                            unregister(definition);
                        } catch (Exception ignored) {}
                    }
                }
            }
            final Iterator<CapabilityDefinition<?>> i = _knownDefinitions.iterator();
            while (i.hasNext()) {
                final CapabilityDefinition<?> oldDefinition = i.next();
                if (!newDefinitions.contains(oldDefinition)) {
                    i.remove();
                }
            }
            _knownDefinitions.addAll(newDefinitions);
        }
    }

    protected void register(@Nonnull CapabilityDefinition<?> definition) {
        final CapabilityDynamicMBean mBean = new CapabilityDynamicMBean(definition, _capabilityProvider);
        final ObjectName name = buildNameFor(definition);
        try {
            _server.registerMBean(mBean, name);
        } catch (InstanceAlreadyExistsException ignored) {
        } catch (Exception e) {
            throw new RuntimeException("Could not register mBean for definition " + definition + " under name '" + name + "'.", e);
        }
    }

    protected void unregister(@Nonnull CapabilityDefinition<?> definition) {
        final ObjectName name = buildNameFor(definition);
        try {
            _server.unregisterMBean(name);
        } catch (InstanceNotFoundException ignored) {
        } catch (Exception e) {
            throw new RuntimeException("Could not unregister mBean for definition " + definition + " with name '" + name + "'.", e);
        }
    }

    @Nonnull
    protected ObjectName buildNameFor(@Nonnull CapabilityDefinition<?> definition) {
        try {
            return new ObjectName(Capability.class.getPackage().getName() + ":type=" + normalize(definition.getId()));
        } catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException("Could not from from the id of " + definition + " a valid object name.", e);
        }
    }

    @Nonnull
    protected String normalize(@Nonnull String what) {
        final char[] in = what.toCharArray();
        final char[] out = new char[in.length];
        int i = 0;
        for (char c : in) {
            if (Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.') {
                out[i++] = c;
            } else if (Character.isWhitespace(c)) {
                out[i++] = '_';
            }
        }
        return new String(out, 0, i);
    }

    @Override
    public void close() throws Exception {
        stop(_refresher);
    }

    protected class Refresher extends Thread {

        public Refresher() {
            super(CapabilityPropagater.this.getClass().getSimpleName() + ".Refresher");
        }

        @Override
        public void run() {
            try {
                while (!currentThread().isInterrupted()) {
                    _indexRefreshDuration.sleep();
                    refresh();
                }
            } catch (InterruptedException ignored) {
                currentThread().interrupt();
            }
        }
    }

}
