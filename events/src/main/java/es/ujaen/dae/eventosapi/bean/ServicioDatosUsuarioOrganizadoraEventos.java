package es.ujaen.dae.eventosapi.bean;


import es.ujaen.dae.eventosapi.exception.CamposVaciosException;
import es.ujaen.dae.eventosapi.exception.UsernameNotFoundException;
import es.ujaen.dae.eventosapi.exception.UsuarioNoRegistradoNoEncontradoException;
import es.ujaen.dae.eventosapi.modelo.Usuario;

public class ServicioDatosUsuarioOrganizadoraEventos{
	OrganizadoraEventosImp organizadoraEventos = new OrganizadoraEventosImp();

	
	public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = null;
		try {
			usuario = organizadoraEventos.obtenerUsuario(username).toEntity();
		} catch (UsuarioNoRegistradoNoEncontradoException e) {} catch (CamposVaciosException e) {}
		
		if(usuario==null) {
			throw new UsernameNotFoundException(username);
		}
		return usuario;
	}
}
