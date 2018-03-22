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

	public enum Status {OnService, Offline, Full};
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
	
	
	
<<<<<<< HEAD
	public Integer selectFreeSlot() throws NoAvailableFreeSlotsException {
		for (int i=0; i<=this.getParkingSlots().size(); i++) {
			if (this.getParkingSlots().get(i).getStatus() == ParkingSlot.Status.Free) {
					System.out.println("Please return your bicycle at slot "+ i);
=======
	
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
			//TODO � v�rifier le println ??
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
>>>>>>> ce0a5fafdc580ab2043912930eb2cf15fff048fc
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
		return ""+this.getPosition();
		//return "Station [network = " + network + "id = " + id + ", name = " + name + ", freeSlots = "
		//		+ this.slotsFree() + ", slots occupied by mechanical = " + this.slotsOccupiedByMechanical() + ", slots occupied by electrical = " + this.slotsOccupiedByElectrical() + ", slots broken = " + this.slotsBroken() + "]";
	}
	
	
	
//*****************************************************************//
//							EXCEPTIONS 							   //
//*****************************************************************//
	

	public class NoBikesAvailableException extends Exception{
		public NoBikesAvailableException(){
		    System.out.println("Sorry, no more bikes of the desired type available.");
		  }  
	}
	
	public class OfflineStationException extends Exception{
		public OfflineStationException(){
		    System.out.println("Sorry, this stations is offline.");
		  }  
	}

	public class NoAvailableFreeSlotsException extends Exception{
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

	
	
	
	
}

