package myVelib;

import java.time.Duration;

public class VmaxCard extends Card implements CardInterface {
	public Duration timeCredit;

	public VmaxCard() {
		super();
		// TODO Auto-generated constructor stub
	}
	private static Double costH1 = 0.0;
	private static Double costH2 = 1.0;
	private static Double costafterH2 = 2.0;
	public void accept(User user, Duration tripTime, Bicycle.BicycleType type) throws Exception {
		// TODO Auto-generated method stub
		user.visit(this, tripTime, type);
	}
}
