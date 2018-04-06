package CLUI;

import java.util.ArrayList;

import myVelib.MyVelib;
import myVelib.Station;

public class CommandOnline extends Command {

	public CommandOnline(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void execute() throws SyntaxErrorException {
		MyVelib myVelib = this.getMyVelib();
		myVelib.printCurrentTime();
		int stationID=stringToInt(getArgs().get(0), "station ID");
		Station station = myVelib.getStation(stationID);
		if (station!=null) {
			station.setStatus(Station.Status.OnService);
			System.out.println("The station with ID "+stationID+ " is On Service.\n");
			}
		else {throw new SyntaxErrorException("Please check the station ID.");}		

	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(1);
	}

}
