package economy;

import java.util.HashMap;

public class Acre implements IAcre {
	private HashMap<Food, Double> _yield = new HashMap<Food, Double>();
	
	public Acre (HashMap<Food, Double> yields) {
		_yield = yields;
	}
	
	public double yield(Food food) {
		return _yield.get(food);
	}
}
