package de.bypixels.serveremote.util.clients;

import de.bypixels.serveremote.util.Crypter;
import de.bypixels.serveremote.util.Utils;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class ReadData extends Thread{

    Client client;

    public ReadData(Client client) {
        this.client = client;
    }


    @SuppressWarnings("deprecation")
    public void run(){
        while (!client.getClient().isClosed()){
            try {
                InputStream inputStream = client.getClient().getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String data = null;

                while (!client.getClient().isClosed() && (data = reader.readLine()) != null){
                    client.readData(Crypter.decode(data));
                }
            } catch (Exception e) {

              try {
                  if (!client.getClient().isClosed())
                  client.sendData("#ERROR");
              }catch (Exception exception){
                  System.out.println("ERROR #10");
              }
                Utils.connectedClients.remove(this.client);
                e.printStackTrace();
                try {
                    client.getClient().close();
                    this.stop();
                } catch (Exception error) {
                    System.out.println("ERROR #4");
                }
            }
        }
    }
}
