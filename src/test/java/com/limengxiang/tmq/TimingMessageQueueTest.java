package com.limengxiang.tmq;

import com.limengxiang.tmq.message.DefaultMessage;
import com.limengxiang.tmq.message.DefaultMessageIDStrategy;
import com.limengxiang.tmq.message.MessageInterface;
import com.limengxiang.tmq.queue.DefaultQueueStorage;
import com.limengxiang.tmq.slice.DefaultTimingSlicer;
import com.limengxiang.tmq.xid.Generator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimingMessageQueueTest {

    private int cnt;
    private Date schedule1;
    private Date schedule2;
    private TimingMessageQueue tmq;

    @Before
    public void setUp() throws IOException {
        if (tmq == null) {
            InputStream stream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSession sqlSession = new SqlSessionFactoryBuilder().build(stream).openSession(true);
            DefaultQueueStorage queueStorage = sqlSession.getMapper(DefaultQueueStorage.class);

            tmq = new TimingMessageQueue("order_payment_remind");
            tmq.setQueueStorage(queueStorage);
            tmq.setMessageIdStrategy(new DefaultMessageIDStrategy());
            tmq.setSlicer(new DefaultTimingSlicer());

            Generator.forMachine(1000000);
            Calendar cal = Calendar.getInstance();
            schedule1 = cal.getTime();
            cal.add(Calendar.MINUTE, 10);
            schedule2 = cal.getTime();
            cnt = 10;
        }
    }

    @Test
    public void aPush() {
        Long start = new Date().getTime();
        for (int i = 0; i < cnt; i++) {
            DefaultMessage msg = new DefaultMessage();
            msg.setBody("right now");
            msg.setSchedule(schedule1);
            tmq.push(msg);
        }
        for (int i = 0; i < cnt; i++) {
            DefaultMessage msg = new DefaultMessage();
            msg.setBody("10 minutes later");
            msg.setSchedule(schedule2);
            tmq.push(msg);
        }
        Assert.assertEquals(tmq.size(schedule1).intValue(), cnt);
        Assert.assertEquals(tmq.size(schedule2).intValue(), cnt);
        Long end = new Date().getTime();
        System.out.println("----------------------Push time consumption in milli second:" + (end - start));
    }

    @Test
    public void bPull() {
        MessageInterface msg1 = tmq.pull(schedule1);
        MessageInterface msg2 = tmq.pull(schedule2);
        Assert.assertEquals(msg1.getBody(), "right now");
        Assert.assertEquals(msg2.getBody(), "10 minutes later");
    }

    @Test
    public void poll() {
    }

    @Test
    public void consumed() {
    }
}