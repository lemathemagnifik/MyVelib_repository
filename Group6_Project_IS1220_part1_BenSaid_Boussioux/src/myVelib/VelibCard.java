package myVelib;

import java.time.Duration;

public interface VelibCard {
	public void accept(CardVisitor visitor, Duration tripTime, Bicycle.BicycleType type) throws Exception;
}
