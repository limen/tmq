package com.limengxiang.tmq.slice;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 按分钟分片
 */
public class DefaultSliceKeyStrategy implements SliceKeyStrategyInterface {

    private SimpleDateFormat df;

    public DefaultSliceKeyStrategy() {
        df = new SimpleDateFormat("yyyyMMddhhmm");
    }

    public String key(Date t) {
        return df.format(t);
    }
}
