package com.liuxn.common.base.log;


public class LogFactory {

    public static Log getLog(Class<?> clazz) {
        return new Log(clazz);
    }

}
