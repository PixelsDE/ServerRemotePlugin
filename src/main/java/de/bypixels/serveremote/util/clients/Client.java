package de.bypixels.serveremote.util.clients;

import com.lmax.disruptor.util.Util;
import com.mojang.authlib.yggdrasil.response.User;
import de.bypixels.serveremote.ServerRemote;
import de.bypixels.serveremote.util.Crypter;
import de.bypixels.serveremote.util.Utils;
import de.bypixels.serveremote.util.enums.Commands;
import de.bypixels.serveremote.util.enums.Permissions;
import de.bypixels.serveremote.util.enums.Users;
import it.unimi.dsi.fastutil.Stack;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.digest.Crypt;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

public class Client {

    private Socket client = null;
    private boolean isAuth = false;
    private Users user = null;

    public Client(Socket client) {
        this.client = client;

        Thread readData = new Thread(new ReadData(this));
        readData.start();
    }

    public void sendData(String data) {
        data = Crypter.encrypt(data);
        try {
            OutputStream outputStream = client.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
            writer.write(data + "\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("ERROR #1");
        }
    }


    public void auth(String data) {
        try {
            for (Client clients : Utils.connectedClients) {
                if (clients.getClient().getInetAddress().equals(client.getInetAddress())) {
                    System.out.println("[" + clients.getClient().getInetAddress().getHostName() + "] is already connected!");
                    sendData("#ALREADY");
                    return;
                }
            }

            String decodedKey = data;
            if (decodedKey.startsWith("#AUTH")) {
                String[] splitter = decodedKey.replace("#AUTH", "").split(";");
                String username = splitter[0];
                String password = splitter[1];

                for (Users user : Users.values()) {
                    if (user.getName().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                        System.out.println("[" + client.getInetAddress().getHostName() + "] Sucessfully Auth -> " + user.getName());

                        setUser(user);
                        Utils.connectedClients.add(this);
                        setAuth(true);
                        sendData("#OK");
                        sendData("#LOGIN" + user.getName() + ";" + user.getRank().toString());
                        return;
                    }else{

                        send(i);
                        i++;

                    }
                }

                Utils.connectedClients.remove(this);
                setAuth(false);
                sendData("#FAIL");
                System.out.println("[" + client.getInetAddress().getHostName() + "] Failed Auth -> " + "Closed Connection!");
                client.close();
            }else {
                System.out.println("STARTED NICHT MIT #AUTH!");
            }
        } catch (Exception e) {
            System.out.println("ERROR #1");
        }
    }


    int i = 0;
    private void send(int i){
        if (i== 0)
        System.out.println("Falsche Logindaten verwendet!");
    }


    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }


    private void withCommandsPremade(String data){
        for (Commands cmd : Commands.values()) {
            System.out.println(cmd.getCommand());
            if (data.startsWith(cmd.getCommand())) {
                boolean permission = false;
                for (Permissions permissions : cmd.getPermissions()) {
                    System.out.println(user.getRank());
                    if (user.hasPermissions(permissions)) {
                        permission = true;
                    }
                }
                if (permission == true) {
                    try {

                        ServerRemote.runCommand(data);
                    } catch (CommandException e) {
                        e.printStackTrace();
                    }
                    System.out.println("[" + user.getName() + "] Performed Command: " + data);
                    Bukkit.broadcastMessage(user.getName() + " ->" + data);
                    permission = false;
                }else{
                    System.out.println("[" + user.getName() + "] Tried to perform Command: " + data);
                    sendData("#PERM");
                }


            }else {

                System.out.println("[" + user.getName() + "] Tried to perform Command: " + data);
                sendData("#PERM");

            }
            return;
        }
    }

    private void justSendCommand(String data){
        ServerRemote.runCommand(data);
    }

    public void readData(String data) {
        if (isAuth) {

            justSendCommand(data);
          //  withCommandsPremade(data);
            System.out.println("[" + user.getName() + "] Tried to perform Command: " + data);
            sendData("#CMD");
            return;
        } else {
            auth(Crypter.decode(data));
        }

    }

    public Socket getClient() {
        return client;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }
}
