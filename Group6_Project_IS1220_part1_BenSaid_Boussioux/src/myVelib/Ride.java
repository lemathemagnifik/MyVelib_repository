package myVelib;

import java.sql.Timestamp;
import java.time.Duration;

public class Ride {
	public Timestamp departureTime;
	public Timestamp arrivalTime;
	public Station departureStation;
	public Station arrivalStation;
	public Bicycle bicycle;
	public Double cost;
	public Duration timeCredit;
	
	
	public Ride(Timestamp departureTime, Timestamp arrivalTime, Station departureStation, Station arrivalStation,
			Bicycle bicycle, Double cost, Duration timeCredit) {
		super();
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.departureStation = departureStation;
		this.arrivalStation = arrivalStation;
		this.bicycle = bicycle;
		this.cost = cost;
		this.timeCredit = timeCredit;
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
	
	
	
	
	
	
}
