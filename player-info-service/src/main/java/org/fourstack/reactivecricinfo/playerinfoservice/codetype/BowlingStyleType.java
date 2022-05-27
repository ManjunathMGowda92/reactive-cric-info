package org.fourstack.reactivecricinfo.playerinfoservice.codetype;

public enum BowlingStyleType {
    RIGHT_ARM_OFFBREAK {
        @Override
        public String toString() {
            return "RIGHT_ARM_OFFBREAK";
        }
    },
    LEFT_ARM_OFFBREAK {
        @Override
        public String toString() {
            return "LEFT_ARM_OFFBREAK";
        }
    },
    RIGHT_ARM_LEGBREAK {
        @Override
        public String toString() {
            return "RIGHT_ARM_LEGBREAK";
        }
    },
    LEFT_ARM_LEGBREAK {
        @Override
        public String toString() {
            return "RIGHT_ARM_LEGBREAK";
        }
    },
    RIGHT_ARM_FAST_MEDIUM {
        @Override
        public String toString() {
            return "RIGHT_ARM_FAST_MEDIUM";
        }
    },
    LEFT_ARM_FAST_MEDIUM {
        @Override
        public String toString() {
            return "LEFT_ARM_FAST_MEDIUM";
        }
    },
    RIGHT_ARM_FAST {
        @Override
        public String toString() {
            return "RIGHT_ARM_FAST";
        }
    },
    LEFT_ARM_FAST {
        @Override
        public String toString() {
            return "RIGHT_ARM_FAST";
        }
    },
    RIGHT_ARM_MEDIUM {
        @Override
        public String toString() {
            return "RIGHT_ARM_MEDIUM";
        }
    },
    LEFT_ARM_MEDIUM {
        @Override
        public String toString() {
            return "LEFT_ARM_MEDIUM";
        }
    },
    LEFT_ARM_ORTHODOX {
        @Override
        public String toString() {
            return "LEFT_ARM_ORTHODOX";
        }
    },
    RIGHT_ARM_ORTHODOX {
        @Override
        public String toString() {
            return "RIGHT_ARM_ORTHODOX";
        }
    }
}
