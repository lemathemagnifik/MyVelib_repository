package myVelib;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;
import java.util.Observable;
import java.util.concurrent.ConcurrentSkipListMap;

import myVelib.Bicycle.BicycleType;
import myVelib.ParkingSlot.UnavailableSlotException;
import myVelib.Station.NoAvailableFreeSlotsException;




public class Station extends Observable {

//*****************************************************************//
//							Attributes 							   //
//*****************************************************************//

	public enum Status {OnService, Offline};
	public enum StationType {Normal, Plus}
	
	static int IDcounter=0;
	static final Duration plusTimeCredit = Duration.ZERO.plusMinutes(5);
	private Network network;
	private int id;
	private String name;
	private StationType type;
	private GPS position;
	private Status status;
	private ArrayList<ParkingSlot> parkingSlots;
	
	private ConcurrentSkipListMap <Timestamp, int[]> stationHistory = new ConcurrentSkipListMap<Timestamp, int[]>();
	private int numberOfRentals;
	private int numberOfReturns;


	
	
//*****************************************************************//
//						Constructor 							   //
//*****************************************************************//	

	public Station(String name, StationType type, GPS position, ArrayList<ParkingSlot> parkingSlots, Status status,Network network) {
		super();
		IDcounter++;
		this.id=IDcounter;
		this.name = name;
		this.type = type;
		this.position = position;
		this.parkingSlots = parkingSlots;
		this.status = status;
		this.network=network;
	}

	

	public Station(String name, StationType type, GPS position, Network network) {
		super();
		IDcounter++;
		this.id=IDcounter;
		this.name = name;
		this.type = type;
		this.position = position;
		this.parkingSlots = new ArrayList<ParkingSlot>();
		this.status = Status.OnService;
		this.network=network;		
	}
	
			
	
	
	
//*****************************************************************//
//					Setters and Getters 						   //
//*****************************************************************//
	
	public Network getNetwork() {
		return network;
	}



	public void setNetwork(Network network) {
		this.network = network;
	}
	
	
	public int getNumberOfRentals() {
		return numberOfRentals;
	}



	public int getNumberOfReturns() {
		return numberOfReturns;
	}

	public void setNumberOfRentals(int numberOfRentals) {
		this.numberOfRentals = numberOfRentals;
	}

