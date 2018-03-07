package myVelib;

public class Trip {
	private TripPreference preference;
	private GPS departure;
	private GPS arrival;
	private Network network;
	private boolean plus;
	private boolean uniformity;
	private boolean fastest;
	
	public Trip(Network network, GPS departure, GPS arrival, boolean plus, boolean uniformity, boolean fastest) {
		super();
		this.network = network;
		this.departure = departure;
		this.arrival = arrival;
		this.fastest = fastest;
		this.plus = plus;
		this.uniformity = uniformity;
		if (fastest) {
			if (plus) {
				if (uniformity) {
					this.preference = new FastestPlusUniformity(network, departure, arrival);
				}
				else {
					this.preference = new FastestPlus(network, departure, arrival);
				}
			}
			else {
				if (uniformity) {
					this.preference = new FastestNonPlusUniformity(network, departure, arrival);
				}
				else {
					this.preference = new FastestNonPlus(network, departure, arrival);
				}
				
			}
		}
		else {
			if (plus) {
				if (uniformity) {
					this.preference = new ShortestPlusUniformity(network, departure, arrival);
				}
				else {
					this.preference = new ShortestPlus(network, departure, arrival);
				}
			}
			else {
				if (uniformity) {
					this.preference = new ShortestNonPlusUniformity(network, departure, arrival);
				}
				else {
					this.preference = new ShortestNonPlus(network, departure, arrival);
				}				
			}
		}
	}
	
}
	
