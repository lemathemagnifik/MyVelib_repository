package myVelib;

import java.time.Duration;

public class VlibreCard extends Card{
	public VlibreCard(Id id, User user, Double costH1, Double costH2, Double costafterH2, Duration timeCredit) {
		super(id, user, costH1, costH2, costafterH2, timeCredit);
		// TODO Auto-generated constructor stub
	}
	private static Double costH1 = 0.0;
	private static Double costH2 = 1.0;
	private static Double costafterH2 = 2.0;
	

}
