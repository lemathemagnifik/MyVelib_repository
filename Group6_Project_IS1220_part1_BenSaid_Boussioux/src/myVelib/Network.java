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
		for (Station s:stations) {
			s.setNetwork(this);
		}
	}
	
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
		for (User u:users) {
			u.setNetwork(this);
		}
	}

	public ArrayList<Bicycle> getBicycles() {
		return bicycles;
	}

	public void setBicycles(ArrayList<Bicycle> bicycles) {
		this.bicycles = bicycles;
		for (Bicycle b:bicycles) {
			b.setNetwork(this);
		}
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
	 * 
	 * @return the plus stations.
	 */
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
	
	/**
	 * 
	 * @param position
	 * @return the closest stations to GPS position.
	 */
	public ArrayList<Station> isClosest(GPS position) {
		
		ArrayList<Station> closest = new ArrayList<Station>();
		double mindist = Double.MAX_VALUE;
		double eps= Math.pow(10, -15);
		
		for (Station station:stations) {
			GPS location=station.getPosition();
			if (position.equals(location)){
				continue;
			}
			if (station.slotsFree()==0 || station.getStatus()==Station.Status.Offline) {
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
	
	/**
	 * 
	 * @param position
	 * @param percent
	 * @return the station in a radius that equals the percent of the distance separating the GPS position and the closest station to it.
	 */
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
			stations.get(i).addParkingSlot();
		}
		}
		return stations;
	}
	
	public ArrayList<Station> stationWithBicycles (ArrayList<Station> stations, int pourcentage){
		int slotsPerStation = m/stations.size(); 
		int rest = m%stations.size();
		for (int i=0 ; i<stations.size() ; i++) {
			for (int j=0;j<slotsPerStation;j++) {
				stations.get(i).addParkingSlot();
			}
		for (int k=0 ; k<rest;k++) {
			stations.get(i).addParkingSlot();
		}
		}
		return stations;
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
