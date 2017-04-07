package com.lb.core.domain;

import com.alibaba.fastjson.JSON;
import com.lb.core.annotation.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by libo on 2017/4/7.
 */
public class Job implements Serializable {
    private static final long serialVersionUID = -3831257483826495721L;

    @NotNull
    @Getter
    @Setter
    private String taskId;

    // 优先级（数值越大，优先级越低）
    @Getter
    @Setter
    private Integer priority;

    // 提交的节点(可以手动指定)
    @Getter
    @Setter
    private String submitNodeGroup;

    // 执行的节点
    @NotNull
    @Getter
    @Setter
    private String taskTrackerNodeGroup;

    // 额外参数
    private Map<String, String> extParams;

    // 是否需要反馈给客户端， 默认不反馈
    @Getter
    @Setter
    private boolean needFeedBack = false;

    // 任务最大重试次数
    @Getter
    @Setter
    private int maxRetryTimes = 0;

    /**
     * 执行表达式 和 quartz的一样
     * 如果这个为空，表示立即执行
     */
    @Getter
    @Setter
    private String cronExpression;

    /**
     * 重复次数（-1 标识无限重复）
     */
    @Getter
    @Setter
    private int repeatCount = 0;

    /**
     * 重复interval
     */
    @Getter
    @Setter
    private Long repeatInterval;

    /**
     * 任务的最初触发事件
     * 如果设置了 cronExpression, 那么这个字段没用
     */
    @Getter
    @Setter
    private Long triggerTime;

    /**
     * 当任务队列中存在这个任务的时候，是否替换更新
     */
    @Getter
    @Setter
    private boolean replaceOnExist = false;

    /**
     * 是否依赖于上一个执行周期（对于周期性任务才起作用）
     */
    @Getter
    @Setter
    private boolean relyOnPrevCycle = true;

    public String getParam(String key) {
        if (extParams == null) {
            return null;
        }
        return extParams.get(key);
    }

    public void setParam(String key, String value) {
        if (extParams == null) {
            extParams = new HashMap<String, String>();
        }
        extParams.put(key, value);
    }


    public boolean isRepeatable() {
        return (this.repeatInterval != null && this.repeatInterval > 0) && (this.repeatCount >= -1 && this.repeatCount != 0);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * 检查任务属性设置是否合理
     * @throws Exception 任务属性初始化异常
     */
    public void checkField() throws Exception{

    }

}




























