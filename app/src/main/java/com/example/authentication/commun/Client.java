package com.example.authentication.commun;

import android.content.Context;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class Client {

    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Client(String address, int port, String username, String password, Context context) {
        try {
            KeyStore truststore = KeyStore.getInstance("PKCS12");
            char[] truststorePassword = "AlexReb123!".toCharArray();

            InputStream keyStoreData = context.getAssets().open("client.truststore");
            truststore.load(keyStoreData, truststorePassword);

            //Security.addProvider(new BouncyCastleProvider());

            String trustMgrFactAlg = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustMgrFact = TrustManagerFactory.getInstance(trustMgrFactAlg);
            trustMgrFact.init(truststore);

            SSLContext clientContext = SSLContext.getInstance("TLS");
            clientContext.init(null, trustMgrFact.getTrustManagers(), null);

            SSLSocketFactory fact = clientContext.getSocketFactory();
            socket = (SSLSocket) fact.createSocket(address, port) ;
            System.out.println("Connected");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("login");
            //out.writeUTF("register");

            String received = in.readUTF();
            System.out.println(received);

            out.writeUTF(username);
            out.writeUTF(password);

            System.out.println(in.readUTF());

            in.close();
            out.close();
            socket.close();

            System.out.println("Connection Closed");

        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException | KeyManagementException e) {
            System.out.println(e);
        }
    }
}
