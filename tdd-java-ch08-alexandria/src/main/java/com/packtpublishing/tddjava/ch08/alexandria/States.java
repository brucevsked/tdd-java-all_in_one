package com.packtpublishing.tddjava.ch08.alexandria;

enum States {
    BOUGHT(1),
    RENTED(2),
    AVAILABLE(3),
    CENSORED(4);

    private final int value;

    private States(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static States fromValue(int value) {
        for (States state : values()) {
            if (state.getValue() == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("Value '" + value
                + "' could not be found in States");
    }
}
