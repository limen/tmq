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
import java.util.ArrayList;
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
        ArrayList<MessageInterface> slice1 = new ArrayList<>();
        ArrayList<MessageInterface> slice2 = new ArrayList<>();
        Long start = new Date().getTime();
        while (true) {
            MessageInterface msg = tmq.pull(schedule1);
            if (msg == null) {
                break;
            }
            slice1.add(msg);
        }
        while (true) {
            MessageInterface msg = tmq.pull(schedule2);
            if (msg == null) {
                break;
            }
            slice2.add(msg);
        }
        Long end = new Date().getTime();
        System.out.println("------------------------Pull time consumption in milli second:" + (end - start));
        Assert.assertEquals(slice1.size(), cnt);
        Assert.assertEquals(slice2.size(), cnt);
        Assert.assertEquals(slice1.get(0).getBody(), "right now");
        Assert.assertEquals(slice2.get(0).getBody(), "10 minutes later");
    }

    @Test
    public void poll() {
    }

    @Test
    public void consumed() {
    }
}