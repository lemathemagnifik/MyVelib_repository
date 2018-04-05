package CLUI;

import java.sql.Timestamp;
import java.util.ArrayList;

import myVelib.MyVelib;
import myVelib.Network;

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
		Timestamp t2 = new Timestamp(System.currentTimeMillis());
		Timestamp t1 = new Timestamp(0);
		if(sortpolicy.equalsIgnoreCase("mostUsedStations")) {
			Network.displayArrayStations(myVelib.getNetwork(networkName).mostUsedStations(), t1, t2);
		}
		if(sortpolicy.equalsIgnoreCase("leastOccupiedStations")) {
			Network.displayArrayStations(myVelib.getNetwork(networkName).leastOccupiedStations(t1,t2),t1,t2);
		}
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(2);
		
	}

}
