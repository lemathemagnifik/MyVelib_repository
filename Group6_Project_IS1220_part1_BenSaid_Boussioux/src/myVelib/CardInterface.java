package myVelib;

import java.time.Duration;

public interface CardInterface {
	public void 	accept(User user, Duration tripTime, Bicycle.BicycleType type) throws Exception;
}
