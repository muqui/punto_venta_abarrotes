/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author mq12
 */
public class Encryption {
    public static String encrypt(String source) {
String md5 = null;
try {
MessageDigest mdEnc = MessageDigest.getInstance("MD5"); // Encryption algorithm
mdEnc.update(source.getBytes(), 0, source.length());
md5 = new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted string
} catch (Exception ex) {
return null;
}
return md5;
}
}
