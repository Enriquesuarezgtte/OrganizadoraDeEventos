package es.ujaen.dae.eventosapi.bean;

import es.ujaen.dae.eventosapi.exception.CamposVaciosException;
import es.ujaen.dae.eventosapi.exception.UsuarioNoRegistradoNoEncontradoException;
import es.ujaen.dae.eventosapi.modelo.Usuario;

public class ServicioDatosUsuarioOrganizadoraEventos {

	OrganizadoraEventosImp organizadoraEventos;
	
	public void loadUser(String username) {
		Usuario usuario = null;
	
			try {
				usuario = organizadoraEventos.obtenerUsuario(username).toEntity();
			} catch (UsuarioNoRegistradoNoEncontradoException | CamposVaciosException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		if(usuario==null) {
			throw new UsuarioNoRegistradoNoEncontradoException();
		}
	}
	
		
	
}
