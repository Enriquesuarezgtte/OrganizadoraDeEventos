package es.ujaen.dae.eventosconsolamail.strategy;

import es.ujaen.dae.eventosconsolamail.modelo.Usuario;
import es.ujaen.dae.eventosconsolamail.modelo.Usuario.UserType;

public class StrategyTasaImplementNormal implements StrategyTasa{

	@Override
	public void cambiarLimiteTasa(Usuario usuario) {
		// TODO Auto-generated method stub
		usuario.setTipoUsuario(UserType.normal);
		usuario.setLimiteCreacionesEventos(30d);
	}

}
