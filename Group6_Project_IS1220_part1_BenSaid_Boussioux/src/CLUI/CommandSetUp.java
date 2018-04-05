package CLUI;

import java.util.ArrayList;


import myVelib.*;
import myVelib.ParkingSlot.UnavailableSlotException;

public class CommandSetUp extends Command {

	public CommandSetUp(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void execute() throws SyntaxErrorException, MisuseException {
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
		
		else if (getArgs().size()==5) {
			try {
				//myVelib.addNetwork(CreateTestNetwork.CreateTestingNetwork());
				int nstations = stringToInt(getArgs().get(1),"nstations");
				int nslots = stringToInt(getArgs().get(2),"nslots");
				double sidearea  = stringToDouble(getArgs().get(3),"sidearea");
				int nbikes = stringToInt(getArgs().get(4),"nbikes");
				if (nbikes>nstations*nslots) {
					System.out.println("Too many bikes for this network.");
					throw new MisuseException();}
				myVelib.addNetwork(networkName,nstations, nslots, sidearea, nbikes);
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
