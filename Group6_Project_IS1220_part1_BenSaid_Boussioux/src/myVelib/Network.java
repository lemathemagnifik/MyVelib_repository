package myVelib;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class Network {
	private String name;
	private ArrayList<Station> stations;
	private ArrayList<User> users;
	private ArrayList<Bicycle> bicycles;
	
	
	//TODO
	public Network() {
		name ="MyNetwork";
		stations= new ArrayList<Station>();
		users= new ArrayList<User>();
		bicycles=new ArrayList<Bicycle>();
		
	}

	public Network(String name, ArrayList<Station> stations, ArrayList<User> users, ArrayList<Bicycle> bicycles) {
		super();
		this.name = name;
		this.stations = stations;
		this.users = users;
		this.bicycles = bicycles;
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}
	
	public ArrayList<Station> getPlusStation(){
		ArrayList<Station> plusStations = new ArrayList<Station>();
		for (Station s:stations) {
			Station.StationType type=s.getStationType();
			if (type==Station.StationType.Plus) {
				plusStations.add(s);
			}
		}
		return plusStations;
	}
	
	public ArrayList<Station> isClosest(GPS position) {
		
		ArrayList<Station> closest = new ArrayList<Station>();
		double mindist = Double.MAX_VALUE;
		double eps= Math.pow(10, -15);
		
		for (Station station:stations) {
			GPS location=station.getPosition();
			if (position.equals(location)){
				continue;
			}
			if (station.getStatus()==Station.Status.Full || station.getStatus()==Station.Status.Offline) {
				continue;
			}
			
			double dist=position.distance(location);
			
			if ((dist-eps)<=mindist) {
				if (Math.abs(dist-mindist)>eps) {
					mindist=dist;
					closest.clear();
				}
				closest.add(station);
			}	
		}
		return closest;
	}
	

	public ArrayList<Station> getStationsInRadiusPercent( GPS position, Double percent ) {
		ArrayList<Station> foundStations = new ArrayList<Station>();
		ArrayList<Station> closestStations = this.isClosest(position);
		Double distanceToClosest = position.distance(closestStations.get(0).getPosition());
		Double radius = percent*distanceToClosest;
		for (Station s:stations) {
			Double distance=position.distance(s.getPosition());
			if (distance<radius) {
				foundStations.add(s);
			}
		}
		return foundStations;
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	// trouver une solution pour affecter network � chacun des User/Bicycle/Station des ArrayList ou effacer ces m�thode (kiss!)
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public ArrayList<Bicycle> getBicycles() {
		return bicycles;
	}

	public void setBicycles(ArrayList<Bicycle> bicycles) {
		this.bicycles = bicycles;
	}
	
	public void addStation(Station station) {
		this.getStations().add(station);
		station.setNetwork(this);
	}
	
	public void addUser(User user) {
		this.getUsers().add(user);
		user.setNetwork(this);
	}
	
	public void addBicycle(Bicycle bicycle) {
		this.getBicycles().add(bicycle);
		bicycle.setNetwork(this);
	}
	
	/**
	 * Creates an ArrayList of n Stations randomly distributed in a square n*n
	 * @param n
	 * @return
	 */
	public ArrayList<Station> stationList (int n){
		ArrayList<Station> stations = new ArrayList<Station>();
		for (int i=0 ; i<n ; i++) {
			
			double random1 = Math.random() * n;
			double random2 = Math.random() * n;
			stations.add(new Station(String.valueOf(i), Station.StationType.Normal,new GPS(random1,random2),this));

		}

		return stations;
	}
	
	public ArrayList<Station> stationWithSlotList (ArrayList<Station> stations, int m){
		int slotsPerStation = m/stations.size(); 
		int rest = m%stations.size();
		for (int i=0 ; i<stations.size() ; i++) {
			for (int j=0;j<slotsPerStation;j++) {
				stations.get(i).addParkingSlot();
			}
		for (int k=0 ; k<rest;k++) {
			stations.get(k).addParkingSlot();
		}
		}
		return stations;
	}
	
	/**
	 * This ugly method is able to put a uniform number of bicycles in all the stations according to given
	 * percentages.
	 * @param stations
	 * @param numberOfSlots
	 * @param percentageOfOccupation
	 * @param percentageOfMechanical
	 * @return
	 */
	
	//TODO C'est un enfer car il faut rattrapper l'exception de addBicycle je ne sais combien de fois.
	
	public ArrayList<Station> stationWithBicycles (ArrayList<Station> stations, int numberOfSlots, int percentageOfOccupation, int percentageOfMechanical){
		int numberOfMechanical = numberOfSlots*percentageOfOccupation*percentageOfMechanical/100/100;
		int numberOfElectrical = numberOfSlots*percentageOfOccupation*(100-percentageOfMechanical)/100/100;
		
		int bicyclesElectricalPerStation = numberOfElectrical/stations.size(); 
		int rest = numberOfElectrical%stations.size();
		for (int i=0 ; i<stations.size() ; i++) {
			for (int j=0;j<bicyclesElectricalPerStation;j++) {
				stations.get(i).getParkingSlots().get(j).addBicycle(new Bicycle(Bicycle.BicycleType.Electrical));;
			}
		}
		for (int k=0 ; k<rest;k++) {
			stations.get(k).getParkingSlots().get(bicyclesElectricalPerStation).addBicycle(new Bicycle(Bicycle.BicycleType.Electrical));;
		}
		
		int bicyclesMechanicalPerStation = numberOfMechanical/stations.size(); 
		int rest2 = numberOfMechanical%stations.size();
		for (int l=0 ; l<stations.size() ; l++) {
			for (int j=0;j<bicyclesElectricalPerStation;j++) {
				stations.get(l).getParkingSlots().get(j+bicyclesElectricalPerStation).addBicycle(new Bicycle(Bicycle.BicycleType.Mechanical));;
			}
		}
		for (int k=0 ; k<rest2;k++) {
			stations.get(k).getParkingSlots().get(bicyclesElectricalPerStation+bicyclesMechanicalPerStation).addBicycle(new Bicycle(Bicycle.BicycleType.Mechanical));;
		}
		
		return stations;}
	} 
	
//*****************************************************************//
//					STATISTICAL FUNCTIONS 					     //
//*****************************************************************//	
	
	public ArrayList<Station> mostUsedStations(){
		ArrayList<Station> sortedStations = this.stations;
		Collections.sort(sortedStations, Station.mostUsedComparator);
		return sortedStations;
	}
	
	public ArrayList<Station> leastOccupiedStations(Timestamp t1, Timestamp t2){
		ArrayList<Station> sortedStations = this.stations;
		Collections.sort(sortedStations, Station.leastOccupiedComparator(t1, t2));
		return sortedStations;
	}
	
	
	
	
	
	
	
	
	
}
