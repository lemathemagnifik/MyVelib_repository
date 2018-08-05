package CLUI;

import java.util.ArrayList;
import myVelib.*;


public class CommandOffline extends Command {

	public CommandOffline(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
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
			station.setStatus(Station.Status.Offline);
			System.out.println("The station with ID "+ station.getId() + " is now Offline.");
			}
		else {throw new SyntaxErrorException("Please check the station ID.");}
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(1);
	}

	public static void main(String[] args) {
		Double a=Double.parseDouble("1");
		System.out.println(a);
	}

}

