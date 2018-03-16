package myVelib;

public class Trip {
	private TripPreference preference;
	private GPS departure;
	private GPS arrival;
	private Network network;
	private boolean plus;
	private boolean uniformity;
	private boolean fastest;
	private boolean alreadyHaveBicycle;
	
	public Trip(Network network, GPS departure, GPS arrival, boolean plus, boolean uniformity, boolean fastest, boolean alreadyHaveBicycle) {
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
		
	}
	
	
	
}
	
