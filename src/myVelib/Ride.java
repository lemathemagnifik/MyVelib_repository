package myVelib;

import java.sql.Timestamp;
import java.time.Duration;

import Tests.CreateTestNetwork;
import myVelib.Bicycle.BicycleType;
import myVelib.ParkingSlot.UnavailableSlotException;

public class Ride {
	public Timestamp departureTime;
	public Timestamp arrivalTime;
	public Duration duration;
	public Station departureStation;
	public Station arrivalStation;
	public Bicycle bicycle;
	public BicycleType bicycleType;
	public Double cost;
	public Duration timeCredit;
	
	public Ride() {
		super();
		this.duration=Duration.ZERO;
		this.timeCredit=Duration.ZERO;
	}


	public Timestamp getDepartureTime() {
		return departureTime;
	}


	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}


	public Timestamp getArrivalTime() {
		return arrivalTime;
	}


	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}


	public Station getDepartureStation() {
		return departureStation;
	}


	public void setDepartureStation(Station departureStation) {
		this.departureStation = departureStation;
	}


	public Station getArrivalStation() {
		return arrivalStation;
	}


	public void setArrivalStation(Station arrivalStation) {
		this.arrivalStation = arrivalStation;
	}


	public Duration getDuration() {
		return duration;
	}


	public void setDuration(Duration duration) {
		this.duration = duration;
	}


	public Bicycle getBicycle() {
		return bicycle;
	}


	public void setBicycle(Bicycle bicycle) {
		this.bicycle = bicycle;
	}


	public Double getCost() {
		return cost;
	}


	public void setCost(Double cost) {
		this.cost = cost;
	}


	public Duration getTimeCredit() {
		return timeCredit;
	}


	public void setTimeCredit(Duration timeCredit) {
		this.timeCredit = timeCredit;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}


	@Override
	public String toString() {
		return "Ride [departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", duration=" + duration
				+ ", departureStation=" + departureStation + ", arrivalStation=" + arrivalStation + ", bicycle="
				+ bicycle + ", bicycleType=" + bicycleType + ", cost=" + cost + ", timeCredit=" + timeCredit + "]";
	}


	public static void main(String[] args) throws UnavailableSlotException {
		Network myNetwork = CreateTestNetwork.CreateTestingNetwork();
		Ride ride = new PlannedRide(myNetwork, new GPS(1,1), new GPS(3.4,5), true, true, false, false);
		System.out.println(ride.getDepartureStation());
		System.out.println(ride.getArrivalStation());

	}
	
	
	
	
}
