package myVelib;

import java.time.Duration;

public class VlibreCard extends Card implements VisitableCard{
	
	public VlibreCard(Id id, User user, Double costH1electrical, Double costH2eletrical, Double costafterH2electrical,
			Double costH1mechanical, Double costH2mechanical, Double costafterH2mechanical, Duration timeCredit) {
		super(id, user, costH1electrical, costH2eletrical, costafterH2electrical, costH1mechanical, costH2mechanical,
				costafterH2mechanical, timeCredit);
		// TODO Auto-generated constructor stub
	}

	private static Double costH1electrical = 0.0;
	private static Double costH2electrical = 1.0;
	private static Double costafterH2electrical = 2.0;
	private static Double costH1mechanical = 0.0;
	private static Double costH2mechanical = 1.0;
	private static Double costafterH2mechanical = 2.0;
	
	public void accept(User user, Duration tripTime, Bicycle.BicycleType type) throws Exception {
		// TODO Auto-generated method stub
		user.visit(this, tripTime, type);
	}
}
