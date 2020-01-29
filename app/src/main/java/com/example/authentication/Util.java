package com.example.authentication;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.util.concurrent.Callable;

public class Util {
    public interface  BlockingCallable extends Callable {
        void await() throws InterruptedException;
    }

    public static class Task implements Runnable {
        private final Callable callable;
        public Task(Callable callable)
        {
            this.callable = callable;
        }
        public void run() {
            try {
                callable.call();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                if (e.getCause() != null) {
                    e.getCause().printStackTrace(System.err);
                }
            }
        }
    }

    public static void runClientAndServer(BlockingCallable server, BlockingCallable client)
        throws InterruptedException
    {
        new Thread(new Util.Task(server)).start();
        server.await();
        new Thread(new Util.Task(client)).start();
        client.await();
    }

    public static void doClientProtocol(Socket sock, String text)
        throws IOException
    {
        OutputStream out = sock.getOutputStream();
        InputStream in = sock.getInputStream();
        out.write(text.getBytes());
        out.write('!');
        int ch = 0;
        while ((ch = in.read()) != '!') {
            System.out.print((char)ch);
        }
        System.out.println((char)ch);
    }

    public static void doServerProtocol (Socket sock, String text)
        throws IOException
    {
        OutputStream out = sock.getOutputStream();
        InputStream in = sock.getInputStream();
        int ch = 0;
        while ((ch = in.read()) != '!'){
            System.out.print((char)ch);
        }
        out.write(text.getBytes());
        out.write('!');
        System.out.println((char)ch);
    }

    public static void main(String[] args) throws NoSuchProviderException, KeyStoreException, InterruptedException {

        KeyStore trustStore =  KeyStore.getInstance("BCFKS", "BCFIPS");
        KeyStore serverStore =  KeyStore.getInstance("BCFKS", "BCFIPS");

        char [] passKey = {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};

        runClientAndServer(new SimpleServer(serverStore, passKey), new SimpleClient(trustStore));
    }
}
