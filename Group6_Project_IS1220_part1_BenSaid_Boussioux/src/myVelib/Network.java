package myVelib;

import java.util.ArrayList;


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
	}
	
	public void addUser(User user) {
		this.getUsers().add(user);
	}
	
	public void addBicycle(Bicycle bicycle) {
		this.getBicycles().add(bicycle);
	}
	
	
}
