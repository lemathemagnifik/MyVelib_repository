package myVelib;

import java.sql.Timestamp;
import java.util.concurrent.ConcurrentSkipListMap;



public class ParkingSlot {
	
	
	private ConcurrentSkipListMap <Timestamp, ParkingSlot.Status> slotHistory = new ConcurrentSkipListMap<Timestamp, ParkingSlot.Status>();
	public enum Status {Free, OccupiedByElectrical, OccupiedByMechanical, Broken};
	static int counter;
	private int id;
	private Station station;
	private Bicycle bicycle;
	private ParkingSlot.Status status;
	
	

	public ParkingSlot(Station station) {
		super();
		counter++;
		this.id = counter;
		this.station = station;
		this.status = ParkingSlot.Status.Free;
		this.bicycle=null;
	}
	

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public ParkingSlot.Status getStatus() {
		return status;
	}

	public void setStatus(ParkingSlot.Status status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void becomesFree(){
		this.status = Status.Free;
		this.bicycle = null;
	}
	
	public Bicycle getBicycle() {
		return bicycle;
	}


	public void setBicycle(Bicycle bicycle) {
		this.bicycle = bicycle;
	}


	public void becomesBroken() {
		this.status = Status.Broken;
	}
	
	// Ici on n'a pas encore géré l'exception du fait qu'il y aurait déjà un vélo dans le slot.
	public void addBicycle(Bicycle bicycle) {
		if (bicycle.getType()==Bicycle.BicycleType.Mechanical) {
		this.status = Status.OccupiedByMechanical;
		this.bicycle = bicycle;
		
		}
		else {
			this.status = Status.OccupiedByElectrical;
			this.bicycle = bicycle;
		}
	}
	
	
	
	/**
	 * adds an event to the slot's history. Also checks that the events are entered in chronological order.
	 * @param t
	 * @param s
	 */
	
	// Je pense que c'est dans le time qu'on va printer le fait qu'un utilisateur récupère un vélo.
	public void updateUserHistory(Timestamp t, Status s){
		if(!slotHistory.isEmpty()&& slotHistory.lastKey().compareTo(t)>0){
			System.out.println("Error, do not enter a time in the past.");
		}
		else{
			slotHistory.put(t,s);
			// vérifier le toString()
			System.out.println("The slot's history is updated: the slot is now "+s.toString()+" at time "+t.toString());
		}
	}
}
