package myVelib;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

import MyVelib.User.UserAction;


public class Station extends Observable{
	static int counter=0;
	private int id;
	private Network network;
	private String name;
	private StationType type;
	private GPS position;
	private ArrayList<ParkingSlot> parkingSlots;
	private Status status;
	private ConcurrentSkipListMap <Timestamp, int[]> stationHistory = new ConcurrentSkipListMap<Timestamp, int[]>();
	
	



	public enum Status {Full, Available, Offline};
	public enum StationType {Normal, Plus}

	
	public Station(String name, StationType type, GPS position, ArrayList<ParkingSlot> parkingSlots, Status status,Network network) {
		super();
		counter++;
		this.id=counter;
		this.name = name;
		this.type = type;
		this.position = position;
		this.parkingSlots = parkingSlots;
		this.status = status;
		this.network=network;
	}

	

	public Station(String name, StationType type, GPS position, Network network) {
		// TODO 
		super();
		counter++;
		this.id=counter;
		this.name = name;
		this.type = type;
		this.position = position;
		this.parkingSlots = new ArrayList<ParkingSlot>();
		this.status = Status.Available;
		this.network=network;		
	}


	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public StationType getType() {
		return type;
	}

	public void setType(StationType type) {
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
	

	public void addParkingSlot(ParkingSlot slot) {
		this.parkingSlots.add(slot);
	}
	public void addParkingSlot() {
		ParkingSlot slot = new ParkingSlot(this);
		this.parkingSlots.add(slot);
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
	
	public int slotsFree() {
		int counter =0;
		for (int i=0; i<=parkingSlots.size();i++) {
			if (parkingSlots.get(i).getStatus()==ParkingSlot.Status.Free)
				counter+=1;
		}
		return counter;
	}
	
	public int slotsBroken() {
		int counter =0;
		for (int i=0; i<=parkingSlots.size();i++) {
			if (parkingSlots.get(i).getStatus()==ParkingSlot.Status.Broken)
				counter+=1;
		}
		return counter;
	}
	
	public int slotsOccupiedByMechanical() {
		int counter =0;
		for (int i=0; i<=parkingSlots.size();i++) {
			if (parkingSlots.get(i).getStatus()==ParkingSlot.Status.OccupiedByMechanical)
				counter+=1;
		}
		return counter;
	}
	public int slotsOccupiedByElectrical() {
		int counter =0;
		for (int i=0; i<=parkingSlots.size();i++) {
			if (parkingSlots.get(i).getStatus()==ParkingSlot.Status.OccupiedByElectrical)
				counter+=1;
		}
		return counter;
	}
	
	public void addEntryToStationHistory(Timestamp t){
		if(!stationHistory.isEmpty()&& stationHistory.lastKey().compareTo(t)>0){
			System.out.println("Error, do not enter a time in the past.");
		}
		else{
			stationHistory.put(t,new int[] {this.slotsFree(),this.slotsOccupiedByMechanical(),slotsOccupiedByElectrical(),slotsBroken()});
			System.out.println("The station's history is updated: the station has "+station.toString());
		}
	}
	
}
