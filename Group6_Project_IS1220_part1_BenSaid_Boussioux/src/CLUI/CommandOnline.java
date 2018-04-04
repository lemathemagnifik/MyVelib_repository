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
		String stationID=getArgs().get(0);
		Station station = this.getMyVelib().getStation(stationID);
		if (station!=null) {
			station.setStatus(Station.Status.OnService);
			System.out.println("The station with ID "+stationID+ " is On Service.");
			}
		else {throw new SyntaxErrorException("Please check the station ID.");}		

	}

	@Override
	public void check() throws SyntaxErrorException {
		// TODO Auto-generated method stub
		
	}

}
