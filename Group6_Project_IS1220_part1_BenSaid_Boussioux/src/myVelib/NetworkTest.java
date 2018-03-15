package myVelib;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.ecp.IS1220.tutorial6.shapeFactory.BadShapeCreationException;
import myVelib.Bicycle.BicycleType;
import myVelib.ParkingSlot.UnavailableSlotException;
import myVelib.Station.StationType;

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
			
			myNetwork.addUser(new User());
			
			//System.out.println(myNetwork.getUsers().get(i).toString());
			
			
		}
		
		// We try to put a bicycle where there is already one.
		try{
			stationNotreDame.getParkingSlots().get(1).addBicycle(new Bicycle(BicycleType.Mechanical));
		}
		catch(UnavailableSlotException e) {System.out.println("slot occupied: "  + e.toString());
		}
		//System.out.println(stationNotreDame.toString3());
		System.out.println(stationNotreDame.slotsOccupied());
		System.out.println(stationNotreDame.toString());
		
		//We add a new user
		
		User leonard = new User();
		
		
		
		
	
	
	}
}
