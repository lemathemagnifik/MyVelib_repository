package myVelib;

import java.util.ArrayList;

import Tests.CreateTestNetwork;
import myVelib.ParkingSlot.UnavailableSlotException;

public class ShortestPath extends TripPreference {
	
	
	public ShortestPath() {
	}


	@Override
	Station[] setPath(Network network, GPS departure, GPS arrival, boolean uniformity, boolean plus ) {
		ArrayList<Station> departureStations;
		ArrayList<Station> arrivalStations;
		
		departureStations = getDepartures(network, departure, uniformity);
		arrivalStations = getArrivals(network, arrival, uniformity, plus);
		return ClosestStations(departureStations, arrivalStations);
	}
	
	
	public static void main(String[] args)  {
		
		Network myNetwork = new Network();
		try {
			myNetwork = CreateTestNetwork.CreateTestingNetwork();
		} catch (UnavailableSlotException e) {
			e.printStackTrace();
		}
		
		Station[] path = new ShortestPath().setPath(myNetwork, new GPS(2,2), new GPS(5,5), false, false);
		System.out.println(path[0]);
		System.out.println(path[1]);
		
		
	}



}


