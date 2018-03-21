package myVelib;

import java.time.Duration;

import myVelib.Bicycle.BicycleType;

public class VmaxCard implements VelibCard {
	private User user;
	private Duration timeCredit;
	private int ID;
	
	private static int IDcounter=0;
	private static Double costH1 = 0.0;
	private static Double costH2 = 1.0;
	private static Double costAfterH2 = 2.0;
	
	
	
	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Duration getTimeCredit() {
		return timeCredit;
	}



	public void setTimeCredit(Duration timeCredit) {
		this.timeCredit = timeCredit;
	}



	public static Double getCostH1() {
		return costH1;
	}



	public static void setCostH1(Double costH1) {
		VmaxCard.costH1 = costH1;
	}



	public static Double getCostH2() {
		return costH2;
	}



	public static void setCostH2(Double costH2) {
		VmaxCard.costH2 = costH2;
	}



	public static Double getCostAfterH2() {
		return costAfterH2;
	}



	public static void setCostAfterH2(Double costAfterH2) {
		VmaxCard.costAfterH2 = costAfterH2;
	}



	public int getID() {
		return ID;
	}



	public VmaxCard(User user, Duration timeCredit) {
		super();
		IDcounter++;
		this.user = user;
		this.timeCredit = timeCredit;
		this.ID = IDcounter;
	}
	
	

	@Override
	public void accept(CardVisitor visitor, Duration tripTime, BicycleType type) throws Exception {
		visitor.visit(this, tripTime, type);		
	}

}
