package CLUI;

import java.util.ArrayList;

import myVelib.MyVelib;



public class CommandAddUser extends Command {

	public CommandAddUser(MyVelib myvelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myvelib,args);
	}



	@Override
	public void execute() {
		MyVelib myVelib = this.getMyVelib();
		String userName = getArgs().get(0);
		String cardType = getArgs().get(1);
		String velibnetworkName = getArgs().get(2);
		myVelib.addUser(userName, cardType, velibnetworkName);
	}

	@Override
	public void check() throws SyntaxErrorException {
		// TODO Auto-generated method stub
		
	}
}