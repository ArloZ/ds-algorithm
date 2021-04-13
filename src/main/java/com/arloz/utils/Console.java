package com.arloz.utils;

import lombok.experimental.UtilityClass;

/**
 * @author arloz 2021/4/13
 */
@UtilityClass
public class Console {
    public static final String COMMON = ",";

    /**
     * @param params
     * @param <T>
     */
    public <T> void info(T[] params) {
        if (params == null) {
            System.out.println();
            return;
        }

        StringBuffer sb = new StringBuffer();
        for (T item : params) {
            sb.append(item).append(COMMON);
        }
        System.out.println(sb.deleteCharAt(sb.length() - 1).toString());
    }

    /**
     * @param obj
     */
    public void info(Object obj) {
        System.out.println(obj);
    }
}
