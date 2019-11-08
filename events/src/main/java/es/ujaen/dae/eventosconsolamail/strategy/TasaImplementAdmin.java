package es.ujaen.dae.eventosconsolamail.strategy;

import es.ujaen.dae.eventosconsolamail.modelo.Usuario;
import es.ujaen.dae.eventosconsolamail.modelo.Usuario.UserType;

public class TasaImplementAdmin implements StrategyTasa{

	@Override
	public void cambiarLimiteTasa(Usuario usuario) {
		usuario.setTipoUsuario(UserType.administrador);
		usuario.setLimiteCreacionesEventos(1000d);		
	}

}
