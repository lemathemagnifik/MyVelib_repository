package CLUI;

import java.time.Duration;
import java.util.ArrayList;
import myVelib.*;
import myVelib.Bicycle.BicycleType;
import myVelib.Station.NoBikesAvailableException;
import myVelib.Station.OfflineStationException;
import myVelib.User.AlreadyHasABikeException;

public class CommandRentBike extends Command {

	public CommandRentBike(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void execute() throws MisuseException, SyntaxErrorException {
		MyVelib myVelib = getMyVelib();
		
		int userID = stringToInt(getArgs().get(0), "userID");
		int stationID = stringToInt(getArgs().get(1), "stationID");
		String bikeType = getArgs().get(2);
		
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
		
		BicycleType bType;
		if (bikeType.equalsIgnoreCase("Electrical")) {
			bType= Bicycle.BicycleType.Electrical;
			bikeType = "an electrical";
		}
		else if (bikeType.equalsIgnoreCase("Mechanical")) {
			bType= Bicycle.BicycleType.Mechanical;
			bikeType= "a mechanical";
		}
		else throw new SyntaxErrorException("Please check the bicycle type.\n");
		try {
			myVelib.advanceTime(Duration.ofMillis(1));
			myVelib.printCurrentTime();
			user.rentBike(station, bType);
			System.out.println("The user "+user.getName()+ " with the ID "+user.getId()+" rented "+bikeType+" bike from the station "+station.getId()+".\n");
		} catch (AlreadyHasABikeException | OfflineStationException | NoBikesAvailableException e) {}
		
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(3);
	}

}
