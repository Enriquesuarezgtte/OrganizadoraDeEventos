package es.ujaen.dae.eventosconsolamail.strategy;

import es.ujaen.dae.eventosconsolamail.modelo.Usuario;
import es.ujaen.dae.eventosconsolamail.modelo.Usuario.UserType;

public class TasaImplementGold implements StrategyTasa {

	@Override
	public void cambiarLimiteTasa(Usuario usuario) {
		usuario.setTipoUsuario(UserType.gold);
		usuario.setLimiteCreacionesEventos(100d);		
	}

}
