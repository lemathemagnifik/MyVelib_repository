package Tests;

import myVelib.*;
import myVelib.Bicycle.BicycleType;
import myVelib.ParkingSlot.UnavailableSlotException;
import myVelib.Station.StationType;


public class testUser {

	
	public void testPlanRideShortestPath() {
		Network myNetwork = new Network();
		try {myNetwork = CreateTestNetwork.CreateTestingNetwork();} catch (UnavailableSlotException e) { }
		User user = new User("Anis");
		myNetwork.addUser(user);
		user.planRide(new GPS(4.3,5.6), false, false, false);
		System.out.println(user.getRide());
	}
	
	//public void testPlanRideFastestPath();
	public static void main(String[] args) {
		//On cr�e un Network adapt� pour test� la fonction fastest.
		Network fastestNetwork = new Network();
		
		//On cr�e une station contenant un v�lo m�canique.
		Station dm = new Station("",StationType.Normal, new GPS(0,0), fastestNetwork);
		ParkingSlot pm = new ParkingSlot(dm);
		Bicycle bm = new Bicycle(BicycleType.Mechanical);
		try {pm.addBicycle(bm);
		} catch (UnavailableSlotException e) {}
		dm.addParkingSlot(pm);
		
		//On cr�e une station contenant un v�lo �l�ctrique.
		Station de = new Station("",StationType.Normal, new GPS(5,0), fastestNetwork);
		ParkingSlot pe = new ParkingSlot(de);
		Bicycle be = new Bicycle(BicycleType.Electrical);
		try {pe.addBicycle(be);} catch (UnavailableSlotException e) {}
		de.addParkingSlot(pe);
		
		//On cr�e une station avec un emplacement vide qui sera la station d'arriver.
		Station a = new Station("",StationType.Normal, new GPS(60,0), fastestNetwork);
		ParkingSlot pa = new ParkingSlot(a);
		a.addParkingSlot(pa);
		
		// On ajoute les stations au network
		fastestNetwork.addStation(de);
		fastestNetwork.addStation(dm);
		fastestNetwork.addStation(a);
		
//		Station[] fastpath = new FastestPath().setPath(fastestNetwork, new GPS(0,0), new GPS(20+60,0), false, false);
//		System.out.println(fastpath[0]);
//		System.out.println(fastpath[1]);

		User user = new User("Anis");
		fastestNetwork.addUser(user);
		user.planRide(new GPS(0,0), false, false, true);
		System.out.println(user.getRide());
		
	}


}
