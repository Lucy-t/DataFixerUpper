// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT license.
package com.mojang.serialization;

public class Lifecycle {
    private static final Lifecycle STABLE = new Lifecycle() {
        @Override
        public String toString() {
            return "Stable";
        }
    };
    private static final Lifecycle EXPERIMENTAL = new Lifecycle() {
        @Override
        public String toString() {
            return "Experimental";
        }
    };

    private Lifecycle() {
    }

    protected static final class Deprecated extends Lifecycle {
        private final int since;

        public Deprecated(final int since) {
            this.since = since;
        }

        public int since() {
            return since;
        }
    }

    public static Lifecycle experimental() {
        return EXPERIMENTAL;
    }

    public static Lifecycle stable() {
        return STABLE;
    }

    public static Lifecycle deprecated(final int since) {
        return new Deprecated(since);
    }

    public Lifecycle add(final Lifecycle other) {
        if (this == EXPERIMENTAL || other == EXPERIMENTAL) {
            return EXPERIMENTAL;
        }
        if (this instanceof Deprecated) {
            if (other instanceof Deprecated) {
                return new Deprecated(Math.min(((Deprecated) this).since, ((Deprecated) other).since));
            }
            return this;
        }
        return STABLE;
    }
}