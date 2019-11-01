/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LlavePrivada;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES extends CiframientoLlavePrivada {
    private static String secret = "¡KonradLorenz!";
        private static SecretKeySpec secretKey;
    private static byte[] key;
    private String textCifrado, textDescrifrado;
    
    public static void setKey(String ownKey){
        MessageDigest sha = null;
        try {
            key = ownKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key,16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public String descifrar(String msj) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(msj)));
        } catch (Exception ex) {
            System.out.println("Error descencripción en LlavePrivada.AES.descifrar()");
        }
        return "";
    }

    @Override
    public String cifrar(String msj) {
        setKey(secret);
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            textCifrado = Base64.getEncoder().encodeToString(cipher.doFinal(msj.getBytes("UTF-8")));
            return textCifrado;
        } catch(Exception e){
            System.out.println("Error encripción en LlavePrivada.AES.cifrar()");
        }
        return "";
    }
    
    
    
}
