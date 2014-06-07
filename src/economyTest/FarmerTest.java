package economyTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;

import mockit.*;

import org.junit.Test;

import economy.Food;
import economy.IAcre;
import economy.Acre;
import economy.Farmer;

public class FarmerTest {
	private HashMap<Food, Double> _demand = new HashMap<Food, Double>();
	private int _farmerConsumption = 100;
	
	public FarmerTest () {
		_demand.put(Food.Wheat, 40.0);
		_demand.put(Food.Corn, 40.0);
		_demand.put(Food.Strawberry, 20.0);
	}
	
	@Test
	public void testChoseFood1() {
		HashMap<Food, Double> yieldA = new HashMap<Food, Double>();
		yieldA.put(Food.Wheat, 40.0);
		yieldA.put(Food.Corn, 40.0);
		yieldA.put(Food.Strawberry, 20.0);

		HashMap<Food, Double> yieldB = new HashMap<Food, Double>();
		yieldB.put(Food.Wheat, 41.0);
		yieldB.put(Food.Corn, 38.0);
		yieldB.put(Food.Strawberry, 18.0);

		IAcre[] acreArray = new IAcre[2];
		acreArray[0] = new Acre(yieldA);
		acreArray[1] = new Acre(yieldB);
		
		Farmer farmer = new Farmer(acreArray, _demand, _farmerConsumption);
				
		double totalYield = 0;
		double totalAcreage = 0;
		for(Food key : Food.values()) {
			org.junit.Assert.assertTrue("Food grown makes sense.", 
					Math.abs(farmer.avgYield(key) * farmer.acreageUsed(key) - farmer.foodGrown(key)) < 0.1);
			totalYield += farmer.foodGrown(key);
			totalAcreage += farmer.acreageUsed(key);
		}
		org.junit.Assert.assertTrue("Total acreage is about right.", Math.abs(totalAcreage - acreArray.length) < 0.001);
		org.junit.Assert.assertTrue("Total Yield is less than farmer needs to eat, but close to maximum possible to grow.", 70 <= totalYield && totalYield < 80);
		org.junit.Assert.assertTrue("The ratio of wheat to strawberries grown is much greater than desired because there is no way to grow as much food as farmer needs.",
				_demand.get(Food.Wheat) / _demand.get(Food.Strawberry) * 1.1
				< farmer.foodGrown(Food.Wheat) / farmer.foodGrown(Food.Strawberry));
		org.junit.Assert.assertTrue("The ratio of corn to strawberries grown is much greater than desired because there is now way to grow as much food as farmer needs.",
				_demand.get(Food.Wheat) / _demand.get(Food.Strawberry) * 1.1
				< farmer.foodGrown(Food.Corn) / farmer.foodGrown(Food.Strawberry));
	}
	
	@Test
	public void testChoseFood2() {
		HashMap<Food, Double> yieldA = new HashMap<Food, Double>();
		yieldA.put(Food.Wheat, 60.0);
		yieldA.put(Food.Corn, 40.0);
		yieldA.put(Food.Strawberry, 20.0);

		HashMap<Food, Double> yieldB = new HashMap<Food, Double>();
		yieldB.put(Food.Wheat, 59.0);
		yieldB.put(Food.Corn, 38.0);
		yieldB.put(Food.Strawberry, 18.0);

		IAcre[] acreArray = new IAcre[2];
		acreArray[0] = new Acre(yieldA);
		acreArray[1] = new Acre(yieldB);
		
		Farmer farmer = new Farmer(acreArray, _demand, _farmerConsumption);
				
		double totalYield = 0;
		double totalAcreage = 0;
		for(Food key : Food.values()) {
			org.junit.Assert.assertTrue("Food grown makes sense.", 
					Math.abs(farmer.avgYield(key) * farmer.acreageUsed(key) - farmer.foodGrown(key)) < 0.1);
			totalYield += farmer.foodGrown(key);
			totalAcreage += farmer.acreageUsed(key);
		}
		org.junit.Assert.assertTrue("Total acreage is about right.", Math.abs(totalAcreage - acreArray.length) < 0.001);
		org.junit.Assert.assertTrue("Total Yield is about right.", 
				_farmerConsumption - 5 <= totalYield && totalYield < _farmerConsumption + 5);
		org.junit.Assert.assertTrue("The ratio of wheat to corn grown is much greater than desired because wheat is cheaper.",
				_demand.get(Food.Wheat) / _demand.get(Food.Corn) * 1.1
				< farmer.foodGrown(Food.Wheat) / farmer.foodGrown(Food.Corn) );
		org.junit.Assert.assertTrue("The ratio of corn to strawberries grown is much greater than desired because corn is cheaper.",
				_demand.get(Food.Corn) / _demand.get(Food.Strawberry) * 1.1
				< farmer.foodGrown(Food.Corn) / farmer.foodGrown(Food.Strawberry) );
	}
}