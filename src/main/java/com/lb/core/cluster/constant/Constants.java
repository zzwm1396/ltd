package com.lb.core.cluster.constant;

import java.util.regex.Pattern;

/**
 * 一些配置常量
 * Created by libo on 2017/4/14.
 */
public interface Constants {
    /**
     * 匹配逗号，包含逗号前后有任意多空白字符的情况
     */
    Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");
}
