package myVelib;

import java.util.ArrayList;



abstract class TripPreference {
	
	/**
	 * Method giving the departure and arrival stations.
	 * @param departure
	 * @param arrival
	 * @param network
	 * @return
	 * @throws Exception 
	 */
	
	/**
	 * Based on the Strategy Patter.
	 * @param network
	 * @param departure
	 * @param arrival
	 * @param uniformity
	 * @param plus
	 * @return returns a list of 2 stations based on users trip preference. The first, represents the departure station and the second the arrival station.
	 * @throws Exception
	 */
	abstract Station[] setPath(Network network, GPS departure, GPS arrival, boolean uniformity, boolean plus)  ;
	
	/**
	 * Return the list of the closest stations from the ArrayList<Station> to GPS position. 
	 * @param position
	 * @param stations
	 * @return
	 */
	public static ArrayList<Station> isClosest(GPS position, ArrayList<Station> stations) {
		
		ArrayList<Station> closest = new ArrayList<Station>();
		double mindist = Double.MAX_VALUE;
		double eps= Math.pow(10, -15);
		
		for (Station station:stations) {
			GPS location=station.getPosition();
//			if (position.equals(location)){
//				continue;
//			}
			if (station.slotsFree()==0 || station.getStatus()==Station.Status.Offline) {
				continue;
			}
			
			double dist=position.distance(location);
			
			if ((dist-eps)<=mindist) {
				if (Math.abs(dist-mindist)>eps) {
					mindist=dist;
					closest.clear();
				}
				closest.add(station);
			}	
		}
		return closest;
	}
	
	
	/**
	 * Returns Station[2] the couple of stations from ArrayList<Station> departures and ArrayList<Station> arrivals who are the closest to one another.
	 * @param departures
	 * @param arrivals
	 * @return
	 */
	public static Station[] ClosestStations(ArrayList<Station> departures, ArrayList<Station> arrivals) {
		
		if (departures==null ||arrivals==null) {
			return null;
		}
		
		Station[] closestCouple = {departures.get(0),arrivals.get(0)};
		Double mindist = Double.MAX_VALUE;
		double eps= Math.pow(10, -15);
		
		for (Station d:departures) {
			ArrayList<Station> closestArrivals = isClosest(d.getPosition(),arrivals);
			GPS departureGPS = d.getPosition();
			GPS arrivalGPS = closestArrivals.get(0).getPosition();
			Double distance= departureGPS.distance(arrivalGPS);
			if ((distance-eps)<=mindist) {
				if (Math.abs(distance-mindist)>eps) {
					mindist=distance;
					closestCouple[0]=d;
					closestCouple[1]=closestArrivals.get(0);
				}
					
				
			}
					
		}
				
		return closestCouple;
	}
	
	/**
	 * Returns an ArrayList<Station> containing all the stations that are in a radius that equals a percentage of the distance to the closest station amongst stations to the GPS positions. If onlyPlus == True, returns only the plus stations. 
	 * @param stations
	 * @param position
	 * @param percent
	 * @param onlyPlus
	 * @return
	 */
	public ArrayList<Station> getStationsInRadiusPercent(ArrayList<Station> stations, GPS position, Double percent, boolean onlyPlus ) {
		ArrayList<Station> foundStations = new ArrayList<Station>();
		ArrayList<Station> closestStations = isClosest(position,stations);
		Double distanceToClosest = position.distance(closestStations.get(0).getPosition());
		Double radius = percent*distanceToClosest;
		for (Station s:stations) {
			if (onlyPlus && s.getStationType()!=Station.StationType.Plus) {
				continue;
			}
			Double distance=position.distance(s.getPosition());
			if (distance<=radius) {
				foundStations.add(s);
			}
		}
		return foundStations;
	}
	
