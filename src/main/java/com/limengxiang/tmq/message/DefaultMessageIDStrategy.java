package com.limengxiang.tmq.message;

import com.limengxiang.tmq.xid.Generator;

/**
 * 唯一ID生成策略
 */
public class DefaultMessageIDStrategy implements MessageIDStrategyInterface {

    public String uniqueId() {
        try {
            return Generator.gen().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
