package com.limengxiang.tmq;

import com.limengxiang.tmq.message.MessageIDStrategyInterface;
import com.limengxiang.tmq.message.MessageInterface;
import com.limengxiang.tmq.queue.QueueStorageInterface;
import com.limengxiang.tmq.slice.TimingSlicerInterface;

import java.util.Date;

public class TimingMessageQueue implements TimingMessageQueueInterface {

    public static final Integer StatusWaiting = 0;
    public static final Integer StatusOccupy = 1;
    public static final Integer StatusConsumed = 2;

    private String name;
    private QueueStorageInterface queueStorage;
    private MessageIDStrategyInterface messageIdStrategy;
    private TimingSlicerInterface slicer;

    public TimingMessageQueue() {

    }

    public TimingMessageQueue(String name) {
        this.name = name;
    }

    public String queueName() {
        return name;
    }

    public String push(MessageInterface msg) {
        prepareMessage(msg);
        Integer added = queueStorage.add(msg);
        if (added == 1) {
            slicer.push(msg.getMsgId(), msg.getSchedule());
            return msg.getMsgId();
        }
        return null;
    }

    public MessageInterface pull() {
        Date t = new Date();
        return pull(t);
    }

    public MessageInterface pull(Date t) {
        String msgId = slicer.pull(t);
        if (msgId == null) {
            return null;
        }
        MessageInterface msg = queueStorage.get(msgId);
        if (msg == null) {
            return null;
        }
        msg.setStatus(StatusOccupy);
        queueStorage.update(msg);
        return msg;
    }

    public MessageInterface poll(String id) {
        return queueStorage.get(id);
    }

    public Long size(Date t) {
        return slicer.size(t);
    }

    public Integer consumed(MessageInterface msg) {
        msg.setStatus(StatusConsumed);
        msg.setConsumeAt(new Date());
        return queueStorage.update(msg);
    }

    public void setQueueStorage(QueueStorageInterface queueStorage) {
        this.queueStorage = queueStorage;
    }

    public void setSlicer(TimingSlicerInterface slicer) {
        this.slicer = slicer;
        this.slicer.setTimingQueue(this);
    }

    public void setMessageIdStrategy(MessageIDStrategyInterface messageIdStrategy) {
        this.messageIdStrategy = messageIdStrategy;
    }

    private void prepareMessage(MessageInterface msg) {
        msg.setQueueName(queueName());
        msg.setMsgId(messageIdStrategy.uniqueId());
        msg.setStatus(StatusWaiting);
        if (msg.getReceiveAt() == null) {
            msg.setReceiveAt(new Date());
        }
    }
}
