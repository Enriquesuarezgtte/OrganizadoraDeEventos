package es.ujaen.dae.eventosconsolamail.factory;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import es.ujaen.dae.eventosconsolamail.modelo.Usuario.UserType;
import es.ujaen.dae.eventosconsolamail.strategy.StrategyTasa;

@Component
public class StrategyFactory {

	private Map<UserType, StrategyTasa> strategies = new EnumMap<>(UserType.class);
	public StrategyFactory() {
		initStrategies();
	}
	private void initStrategies() {
		
	}
}
