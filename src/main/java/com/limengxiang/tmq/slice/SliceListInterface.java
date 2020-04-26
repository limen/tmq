package com.limengxiang.tmq.slice;

public interface SliceListInterface {

    public Long push(String key, String msgId);

    public String pop(String key);

}
