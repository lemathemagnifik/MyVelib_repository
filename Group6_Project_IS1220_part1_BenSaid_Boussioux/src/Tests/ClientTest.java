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
		ArrayList<Station> stations = myNetwork.stationWithBicycles(50, 2000, 70, 70);
		System.out.println(stations);
		System.out.println("Nous venons de tester avec succès le premier use case de création d'un réseau Velib");

		User user = new User("Leonard");
		user.setPosition(new GPS(10,10));
		
		//Premier test
		try {
			try {
				try {
					user.rentBike(stations.get(5),Bicycle.BicycleType.Mechanical,new Timestamp(20));
					}
				catch(AlreadyHasABikeException e) {e.toString();}	
				}
			catch(OfflineStationException e) {e.toString();}
		}
		catch(NoBikesAvailableException e) {e.toString();}
		
		
		
		//Deuxième test
		try {
			try {
				user.returnBike(stations.get(9), new Timestamp(10000000));
				System.out.println("Nous venons de tester avec succès le second use case : location et retour d'un vélo");

				}
			catch (NoAvailableFreeSlotsException e) {e.toString();}		
			}
		catch(OfflineStationException e) {e.toString();}
		
		//Troisième test
		
		GPS destination = new GPS(30,35);
		user.planRide(destination, true, false, false);
		
	}
		catch (UnavailableSlotException e) {e.toString() ;}


}
}
