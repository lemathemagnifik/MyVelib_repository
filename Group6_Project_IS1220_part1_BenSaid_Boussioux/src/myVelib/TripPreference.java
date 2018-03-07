package myVelib;

import java.util.ArrayList;



abstract class TripPreference {
	private GPS departure;
	private GPS arrival;
	private Network network;
	
	/**
	 * Method giving the departure and arrival stations.
	 * @param departure
	 * @param arrival
	 * @param network
	 * @return
	 */
	abstract Station[] setPath(GPS departure, GPS arrival, Network network);
	
	public ArrayList<Station> isClosest(GPS position, ArrayList<Station> stations) {
		
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
	
	
	public Station[] ClosestStations(ArrayList<Station> departures, ArrayList<Station> arrivals) {
		
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
	
	public static void main(String[] args) {
		Network network = new Network();
		
	}
	
}
