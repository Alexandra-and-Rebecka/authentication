package com.example.authentication;

import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class SimpleClient implements Util.BlockingCallable {

    private final KeyStore trustStore;
    private final CountDownLatch latch;

    public SimpleClient(KeyStore trustStore){
        this.trustStore = trustStore;
        this.latch = new CountDownLatch(1);
    }

    public void await() throws InterruptedException {
        latch.await();
    }

    public Object call() throws Exception {
        TrustManagerFactory trustMgrFact = TrustManagerFactory.getInstance("SunX509");
        trustMgrFact.init(trustStore);

        SSLContext clientContext = SSLContext.getInstance("TLS");
        clientContext.init( null, trustMgrFact.getTrustManagers(), SecureRandom.getInstance("DEFAULT", "BCFIPS"));

        SSLSocketFactory fact = clientContext.getSocketFactory();
        SSLSocket cSock = (SSLSocket)fact.createSocket("192.168.56.1", 443);
        Util.doClientProtocol(cSock, "Hello");

        latch.countDown();
        return null;
    }
}
