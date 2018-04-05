package CLUI;

import java.util.ArrayList;

import myVelib.*;
import myVelib.ParkingSlot.UnavailableSlotException;
import Tests.*;

public class CommandSetUp extends Command {

	public CommandSetUp(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void execute() {
		MyVelib myVelib = getMyVelib();
		try {
			myVelib.addNetwork(CreateTestNetwork.CreateTestingNetwork());
		} catch (UnavailableSlotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void check() throws SyntaxErrorException {
		// TODO Auto-generated method stub
		
	}

}
