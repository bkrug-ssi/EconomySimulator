package economy;

import java.util.HashMap;

public class Farmer {
    private IAcre[] _acres;
    private double _foodConsumption;
	private HashMap<Food, Double> _demand = new HashMap<Food, Double>();
	private HashMap<Food, Double> _portionOfAcresUsed = new HashMap<Food, Double>();
    
    public Farmer(IAcre[] acres, HashMap<Food, Double> demand, double foodConsumption) {
    	_acres = acres;
    	_demand = demand;
    	_foodConsumption = foodConsumption;
    }
    
    public IAcre getAcre(int acreIndex) {
    	return _acres[acreIndex];
    }
    
    public double avgYield(Food food) {
    	double sum = 0;
    	for (IAcre a: _acres) sum += a.yield(food);
    	return sum / (double)_acres.length;
    }
    
    public double acreageUsed(Food food) {
    	if (!_portionOfAcresUsed.containsKey(food))
    		choseFood();
    	return _portionOfAcresUsed.get(food);
    }
    
    public double foodGrown(Food food) {
    	return avgYield(food) * acreageUsed(food);
    }
    
    private void choseFood() {
    	HashMap<Food, Double> willToBuy = new HashMap<Food, Double>();
    	double totalWillToBuy = 0;
    	for(Food key: Food.values()) {
    		willToBuy.put(key, _demand.get(key) * avgYield(key));
    		totalWillToBuy += willToBuy.get(key);
    	}
    	for(Food key: Food.values()) {
    		double relativeWill = willToBuy.get(key) / totalWillToBuy; 
    		_portionOfAcresUsed.put(key, relativeWill * (double)_acres.length);
    	}
    }
}
