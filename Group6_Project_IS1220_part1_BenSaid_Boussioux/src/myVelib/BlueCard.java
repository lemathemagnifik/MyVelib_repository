package myVelib;

import java.time.Duration;

public class BlueCard extends Card implements CardInterface {
	private static Double costH1electrical = 2.0;
	private static Double costH2electrical = 2.0;
	private static Double costafterH2electrical = 2.0;
	private static Double costH1mechanical = 0.0;
	private static Double costH2mechanical = 1.0;
	private static Double costafterH2mechanical = 2.0;
	
	public BlueCard(User user) {
		super(user);
		this.user=user;
	}
	
	
	



	@Override
	public void accept(User user, Duration tripTime, Bicycle.BicycleType type) throws Exception {
		// TODO Auto-generated method stub
		user.visit(this, tripTime, type);
	}

}
