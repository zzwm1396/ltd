package com.lb.core.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by libo on 2017/4/7.
 */
public class CollectionUtils {
    private CollectionUtils(){

    }

    public static <T> List<T> newArrayListOnNull(List<T> list){
        if (list == null){
            list = new ArrayList<>();
        }
        return list;
    }

    public static <T> List<T> setToList(Set<T> set){
        if (set == null){
            return null;
        }
        return new ArrayList<>(set);
    }
}
