package myVelib;

import java.time.Duration;

public class BlueCard extends Card implements CardInterface {

	public BlueCard() {
		// TODO Auto-generated constructor stub
		
		
	}

	@Override
	public void accept(User user, Duration tripTime, Bicycle.BicycleType type) throws Exception {
		// TODO Auto-generated method stub
		user.visit(this, tripTime, type);
	}

}
