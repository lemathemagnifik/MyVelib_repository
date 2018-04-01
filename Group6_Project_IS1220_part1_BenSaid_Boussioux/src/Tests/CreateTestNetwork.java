package Tests;
import myVelib.*;

import myVelib.ParkingSlot.UnavailableSlotException;

public class CreateTestNetwork {
	public static Network CreateTestingNetwork() throws UnavailableSlotException {
		Network myNetwork= new Network();
		
		for (Integer i=1 ; i<=10; i++) {
			for (Integer j=1; j<=10; j++) {
			
				Station.StationType stationtype;
				if (i%3==0 && i==j){stationtype = Station.StationType.Plus;}
				else {stationtype = Station.StationType.Normal;}
				
				Station station = new Station("Station"+i.toString()+j.toString(), stationtype, new GPS(i,j), myNetwork ); 
				for (int k=1; k<=15;k++) {
					ParkingSlot parking = new ParkingSlot(station);
					Bicycle bicycle;
					if (k<=3) {
						bicycle = new Bicycle(Bicycle.BicycleType.Electrical);
						parking.addBicycle(bicycle);
						myNetwork.addBicycle(bicycle);
						}
					else if(k<=10) {
						bicycle = new Bicycle(Bicycle.BicycleType.Mechanical);
						parking.addBicycle(bicycle);
						myNetwork.addBicycle(bicycle);
						}
					station.addParkingSlot(parking);
				}
				myNetwork.addStation(station);
			}
		}
		return myNetwork;
	}
	
	public static void main(String[] args) {
		try {
			Network network = CreateTestingNetwork();
			System.out.println(network);

		} catch (UnavailableSlotException e) {
			e.printStackTrace();
		}
	}
}
