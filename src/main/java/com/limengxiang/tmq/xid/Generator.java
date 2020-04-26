package com.limengxiang.tmq.xid;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生成消息唯一ID
 * Machine ID难以获取，简化为参数
 *
 * see https://github.com/rs/xid
 */
public class Generator {

    private final static char[] encoding = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v'
    };

    private static Integer machineId;
    private static AtomicInteger counter;
    private static Integer processId;

    /**
     * Machine ID简化为参数
     * @param machine
     */
    public static void forMachine(Integer machine) {
        machineId = machine;
        counter = new AtomicInteger(random());
        processId = getProcessId();
    }

    public static Xid gen() throws Exception {
        if (machineId == null || processId == null || counter == null) {
            throw new Exception("Generator not initialized");
        }

        Integer cnt = counter.incrementAndGet();
        Integer ts = new Long(new Date().getTime() / 1000).intValue();
        Integer mask = 0xFF;

        Integer[] id = new Integer[12];
        id[0] = ts & mask;
        id[1] = (ts >> 8) & mask;
        id[2] = (ts >> 16) & mask;
        id[3] = (ts >> 24) & mask;
        id[4] = machineId & mask;
        id[5] = (machineId >> 8) & mask;
        id[6] = (machineId >> 16) & mask;
        id[7] = processId & mask;
        id[8] = (processId >> 8) & mask;
        id[9] = cnt & mask;
        id[10] = (cnt >> 8) & mask;
        id[11] = (cnt >> 16) & mask;

        char[] dst = new char[20];
        dst[19] = encoding[(id[11] << 4) & 0x1F];
        dst[18] = encoding[(id[11] >> 1) & 0x1F];
        dst[17] = encoding[(id[11] >> 6) & 0x1F | (id[10] << 2) & 0x1F];
        dst[16] = encoding[id[10] >> 3];
        dst[15] = encoding[id[9] & 0x1F];
        dst[14] = encoding[(id[9] >> 5) | (id[8] << 3) & 0x1F];
        dst[13] = encoding[(id[8] >> 2) & 0x1F];
        dst[12] = encoding[id[8] >> 7 | (id[7] << 1) & 0x1F];
        dst[11] = encoding[(id[7] >> 4) & 0x1F | (id[6] << 4) & 0x1F];
        dst[10] = encoding[(id[6] >> 1) & 0x1F];
        dst[9] = encoding[(id[6] >> 6) & 0x1F | (id[5] << 2) & 0x1F];
        dst[8] = encoding[id[5] >> 3];
        dst[7] = encoding[id[4] & 0x1F];
        dst[6] = encoding[id[4] >> 5 | (id[3] << 3) & 0x1F];
        dst[5] = encoding[(id[3] >> 2) & 0x1F];
        dst[4] = encoding[id[3] >> 7 | (id[2] << 1) & 0x1F];
        dst[3] = encoding[(id[2] >> 4) & 0x1F | (id[1] << 4) & 0x1F];
        dst[2] = encoding[(id[1] >> 1) & 0x1F];
        dst[1] = encoding[(id[1] >> 6) & 0x1F | (id[0] << 2) & 0x1F];
        dst[0] = encoding[id[0] >> 3];

        return new Xid(id, dst, machineId, processId, ts, cnt);
    }

    /**
     * 获取进程ID
     * @return
     */
    private static Integer getProcessId() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return Integer.parseInt(name.split("@")[0]);
    }

    /**
     * 生成随机数
     * @return
     */
    private static Integer random() {
        Double rand = Math.random();
        return new Double(1000000 * rand).intValue();
    }
}
