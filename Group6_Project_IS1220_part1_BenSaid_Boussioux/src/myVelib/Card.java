package myVelib;

import java.time.Duration;

public abstract class Card {
	public Id id;
	private User user;
	private Double costH1;
	private Double costH2;
	private Double costafterH2;
	private Duration timeCredit;
	
	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getCostH1() {
		return costH1;
	}

	public void setCostH1(Double costH1) {
		this.costH1 = costH1;
	}

	public Double getCostH2() {
		return costH2;
	}

	public void setCostH2(Double costH2) {
		this.costH2 = costH2;
	}

	public Double getCostafterH2() {
		return costafterH2;
	}

	public void setCostafterH2(Double costafterH2) {
		this.costafterH2 = costafterH2;
	}

	public int getTimeCredit() {
		return timeCredit;
	}

	public void setTimeCredit(int timeCredit) {
		this.timeCredit = timeCredit;
	}

	public Card(Id id, User user, Double costH1, Double costH2, Double costafterH2, int timeCredit) {
		super();
		this.id = id;
		this.user = user;
		this.costH1 = costH1;
		this.costH2 = costH2;
		this.costafterH2 = costafterH2;
		this.timeCredit = timeCredit;
	}
	
	/**
	 * Adds time credit on the card.
	 * @param d
	 * d must be a long representing the time Credit in ms
	 */
	public void creditTime(long d) {
		this.timeCredit=this.timeCredit.plusMillis(d);
	}
	
	/**
	 * Adds automatically 5min time credit on the card.
	 * 
	 */
	public void creditTime() {
		this.timeCredit=this.timeCredit.plusMillis(300000);
	}
}
