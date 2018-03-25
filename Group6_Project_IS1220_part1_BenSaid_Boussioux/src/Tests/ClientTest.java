package Tests;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Test;

import myVelib.Bicycle;
import myVelib.GPS;
import myVelib.Network;
import myVelib.ParkingSlot.UnavailableSlotException;
import myVelib.Station;
import myVelib.User;

public class ClientTest {

	@Test
	public void test() {
		
		
		
		Network myNetwork = new Network();
		
		
		
		try {
		ArrayList<Station> stations = myNetwork.stationWithBicycles(50, 2000, 70, 70);
		System.out.println(stations);
		System.out.println("Nous venons de tester avec succès le premier use case de création d'un réseau Velib");

		User user = new User("Leonard");
		user.setPosition(new GPS(10,10));
		try {
		user.rentBike(stations.get(5),Bicycle.BicycleType.Mechanical,new Timestamp(20));

		}
		c
			
		}
		catch (UnavailableSlotException e) {e.toString() ;}
		
		////Test 2
		
		
		

	}
	

}
