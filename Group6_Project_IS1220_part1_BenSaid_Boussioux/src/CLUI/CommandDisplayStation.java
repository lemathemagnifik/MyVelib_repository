package CLUI;

import java.util.ArrayList;

import myVelib.MyVelib;
import myVelib.Station;
import myVelib.User;

public class CommandDisplayStation extends Command {

	public CommandDisplayStation(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void execute() throws SyntaxErrorException {
		MyVelib myVelib = getMyVelib();
		int stationID = stringToInt(getArgs().get(1),"stationID");
		String networkName = getArgs().get(0);
		Station station = myVelib.getStation(networkName, stationID);
		if (station==null) {
			throw new SyntaxErrorException("Please check the user ID or the network name.");
		}
		else station.displayStation();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(2);
		
	}

}
