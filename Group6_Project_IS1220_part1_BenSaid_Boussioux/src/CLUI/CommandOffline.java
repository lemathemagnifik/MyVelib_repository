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
		String stationID=getArgs().get(0);
		Station station = this.getMyVelib().getStation(stationID);
		if (station!=null) {
			station.setStatus(Station.Status.Offline);
			System.out.println("The station with ID "+stationID+ " is Offline.");
			}
		else {throw new SyntaxErrorException("Please check the station ID.");}
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(1);
	}

<<<<<<< HEAD
	public static void main(String[] args) {
		Double a=Double.parseDouble("1");
		System.out.println(a);
	}
=======
	
>>>>>>> 8ab6275ed6c6164f555f25698efcdf47c655dab7
}

