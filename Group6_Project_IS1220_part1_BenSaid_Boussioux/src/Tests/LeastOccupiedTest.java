package Tests;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Test;

import myVelib.Network;
import myVelib.ParkingSlot;
import myVelib.Station;
import myVelib.User;
import myVelib.ParkingSlot.UnavailableSlotException;

public class LeastOccupiedTest {

	@Test
	public void test() {
		Network myNetwork = new Network();
		
		
		
		try {
		System.out.println("Test 1");
		System.out.println("We are going to put 50 stations, 2000 parking slots occupied at a rate of 70% with 70% mechanical.");	
		ArrayList<Station> stations = myNetwork.stationWithBicycles(50, 2000, 70, 70);
		myNetwork.setStations(stations);
		System.out.println("Nous venons de tester avec succès le premier use case de création d'un réseau Velib");

		System.out.println();
		System.out.println("Test 2");
		System.out.println("Leonard is a new User of the network, he is at point (10,10) and decides to rent a Bicycle at station 5.");
		User user = new User("Leonard");
		myNetwork.addUser(user);
		
		Timestamp t = new Timestamp(1);
		Timestamp r = new Timestamp(0);
		ParkingSlot slot = myNetwork.getStations().get(0).getParkingSlots().get(0);
		System.out.println(slot.getSlotHistory());
		System.out.println(slot.getBicycle().getType());
		System.out.println(slot.getSlotHistory().lowerEntry(r));
		System.out.println(slot.occupationTime(new Timestamp(0), new Timestamp(100)));
		System.out.println(myNetwork.leastOccupiedStations(r, new Timestamp(1000)));
		
	}
		catch (UnavailableSlotException e) {e.toString() ;}

}
}