	public void setNumberOfReturns(int numberOfReturns) {
		this.numberOfReturns = numberOfReturns;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StationType getStationType() {
		return type;
	}

	public void setStationType(StationType type) {
		this.type = type;
	}

	public GPS getPosition() {
		return position;
	}

	public void setPosition(GPS position) {
		this.position = position;
	}

	public ArrayList<ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(ArrayList<ParkingSlot> parkingSlots) {
		this.parkingSlots = parkingSlots;
	}
		
	public Status getStatus() {
		return status;
	}
//TODO
	public void setStatus(Status status) {
		this.status = status;
		this.setChanged();
		this.notifyObservers();

	}

	public int getId() {
		return id;
	}
	
	public ConcurrentSkipListMap<Timestamp, int[]> getStationHistory() {
		return stationHistory;
	}

	public void setStationHistory(ConcurrentSkipListMap<Timestamp, int[]> stationHistory) {
		this.stationHistory = stationHistory;
	}
	
	
	
//*****************************************************************//
//								Methods		 					   //
//*****************************************************************//	
	
	
//-----------------------------------------------------------------------------//
// Modified getters and setters 

	public int getSize() {
		return this.parkingSlots.size();
	}


	public int slotsFree() {
		int counter = 0;
		for (int i=0; i<parkingSlots.size();i++) {
			if (parkingSlots.get(i).getStatus()==ParkingSlot.Status.Free)
				counter+=1;
		}
		return counter;
	}
	
	public int slotsBroken() {
		int counter =0;
		for (int i=0; i<parkingSlots.size();i++) {
			if (parkingSlots.get(i).getStatus()==ParkingSlot.Status.Broken)
				counter+=1;
		}
		return counter;
	}
	
	public int slotsOccupiedByMechanical() {
		int counter =0;
		for (int i=0; i<parkingSlots.size();i++) {
			if (parkingSlots.get(i).getStatus()==ParkingSlot.Status.OccupiedByMechanical)
				counter+=1;
		}
		return counter;
	}
	
	public int slotsOccupiedByElectrical() {
		int counter =0;
		for (int i=0; i<parkingSlots.size();i++) {
			if (parkingSlots.get(i).getStatus()==ParkingSlot.Status.OccupiedByElectrical)
				counter+=1;
		}
		return counter;
	}
	
	public int slotsOccupied(Bicycle.BicycleType bType) {
		if (bType==null) {
			return this.slotsOccupiedByElectrical()+this.slotsOccupiedByMechanical();
		}
		else if (bType==Bicycle.BicycleType.Electrical) {
			return this.slotsOccupiedByElectrical();
		}
		else if (bType==Bicycle.BicycleType.Mechanical) {
			return this.slotsOccupiedByMechanical();
		}
		else {return 0;}
	}
	


//-----------------------------------------------------------------------------//
// Network configuration  
	
	
	public void addParkingSlot(ParkingSlot slot) {
		this.parkingSlots.add(slot);
	}
	

	public void addParkingSlot() {
		ParkingSlot slot = new ParkingSlot(this);
		slot.getSlotHistory().put(new Timestamp(0), ParkingSlot.Status.Free);
		this.parkingSlots.add(slot);
	}
	
	public void addParkingSlot(Timestamp t) {
		ParkingSlot slot = new ParkingSlot(this);
		slot.getSlotHistory().put(t, ParkingSlot.Status.Free);
		this.parkingSlots.add(slot);
	}
	

	public void addEntryToStationHistory(Timestamp t){
		if(!stationHistory.isEmpty()&& stationHistory.lastKey().compareTo(t)>0){
			System.out.println("Error, do not enter a time in the past.");
		}
		else{
			stationHistory.put(t,new int[] {this.slotsFree(),this.slotsOccupiedByMechanical(),slotsOccupiedByElectrical(),slotsBroken()});
			System.out.println("The station's history is updated: the station has "+this.toString());
		}
	}

	
	
	//Computes the occupation rate.
	public double occupationRate(Timestamp t1, Timestamp t2) {
		long occupationTime = 0; 
		for (int i=0;i<this.getParkingSlots().size();i++)
			occupationTime +=this.getParkingSlots().get(i).occupationTime(t1, t2);
		return (occupationTime / ((t2.getTime()-t1.getTime())*this.getParkingSlots().size()));
	}
	
	
	public Integer selectFreeSlot() throws NoAvailableFreeSlotsException {
		for (int i=0; i<=this.getParkingSlots().size(); i++) {
			if (this.getParkingSlots().get(i).getStatus() == ParkingSlot.Status.Free) {
					System.out.println("Please return your bicycle at slot "+ i);
					return i;
				}
		}
		throw new NoAvailableFreeSlotsException();
	}
	
	public void returnBicycle (Bicycle bicycle, Timestamp t) throws OfflineStationException, NoAvailableFreeSlotsException {
		if (this.getStatus()==Station.Status.Offline) {
			throw new OfflineStationException();
		}
		int freeSlotNB = this.selectFreeSlot();
		try {
			this.getParkingSlots().get(freeSlotNB).addBicycle(bicycle,t);
		} catch (UnavailableSlotException e) {
		}
}
	


	
	public Integer selectBicycle (Bicycle.BicycleType bType) throws NoBikesAvailableException{
		for (int i=0; i<=this.getParkingSlots().size(); i++) {
			if (this.getParkingSlots().get(i).getStatus()==ParkingSlot.Status.Broken) {
				continue;
			}
				
			else if (this.getParkingSlots().get(i).getBicycle()!=null) {
				if (bType==null) {
					System.out.println("Go take the " + Bicycle.bicycleTypeString(bType) + " bicycle at slot "+ i);
					return i;
				}
				else if (this.getParkingSlots().get(i).getBicycle().getType()==bType) {
					System.out.println("Go take the " + Bicycle.bicycleTypeString(bType) + " bicycle at slot "+ i);
					return i;
				}	
			}	
		}
		throw new NoBikesAvailableException();
	}
	
	/** 
	 * This function tells the User in which slot he should take his mechanical bicycle.
	 * @param s
	 * @return i
	 * i is the parking slot where the user can take the bicycle.
	 * @throws OfflineStationException 
	 * @throws NoBikesAvailableException 
	 * @throws NoMoreBikeException 
	 */
	
	public Bicycle retrieveBicycle(BicycleType bType, Timestamp t) throws OfflineStationException, NoBikesAvailableException {
		Bicycle bicycle = null;
		if (this.getStatus()==Status.Offline) {
			throw new OfflineStationException();
			} 
		int slotNB = 0;
		slotNB = this.selectBicycle(bType);
		bicycle = this.getParkingSlots().get(slotNB).getBicycle();
		this.getParkingSlots().get(slotNB).becomesFree(t);
		return bicycle;
	}
		

	
//------------------------------------------------------------------------------//
	
// toString METHODS
	
	
	public String toString() {
		String strType = "";
		String strStatus = "";
		
		if (status == Status.OnService) {strStatus = "On Service";}
		else {strStatus = "Offline";}
		if (type == StationType.Normal) {strType = "Normal";}
		else {strType = "Plus";}
		return "Station name : " + name + " | ID : "+this.id+ "| Type : " + strType + " | Position " + this.position + " | Status : "+ strStatus + " | Free Slots : " + slotsFree() + " | Broken Slots : " + slotsBroken() + " |  Occupied Slots : " + slotsOccupied(BicycleType.Electrical) + " (Electrical) + " + slotsOccupied(BicycleType.Mechanical) + " (Mechanical) ;";	
	}
	public String toArray() {
		String strType = "";
		String strStatus = "";
		
		if (status == Status.OnService) {strStatus = "On Service";}
		else {strStatus = "Offline";}
		if (type == StationType.Normal) {strType = "Normal";}
		else {strType = "Plus";}
		return String.format("%-20s %1s %-10s %1s %-6s %1s %-25s %1s %-10s %1s %-13s %1s %-15s %1s %-19s %1s %-20s %1s", name, "|", this.id, "|", strType, "|", this.position, "|", strStatus, "|", slotsFree(), "|", slotsBroken(), "|", slotsOccupied(BicycleType.Electrical), "|", slotsOccupied(BicycleType.Mechanical),"|");

	}
	
	public String toArrayBalance(Timestamp t1, Timestamp t2) {
		String strType = "";
		String strStatus = "";
		
		if (status == Status.OnService) {strStatus = "On Service";}
		else {strStatus = "Offline";}
		if (type == StationType.Normal) {strType = "Normal";}
		else {strType = "Plus";}
		return String.format("%-20s %1s %-10s %1s %-6s %1s %-25s %1s %-10s %1s %-13s %1s %-15s %1s %-19s %1s %-20s %1s", name, "|", this.id, "|", strType, "|", this.position, "|", strStatus, "|", getNumberOfRentals(), "|", getNumberOfReturns(), "|", getNumberOfRentals()+getNumberOfReturns(), "|", occupationRate(t1, t2),"|");

	}
	
	public void displayStation(Timestamp t1, Timestamp t2) {
		String str ="";
		str+="\n====================================================================================================================================================================\n";
		str+= String.format("%-20s %1s %-10s %1s %-6s %1s %-25s %1s %-10s %1s %-13s %1s %-15s %1s %-19s %1s %-20s %1s", "Station Name", "|", "Station ID", "|", "Type", "|", "Location", "|", "Status", "|", "Nb Free Slots", "|", "Nb Broken Slots", "|", "Nb Electrical Bikes", "|", "Nb Mechanical Bikes","|");
		str+="\n====================================================================================================================================================================\n";
		str+=this.toArrayBalance(t1, t2);
		System.out.println(str);		
	}
	
//*****************************************************************//
//							EXCEPTIONS 							   //
//*****************************************************************//
	

	public class NoBikesAvailableException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2205326446857393950L;

		public NoBikesAvailableException(){
		    System.out.println("Sorry, no more bikes of the desired type available.");
		  }  
	}
	
