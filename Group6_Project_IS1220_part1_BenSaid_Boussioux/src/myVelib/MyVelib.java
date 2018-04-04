package myVelib;

import java.time.Duration;
import java.util.ArrayList;

public class MyVelib {
	private String name;
	private ArrayList<Network> networks;
	
	
	public Network getNetwork(String networkName) {
		for (Network n:networks) {
			if (n.getName().equalsIgnoreCase(networkName)) {
				return n;
			}
			else {
				System.out.println("Non existant network.");
			}
		}
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
		System.out.println("There are no stations with the given ID.");
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
	
}
