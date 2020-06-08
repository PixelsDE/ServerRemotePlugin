package de.bypixels.serveremote.util;

import java.util.Base64;

public class Crypter {


    private static String code = null;
    public static String encrypt(String text){

        try {
            setCode(Base64.getEncoder().encodeToString(text.getBytes()));
            return getCode();
        }catch (Exception e){
            System.out.println("ERROR! 5");
        }
        return null;
    }

    public static String  getCode() {
        return code;
    }

    public static String decode(String code){
        try {
            return new String(Base64.getDecoder().decode(code));
        } catch (Exception e) {
            System.out.println("ERROR! #6");
        }
        return null;
    }

    public static void setCode(String code) {
        Crypter.code = code;
    }
}
