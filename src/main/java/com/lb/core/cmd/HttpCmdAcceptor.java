package com.lb.core.cmd;

import com.lb.core.constant.Constants;
import com.lb.core.factory.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * httpCmd 命令监听器
 * Created by libo on 2017/4/17.
 */
@Slf4j
public class HttpCmdAcceptor {

    private final AtomicBoolean startd = new AtomicBoolean(false);

    private final ExecutorService executorService;

    private ServerSocket serverSocket;

    private Thread thread;

    private HttpCmdContext context;

    public HttpCmdAcceptor(ServerSocket serverSocket, HttpCmdContext context){
        this.context = context;
        this.serverSocket = serverSocket;
        this.executorService = new ThreadPoolExecutor(Constants.AVAILABLE_PROCESSOR,
                Constants.AVAILABLE_PROCESSOR,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(100),
                new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    /**
     * 启动命令监听器
     */
    public void start() {

        if (!startd.compareAndSet(false, true)) {
            // 如果已经启动了,就不重复启动
            return;
        }

        if (thread == null) {
            this.thread = new NamedThreadFactory("HTTP-CMD-ACCEPTOR", true).newThread(
                    new Runnable() {
                        @Override
                        public void run() {

                            while (isStarted()) {
                                try {
                                    Socket socket = serverSocket.accept();
                                    if (socket == null) {
                                        continue;
                                    }
                                    executorService.submit(new HttpCmdExecutor(context, socket));

                                } catch (Throwable t) {
                                    log.error("Accept error ", t);
                                    try {
                                        Thread.sleep(1000); // 1s
                                    } catch (InterruptedException ignored) {
                                    }
                                }
                            }

                        }
                    }
            );
        }
        // 启动线程
        thread.start();

        log.info("HttpCmdAcceptor start succeed ");
    }

    /**
     * 停止命令监听器
     */
    public void stop() {
        try {
            if (startd.compareAndSet(true, false)) {
                executorService.shutdown();
                log.info("HttpCmdAcceptor stop succeed ");
            }
        } catch (Throwable t) {
            log.error("HttpCmdAcceptor stop error ", t);
        }
    }

    private boolean isStarted() {
        return startd.get();
    }
}
