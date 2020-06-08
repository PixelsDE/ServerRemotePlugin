package de.bypixels.serveremote;

import de.bypixels.serveremote.util.Utils;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static ServerSocket server = null;

    private static int port = 9999;


    public Server() {
    }

    public static void startServer() {
        try {
            ServerSocket server = new ServerSocket(getPort());
            setServer(server);
            Utils.connectedClients.clear();
            System.out.println("SERVER STARTED! on PORT: " + getPort());
            Thread waitForConnection = new Thread(new WaitForConnection());
            waitForConnection.start();
        } catch (IOException e) {
            System.out.println("ERROR! #7");
        }

    }

    public static void stopServer() {

        if (getServer() != null) {
            try {
                getServer().close();
            } catch (IOException e) {
                System.out.println("ERROR! #8");
            }
        }
        System.out.println("SERVER STOPPED! on PORT: " + getPort());

    }


    public static ServerSocket getServer() {
        return server;
    }

    public static void setServer(ServerSocket server) {
        Server.server = server;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Server.port = port;
    }
}
