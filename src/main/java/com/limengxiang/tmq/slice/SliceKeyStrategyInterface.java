package com.limengxiang.tmq.slice;

import java.util.Date;

/**
 * 基于时间的分片策略
 */
public interface SliceKeyStrategyInterface {

    /**
     * 生成分片索引
     * @param t
     * @return
     */
    public String key(Date t);

}
