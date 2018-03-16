package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import myVelib.Bicycle;
import myVelib.GPS;
import myVelib.Id;
import myVelib.Network;
import myVelib.Station;
import myVelib.User;
import myVelib.Bicycle.BicycleType;
import myVelib.ParkingSlot.UnavailableSlotException;
import myVelib.Station.StationType;

public class ObserverTest {

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
		Id id = new Id();
		User user = new User("Leonard");
		user.destinationStation(stationNotreDame);
		

}}
