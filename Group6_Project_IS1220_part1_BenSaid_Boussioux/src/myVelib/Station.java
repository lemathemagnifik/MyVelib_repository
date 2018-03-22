package myVelib;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;
import java.util.Observable;
import java.util.concurrent.ConcurrentSkipListMap;

import myVelib.ParkingSlot.UnavailableSlotException;




public class Station extends Observable {

//*****************************************************************//
//							Attributes 							   //
//*****************************************************************//

	public enum Status {Full, Available, Offline};
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
		this.status = Status.Available;
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
	
	public ParkingSlot getAFreeSlot() {
		for (ParkingSlot p : this.getParkingSlots()) {
			if (p.getStatus() == ParkingSlot.Status.Free) {
				return p;
			}
		}
		System.out.print("No free slots available.");
		return null;
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

	
	
	//[TODO]g√©rer exception du temps
	public double occupationRate(Timestamp t1, Timestamp t2) {
		long occupationTime = 0; 
		for (int i=0;i<this.getParkingSlots().size();i++)
			occupationTime +=this.getParkingSlots().get(i).occupationTime(t1, t2);
		return (occupationTime / ((t2.getTime()-t1.getTime())*this.getParkingSlots().size()));
	}
	
	
	
	
	public void addBicycle (Bicycle bicycle, Timestamp t) throws UnavailableStationException, NoMoreAvailableSlotsException {
		if (this.getStatus()==Station.Status.Offline) {
			throw new UnavailableStationException();
		}
		else if (this.getStatus()==Station.Status.Full) {
			throw new NoMoreAvailableSlotsException();
		}
		else {
			ParkingSlot freeParkingSlot = this.getAFreeSlot();		
			System.out.println("Please, put your bicycle at a free slot");
			try {
				freeParkingSlot.addBicycle(bicycle,t);
			}
			//TODO ‡ vÈrifier le println ??
			catch(UnavailableSlotException e) {e.toString();};
		}
		this.addEntryToStationHistory(t);
		this.setNumberOfReturns(this.getNumberOfReturns()+1);
	}
	
	
	public int selectBicycleMechanical () throws NoMoreMechanicalException{
		if (this.slotsOccupiedByMechanical() == 0)
				throw new NoMoreMechanicalException(); 
		else {
			for (int i=0; i<=this.getParkingSlots().size(); i++) {
				if (this.getParkingSlots().get(i).getStatus() == ParkingSlot.Status.OccupiedByMechanical)
				{
					System.out.println("Go take mechanical bicycle at slot "+ i);
					return i;	}}}return 0;}

	public int selectBicycleElectrical  () throws NoMoreElectricalException{

		if (this.slotsOccupiedByElectrical() == 0){
				throw new NoMoreElectricalException();}
		else {
			for (int i=0; i<=this.getParkingSlots().size(); i++) {
				if (this.getParkingSlots().get(i).getStatus() == ParkingSlot.Status.OccupiedByElectrical)
				{
					System.out.println("Go take electrical bicycle at slot "+ i);
					return i;
					
				}
			}
		}
		return 0;//for debugging purposes
		
	}
	
	public Integer selectBicycle (Bicycle.BicycleType bType) throws NoMoreBikeException{
		if (this.slotsOccupied(bType) == 0){
				throw new NoMoreBikeException();
				}
		else  {
			for (int i=0; i<=this.getParkingSlots().size(); i++) {
				if (this.getParkingSlots().get(i).getBicycle()!=null) {
					if (bType==null) {
						System.out.println("Go take the " + Bicycle.bicycleTypeString(bType) + " bicycle at slot "+ i);
						return i;				}
					else if (this.getParkingSlots().get(i).getBicycle().getType()==bType) {
						System.out.println("Go take the " + Bicycle.bicycleTypeString(bType) + " bicycle at slot "+ i);
						return i;
					}

				}
			}
		}
		return null;
	}
	
	/** 
	 * This function tells the User in which slot he should take his mechanical bicycle.
	 * @param s
	 * @return i
	 * i is the parking slot where the user can take the bicycle.
	 */

	
	
//------------------------------------------------------------------------------//
	
// toString METHODS
	
	
	public String toString() {
		return ""+this.getPosition();
		//return "Station [network = " + network + "id = " + id + ", name = " + name + ", freeSlots = "
		//		+ this.slotsFree() + ", slots occupied by mechanical = " + this.slotsOccupiedByMechanical() + ", slots occupied by electrical = " + this.slotsOccupiedByElectrical() + ", slots broken = " + this.slotsBroken() + "]";
	}
	
	
	
//*****************************************************************//
//							EXCEPTIONS 							   //
//*****************************************************************//
	
	
	public class NoMoreElectricalException extends Exception{
		public NoMoreElectricalException(){
		    System.out.println("Sorry, no more electrical bikes available.");
		  }  
	}
	
	public class NoMoreMechanicalException extends Exception{
		public NoMoreMechanicalException(){
		    System.out.println("Sorry, no more mechanical bikes available.");
		  }  
	}
	
	public class NoMoreBikeException extends Exception{
		public NoMoreBikeException(){
		    System.out.println("Sorry, no more bikes of the desired type available.");
		  }  
	}
	
	public class UnavailableStationException extends Exception{
		public UnavailableStationException(){
		    System.out.println("Sorry, this stations is unavailable to drop off your bicycle.");
		  }  
	}

	public class NoMoreAvailableSlotsException extends Exception{
		public NoMoreAvailableSlotsException(){
		    System.out.println("Sorry, this station has no more available slots.");
		  }  
	}
	




	
	
	
	
}
