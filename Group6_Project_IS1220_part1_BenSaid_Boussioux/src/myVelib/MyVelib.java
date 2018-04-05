package myVelib;

import java.time.Duration;
import java.util.ArrayList;

public class MyVelib {
	private String name;
	private ArrayList<Network> networks;
	
	public User getUser(String struserID) {
		int userID=Integer.parseInt(struserID);
		for (Network n:networks) {
			for (User u:n.getUsers()) {
				if (u.getId()==userID) {
					return u;
				}
			}
		}
		System.out.println("There is no user with the ID : "+struserID+".");
		return null;
	}
	
	public User getUser(String networkName, String struserID) {
		int userID=Integer.parseInt(struserID);
		Network network = this.getNetwork(networkName);
		if (network!=null) {
			for (User u:network.getUsers()) {
				if (u.getId()==userID) {
					return u;
				}
			}
			System.out.println("There is no user with the ID : "+struserID+".");
			return null;
		}
		return null;
	}
	
	public Network getNetwork(String networkName) {
		for (Network n:networks) {
			if (n.getName().equalsIgnoreCase(networkName)) {
				return n;
			}
		}
		System.out.println("Non existant network.");
		return null;
	}
	
	public Station getStation(String strstationID) {
		int stationID=Integer.parseInt(strstationID);
		for (Network network:this.networks) {
			for (Station s:network.getStations()) {
				if (s.getId() == stationID) {
					return s;
				}
			}
		}
		System.out.println("There is no station with the ID : "+strstationID+".");
		return null;
	}
	

	
	public void addNetwork(Network network) {
		this.networks.add(network);
	}
	
//	public ArrayList<Station> mostUsedStation() {
//		return stationList;
//		
//	}
//	
//	public ArrayList<Station> leastOccupiedStation(){
//		return stationList;
//		
//	}
	
	
	
	
	public MyVelib() {
	
		
	}
	public static void main(String[] args) {
		System.out.println(Duration.ofMinutes(50).);
	}
}
