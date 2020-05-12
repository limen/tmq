package com.limengxiang.tmq.slice;

import com.limengxiang.tmq.TimingMessageQueueInterface;

import java.util.Date;

/**
 * 将消息队列按投递时间分片
 */
public interface TimingSlicerInterface {

    public Long push(String id, Date t);

    public String pull(Date date);

    /**
     * 获取时间对应的切片大小
     * @param t
     * @return
     */
    public Long size(Date t);

    public void setTimingQueue(TimingMessageQueueInterface timingQueue);

    public TimingMessageQueueInterface getTimingQueue();

    public void setSliceKeyStrategy(SliceKeyStrategyInterface strategy);

    public void setSliceList(SliceListInterface sliceList);

}
