package com.packetpublishing.tddjava.ch09.service;

/**
 * Created by lj1218.
 * Date: 2019/11/25
 */
public class FibonacciNumber {
    private final int number, value;

    public FibonacciNumber(int number, int value) {
        this.number = number;
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public int getValue() {
        return value;
    }
}
