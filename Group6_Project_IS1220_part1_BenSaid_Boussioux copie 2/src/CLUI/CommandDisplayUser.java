package CLUI;

import java.util.ArrayList;

import myVelib.*;

public class CommandDisplayUser extends Command {
	
	public CommandDisplayUser(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws SyntaxErrorException, MisuseException {
		MyVelib myVelib = getMyVelib();
		myVelib.printCurrentTime();
		int userID = stringToInt(getArgs().get(0),"userID");
		String networkName = getArgs().get(1);
		User user = myVelib.getUser(networkName, userID);
		if (user==null) {
			throw new SyntaxErrorException("Please check the user ID or the network name.");
		}
		else System.out.println(user);
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(2);

	}

}
