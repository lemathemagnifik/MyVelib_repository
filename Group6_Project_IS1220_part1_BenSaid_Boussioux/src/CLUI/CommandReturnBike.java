package CLUI;

import java.util.ArrayList;
import java.time.Duration;
import myVelib.*;
import myVelib.Station.NoAvailableFreeSlotsException;
import myVelib.Station.OfflineStationException;


public class CommandReturnBike extends Command {

	public CommandReturnBike(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
	}



	@Override
	public void execute() throws SyntaxErrorException, MisuseException {
		MyVelib myVelib = getMyVelib();
		
		int userID = stringToInt(getArgs().get(0), "user ID");
		int stationID = stringToInt(getArgs().get(1),"stationID");
		String strDuration = getArgs().get(2);
		
		User user = myVelib.getUser(userID);
		if (user==null) {
			throw new SyntaxErrorException("Please check the user ID.");
		}

		Station station = myVelib.getStation(stationID);
		if (station==null) {
			throw new SyntaxErrorException("Please check the station ID.");
		}
		
		if (station.getNetwork().getName().compareTo(user.getNetwork().getName())==1) {
			throw new MisuseException("The user and the station do not belong to the same network.\n");
			}
		
		
		Duration tripDuration = Duration.ofMinutes(Long.parseLong(strDuration));
		
		try {
			user.returnBike(station, tripDuration);
			System.out.println("The user "+user.getName()+ " with the ID "+user.getId()+" returned his bike to the station "+station.getId()+".\n");
		} catch (OfflineStationException | NoAvailableFreeSlotsException e) {}
		
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(3);
	}

}
