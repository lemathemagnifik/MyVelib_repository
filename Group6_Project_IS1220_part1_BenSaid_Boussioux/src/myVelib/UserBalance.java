package myVelib;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.concurrent.ConcurrentSkipListMap;

//should allow to retrieve the number of rides, the total time spent on
//a bike, the total amount of charges for all rides performed by a user, as well as the
//time-credit earned by a user
public class UserBalance {
	private int numberOfRides;
	private Duration totalTime;
	private Double totalCharges;
	private Duration totalTimeCredit;
	
	
	public UserBalance(int numberOfRides, Duration totalTime, Double totalCharges, Duration totalTimeCredit) {
		super();
		this.numberOfRides = numberOfRides;
		this.totalTime = totalTime;
		this.totalCharges = totalCharges;
		this.totalTimeCredit = totalTimeCredit;
	}


	public UserBalance() {
		super();
		this.numberOfRides = 0;
		this.totalTime = Duration.ZERO;
		this.totalCharges = (double) 0;
		this.totalTimeCredit = Duration.ZERO;
	}


	public int getNumberOfRides() {
		return numberOfRides;
	}


	public void setNumberOfRides(int numberOfRides) {
		this.numberOfRides = numberOfRides;
	}


	public Duration getTotalTime() {
		return totalTime;
	}


	public void setTotalTime(Duration totalTime) {
		this.totalTime = totalTime;
	}


	public Double getTotalCharges() {
		return totalCharges;
	}


	public void setTotalCharges(Double totalCharges) {
		this.totalCharges = totalCharges;
	}


	public Duration getTotalTimeCredit() {
		return totalTimeCredit;
	}


	public void setTotalTimeCredit(Duration totalTimeCredit) {
		this.totalTimeCredit = totalTimeCredit;
	}
	
	
	
	
	
}
