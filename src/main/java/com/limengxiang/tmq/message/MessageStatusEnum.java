package com.limengxiang.tmq.message;

public enum MessageStatusEnum {

    WAITING(0),
    OCCUPIED(1),
    CONSUMED(2);

    private int status;

    MessageStatusEnum(int status) {
        this.status = status;
    }

    public int value() {
        return status;
    }
}
