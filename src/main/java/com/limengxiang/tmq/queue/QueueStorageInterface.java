package com.limengxiang.tmq.queue;

import com.limengxiang.tmq.message.MessageInterface;

/**
 * 消息持久存储
 */
public interface QueueStorageInterface {

    /**
     * 新增消息
     * @param msg
     * @return
     */
    public Integer add(MessageInterface msg);

    /**
     * 查询消息
     * @param id
     * @return
     */
    public MessageInterface get(String id);

    /**
     * 更新消息
     * @param msg
     * @return
     */
    public Integer update(MessageInterface msg);

}
