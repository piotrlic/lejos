package com.tomtom.lejos.stefan;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Rupert Young
 */
public class SocketServer {

    private final ServerSocket serverSocket;
    private Socket server;
    private DataOutputStream out;
    private DataInputStream in;

    public SocketServer(int port, int timeout) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(timeout);
    }

    public void connect() throws IOException {
        System.out.println("Wait on " + serverSocket.getLocalPort() + "...");
        server = serverSocket.accept();

        System.out.println("Connected to " + server.getRemoteSocketAddress());
        out = new DataOutputStream(server.getOutputStream());
        in = new DataInputStream(server.getInputStream());
    }

    public int getPort() {
        int port = 0;
        if (serverSocket != null) {
            port = serverSocket.getLocalPort();
        }
        return port;
    }

    public boolean isConnected() {
        if (server == null) {
            return false;
        }
        return server.isConnected();
    }

    public String socketStatus() {
        return server == null ? "null" : "not null";
    }

    public void connectionLost() {
        server = null;
    }

    public String receive() throws IOException{
    	return in.readUTF();
    }
    public String sendAndReceive(String message) throws IOException {
        out.writeUTF(message);
        String response = in.readUTF();
        return response;
    }
}