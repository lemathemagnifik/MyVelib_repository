package myVelib;

import java.time.Duration;

public interface Card {
	
	public Double accept(CardVisitor visitor, Duration tripTime, Bicycle.BicycleType type) throws Exception;
	
}
