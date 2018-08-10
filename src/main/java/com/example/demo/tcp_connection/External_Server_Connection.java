package com.example.demo.tcp_connection;

import org.json.simple.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class External_Server_Connection extends Thread {
    public Socket socket;
    public ClientSender clientSender;
    public Thread sender, receiver;

    public External_Server_Connection(String addr) {
        try {
            socket = new Socket(addr, 6000);
            clientSender = new ClientSender(socket, "main_car");
            sender = new Thread(clientSender);
            receiver = new Thread(new ClientReceiver(socket));
            sender.start();
            receiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ClientSender extends Thread {
        Socket socket;
        DataOutputStream out;
        String name;

        public ClientSender(Socket socket, String name) {
            this.socket = socket;
            try {
                this.out = new DataOutputStream(this.socket.getOutputStream());
                this.name = name;
            } catch (Exception e) {
            }
        }

        public void run() {
            try {
                if (out != null) {
                    this.out.writeUTF(name);
                    this.out.flush();
                    System.out.println(name);
                    JSONObject jsonObject = new JSONObject();

                    while (this.out != null) {
                    }
                }

            } catch (IOException e) {
            }
        }
    }

    class ClientReceiver extends Thread {
        Socket socket;
        DataInputStream in;

        public ClientReceiver(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
            }
        }

        public void run() {
            while (in != null) {
                try {
                    System.out.println(in.readUTF());
                } catch (IOException e) {
                }
            }
        } // run
    }
}


