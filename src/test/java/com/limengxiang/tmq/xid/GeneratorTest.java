package com.limengxiang.tmq.xid;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

public class GeneratorTest {

    @Before
    public void setUp() {
        Generator.forMachine(100000);
    }

    @Test
    public void gen() throws Exception {
        int cnt = 1000000;
        HashMap<String, Boolean> occupy = new HashMap<String, Boolean>();
        Long start = new Date().getTime();
        for (int i = 0; i < cnt; i++) {
            Xid gen = Generator.gen();
            occupy.put(gen.toString(), true);
            for (Integer b : gen.getBytes()) {
                Assert.assertTrue(b >= 0 && b <= 255);
            }
        }
        Long end = new Date().getTime();
        Assert.assertEquals(cnt, occupy.size());
        Long time = (end - start) * 1000;
        System.out.println("Total time consumption in micro seconds:" + time);
        Double perOP = new Double(time) / cnt;
        System.out.println("Average time consumption per op in micro second:" + perOP);
        System.out.println("Throughput per second:" + (1000000 / perOP));
    }
}