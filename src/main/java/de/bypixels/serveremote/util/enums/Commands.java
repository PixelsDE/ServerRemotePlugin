package de.bypixels.serveremote.util.enums;

import org.bukkit.permissions.Permission;

public enum Commands {

    BAN("ban", Permissions.ADMINISTRATOR),
    KICK("kick", Permissions.ADMINISTRATOR),
    SAY("say", Permissions.MODERATOR);

    public String getCommand() {
        return command;
    }

    public Permissions[] getPermissions() {
        return permissions;
    }

    String command;
    Permissions[] permissions;

    Commands(String command, Permissions... permissions) {
        this.command = command;
        this.permissions = permissions;
    }
}
