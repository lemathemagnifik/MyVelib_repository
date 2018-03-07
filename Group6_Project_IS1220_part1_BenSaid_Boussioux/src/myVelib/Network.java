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
			Station.StationType type=s.getType();
			if (type==Station.StationType.Plus) {
				plusStations.add(s);
			}
		}
		return plusStations;
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