	/**
	 * Among the stations in a radius equal to 105% of the distance separating the user and the closest station, returns the list of station who have the most bicycles of the desired type.
	 * @param stations
	 * @param departure
	 * @param bType
	 * @return
	 */
	public ArrayList<Station> uniformiseDepartures(ArrayList<Station> stations,GPS departure, Bicycle.BicycleType bType){
		ArrayList<Station> uniformStations = new ArrayList<Station>();
		
		ArrayList<Station> stationsInRadius = getStationsInRadiusPercent(stations, departure, 1.05, false);
		int maxOccupied = 0;
		for (Station s:stationsInRadius) {
			if (( bType == Bicycle.BicycleType.Electrical && s.slotsOccupiedByElectrical()!=0) || (bType==Bicycle.BicycleType.Mechanical && s.slotsOccupiedByMechanical()!=0 ) || bType==null && s.slotsOccupied(null)!=0) {
				if (s.slotsOccupied(bType)>=maxOccupied) {
					if (s.slotsFree()>maxOccupied) {
						uniformStations.clear();
					}
					uniformStations.add(s);
				}
			}

		}
		return uniformStations;
	}
	
	
	/**
	 * Among the stations in a radius equal to 105% of the distance separating the user's destination and the closest station, returns the list of station who have the most free slots.
	 * @param stations
	 * @param arrival
	 * @return
	 */
	// remark : The bicycle type doesn't matter. KISS method.
	public ArrayList<Station> uniformiseArrivals(ArrayList<Station> stations,GPS arrival){
		
		ArrayList<Station> uniformStations = new ArrayList<Station>();
		
		ArrayList<Station> stationsInRadius = getStationsInRadiusPercent(stations, arrival, 1.05, false);
		int maxFree = 0;
		for (Station s:stationsInRadius) {
			if (s.slotsFree()>=maxFree) {
				if (s.slotsFree()>maxFree) {
					uniformStations.clear();
				}
				uniformStations.add(s);
			}
		}
		return uniformStations;
	} 
	
	/**
	 * From a list of stations, returns the plus stations in a radius equal to 110% of the ditance separating the destination and the closest station among the list.
	 * @param stations
	 * @param arrival
	 * @return
	 */
	public ArrayList<Station> onlyPlusStations(ArrayList<Station> stations, GPS arrival){		
		return getStationsInRadiusPercent(stations, arrival, 1.10, true);
		
	}
	
	/**
	 * returns the possible departure stations in network that fulfills the uniformity constraint considering the user position.
	 * @param network
	 * @param departure Position of the user
	 * @param uniformity
	 * @return
	 */
	public ArrayList<Station> getDepartures(Network network, GPS departure, boolean uniformity) {

		if (uniformity) {
			return isClosest(departure, uniformiseDepartures(network.getOnServiceStations(), departure, null));
			}
		else {
			return isClosest(departure,network.getOnServiceStations());
		}
	}
	
	/**
	 * returns the possible arrival stations in network that fulfills the uniformity constraint considering the user position.
	 * @param network
	 * @param departure Position of the user
	 * @param uniformity
	 * @return
	 */
	public ArrayList<Station> getArrivals(Network network, GPS arrival, boolean uniformity, boolean plus) {
		ArrayList<Station> arrivalStations;
		if (plus) {
			arrivalStations = onlyPlusStations(network.getOnServiceStations(), arrival);
			if (arrivalStations.size()==0) {
				arrivalStations = network.getOnServiceStations();
			}
		}
		else {
			arrivalStations = network.getOnServiceStations();
		}
			if (uniformity) {
				ArrayList<Station> lessFullStations = uniformiseArrivals(arrivalStations, arrival);
				arrivalStations = isClosest(arrival, lessFullStations);
			}
			else {
				arrivalStations = isClosest(arrival,network.getStations());
			}
			return arrivalStations;
	}
		
//	
//	public static void main(String[] args)  {
//		Network myNetwork = new Network();
//		try {
//			myNetwork = Test.CreateTestNetwork();
//		} catch (UnavailableSlotException e) {
//			e.printStackTrace();
//		}
//		
//		
//		ArrayList<Station> testRadius = new ShortestPath().getStationsInRadiusPercent(myNetwork.getStations(), new GPS(20,10), 1.10, false);
//		System.out.println(testRadius);
//		
//
//		ArrayList<Station> departureStations;
//		departureStations = new FastestPath().getDepartures(myNetwork, new GPS(2.5,2),false);
//		System.out.println(departureStations);
//		
//		ArrayList<Station> arrivalStations;
//		arrivalStations = new FastestPath().getArrivals(myNetwork, new GPS(5.5,5), false, false);
//		System.out.println(arrivalStations);
//
//
//		
//
//	}




}
