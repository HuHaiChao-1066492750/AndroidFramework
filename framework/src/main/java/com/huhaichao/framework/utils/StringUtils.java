package com.huhaichao.framework.utils;

import java.util.regex.Pattern;

/**
 * Created by HuHaiChao on 2018/8/7.
 */

public class StringUtils {

    /**
     * 判断字符串是否全为数字
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     *
     */
}
