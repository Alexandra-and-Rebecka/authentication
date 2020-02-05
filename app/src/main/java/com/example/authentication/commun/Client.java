package com.example.authentication.commun;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Client {

    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Client( String address, int port, String username, String password) {
        try {
            socket = new Socket(address, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            String received = in.readUTF();
            System.out.println(received);

            out.writeUTF(username);
            out.writeUTF(password);

            in.close();
            out.close();
            socket.close();

            System.out.println("Connection Closed");


        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