	public class OfflineStationException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2527155476397485679L;

		public OfflineStationException(){
		    System.out.println("Sorry, this stations is offline.");
		  }  
	}

	public class NoAvailableFreeSlotsException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1974553296026094071L;

		public NoAvailableFreeSlotsException(){
		    System.out.println("Sorry, this station has no more available slots.");
		  }  
	}
	
	//*****************************************************************//
	//	COMPARATORS 							   //
	//*****************************************************************//


	public static Comparator<Station> mostUsedComparator = new Comparator<Station>() {
		
		public int compare(Station s1, Station s2){
			
			int numberOfOperations1 = s1.getNumberOfRentals()+s1.getNumberOfReturns();
			int numberOfOperations2 = s2.getNumberOfRentals()+s2.getNumberOfReturns();

			return numberOfOperations1-numberOfOperations2;
		}      
	
	};
	
	public static Comparator<Station> leastOccupiedComparator(Timestamp t1, Timestamp t2){
		return new Comparator<Station>(){
	
		
		public int compare(Station s1, Station s2){
			
			double occupationRate1 = s1.occupationRate(t1, t2);
			double occupationRate2 = s2.occupationRate(t1, t2); 

			if (occupationRate1 > occupationRate2) {
	              return -1;
	            }
			if (occupationRate1 < occupationRate2) {
	              return 1;
	            }
	        return 0;
		}      
	
	};}

	
	
	public static void main(String[] args) {
		Station s = new Station("Staion", StationType.Normal, new GPS(1,1), new Network());
		System.out.println(s);
	}
	
}

