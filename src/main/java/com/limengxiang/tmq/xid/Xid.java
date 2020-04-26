package com.limengxiang.tmq.xid;

/**
 * 唯一ID
 */
public class Xid {

    private final Integer machineId;
    private final Integer processId;
    private final Integer timestamp;
    private final Integer counter;
    private final char[] chars;
    private final Integer[] bytes;
    private String strValue;

    public Xid(Integer[] bytes, char[] chars, Integer machineId, Integer processId, Integer timestamp, Integer counter) {
        this.bytes = bytes;
        this.chars = chars;
        this.machineId = machineId;
        this.processId = processId;
        this.timestamp = timestamp;
        this.counter = counter;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public Integer getProcessId() {
        return processId;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public Integer getCounter() {
        return counter;
    }

    public Integer[] getBytes() {
        return bytes;
    }

    public String toString() {
        if (strValue == null) {
            strValue = new String(chars);
        }

        return strValue;
    }
}
