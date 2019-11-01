package cifrado;

public class Contexto implements ICifrado{
    private ICifrado cifrado;

    public Contexto(ICifrado cifrado) {
        this.cifrado = cifrado;
    }

    @Override
    public String cifrar(String msj) {
        return cifrado.cifrar(msj);
    }

    @Override
    public String descifrar(String msj) {
        return cifrado.descifrar(msj);
    }
   
    
    
    
    
    
}
