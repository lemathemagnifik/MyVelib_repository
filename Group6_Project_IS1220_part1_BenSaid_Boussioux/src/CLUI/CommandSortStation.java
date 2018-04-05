package CLUI;

import java.sql.Timestamp;
import java.util.ArrayList;

import myVelib.MyVelib;

public class CommandSortStation extends Command {

	public CommandSortStation(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void execute() {
		MyVelib myVelib = getMyVelib();
		String networkName = getArgs().get(0);
		String sortpolicy = getArgs().get(1);
		if(sortpolicy.equalsIgnoreCase("mostUsedStations")) {
			myVelib.getNetwork(networkName).mostUsedStations();
		}
		if(sortpolicy.equalsIgnoreCase("leastOccupiedStations")) {
			Timestamp t = new Timestamp(System.currentTimeMillis());
			myVelib.getNetwork(networkName).leastOccupiedStations(new Timestamp(0),t);
		}
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(2);
		
	}

}
