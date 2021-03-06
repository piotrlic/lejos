package com.tomtom.lejos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Rupert Young
 */
public class SocketClient {

    private final String server;
    private final int port;
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;

    public SocketClient(String server, int port) {
        this.server = server;
        this.port = port;
    }
 
    public void connect() throws IOException {
        System.out.println("Connecting to " + server + " on port " + port);
        client = new Socket(server, port);
        System.out.println("Just connected to " + client.getRemoteSocketAddress());
        in = new DataInputStream(client.getInputStream());
        out = new DataOutputStream(client.getOutputStream());
    }

    public String receive() throws IOException, EOFException {
        String message =  in.readUTF();

        //out.writeUTF(response);
        return message;
    }
    public void sendMessage(String message) throws IOException{
    	out.writeUTF(message);
    }
    public boolean isConnected() {
        if(client==null)
            return false;
        return client.isConnected();
    }

    public void close() throws IOException {
        client.close();
    }

    public static void main(String[] args) throws InterruptedException {
        String serverName = args[0];
        int port = Integer.parseInt(args[1]);

        SocketClient sc = new SocketClient(serverName, port);
        String message = null;
        try {
            sc.connect();

            while(true){
                /*sc.sendMessage("FORWARD:180");
                sc.sendMessage("FORWARD:-90");
                sc.sendMessage("TURN:-90");*/
                /*sc.sendMessage("DRIVE_F");
                Thread.sleep(2000);
                sc.sendMessage("STOP");
                Thread.sleep(1000);*/
                //sc.sendMessage("HELLO");
                /*sc.sendMessage("GOTO:10,10");
                sc.sendMessage("GOTO:10,-10");
                sc.sendMessage("GOTO:0,0");*/
            	sc.sendMessage("HELLO");
                Thread.sleep(5000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}