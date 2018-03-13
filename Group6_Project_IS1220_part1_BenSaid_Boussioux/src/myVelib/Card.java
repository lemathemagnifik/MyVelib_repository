package myVelib;

import java.time.Duration;

public abstract class Card {
	public Id id;
	private User user;
	private Double costH1electrical;
	private Double costH2eletrical;
	private Double costafterH2electrical;
	private Double costH1mechanical;
	private Double costH2mechanical;
	private Double costafterH2mechanical;
	//time credit in Duration
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

	
	

	public Duration getTimeCredit() {
		return timeCredit;
	}

	public void setTimeCredit(Duration timeCredit) {
		this.timeCredit = timeCredit;
	}

	
	
	public Double getCostH1electrical() {
		return costH1electrical;
	}

	public void setCostH1electrical(Double costH1electrical) {
		this.costH1electrical = costH1electrical;
	}

	public Double getCostH2eletrical() {
		return costH2eletrical;
	}

	public void setCostH2eletrical(Double costH2eletrical) {
		this.costH2eletrical = costH2eletrical;
	}

	public Double getCostafterH2electrical() {
		return costafterH2electrical;
	}

	public void setCostafterH2electrical(Double costafterH2electrical) {
		this.costafterH2electrical = costafterH2electrical;
	}

	public Double getCostH1mechanical() {
		return costH1mechanical;
	}

	public void setCostH1mechanical(Double costH1mechanical) {
		this.costH1mechanical = costH1mechanical;
	}

	public Double getCostH2mechanical() {
		return costH2mechanical;
	}

	public void setCostH2mechanical(Double costH2mechanical) {
		this.costH2mechanical = costH2mechanical;
	}

	public Double getCostafterH2mechanical() {
		return costafterH2mechanical;
	}

	public void setCostafterH2mechanical(Double costafterH2mechanical) {
		this.costafterH2mechanical = costafterH2mechanical;
	}

	public Card(Id id, User user, Double costH1electrical, Double costH2eletrical, Double costafterH2electrical,
			Double costH1mechanical, Double costH2mechanical, Double costafterH2mechanical, Duration timeCredit) {
		super();
		this.id = id;
		this.user = user;
		this.costH1electrical = costH1electrical;
		this.costH2eletrical = costH2eletrical;
		this.costafterH2electrical = costafterH2electrical;
		this.costH1mechanical = costH1mechanical;
		this.costH2mechanical = costH2mechanical;
		this.costafterH2mechanical = costafterH2mechanical;
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
