package de.bypixels.serveremote;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Callable;

public final class ServerRemote extends JavaPlugin {

  private static ServerRemote plugin;

    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        Server.startServer();


    }

    public static ServerRemote getPlugin() {
        return plugin;
    }

    public static void runCommand(String command){
        Bukkit.getScheduler().callSyncMethod(getPlugin(), new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return Bukkit.dispatchCommand( Bukkit.getConsoleSender(), command );
            }
        } );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Server.stopServer();
    }
}
