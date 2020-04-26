package com.limengxiang.tmq.message;

import java.util.Date;

public class DefaultMessage implements MessageInterface {

    private String msgId;
    private String body;
    private Integer status;
    private Date schedule;
    private Date receiveAt;
    private Date consumeAt;

    public void setMsgId(String id) {
        this.msgId = id;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public Date getSchedule() {
        return this.schedule;
    }

    public void setReceiveAt(Date t) {
        this.receiveAt = t;
    }

    public Date getReceiveAt() {
        return this.receiveAt;
    }

    public void setConsumeAt(Date t) {
        consumeAt = t;
    }

    public Date getConsumeAt() {
        return consumeAt;
    }

}
