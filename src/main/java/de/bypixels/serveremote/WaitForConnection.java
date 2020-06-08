package de.bypixels.serveremote;

import de.bypixels.serveremote.util.clients.Client;
import de.bypixels.serveremote.util.Utils;

import java.net.Socket;

public class WaitForConnection extends Thread {

    public void run() {
        while (!Server.getServer().isClosed()) {
            try {
                Socket client = Server.getServer().accept();
                System.out.println("["+ client.getInetAddress().getHostName()+"]" + " Client Connected -> Try Auth");

                Client clientClass = new Client(client);


            } catch (Exception e) {
                System.out.println("ERROR! #9" );
            }
        }
    }

}
