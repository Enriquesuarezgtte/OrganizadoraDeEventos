/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LlavePrivada;

import javax.crypto.SecretKey;

import cifrado.ICifrado;

/**
 *
 * @author ingenierias
 */
public class CiframientoLlavePrivada implements ICifrado {

    SecretKey llavePrivada;

    @Override
    public String cifrar(String msj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String descifrar(String msj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
}
