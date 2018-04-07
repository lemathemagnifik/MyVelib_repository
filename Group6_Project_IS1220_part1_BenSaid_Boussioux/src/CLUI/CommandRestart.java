package CLUI;

import java.util.ArrayList;

import myVelib.*;

public class CommandRestart extends Command {

	public CommandRestart(MyVelib myVelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myVelib, args);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws SyntaxErrorException, MisuseException {
		setMyVelib(new MyVelib());
		System.out.println("The simulation has restarted.\n");
	}

	@Override
	public void check() throws SyntaxErrorException {
		checkNumOfArgs(0);
	}

}
