package Tests;

import myVelib.*;
import myVelib.ParkingSlot.UnavailableSlotException;


public class testUser {
	public static void main(String[] args) {
	Network myNetwork = new Network();
	try {myNetwork = CreateTestNetwork.CreateTestingNetwork();} catch (UnavailableSlotException e) { }
	User user = new User("Anis");
	myNetwork.addUser(user);
	
	user.planRide(new GPS(4.3,5.6), false, false, false);
	System.out.println(user.getRide());
	}


}
