package es.ujaen.dae.eventosconsolamail.factory;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import es.ujaen.dae.eventosconsolamail.modelo.Usuario.UserType;
import es.ujaen.dae.eventosconsolamail.strategy.StrategyTasa;
import es.ujaen.dae.eventosconsolamail.strategy.StrategyTasaImplementNormal;
import es.ujaen.dae.eventosconsolamail.strategy.TasaImplementAdmin;
import es.ujaen.dae.eventosconsolamail.strategy.TasaImplementGold;

@Component
public class StrategyFactory {

	private Map<UserType, StrategyTasa> strategies = new EnumMap<>(UserType.class);
	public StrategyFactory() {
		initStrategies();
	}
	
	  public StrategyTasa getStrategy(UserType userType) {
	       if (userType == null || !strategies.containsKey(userType)) {
	           throw new IllegalArgumentException("Invalid " + userType);
	       }
	       return strategies.get(userType);
	   }
	
	
	private void initStrategies() {
		strategies.put(UserType.normal, new StrategyTasaImplementNormal());
		strategies.put(UserType.gold, new TasaImplementGold());
		strategies.put(UserType.administrador, new TasaImplementAdmin());
	}
}
