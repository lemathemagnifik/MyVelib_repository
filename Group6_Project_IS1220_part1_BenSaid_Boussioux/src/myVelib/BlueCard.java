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
	
	public Double getCostH1electrical() {
		return costH1electrical;
	}
	
	public Double getCostH1mechanical() {
		return costH1mechanical;
	}


	@Override
	public void accept(CardVisitor visitor, Duration tripTime, Bicycle.BicycleType bType) throws Exception {
		visitor.visit(this, tripTime, bType);
	}

}
