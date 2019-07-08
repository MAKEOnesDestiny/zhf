package com.leqee.demo.netty.privateprotocol.bean;

public enum MessageType {

    LOGIN_REQ((byte) 3),
    LOGIN_RESP((byte) 4),
    HEART_BEAT_REQ((byte) 5),
    HEART_BEAT_RESP((byte) 6);

    MessageType(byte value) {
        this.value = value;
    }

    public byte value;

    public byte getValue() {
        return value;
    }

}
