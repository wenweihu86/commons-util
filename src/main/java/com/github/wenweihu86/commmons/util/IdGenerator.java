package com.github.wenweihu86.commmons.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 唯一id生成器，参照算法twitter snowflake实现
 * https://github.com/twitter/snowflake
 */
public class IdGenerator {
    private static final long SEQUENCE_BIT_NUM = 12L;
    private static final long NODE_ID_BIT_NUM = 10L;
    private static final long MAX_SEQUENCE = 4096L;
    private static final long MAX_NODE_ID = 1024L;

    private AtomicLong sequence = new AtomicLong(0);
    private long nodeId;
    private long lastTimestamp = 0L;

    private static IdGenerator instance = new IdGenerator();

    public static IdGenerator instance() {
        return instance;
    }

    private IdGenerator() {
        this(1L);
    }

    public IdGenerator(long nodeId) {
        this.nodeId = nodeId;
    }

    public long getId() {
        long currentTimestamp = System.currentTimeMillis();
        if (currentTimestamp == lastTimestamp) {
            if (sequence.get() == MAX_SEQUENCE) {
                currentTimestamp = tilNextMillis(lastTimestamp);
                sequence = new AtomicLong(0);
            }
        } else {
            sequence = new AtomicLong(0);
        }
        lastTimestamp = currentTimestamp;
        long id = (currentTimestamp << (SEQUENCE_BIT_NUM + SEQUENCE_BIT_NUM))
                | (nodeId << SEQUENCE_BIT_NUM)
                | sequence.getAndIncrement();
        return id;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
