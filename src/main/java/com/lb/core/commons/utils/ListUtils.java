package com.lb.core.commons.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by libo on 2017/4/7.
 */
public class ListUtils {

    /**
     * 过滤
     */
    public static <E> List<E> filter(final List<E> list, Filter<E> filter){
        List<E> newList = new ArrayList<>();
        if (list != null && list.size() != 0){
            for (E e : list) {
                if (filter.filter(e))
                    newList.add(e);
            }
        }
        return newList;
    }

    public interface Filter<E> {
        /**
         * 如果满足需求就返回true
         * @param e
         * @return
         */
        public boolean filter(E e);
    }
}