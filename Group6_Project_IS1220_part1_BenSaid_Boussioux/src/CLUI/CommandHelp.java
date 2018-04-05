package CLUI;

import java.util.ArrayList;

import myVelib.*;

public class CommandHelp extends Command {

	public CommandHelp(MyVelib myVelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myVelib, args);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(){
		String help = "Commands available:\n";
		
		help += "\n- setup <velibnetworkName>:\n";
		help += "to create a myVelib network with given name and consisting of 10 stations each of which has 10 parking slots and such that stations are arranged on a square grid whose of side 4km and initially populated with a 75% bikes randomly distributed over the 10 stations\n";
		
		help += "\n- setup <name> <nstations> <nslots> <sidearea> <nbikes>:\n";
		help += "to create a myVelib net- work with given name and consisting of nstations stations each of which has nslots parking slots and such that stations are arranged on a square grid whose of side sidearea and initially populated with a nbikes bikes randomly distributed over the nstations stations\n";
		
		help += "\n- addUser <userName,cardType, velibnetworkName>:\n";
		help += "to add a user with name userName and card cardType (i.e. ‘‘none’’ if the user has no card) to a myVelib network velibnetworkName. Card types available :\n";
		help += "Vlibre - Vmax\n";
		
		help += "\n- offline <velibnetworkName, stationID>:\n";
		help += "to put offline the station stationID of the myVelib network velibnetworkName.\n";
		
		
		help += "\n- online <velibnetworkName, stationID>:\n";
		help += "to put online the station stationID of the myVelib network velibnetworkName.\n";
		
		help += "\n- rentBike <userID, stationID>:\n";
		help += "to let the user userID renting a bike from station stationID (if no bikes are available it behaves accordingly).\n";
		
		help += "\n- returnBike <userID, stationID, time>:\n";
		help += "to let the user userID returning a bike to station stationID at a given instant of time time (if no parking bay is available it behaves accordingly). This command displays also the cost of the rent.\n";
		
		help += "\n- displayStation<velibnetworkName, stationID>:\n";
		help += "to display the statistics of station stationID of a myVelib network velibnetwork.\n";
		
		help += "\n- displayUser<velibnetworkName, userID>:\n";
		help += "to display the statistics of user userID of a myVelib network velibnetwork.\n";
		
		help += "\n- sortStation<velibnetworkName, sortpolicy>:\n";
		help += "to display the stations in increasing order w.r.t. to the sorting policy of user sortpolicy. Sort Policies available :\n";
		help += "mostUsedStations - leastOccupiedStations\n";

		
		
		help += "\n- display <velibnetworkName>:\n";
		help += "to display the entire status (stations, parking bays, users) of an a myVelib network velibnetworkName.\n";
		
		
		help += "\n- help:\n";
		help += "get help with commands.\n";
		
	    System.out.println(help);
		
	}

	@Override
	public void check() throws SyntaxErrorException {
		// TODO Auto-generated method stub

	}

}

