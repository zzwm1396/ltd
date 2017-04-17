package com.lb.core.cmd;

import com.lb.core.commons.utils.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * httpCmd上下文信息
 * Created by libo on 2017/4/17.
 */
public class HttpCmdContext {
    private ReentrantLock lock = new ReentrantLock();

    private final Map</*节点标识*/String, Map</*cmd标识*/String, HttpCmdProc>>
            NODE_PROCESSOR_MAP = new HashMap<String, Map<String, HttpCmdProc>>();


    public void addCmdProcessor(HttpCmdProc proc) {
        if (proc == null) {
            throw new IllegalArgumentException("proc can not be null");
        }

        String identity = proc.getNodeIdentity();
        Assert.hasText(identity, "nodeIdentity can't be empty");

        String command = proc.getCommand();
        Assert.hasText(command, "command can't be empty");

        Map<String, HttpCmdProc> cmdProcessorMap = NODE_PROCESSOR_MAP.get(identity);
        if (cmdProcessorMap == null) {
            lock.lock();
            cmdProcessorMap = NODE_PROCESSOR_MAP.get(identity);
            if (cmdProcessorMap == null) {
                cmdProcessorMap = new ConcurrentHashMap<String, HttpCmdProc>();
                NODE_PROCESSOR_MAP.put(identity, cmdProcessorMap);
            }
            lock.unlock();
        }
        cmdProcessorMap.put(command, proc);
    }

    public HttpCmdProc getCmdProcessor(String nodeIdentity, String command) {
        Assert.hasText(nodeIdentity, "nodeIdentity can't be empty");

        Map<String, HttpCmdProc> cmdProcessorMap = NODE_PROCESSOR_MAP.get(nodeIdentity);
        if (cmdProcessorMap == null) {
            return null;
        }
        return cmdProcessorMap.get(command);
    }
}
