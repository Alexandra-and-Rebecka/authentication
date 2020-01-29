package com.example.authentication;

import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SimpleServer implements Util.BlockingCallable{

    private  final KeyStore serverStore;
    private final char[] keyPass;
    private final CountDownLatch latch;

    public SimpleServer(KeyStore serverStore, char[] keyPass){
        this.serverStore = serverStore;
        this.keyPass = keyPass;
        this.latch = new CountDownLatch(1);
    }

    public void await() throws InterruptedException {
        latch.await();
    }

    public Object call() throws Exception {
        KeyManagerFactory keyMgrFact = KeyManagerFactory.getInstance("SunX509");
        keyMgrFact.init(serverStore, keyPass);

        SSLContext serverContext = SSLContext.getInstance("TLS");
        serverContext.init(
                keyMgrFact.getKeyManagers(), null, SecureRandom.getInstance("DEFAULT", "BCFIPS")
        );

        SSLServerSocketFactory fact = serverContext.getServerSocketFactory();
        SSLServerSocket sSock = (SSLServerSocket)fact.createServerSocket(443);

        latch.countDown();

        SSLSocket sslSock = (SSLSocket)sSock.accept();
        Util.doServerProtocol(sslSock, "World");

        return null;
    }
}
