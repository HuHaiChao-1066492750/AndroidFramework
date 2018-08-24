package com.huhaichao.framework.utils;

import android.content.Context;

import com.blankj.utilcode.util.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by HuHaiChao on 2018/8/7.
 */

public class CollectionUtilsTest {
    private Context mContext;
    private String name;
    private int age;

    /**
     * 数组转列表
     */
    public static <T> List<T> ArrayToList(T[] array) {
        if (array.length < 0) {
            return null;
        }
        return Arrays.asList(array);
    }

    /**
     * 数组转SET
     */
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        System.out.println(com.blankj.utilcode.util.TimeUtils.millis2String(Long.parseLong("1534394598664"),sdf));
        String shoumai="12:43:18";
        String danqian=com.blankj.utilcode.util.TimeUtils.millis2String(Long.parseLong("1534394598664"),sdf);
        System.out.println(shoumai.compareTo(danqian));
    }

    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    @Override
    public int hashCode() {
        //经典方式
        int result = 17;
        result = 31 * result + mContext.hashCode();
//        result = 31 * result + name.hashCode();
//        result = 31 * result + age;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj != null && obj.getClass() == CollectionUtilsTest.class) {
            CollectionUtilsTest c = (CollectionUtilsTest) obj;
            return this.mContext == c.mContext;
        }
        return false;
    }

    public void test() {
        Map<CollectionUtilsTest, String> map = new HashMap<>();
        map.put(new CollectionUtilsTest(), "66");
        map.containsValue("66");
        List<Map<String, String>> list = new ArrayList<>();

    }
}
