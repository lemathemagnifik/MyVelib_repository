package myVelib;

import java.sql.Timestamp;

import java.util.concurrent.ConcurrentSkipListMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentNavigableMap;


public class ParkingSlot {
	
	
	private ConcurrentSkipListMap <Timestamp, ParkingSlot.Status> slotHistory = new ConcurrentSkipListMap<Timestamp, ParkingSlot.Status>();
	public enum Status {Free, OccupiedByElectrical, OccupiedByMechanical, Broken};
	static int counter;
	public ConcurrentSkipListMap<Timestamp, ParkingSlot.Status> getSlotHistory() {
		return slotHistory;
	}


	public void setSlotHistory(ConcurrentSkipListMap<Timestamp, ParkingSlot.Status> slotHistory) {
		this.slotHistory = slotHistory;
	}

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

	public void becomesFree(Timestamp t){
		this.status = Status.Free;
		this.bicycle = null;
		this.updateSlotHistory(t);
	}
	
	public Bicycle getBicycle() {
		return bicycle;
	}


	public void setBicycle(Bicycle bicycle) {
		this.bicycle = bicycle;
	}

//On peut créer la même fonction sans timestamp peut-être
	public void becomesBroken(Timestamp t) {
		this.status = Status.Broken;
		this.updateSlotHistory(t);
	}
	
	
	
	public class UnavailableSlotException extends Exception{
		public UnavailableSlotException(){
		    System.out.println("Sorry, this slot is broken or occupied.");
		  }  
	}
	// We use this function to add bicycles at the beginning. 
	public void addBicycle(Bicycle bicycle) throws UnavailableSlotException {
		if (!(this.status == ParkingSlot.Status.Free)){
			throw new UnavailableSlotException();}
		
		if (bicycle.getType()==Bicycle.BicycleType.Mechanical) {
		this.status = Status.OccupiedByMechanical;
		this.bicycle = bicycle;
		
		}
		else {
			this.status = Status.OccupiedByElectrical;
			this.bicycle = bicycle;
		}
	}
	
	public void addBicycle(Bicycle bicycle, Timestamp t) throws UnavailableSlotException {
		if (!(this.status == ParkingSlot.Status.Free)){
			throw new UnavailableSlotException();}
		
		if (bicycle.getType()==Bicycle.BicycleType.Mechanical) {
		this.status = Status.OccupiedByMechanical;
		this.bicycle = bicycle;
		
		}
		else {
			this.status = Status.OccupiedByElectrical;
			this.bicycle = bicycle;
		}
		this.updateSlotHistory(t);
	}
	
	/**
	 * adds an event to the slot's history. Also checks that the events are entered in chronological order.
	 * @param t
	 * @param s
	 */
	
	// Je pense que c'est dans le time qu'on va printer le fait qu'un utilisateur récupère un vélo.
	public void updateSlotHistory(Timestamp t){
		if(!this.slotHistory.isEmpty()&& this.slotHistory.lastKey().compareTo(t)>0){
			System.out.println("Error, do not enter a time in the past.");
		}
		else{
			//Vérifier que c'est bien fait
			this.slotHistory.put(t,this.getStatus());
			// vérifier le toString()
			System.out.println("The slot's history is updated: the slot is now "+this.getStatus().toString()+" at time "+t.toString());
		}
	}
	//On doit prendre en compte le fait que r soit plus grand que t
	//On doit vérifier que la liste est non vide
	
	//Cette fontion est ultra relou. Je ne suis pas sûr que toCompute soit effectivement créé correctement
	// si problème de clé. En tout cas il faut gérer le temps avant et après.
	//Ya sans doute des exceptions relous que je n'ai pas gérées.
	public long occupationTime(Timestamp t, Timestamp r) {
		long acc = 0;
		
		ConcurrentNavigableMap <Timestamp,ParkingSlot.Status> toCompute = this.slotHistory.subMap(t, true, r, true);
		
		//gérer le cas où on obtient 
		if (this.slotHistory.lowerEntry(t).getValue()==ParkingSlot.Status.Free)
			acc = acc + toCompute.firstKey().getTime()-t.getTime();
		
		for (Timestamp time : toCompute.keySet() ) {
			if (toCompute.get(time)==ParkingSlot.Status.Free) {
				Timestamp nextTime = toCompute.higherKey(time);
				if (nextTime!=null) {
					acc = acc + nextTime.getTime()-time.getTime();
				}
				else {
					acc = acc + r.getTime()-time.getTime();
				}
					
			}
		}
		
		return (r.getTime()-t.getTime()-acc);
	}
}
