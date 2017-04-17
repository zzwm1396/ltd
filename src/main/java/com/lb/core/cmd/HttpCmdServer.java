package com.lb.core.cmd;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * HttpCmdServer
 * Created by libo on 2017/4/17.
 */
@Slf4j
@Getter
@Setter
public class HttpCmdServer {
    private final AtomicBoolean start = new AtomicBoolean(false);
    private HttpCmdAcceptor acceptor;
    private int port;
    private String bindAddr;
    private HttpCmdContext context;
    private int portFindTimes;

    private HttpCmdServer(String bindAddr, int port) {
        this.port = port > 0 ? port : 8719;
        this.bindAddr = bindAddr;
        this.context = new HttpCmdContext();
    }

    public void start() throws HttpCmdException {
        try {
            if (start.compareAndSet(false, true)) {
                // 开启监听命令
                acceptor = new HttpCmdAcceptor(getServerSocket(), context);
                acceptor.start();
                log.info("\n===========================================================================================\n" +
                        "Start succeed at port {}\n" +
                        "===========================================================================================", port);
            }
        } catch (Exception t) {
            log.error("Start error at port {}", port, t);
            throw new HttpCmdException(t);
        }
    }

    private ServerSocket getServerSocket() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port, 100);
            serverSocket.setReuseAddress(true);
        } catch (BindException e) {
            port = port + 1;
            serverSocket = getServerSocket();
            if (portFindTimes++ > 50) {
                throw e;
            }
        }
        return serverSocket;
    }

    public void stop() {
        if (acceptor != null) {
            acceptor.stop();
        }
    }


    public void registerCommand(HttpCmdProc proc) {
        context.addCmdProcessor(proc);
    }

    public void registerCommands(HttpCmdProc... procs) {
        if (procs != null && procs.length > 0) {
            for (HttpCmdProc proc : procs) {
                this.registerCommand(proc);
            }
        }
    }

    /**
     * 保证一个jvm公用一个 HttpCmdServer
     */
    public static class Factory {

        private static HttpCmdServer httpCmdServer;

        public static HttpCmdServer getHttpCmdServer(String bindAddr, int port) {
            if (httpCmdServer != null) {
                return httpCmdServer;
            }
            synchronized (Factory.class) {
                if (httpCmdServer != null) {
                    return httpCmdServer;
                }
                httpCmdServer = new HttpCmdServer(bindAddr, port);
                return httpCmdServer;
            }
        }
    }
}
