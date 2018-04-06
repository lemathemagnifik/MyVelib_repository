package Tests;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;

import org.junit.Test;

import myVelib.Bicycle;
import myVelib.GPS;
import myVelib.Network;
import myVelib.ParkingSlot.UnavailableSlotException;
import myVelib.PlannedRide;
import myVelib.Station;
import myVelib.Station.NoAvailableFreeSlotsException;
import myVelib.Station.NoBikesAvailableException;
import myVelib.Station.OfflineStationException;
import myVelib.User;
import myVelib.User.AlreadyHasABikeException;

public class ClientTest {

	@Test
	public void test() {
		
		
		
		Network myNetwork = new Network();
		
		
		
		try {
		System.out.println("Test 1");
		System.out.println("We are going to put 50 stations, 2000 parking slots occupied at a rate of 70% with 70% mechanical.");	
		ArrayList<Station> stations = myNetwork.stationWithBicycles(50, 2000, 70, 70,10);
		myNetwork.setStations(stations);
		System.out.println("Nous venons de tester avec succès le premier use case de création d'un réseau Velib");

		System.out.println();
		System.out.println("Test 2");
		System.out.println("Leonard is a new User of the network, he is at point (10,10) and decides to rent a Bicycle at station 5.");
		User user = new User("Leonard");
		myNetwork.addUser(user);
		user.setPosition(new GPS(10,10));
		
		//Premier test
		try {
			try {
				try {
					user.rentBike(stations.get(5),Bicycle.BicycleType.Mechanical);
					}
				catch(AlreadyHasABikeException e) {e.toString();}	
				}
			catch(OfflineStationException e) {e.toString();}
		}
		catch(NoBikesAvailableException e) {e.toString();}
		System.out.println();
		System.out.println("Léonard now returns his bicycle at station 6.");
		
		//Deuxième test
		try {
			try {
				user.returnBike(stations.get(9), new Duration(10));
				System.out.println("Nous venons de tester avec succès le second use case : location et retour d'un vélo");
				}
			catch (NoAvailableFreeSlotsException e) {e.toString();}		
			}
		catch(OfflineStationException e) {e.toString();}
		
		//Troisième test
		System.out.println();
		System.out.println("Test 3");
		System.out.println("Léonard is at (10,10) and wants to go at (45,45). He decides to plan a path.");
		user.setPosition(new GPS(10,10));
		GPS destination = new GPS(45,45);
		user.planRide(destination, false, false, false);
		System.out.println(user.plannedRide);
				try {
					try {
						try {
							System.out.println("Léonard loue son vélo à la station conseillée.");
							user.rentBike(user.plannedRide.getDepartureStation(),Bicycle.BicycleType.Mechanical,new Timestamp(200000000));
							user.subscribeStation(user.plannedRide.getArrivalStation());
							}
						catch(AlreadyHasABikeException e) {e.toString();}	
						}
					catch(OfflineStationException e) {e.toString();}
				}
				catch(NoBikesAvailableException e) {e.toString();}
		user.plannedRide.getArrivalStation().setStatus(Station.Status.Offline);

	}
		catch (UnavailableSlotException e) {e.toString() ;}
		
		//Test de mostUsedStations()
		System.out.println(myNetwork.mostUsedStations());
		System.out.println(myNetwork.leastOccupiedStations(new Timestamp(0), new Timestamp(100)));


}
}
