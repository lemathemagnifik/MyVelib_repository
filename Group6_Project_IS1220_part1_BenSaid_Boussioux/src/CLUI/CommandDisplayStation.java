package CLUI;

import java.sql.Timestamp;
import java.util.ArrayList;
import myVelib.*;


public class CommandDisplayStation extends Command {

	public CommandDisplayStation(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void execute() throws SyntaxErrorException {
		MyVelib myVelib = getMyVelib();
		myVelib.printCurrentTime();
		int stationID = stringToInt(getArgs().get(1),"stationID");
		String networkName = getArgs().get(0);
		Station station = myVelib.getStation(networkName, stationID);
		if (station==null) {
			throw new SyntaxErrorException("Please check the user ID or the network name.\n");
		}
		
		
		Timestamp t = new Timestamp(System.currentTimeMillis());

		station.displayStation(new Timestamp(0),t);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(2);
		
	}

}
