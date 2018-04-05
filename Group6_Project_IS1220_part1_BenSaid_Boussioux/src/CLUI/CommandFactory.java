package CLUI;

import java.util.ArrayList;

import myVelib.*;


public class CommandFactory {
	
	private MyVelib myVelib;
	
	public CommandFactory(MyVelib myVelib) {
		super();
		this.myVelib = myVelib;
	}

	public Command createCommand(String cmd, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		
		if(cmd == null) {
			return null;
		}
		
		if(cmd.equalsIgnoreCase("help")) {
			return new CommandHelp(myVelib, args);
		}
		
		if(cmd.equalsIgnoreCase("setup")) {
			return new CommandSetUp(myVelib, args);
		}
		
		if(cmd.equalsIgnoreCase("addUser")) {
			return new CommandAddUser(myVelib, args);
		}
		
		if(cmd.equalsIgnoreCase("display")) {
			return new CommandDisplay(myVelib, args);
		}
		
		if(cmd.equalsIgnoreCase("displayStation")) {
			return new CommandDisplayStation(myVelib, args);
		}
		
		if(cmd.equalsIgnoreCase("displayUser")) {
			return new CommandDisplayUser(myVelib, args);
		}
		
		if(cmd.equalsIgnoreCase("offline")) {
			return new CommandOffline(myVelib, args);
		}
		
		if(cmd.equalsIgnoreCase("online")) {
			return new CommandOnline(myVelib, args);
		}
		
		if(cmd.equalsIgnoreCase("rentBike")) {
			return new CommandRentBike(myVelib, args);
		}
		
		if(cmd.equalsIgnoreCase("returnBike")) {
			return new CommandReturnBike(myVelib, args);
		}
		
		if(cmd.equalsIgnoreCase("sortStation")) {
			return new CommandSortStation(myVelib, args);
		}
		
		if(cmd.equalsIgnoreCase("runtest")) {
			return new CommandRunTest(myVelib, args);
		}

		throw new SyntaxErrorException("No such command.");
	}

	public MyVelib getMyVelib() {
		return myVelib;
	}

	public void setMyVelib(MyVelib myVelib) {
		this.myVelib = myVelib;
	}


	}
	


