package de.bypixels.serveremote.util.enums;

import de.bypixels.serveremote.util.Crypter;

public enum Users {


    PIXELSDE("PixelsDE", "ZGFuaWVsMjkxMg==", Permissions.ADMINISTRATOR),
    BYPIXELS("byPixels", "ZGFuaWVsMjkxMg==", Permissions.MODERATOR);

    String name, password;
    Permissions rank;

    Users(String name, String password, Permissions rank) {
        this.name = name;
        this.password = password;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return Crypter.decode(password);
    }

    public Permissions getRank() {
        return rank;
    }

    public boolean hasPermissions(Permissions permission) {
        return getRank().equals(permission) ? true : false;
    }
}
