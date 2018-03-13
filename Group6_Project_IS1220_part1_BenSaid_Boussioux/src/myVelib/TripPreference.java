package myVelib;

import java.util.ArrayList;



abstract class TripPreference {
	
	/**
	 * Method giving the departure and arrival stations.
	 * @param departure
	 * @param arrival
	 * @param network
	 * @return
	 */
	
	abstract Station[] setPath(Network network, GPS departure, GPS arrival, boolean uniformity, boolean plus);
	
	public static ArrayList<Station> isClosest(GPS position, ArrayList<Station> stations) {
		
		ArrayList<Station> closest = new ArrayList<Station>();
		double mindist = Double.MAX_VALUE;
		double eps= Math.pow(10, -15);
		
		for (Station station:stations) {
			GPS location=station.getPosition();
			if (position.equals(location)){
				continue;
			}
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
			if (distance<radius) {
				foundStations.add(s);
			}
		}
		return foundStations;
	}
	
	
	public ArrayList<Station> uniformiseDepartures(ArrayList<Station> stations,GPS departure, Bicycle.BicycleType bType){
		ArrayList<Station> uniformStations = new ArrayList<Station>();
		
		ArrayList<Station> stationsInRadius = getStationsInRadiusPercent(stations, departure, 1.05, false);
		int maxOccupied = 0;
		for (Station s:stationsInRadius) {
			if (( bType == Bicycle.BicycleType.Electrical && s.slotsOccupiedByElectrical()!=0) || (bType==Bicycle.BicycleType.Mechanical && s.slotsOccupiedByMechanical()!=0 ) || bType==null && s.slotsOccupied()!=0) {
				if (s.slotsOccupied()>=maxOccupied) {
					if (s.slotsFree()>maxOccupied) {
						uniformStations.clear();
					}
					uniformStations.add(s);
				}
			}

		}
		return uniformStations;
	}
	

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
	
	
	
	public static void main(String[] args) {		
	}
	
}
