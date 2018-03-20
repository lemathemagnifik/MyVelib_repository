package myVelib;

public class PlannedRide {
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
			}
			else {
				preference = new ShortestPath();
			}
		}
		this.path = preference.setPath(network, departure, arrival, uniformity, plus);
	}
	
	
	
}
	
