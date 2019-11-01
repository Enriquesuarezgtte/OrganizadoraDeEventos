/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LlavePrivada;

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

/**
 *
 * @author ingenierias
 */
public class TRESDES extends CiframientoLlavePrivada {
 private KeyGenerator key;
	private SecretKey clave;
	private Cipher cipher;
        private String textoCifrado, textoDescfrado;
	
	public TRESDES() {
		generar();
	}
	
	public void generar() {
		try {
			key = KeyGenerator.getInstance("DESede");
			clave = key.generateKey();
			cipher = Cipher.getInstance("DESede");
		} catch (NoSuchAlgorithmException e) {
			e.getMessage();
		} catch (NoSuchPaddingException e) {
			e.getMessage();		}
		
	}
	
	@Override
	public String cifrar(String mensaje) {
		try {
			
			cipher.init(Cipher.ENCRYPT_MODE, clave);			
			textoCifrado = Base64.getEncoder().encodeToString(cipher.doFinal(mensaje.getBytes("UTF-8")));
            return textoCifrado;
		} catch (InvalidKeyException e) {
			e.getMessage();
		} catch (IllegalBlockSizeException e) {
			e.getMessage();
		} catch (BadPaddingException e) {
			e.getMessage();
		} catch (UnsupportedEncodingException ex) {
         Logger.getLogger(TRESDES.class.getName()).log(Level.SEVERE, null, ex);
     }
		
		return null;
		
	}

	@Override
	public String descifrar(String mensaje) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, clave);			
            return new String(cipher.doFinal(Base64.getDecoder().decode(mensaje)));
		} catch (InvalidKeyException e) {
			e.getMessage();
		} catch (IllegalBlockSizeException e) {
			e.getMessage();
		} catch (BadPaddingException e) {
			e.getMessage();
		}			

		return null;
	}
}