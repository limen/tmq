package com.limengxiang.tmq.message;

import java.util.Date;

public interface MessageInterface {

    public void setMsgId(String id);

    public String getMsgId();

    public void setBody(String body);

    public String getBody();

    public void setStatus(Integer status);

    public Integer getStatus();

    public void setSchedule(Date schedule);

    public Date getSchedule();

    public void setReceiveAt(Date t);

    public Date getReceiveAt();

    public void setConsumeAt(Date t);

    public Date getConsumeAt();

}
