package CLUI;

import java.util.ArrayList;

import myVelib.MyVelib;

public class CommandOnline extends Command {

	public CommandOnline(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void execute() {
		// TODO Auto-generated method stub
		MyVelib myVelib = this.getMyVelib();
		String network = getArgs().get(0);
		String stationID = getArgs().get(1);
		myVelib.offline(network,stationID);
	}

	@Override
	public void check() throws SyntaxErrorException {
		// TODO Auto-generated method stub
		
	}

}
