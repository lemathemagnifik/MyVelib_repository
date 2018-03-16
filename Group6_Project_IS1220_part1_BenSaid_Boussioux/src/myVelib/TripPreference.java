package myVelib;

import java.util.ArrayList;

import myVelib.ParkingSlot.UnavailableSlotException;



abstract class TripPreference {
	
	/**
	 * Method giving the departure and arrival stations.
	 * @param departure
	 * @param arrival
	 * @param network
	 * @return
	 */
	
	abstract Station[] setPath(Network network, GPS departure, GPS arrival, boolean uniformity, boolean plus);
	
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
			if (station.getStatus()==Station.Status.Full || station.getStatus()==Station.Status.Offline) {
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
	 * returns the list of the stations who have the most 
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
	
	// remarque : ici le type de vélo n'est pas pris en compte. KISS method.
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
	
	
	public ArrayList<Station> onlyPlusStations(ArrayList<Station> stations, GPS arrival){		
		return getStationsInRadiusPercent(stations, arrival, 1.10, true);
		
	}
	
	public ArrayList<Station> getDepartures(Network network, GPS departure, boolean uniformity) {
		ArrayList<Station> departureStations;
		if (uniformity) {
			ArrayList<Station> mostFullStations = uniformiseDepartures(network.getStations(), departure, null);
			departureStations = isClosest(departure, mostFullStations);
			}
		else {
			departureStations = isClosest(departure,network.getStations());
		}
		return departureStations;
	}
	
	public ArrayList<Station> getArrivals(Network network, GPS arrival, boolean uniformity, boolean plus) {
		ArrayList<Station> arrivalStations;
		if (plus) {
			arrivalStations = onlyPlusStations(network.getStations(), arrival);
			if (arrivalStations.size()==0) {
				arrivalStations = network.getStations();
			}
		}
		else {
			arrivalStations = network.getStations();
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
		
	
	public static void main(String[] args)  {
		Network myNetwork = new Network();
		try {
			myNetwork = Test.CreateTestNetwork();
		} catch (UnavailableSlotException e) {
			e.printStackTrace();
		}
		
		
		ArrayList<Station> testRadius = new ShortestPath().getStationsInRadiusPercent(myNetwork.getStations(), new GPS(20,10), 1.10, false);
		System.out.println(testRadius);
		

		ArrayList<Station> departureStations;
		departureStations = new FastestPath().getDepartures(myNetwork, new GPS(2.5,2),false);
		System.out.println(departureStations);
		
		ArrayList<Station> arrivalStations;
		arrivalStations = new FastestPath().getArrivals(myNetwork, new GPS(5.5,5), false, false);
		System.out.println(arrivalStations);


		

	}




}
