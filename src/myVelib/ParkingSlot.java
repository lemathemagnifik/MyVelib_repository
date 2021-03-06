package myVelib;

import java.sql.Timestamp;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentNavigableMap;


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
		this.slotHistory.put(new Timestamp(System.currentTimeMillis()), ParkingSlot.Status.Free);
	}
	
	public ConcurrentSkipListMap<Timestamp, ParkingSlot.Status> getSlotHistory() {
		return slotHistory;
	}


	public void setSlotHistory(ConcurrentSkipListMap<Timestamp, ParkingSlot.Status> slotHistory) {
		this.slotHistory = slotHistory;
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
		/**
		 * 
		 */
		private static final long serialVersionUID = -5202936832658305620L;

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
		Timestamp t2 = new Timestamp(System.currentTimeMillis());
		this.updateSlotHistory(t2);
		
		}
		else {
			this.status = Status.OccupiedByElectrical;
			this.bicycle = bicycle;
			//We add a value in the history.
			Timestamp t2 = new Timestamp(System.currentTimeMillis());

			this.updateSlotHistory(t2);

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
//			System.out.println("Error, do not enter a time in the past.");
		}
		else{
			//Vérifier que c'est bien fait
			this.slotHistory.put(t,this.getStatus());
			// vérifier le toString()
			//System.out.println("The slot's history is updated: the slot is now "+this.getStatus().toString()+" at time "+t.toString());
		}
	}
	
	

	public double occupationTime(Timestamp t, Timestamp r) {
		
		if  (t.compareTo(r)>0)
			return occupationTime(r,t);
		
		//[TODO] If there is no slot history we consider the slot has been free all along.
		if (this.slotHistory==null)
			return 0;
		
		if (this.slotHistory.lowerEntry(t)==null)
			t = this.slotHistory.firstKey();
		
		long acc = 0;
		
		ConcurrentNavigableMap <Timestamp,ParkingSlot.Status> toCompute = this.slotHistory.subMap(t, true, r, true);
		
		//gérer le cas où on obtient 
		if (this.slotHistory.lowerEntry(t)!=null){
		if (this.slotHistory.lowerEntry(t).getValue()==ParkingSlot.Status.Free)
			acc = acc + toCompute.firstKey().getTime()-t.getTime();}
		
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
		//System.out.println("acc" +(double) (r.getTime()-t.getTime()-acc));
		//System.out.println("sansacc " +(double) (r.getTime()-t.getTime()));
		//System.out.println((double) (r.getTime()-t.getTime()-acc)/(double)(r.getTime()-t.getTime()));

		return (double) (r.getTime()-t.getTime()-acc)/(double)(r.getTime()-t.getTime());
	}
	
	
	
	@Override
	public String toString() {
		String str="";
		String stat="";
		if (this.status==Status.Free) {
			stat="Free";
		}
		else if (this.status==Status.Broken) {
			stat="Broken";
		}
		else if (this.status==Status.OccupiedByElectrical) {
			stat="Occupied by electrical";
		}
		else if (this.status==Status.OccupiedByMechanical) {
			stat="Occupied by mechanical";
		}
		str=String.format("%-22s %1s", stat, "|");
		return str;
	}

	public static void main(String[] args) {
		
	}
	
}
