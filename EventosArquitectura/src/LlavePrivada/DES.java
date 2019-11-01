/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LlavePrivada;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 *
 * @author ingenierias
 */
public class DES extends CiframientoLlavePrivada{

    String textocifrado;
    String textoDesencriptado;
    SecretKeyFactory skf;
          static  DESKeySpec desks;
           static SecretKey  sk ;
          static  Cipher cipher;
          static  Cipher dcipher;

    public DES() {
        super();
    }


    @Override
    public String descifrar(String msj) {
     
     
        try {
  
            byte[] utf8 = dcipher.doFinal( Base64.getDecoder().decode(msj));
             return new String(utf8, "UTF8");
        } catch (IOException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }catch (BadPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    // Decode using utf-8
        return null;
 
    }

    @Override
    public String cifrar(String msj) {
        try{
            sk = KeyGenerator.getInstance("DES").generateKey();
            cipher= Cipher.getInstance("DES");
            dcipher= Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, sk);
            dcipher.init(Cipher.DECRYPT_MODE, sk);
            byte[] utf8 = msj.getBytes("UTF8");
            byte[] encr = cipher.doFinal(utf8);
            textocifrado = new String(Base64.getEncoder().encode(encr));
            return textocifrado;
            
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "no";
    }
    
    
    
}
