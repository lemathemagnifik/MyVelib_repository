package CLUI;

import java.util.ArrayList;

import myVelib.MyVelib;

public class CommandSimulate extends Command{

	public CommandSimulate(MyVelib myVelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myVelib, args);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws SyntaxErrorException, MisuseException {
		// TODO Auto-generated method stub
		CommandAddUser addUser = new CommandAddUser();
		
	}

	@Override
	public void check() throws SyntaxErrorException {
		// TODO Auto-generated method stub
		
	}

}
