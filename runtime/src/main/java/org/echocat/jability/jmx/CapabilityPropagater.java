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
import org.echocat.jability.Jability;
import org.echocat.jomon.runtime.util.Duration;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import javax.management.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static java.lang.management.ManagementFactory.getPlatformMBeanServer;
import static org.echocat.jomon.runtime.concurrent.ThreadUtils.stop;

@ThreadSafe
@Immutable
public class CapabilityPropagater implements AutoCloseable {

    private final MBeanServer _server;
    private final Jability _jability;

    private final Refresher _refresher = new Refresher();
    private final Set<Capability<?>> _knownCapabilities = new HashSet<>();

    private Duration _indexRefreshDuration = new Duration("10s");

    public CapabilityPropagater(@Nonnull MBeanServer server, @Nonnull Jability jability) {
        _server = server;
        _jability = jability;
        refresh();
        _refresher.start();
    }

    public CapabilityPropagater(@Nonnull Jability jability) {
        this(getPlatformMBeanServer(), jability);
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
            final Set<Capability<?>> newCapabilities = new HashSet<>();
            boolean success = false;
            try {
                for (Capability<?> capability : _jability.getCapabilityProvider()) {
                    if (_knownCapabilities.contains(capability)) {
                        register(capability);
                    }
                    newCapabilities.add(capability);
                }
                success = true;
            } finally {
                if (!success) {
                    for (Capability<?> capability : newCapabilities) {
                        try {
                            unregister(capability);
                        } catch (Exception ignored) {}
                    }
                }
            }
            final Iterator<Capability<?>> i = _knownCapabilities.iterator();
            while (i.hasNext()) {
                final Capability<?> oldCapabilities = i.next();
                if (!newCapabilities.contains(oldCapabilities)) {
                    i.remove();
                }
            }
            _knownCapabilities.addAll(newCapabilities);
        }
    }

    protected void register(@Nonnull Capability<?> capability) {
        final CapabilityDynamicMBean mBean = new CapabilityDynamicMBean(capability, _jability.getCapabilities());
        final ObjectName name = buildNameFor(capability);
        try {
            _server.registerMBean(mBean, name);
        } catch (InstanceAlreadyExistsException ignored) {
        } catch (Exception e) {
            throw new RuntimeException("Could not register mBean for capability " + capability + " under name '" + name + "'.", e);
        }
    }

    protected void unregister(@Nonnull Capability<?> capability) {
        final ObjectName name = buildNameFor(capability);
        try {
            _server.unregisterMBean(name);
        } catch (InstanceNotFoundException ignored) {
        } catch (Exception e) {
            throw new RuntimeException("Could not unregister mBean for capability " + capability + " with name '" + name + "'.", e);
        }
    }

    @Nonnull
    protected ObjectName buildNameFor(@Nonnull Capability<?> capability) {
        try {
            return new ObjectName(Capability.class.getPackage().getName() + ":type=" + normalize(capability.getId()));
        } catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException("Could not from from the id of " + capability + " a valid object name.", e);
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
