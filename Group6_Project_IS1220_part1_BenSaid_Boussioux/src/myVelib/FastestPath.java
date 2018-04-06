package myVelib;

import java.time.Duration;
import java.util.ArrayList;

import myVelib.Bicycle.BicycleType;
import myVelib.ParkingSlot.UnavailableSlotException;
import myVelib.Station.StationType;

public class FastestPath extends TripPreference {
	private BicycleType bicycleType;
	private Duration duration;
	
	public BicycleType getBicycleType() {
		return bicycleType;
	}

	public FastestPath() {
	}
	
	

	@Override
	Station[] setPath(Network network, GPS departure, GPS arrival, boolean uniformity, boolean plus )  {
		ArrayList<Station> arrivalStations;
		Station fastestDepartureStation;
		double walkingDistance;
		double cyclingDistance;
		double cyclingSpeed;
		double travelTime;
		Double minTravelTime;
		Station[] Path = new Station[2];
		BicycleType bType;
		
		arrivalStations = getArrivals(network, arrival, uniformity, plus);
		
		minTravelTime = Double.MAX_VALUE;
		
		for (Station s:network.getStations()) {
			if (s.getStatus()==Station.Status.Offline || s.slotsOccupied(null)==0) {
				continue;
			}
			else {
				GPS sPosition = s.getPosition(); 
				Station closestArrivalStation = isClosest(sPosition, arrivalStations).get(0);
				GPS closestArrivalStationPosition = closestArrivalStation.getPosition();
				walkingDistance = departure.distance(sPosition);
				cyclingDistance = sPosition.distance(closestArrivalStationPosition);
				if (s.slotsOccupiedByElectrical()!=0) {
					cyclingSpeed = Bicycle.electricalSpeed;
					bType = BicycleType.Electrical;
				}
				else if (s.slotsOccupiedByMechanical()!=0){
					cyclingSpeed = Bicycle.mechanicalSpeed;
					bType = BicycleType.Mechanical;
				}
				else {
					continue;
				}
				travelTime = walkingDistance/User.walkingSpeed + cyclingDistance/cyclingSpeed;
				if (travelTime<=minTravelTime) {
					minTravelTime = travelTime;
					fastestDepartureStation = s;
					Path[0] = fastestDepartureStation;
					Path[1] = closestArrivalStation;
					this.bicycleType = bType;
					this.duration = Duration.ZERO.plusMinutes(Math.round(60*minTravelTime)) ;
				}
			}
			if (this.bicycleType==null) {
				System.out.println("No path found.");
			}
		}
		
		return Path;

	}
	
	public static void main(String[] args) throws Exception  {
//		Network myNetwork = new Network();
//		try {
//			myNetwork = Test.CreateTestNetwork();
//		} catch (UnavailableSlotException e) {
//			e.printStackTrace();
//		}
//		
//		
		// Premier test avec le Network genere => On obtient le meme resultat que Fastest.
//		ArrayList<Station> arrivalStations;
//		arrivalStations = new FastestPath().getArrivals(myNetwork, new GPS(2,2), false, true);
//		System.out.println(arrivalStations);
//		Station[] path = new FastestPath().setPath(myNetwork, new GPS(2,2), new GPS(5,5), false, false);
//		System.out.println(path[0]);
//		System.out.println(path[1]);
		
		Network fastestNetwork = new Network();
		
		Station dm = new Station("",StationType.Normal, new GPS(0,0), fastestNetwork);
		ParkingSlot pm = new ParkingSlot(dm);
		Bicycle bm = new Bicycle(BicycleType.Mechanical);
		try {
			pm.addBicycle(bm);
		} catch (UnavailableSlotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dm.addParkingSlot(pm);
		
		Station de = new Station("",StationType.Normal, new GPS(5,0), fastestNetwork);
		ParkingSlot pe = new ParkingSlot(de);
		Bicycle be = new Bicycle(BicycleType.Electrical);
		try {
			pe.addBicycle(be);
		} catch (UnavailableSlotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		de.addParkingSlot(pe);
		
		Station a = new Station("",StationType.Normal, new GPS(60,0), fastestNetwork);
		ParkingSlot pa = new ParkingSlot(a);
		a.addParkingSlot(pa);
		
		fastestNetwork.addStation(de);
		fastestNetwork.addStation(dm);
		fastestNetwork.addStation(a);
		
		Station[] fastpath = new FastestPath().setPath(fastestNetwork, new GPS(0,0), new GPS(20+60,0), false, false);
		System.out.println(fastpath[0]);
		System.out.println(fastpath[1]);

		

	}

	public Duration getDuration() {
		return this.duration;
	}
}
