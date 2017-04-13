package com.lb.core.monitor;

import com.lb.core.AppContext;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by libo on 2017/4/13.
 */
public class TrackerNodeMStatReporter extends AbstractMStatReporter {
    // 执行成功个数
    private AtomicLong exeSuccessNum = new AtomicLong(0);
    // 执行失败个数
    private AtomicLong exeFailedNum = new AtomicLong(0);
    // 延迟执行个数
    private AtomicLong exeLaterNum = new AtomicLong(0);
    // 执行异常个数
    private AtomicLong exeExceptionNum = new AtomicLong(0);
    // 总的运行时间
    private AtomicLong totalRunningTime = new AtomicLong(0);

    public TrackerNodeMStatReporter(AppContext appContext) {
        super(appContext);
    }

    public void incSuccessNum() {
        exeSuccessNum.incrementAndGet();
    }

    public void incFailedNum() {
        exeFailedNum.incrementAndGet();
    }

    public void incExeLaterNum() {
        exeLaterNum.incrementAndGet();
    }

    public void incExeExceptionNum() {
        exeExceptionNum.incrementAndGet();
    }

    public void addRunningTime(Long time) {
        totalRunningTime.addAndGet(time);
    }

    @Override
    protected MData collectMData() {
        TaskTrackerMData mData = new TaskTrackerMData();
        mData.setExeSuccessNum(exeSuccessNum.getAndSet(0));
        mData.setExeFailedNum(exeFailedNum.getAndSet(0));
        mData.setExeLaterNum(exeLaterNum.getAndSet(0));
        mData.setExeExceptionNum(exeExceptionNum.getAndSet(0));
        mData.setTotalRunningTime(totalRunningTime.getAndSet(0));
        return mData;
    }

    @Override
    protected NodeType getNodeType() {
        return NodeType.TASK_TRACKER;
    }
}
