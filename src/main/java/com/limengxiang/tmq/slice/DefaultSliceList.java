package com.limengxiang.tmq.slice;

import redis.clients.jedis.Jedis;

public class DefaultSliceList implements SliceListInterface {

    private Jedis jedis;

    public DefaultSliceList() {
        jedis = new Jedis("localhost");
    }

    public Long push(String key, String msgId) {
        return jedis.lpush(key, msgId);
    }

    public String pop(String key) {
        return jedis.rpop(key);
    }
}
