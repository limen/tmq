package com.limengxiang.tmq.slice;

import com.limengxiang.tmq.TimingMessageQueueInterface;

import java.util.Date;

/**
 * 基于 Redis LIST 实现分片
 */
public class DefaultTimingSlicer implements TimingSlicerInterface {

    private TimingMessageQueueInterface timingQueue;
    private SliceKeyStrategyInterface sliceKeyStrategy;
    private SliceListInterface sliceList;

    public DefaultTimingSlicer() {
        sliceKeyStrategy = new DefaultSliceKeyStrategy();
        sliceList = new DefaultSliceList();
    }

    public DefaultTimingSlicer(SliceKeyStrategyInterface strategy) {
        this.sliceKeyStrategy = strategy;
    }

    public Long push(String id, Date timing) {
        return sliceList.push(getSliceKey(timing), id);
    }

    public String pull(Date t) {
        return sliceList.pop(getSliceKey(t));
    }

    public void setSliceKeyStrategy(SliceKeyStrategyInterface sliceStrategy) {
        this.sliceKeyStrategy = sliceStrategy;
    }

    public void setSliceList(SliceListInterface sliceList) {
        this.sliceList = sliceList;
    }

    public TimingMessageQueueInterface getTimingQueue() {
        return timingQueue;
    }

    public void setTimingQueue(TimingMessageQueueInterface timingQueue) {
        this.timingQueue = timingQueue;
    }

    private String getSliceKey(Date t) {
        return this.timingQueue.queueName() + ":slice:" + sliceKeyStrategy.key(t);
    }
}
