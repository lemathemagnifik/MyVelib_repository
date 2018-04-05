package CLUI;

import java.util.ArrayList;

import myVelib.MyVelib;
import myVelib.Network;

public class CommandDisplay extends Command{

	public CommandDisplay(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
	}



	@Override
	public void execute() throws SyntaxErrorException {
		MyVelib myVelib = this.getMyVelib();
		String velibnetworkName = getArgs().get(0);
		Network network = myVelib.getNetwork(velibnetworkName);
		if (network==null) {
			throw new SyntaxErrorException("Please check the network name.");
		}
		System.out.println(network);
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(1);
	}

}
