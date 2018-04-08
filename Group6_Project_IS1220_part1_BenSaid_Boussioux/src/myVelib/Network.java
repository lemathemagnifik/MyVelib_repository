package myVelib;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import myVelib.ParkingSlot.UnavailableSlotException;
import myVelib.Station.Status;


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
	
	public ArrayList<Station> getOnServiceStations(){
		ArrayList<Station> onService = new ArrayList<Station>();
		for (Station s:this.getStations()) {
			if (s.getStatus()==Status.OnService) {
				onService.add(s);
			}
		}
		return onService;
	}
	
	public Station getStation(String stationName) {
		for (Station s:this.getStations()) {
			if (s.getName().equalsIgnoreCase(stationName)) {
				return s;
			}
			else {
				System.out.println("Non existant station.");
			}
		}
		return null;
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
	 * Creates an ArrayList of n Stations randomly distributed in a square side*side
	 * @param n : number of stations
	 * @return
	 */
	public ArrayList<Station> stationList (int n, double side){
		ArrayList<Station> stations = new ArrayList<Station>();
		for (int i=0 ; i<n ; i++) {
			
			double random1 = Math.round(Math.random() * side * 1000.0)/1000.0;
			double random2 = Math.round(Math.random() * side * 1000.0)/1000.0;
			if ((i+1)%3==0) {
				stations.add(new Station("Station"+String.valueOf(i+1), Station.StationType.Plus,new GPS(random1,random2),this));
			}
			else stations.add(new Station("Station"+String.valueOf(i+1), Station.StationType.Normal,new GPS(random1,random2),this));


		}

		return stations;
	}
	
	/**
	 * 
	 * 
	 * @param n : number of stations
	 * @param m : total number of slots
	 * @param side : square side of the repartition
	 * @return
	 */
	
	public ArrayList<Station> stationWithSlotList (int n, int m, double side){
		ArrayList<Station> stations = this.stationList(n,side);
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
	 * @param numberOfStations
	 * @param numberOfSlots
	 * @param percentageOfOccupation
	 * @param percentageOfMechanical
	 * @param side area

	 * @return
	 * @throws UnavailableSlotException 
	 */
	
	//TODO C'est un enfer car il faut rattrapper l'exception de addBicycle je ne sais combien de fois.
	
	public ArrayList<Station> stationWithBicycles (int numberofStations, int numberOfSlots, double percentageOfOccupation, double percentageOfMechanical, double side) throws UnavailableSlotException{
		ArrayList<Station> stations = this.stationWithSlotList(numberofStations, numberOfSlots, side);
		try {
		int numberOfMechanical = (int) (numberOfSlots*percentageOfOccupation*percentageOfMechanical/100/100);
		int numberOfElectrical = (int) (numberOfSlots*percentageOfOccupation*(100-percentageOfMechanical)/100/100);
		
		int bicyclesElectricalPerStation = numberOfElectrical/numberofStations; 
		int rest = numberOfElectrical%numberofStations;
		for (int i=0 ; i<stations.size() ; i++) {
			for (int j=0;j<bicyclesElectricalPerStation;j++) {
				stations.get(i).getParkingSlots().get(j).addBicycle(new Bicycle(Bicycle.BicycleType.Electrical));;
			}
		}
		for (int k=0 ; k<rest;k++) {
			stations.get(k).getParkingSlots().get(bicyclesElectricalPerStation).addBicycle(new Bicycle(Bicycle.BicycleType.Electrical));;
		}
		
		int bicyclesMechanicalPerStation = numberOfMechanical/numberofStations; 
		int rest2 = numberOfMechanical%numberofStations;
		for (int l=0 ; l<stations.size() ; l++) {
			for (int j=0;j<bicyclesMechanicalPerStation;j++) {
				stations.get(l).getParkingSlots().get(j+bicyclesElectricalPerStation+1).addBicycle(new Bicycle(Bicycle.BicycleType.Mechanical));;
			}
		}
		for (int m=0 ; m<rest2;m++) {
			stations.get(m).getParkingSlots().get(bicyclesElectricalPerStation+bicyclesMechanicalPerStation+1).addBicycle(new Bicycle(Bicycle.BicycleType.Mechanical));;
		}
		
		return stations;}
	catch(UnavailableSlotException e) {return stations;}
		
	}
	
	
//*****************************************************************//
//					STATISTICAL FUNCTIONS 					     //
//*****************************************************************//	
	
	public ArrayList<Station> mostUsedStations(){
		ArrayList<Station> sortedStations = new ArrayList<Station>();
		for (Station s : this.stations) {
			sortedStations.add(s);
		}
		Collections.sort(sortedStations, Station.mostUsedComparator);
		return sortedStations;
	}
	
	public ArrayList<Station> leastOccupiedStations(Timestamp t1, Timestamp t2){
		ArrayList<Station> sortedStations = new ArrayList<Station>();
		for (Station s : this.stations) {
			sortedStations.add(s);
		}
		Collections.sort(sortedStations, Station.leastOccupiedComparator(t1, t2));
		return sortedStations;
	}

	public static void displayArrayStations(ArrayList<Station> stations, Timestamp t1, Timestamp t2) {
		String str ="";
		str+="\n====================================================================================================================================================================\n";
		str+= String.format("%-20s %1s %-10s %1s %-6s %1s %-25s %1s %-10s %1s %-13s %1s %-15s %1s %-19s %1s %-20s %1s", "Station Name", "|", "Station ID", "|", "Type", "|", "Location", "|", "Status", "|", "Nb of rentals", "|", "Nb of returns", "|", "Nb of ops", "|", "Occupation rate","|");
		str+="\n====================================================================================================================================================================\n";
		for (Station s:stations) {
			str+=s.toArrayBalance(t1, t2) + "\n";
		}
		str+="====================================================================================================================================================================\n";
		System.out.println(str);
	}
	
	@Override
	public String toString() {
		String str = "Network \n";
		str+= "Network name : " + name +"\n";
		str+= "=== Stations === \n";
		str+= "Number of stations : "+ stations.size() + "\n";
		str+= "Number of Plus stations : "+ this.getPlusStation().size() + "\n";
		str+= "Number of Normal stations : "+ (stations.size() - this.getPlusStation().size()) + "\n\n";
		
		str+= "=========================================================================  Stations ================================================================================ \n";
		str+= String.format("%-20s %1s %-10s %1s %-6s %1s %-25s %1s %-10s %1s %-13s %1s %-15s %1s %-19s %1s %-20s %1s", "Station Name", "|", "Station ID", "|", "Type", "|", "Location", "|", "Status", "|", "Nb Free Slots", "|", "Nb Broken Slots", "|", "Nb Electrical Bikes", "|", "Nb Mechanical Bikes","|");
		str+="\n====================================================================================================================================================================\n";
				
		for (Station s:stations) {
			str+= s.toArray() + "\n";
		}
		str+="====================================================================================================================================================================";
		str+="\n\n";
		str+= "========= Users ========= \n";
		str+= "Number of Users : "+ users.size() + "\n";
		
		str+= "=================================================================== Users ==========================================================================================\n";
		str+= String.format("%-7s %1s %-20s %1s %-25s %1s %-11s %1s %-11s %1s %-15s %1s %-20s %1s %-13s %1s %-17s %1s", "User ID", "|","User Name","|","Position", "|","Card Type", "|","Time Credit", "|", "Number of rides","|", "Total trips duration", "|", "Total charges","|","Total time credit", "|"   );
		str+="\n====================================================================================================================================================================\n";
		for (User u:users) {
			str+= u.toArray() + "\n";
		}
		str+="====================================================================================================================================================================\n\n";

		return str;
		
	}
	
	
	
	
	
	
	
	
	
	
}
