package com.company.internapp2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface Encryption {
    public static String getEncryptPass(String plainpassword) throws NoSuchAlgorithmException {


    String encryptedpass = null;

    MessageDigest messageDigest =  MessageDigest.getInstance("MD5");

		messageDigest.update(plainpassword.getBytes());
    byte[] pass = messageDigest.digest();
		System.out.println(pass);
    StringBuffer sb = new StringBuffer();
		for(byte b :pass) {
        sb.append(b);

    }

    encryptedpass = sb.toString();
    //System.out.println("enc "+ encryptedpass);
		return encryptedpass;

}
}
