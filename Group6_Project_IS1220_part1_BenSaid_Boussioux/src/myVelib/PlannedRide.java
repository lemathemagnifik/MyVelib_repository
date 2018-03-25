package myVelib;

import java.util.Arrays;


public class PlannedRide extends Ride{
	private TripPreference preference;
	private GPS departure;
	private GPS arrival;
	private Network network;
	private boolean plus;
	private boolean uniformity;
	private boolean fastest;
	private boolean alreadyHaveBicycle;
	private Station[] path;
	
	public PlannedRide(Network network, GPS departure, GPS arrival, boolean plus, boolean uniformity, boolean fastest, boolean alreadyHaveBicycle) {
		super();
		this.network = network;
		this.departure = departure;
		this.arrival = arrival;
		this.fastest = fastest;
		this.plus = plus;
		this.uniformity = uniformity;
		if (alreadyHaveBicycle) {
			preference = new RecalculatePath();
		}
		else {
			if (fastest) {
				preference = new FastestPath();
				FastestPath f =  new FastestPath();
				try{f.setPath(network, departure, arrival, uniformity, plus);} catch (Exception e) {}
				this.bicycleType = f.getBicycleType();
			}
			else {
				preference = new ShortestPath();
			}
		}
		try {
			this.path = preference.setPath(network, departure, arrival, uniformity, plus);
		} catch (Exception e) {	}
		this.departureStation=this.path[0];
		this.arrivalStation=this.path[1];

	}

	public TripPreference getPreference() {
		return preference;
	}

	public void setPreference(TripPreference preference) {
		this.preference = preference;
	}

	public GPS getDeparture() {
		return departure;
	}

	public void setDeparture(GPS departure) {
		this.departure = departure;
	}

	public GPS getArrival() {
		return arrival;
	}

	public void setArrival(GPS arrival) {
		this.arrival = arrival;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public boolean isPlus() {
		return plus;
	}

	public void setPlus(boolean plus) {
		this.plus = plus;
	}

	public boolean isUniformity() {
		return uniformity;
	}

	public void setUniformity(boolean uniformity) {
		this.uniformity = uniformity;
	}

	public boolean isFastest() {
		return fastest;
	}

	public void setFastest(boolean fastest) {
		this.fastest = fastest;
	}

	public boolean isAlreadyHaveBicycle() {
		return alreadyHaveBicycle;
	}

	public void setAlreadyHaveBicycle(boolean alreadyHaveBicycle) {
		this.alreadyHaveBicycle = alreadyHaveBicycle;
	}

	public Station[] getPath() {
		return path;
	}

	public void setPath(Station[] path) {
		this.path = path;
	}
	
	
	


	

	
	
//	public static void main(String[] args) throws UnavailableSlotException {
//		Network myNetwork = Test.CreateTestNetwork();
//		PlannedRide plannedRide = new PlannedRide(myNetwork, new GPS(1,1), new GPS(3.4,5), true, true, false, false);
//		System.out.println(plannedRide.getPath()[0]);
//		System.out.println(plannedRide.getPath()[1]);
//
//	}
//	
	
}
	
