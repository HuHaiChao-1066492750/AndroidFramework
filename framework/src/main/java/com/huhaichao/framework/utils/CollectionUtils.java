package com.huhaichao.framework.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

/**
 * Created by HuHaiChao on 2018/8/7.
 */

public class CollectionUtils {

    /**
     * Array转List
     */
    public static <T> List<T> arrayToList(T[] array) {
        if (array.length < 0) {
            return null;
        }
        return Arrays.asList(array);
    }

    /**
     * List转Array
     */
    public static <T> T[] listToArray(List<T> list, T[] t) {
        return list.toArray(t);
    }

    /**
     * List转Set(不允许相同的元素)->所谓相同：equals()和 hashCode()
     */
    public static <T> Set<T> listToSet(List<T> list) {
        return new HashSet<>(list);
    }

    /**
     * Set转List
     */
    public static <T> List<T> setToList(Set<T> set) {
        return new ArrayList<>(set);
    }

    /**
     * Map转List
     */
    public static <K, V> List<V> mapToList(Map<K, V> map) {
        Collection<V> collection = map.values();
        return new ArrayList<>(collection);
    }

    /**
     * 排序
     */
    public static <T> void sort(List<T> list, Comparator<T> comparator) {
        Collections.sort(list, comparator);
    }

    public static <T> void sort(T[] array, Comparator<T> comparator) {
        Arrays.sort(array, comparator);
    }

    /**
     * 取值
     */


    /**
     * 包含
     */
    public static <T> boolean contains(List<T> list, T t) {
        return list.contains(t);
    }

    public static <T> boolean containsAll(List<T> list, List<T> list1) {
        return list.containsAll(list1);
    }

    /**
     * 查找
     */

    /**
     * 删除
     */
    public static <T> void remove(List<T> list, T t) {
        list.remove(t);
//
//        if (list.isEmpty())
//            return;
//        Iterator<T> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            T result = iterator.next();
//            if (result.equals(t))
//                list.remove(t);
//        }
    }

    /**
     * 遍历
     */
    public static <T> void ergodic(List<T> list) {
        if (list instanceof RandomAccess) {
            //使用传统的for循环遍历。
            int size = list.size();
            for (int i = 0; i < size; i++) {
                list.get(i);
            }
        } else {
            //使用Iterator或者foreach。
//            Iterator<T> iterator = list.iterator();
//            while (iterator.hasNext()) {
//                T t = iterator.next();
//            }
            for (T t : list) {
            }
        }
    }

}
