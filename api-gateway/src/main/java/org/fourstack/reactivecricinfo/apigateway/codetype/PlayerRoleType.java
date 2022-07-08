package org.fourstack.reactivecricinfo.apigateway.codetype;

public enum PlayerRoleType {
    BATSMAN {
        @Override
        public String toString() {
            return "BATSMAN";
        }
    },
    BOWLER {
        @Override
        public String toString() {
            return "BOWLER";
        }
    },
    ALL_ROUNDER {
        @Override
        public String toString() {
            return "ALL_ROUNDER";
        }
    },
    WICKET_KEEPER {
        @Override
        public String toString() {
            return "WICKET_KEEPER";
        }
    }

}
