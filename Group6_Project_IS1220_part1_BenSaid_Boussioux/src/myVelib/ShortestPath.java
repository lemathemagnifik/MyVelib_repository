package myVelib;

import java.util.ArrayList;

public class ShortestPath extends TripPreference {

	public ShortestPath(Network network, GPS departure, GPS arrival, boolean plus, boolean uniformity) {
		// TODO Auto-generated constructor stub
	}

	@Override
	Station[] setPath(Network network, GPS departure, GPS arrival, boolean uniformity, boolean plus ) {
		ArrayList<Station> departureStations;
		ArrayList<Station> arrivalStations;
		ArrayList<Station> plusChoiceStations;
		if (uniformity) {
			ArrayList<Station> mostFullStations = uniformyzeDepartures(network.getStations(), departure, null);
			departureStations = isClosest(departure, mostFullStations);
			}
		else {
			departureStations = isClosest(departure,network.getStations());
		}
		
		if (plus) {
			plusChoiceStations = onlyPlusStations(network.getStations(), arrival);
			if (plusChoiceStations.size()==0) {
				plusChoiceStations = network.getStations();
			}
		}
		else {
			plusChoiceStations = network.getStations();
		}
			if (uniformity) {
				ArrayList<Station> lessFullStations = uniformyzeArrivals(plusChoiceStations, arrival);
				arrivalStations = isClosest(arrival, lessFullStations);
			}
			else {
				arrivalStations = isClosest(arrival,network.getStations());
			}
			
		return ClosestStations(departureStations, arrivalStations);

		}
		
		
}


