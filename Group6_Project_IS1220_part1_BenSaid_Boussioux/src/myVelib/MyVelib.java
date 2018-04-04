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
	
	public void addUser(String userName, String cardType, String networkName) {
		 Network network = this.getNetwork(networkName);
		 User user = new User();
		 if (cardType.equalsIgnoreCase("Vlibre")){
			 user = new User(userName, new VlibreCard(user, Duration.ZERO), network);
		 }
		 else if (cardType.equalsIgnoreCase("VMax")) {
			 user = new User(userName, new VmaxCard(user, Duration.ZERO), network);
		 }
		 else {user = new User(userName, new CreditCard(user), network);}
		 network.addUser(user); ;
	}
	
	public void addNetwork(Network network) {
		this.networks.add(network);
	}
	
	public void offline(String networkName, String stationName) {
		Network network = this.getNetwork(networkName);
		network.getStation(stationName).setStatus(Station.Status.Offline);
	}
	
	public void online(String networkName, String stationName) {
		Network network = this.getNetwork(networkName);
		network.getStation(stationName).setStatus(Station.Status.OnService);
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
