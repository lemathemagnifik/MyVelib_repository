package Tests;

import static org.junit.Assert.*;



import org.junit.Test;
import java.sql.Timestamp;
import java.time.Duration;


import myVelib.User.AlreadyHasABikeException;
import myVelib.User.NoMoreElectricalException;
import myVelib.User.UnavailableStationException;
import myVelib.Bicycle.BicycleType;
import myVelib.ParkingSlot.UnavailableSlotException;
import myVelib.Station.StationType;
import myVelib.*;

public class NetworkTest {

	@Test
	public void test() throws UnavailableSlotException {
		Network myNetwork = new Network();
		Station stationStMichel = new Station("StMichel", StationType.Normal, new GPS(0,0), myNetwork);
		Station stationStJacques = new Station("StJacques", StationType.Normal, new GPS(1,1), myNetwork);
		Station stationNotreDame = new Station("NotreDame", StationType.Plus, new GPS(2,1), myNetwork);
		myNetwork.addStation(stationNotreDame);
		myNetwork.addStation(stationStJacques);
		myNetwork.addStation(stationStMichel);
		
		
		//We add slots to Stations
		for (int i=0; i<=20;i++) {
			stationStMichel.addParkingSlot();
			stationNotreDame.addParkingSlot();
			stationStJacques.addParkingSlot();
			stationNotreDame.getParkingSlots().get(i).addBicycle(new Bicycle(BicycleType.Electrical));
			stationStJacques.getParkingSlots().get(i).addBicycle(new Bicycle(BicycleType.Mechanical));
			
			//System.out.println(myNetwork.getUsers().get(i).toString());
			
			
		}
		
		// We try to put a bicycle where there is already one.
		try{
			stationNotreDame.getParkingSlots().get(1).addBicycle(new Bicycle(BicycleType.Mechanical));
		}
		catch(UnavailableSlotException e) {System.out.println("slot occupied: "  + e.toString());
		}
		//System.out.println(stationNotreDame.toString3());

		
		//We add a new user
		Id id = new Id();
		User user = new User("Leonard");
		
		BlueCard cardTest = new BlueCard(user);
		user.setCard(cardTest);
		System.out.println(cardTest.getCostH1electrical());
		Duration d = Duration.ZERO;
			try {
				user.visit((BlueCard) cardTest, d, BicycleType.Electrical);
				System.out.println("it works");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
		Timestamp t = new Timestamp(20);	
		Timestamp u = new Timestamp(50);	

		try {
			try {
				user.dropOnElectrical(stationNotreDame, t );
				
				
			}
			catch(NoMoreElectricalException e) {System.out.println("no electrical: "  + e.toString());
			} 
		}
		catch(AlreadyHasABikeException e) {System.out.println("already bike: "  + e.toString());
		}	
		
		
		try {
			user.dropOff(stationNotreDame, u);
			}
		catch(UnavailableStationException e) {System.out.println("no electrical: "  + e.toString());
		} 
		//System.out.println(user.getUserHistory());
		user.displayMessage();

		user.subscribeStation(stationNotreDame);
		user.subscribeStation(stationStJacques);
		stationNotreDame.setStatus(Station.Status.Full);
		stationStJacques.setStatus(Station.Status.Offline);
		stationStMichel.setStatus(Station.Status.Offline);
		user.displayMessage();
		
		
	
	
	}
}
