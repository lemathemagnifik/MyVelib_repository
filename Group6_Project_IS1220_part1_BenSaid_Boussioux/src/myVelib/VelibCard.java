package myVelib;

import java.time.Duration;

public class VelibCard {
	protected Duration timeCredit;
	protected User user;

	
	public VelibCard(Duration timeCredit, User user) {
		super();
		this.timeCredit = timeCredit;
		this.user = user;
	}
	

	public Duration getTimeCredit() {
		return timeCredit;
	}
	public void setTimeCredit(Duration timeCredit) {
		this.timeCredit = timeCredit;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public void creditTime() {
		this.timeCredit=this.timeCredit.plus(Station.plusTimeCredit);
	}
	
}
