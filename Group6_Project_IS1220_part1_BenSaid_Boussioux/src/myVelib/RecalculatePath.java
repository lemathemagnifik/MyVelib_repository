package myVelib;

import java.util.ArrayList;

public class RecalculatePath extends TripPreference {

	@Override
	Station[] setPath(Network network, GPS departure, GPS arrival, boolean uniformity, boolean plus) {
		ArrayList<Station> arrivalStations;
		Station[] Path = new Station[2];
		arrivalStations = getArrivals(network, arrival, uniformity, plus);
		Path[1] = isClosest(departure, arrivalStations).get(0); 
		return Path;
	}

}
