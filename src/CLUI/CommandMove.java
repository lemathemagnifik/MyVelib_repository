package CLUI;

import java.util.ArrayList;

import myVelib.GPS;
import myVelib.MyVelib;
import myVelib.User;

public class CommandMove extends Command {

	public CommandMove(MyVelib myVelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myVelib, args);
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
		GPS position = new GPS(stringToDouble(getArgs().get(2), "x"), stringToDouble(getArgs().get(3), "y"));
		user.setPosition(position);
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(4);

	}

}
