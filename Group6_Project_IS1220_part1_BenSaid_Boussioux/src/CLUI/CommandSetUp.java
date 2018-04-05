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
		String networkName = getArgs().get(0);
		
		if (getArgs().size()==1) {
		
		try {
			//myVelib.addNetwork(CreateTestNetwork.CreateTestingNetwork());
			myVelib.addNetwork(networkName);
			 System.out.println("The network "+networkName+" has been created.");

		} catch (UnavailableSlotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		
		if (getArgs().size()==5) {
			try {
				//myVelib.addNetwork(CreateTestNetwork.CreateTestingNetwork());
				
				
				
				
				
				
				
				
				
				
				myVelib.addNetwork(networkName,nstations, nslots, sidea, nbikes);
				 System.out.println("The network "+networkName+" has been created.");

			} catch (UnavailableSlotException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(1,5);
		// TODO Auto-generated method stub
		
	}

}
