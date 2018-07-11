package com.wz.guava.utilities;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @projectName: Guava
 * @package: com.wz.guava.utilities
 * @className: StopWatchExample
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/10 22:20
 **/
public class StopWatchExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(StopWatchExample.class);

    public static void main(String[] args) {
        process("123456");
    }

    private static void process(String orderNo) {
        LOGGER.info(">>> start process the orderNo[{}]", orderNo);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            LOGGER.error(">>> process exception e:[{}]", e);
        }
        LOGGER.info(">>> end process the orderNo[{}] time:[{}]", orderNo, stopwatch.stop());
    }
}
