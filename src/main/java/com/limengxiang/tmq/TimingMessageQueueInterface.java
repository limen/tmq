package com.limengxiang.tmq;

import com.limengxiang.tmq.message.MessageInterface;

import java.util.Date;

public interface TimingMessageQueueInterface {

    /**
     * 获取队列名称
     * @return
     */
    public String queueName();

    /**
     * 发送消息
     * @param msg
     * @return
     */
    public String push(MessageInterface msg);

    /**
     * 拉取消息，默认拉取当前时间消息
     * @return
     */
    public MessageInterface pull();

    /**
     * 按时间拉取消息
     * @param t
     * @return
     */
    public MessageInterface pull(Date t);

    /**
     * 查询消息
     * @param msgId
     * @return
     */
    public MessageInterface poll(String msgId);

    /**
     * 时间分片的长度
     * @param t
     * @return
     */
    public Long size(Date t);

    /**
     * 更新消息
     * @param msg
     * @return
     */
    public Integer consumed(MessageInterface msg);

}
