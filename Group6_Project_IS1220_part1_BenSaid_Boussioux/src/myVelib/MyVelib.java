package myVelib;

import java.time.Duration;
import java.util.ArrayList;

import myVelib.ParkingSlot.UnavailableSlotException;

public class MyVelib {
	private String name;
	private ArrayList<Network> networks;
	
	public User getUser(int userID) {
		for (Network n:networks) {
			for (User u:n.getUsers()) {
				if (u.getId()==userID) {
					return u;
				}
			}
		}
		System.out.println("There is no user with the ID : "+userID+".");
		return null;
	}
	
	public User getUser(String networkName, int userID) {
		Network network = this.getNetwork(networkName);
		if (network!=null) {
			for (User u:network.getUsers()) {
				if (u.getId()==userID) {
					return u;
				}
			}
			System.out.println("There is no user with the ID : "+userID+".");
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
	
	public Station getStation(int stationID) {
		for (Network network:this.networks) {
			for (Station s:network.getStations()) {
				if (s.getId() == stationID) {
					return s;
				}
			}
		}
		System.out.println("There is no station with the ID : "+stationID+".");
		return null;
	}
	
	public Station getStation(String networkName, int stationID) {
		Network network = this.getNetwork(networkName);
		if (network!=null) {
			for (Station s:network.getStations()) {
				if (s.getId()==stationID) {
					return s;
				}
			}
			System.out.println("There is no station with the ID : "+stationID+".");
			return null;
		}
		return null;
	}
	

	
	public void addNetwork(Network network) {
		this.networks.add(network);
	}
	
	public void addNetwork(String network) throws UnavailableSlotException  {
		Network network2 = new Network();
		ArrayList<Station> stations = network2.stationWithBicycles(10,100,75,70,4);
		network2.setStations(stations);
		network2.setName(network);
		this.networks.add(network2);

	}
	
	public void addNetwork(String network, int nstations, int nslots, double sidea, int nbikes) throws UnavailableSlotException  {
		Network network2 = new Network();
		double pourcentage = nbikes*100 / (nslots*nstations);
		ArrayList<Station> stations = network2.stationWithBicycles(nstations,nstations*nslots,pourcentage,70,sidea);
		network2.setStations(stations);
		network2.setName(network);
		this.networks.add(network2);
		

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
		this.networks = new ArrayList<Network>();
	}
	
	public static void main(String[] args) {
	}
}
