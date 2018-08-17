package com.mult;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Slf4JTest {

    public static void main(String[] args) {
        log.info("Current Time: {}", System.currentTimeMillis());
        log.info("Current Time: " + System.currentTimeMillis());
        log.info("Current Time: {}", System.currentTimeMillis());
        log.trace("trace log");
        log.warn("warn log");
        log.debug("debug log");
        log.info("info log");
        log.error("error log");
    }
}