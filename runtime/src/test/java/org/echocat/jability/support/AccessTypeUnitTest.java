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

package org.echocat.jability.support;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

import static org.echocat.jability.support.AccessType.*;
import static org.echocat.jomon.testing.Assert.assertThat;
import static org.echocat.jomon.testing.BaseMatchers.not;

public class AccessTypeUnitTest {

    public static final Object A = new Object();
    protected static final Object B = new Object();
    static final Object C = new Object();
    @SuppressWarnings("UnusedDeclaration")
    private static final Object D = new Object();

    protected static final Field FIELD_A = getField("A");
    protected static final Field FIELD_B = getField("B");
    protected static final Field FIELD_C = getField("C");
    protected static final Field FIELD_D = getField("D");

    @Test
    public void testMatchesOnFields() throws Exception {
        assertThat(FIELD_A, matchesOnly(PUBLIC));
        assertThat(FIELD_B, matchesOnly(PROTECTED));
        assertThat(FIELD_C, matchesOnly(PACKAGE_LOCAL));
        assertThat(FIELD_D, matchesOnly(PRIVATE));

        assertThat(FIELD_A, not(matchesOnly(PROTECTED)));
    }

    @Nonnull
    private static Field getField(@Nonnull String name) {
        try {
            return AccessTypeUnitTest.class.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not find field with name: " + name, e);
        }
    }

    @Nonnull
    protected static Matcher<Field> matchesOnly(@Nonnull final AccessType accessType) {
        return new TypeSafeMatcher<Field>() {
            @Override
            public boolean matchesSafely(Field item) {
                boolean result = true;
                for (AccessType current : AccessType.values()) {
                    if (accessType == current) {
                        if (!AccessType.matches(item, current)) {
                            result = false;
                            break;
                        }
                    } else {
                        if (AccessType.matches(item, current)) {
                            result = false;
                            break;
                        }
                    }
                }
                return result;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("matches only ").appendValue(accessType);
            }
        };
    }
}
