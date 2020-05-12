package com.limengxiang.tmq.slice;

public interface SliceListInterface {

    public Long push(String key, String msgId);

    public String pop(String key);

    /**
     * 切片大小
     * @param key
     * @return
     */
    public Long size(String key);
}
