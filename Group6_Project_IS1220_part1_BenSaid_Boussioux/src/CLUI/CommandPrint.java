package CLUI;

import java.util.ArrayList;

import myVelib.MyVelib;

public class CommandPrint extends Command {

	public CommandPrint(MyVelib myVelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myVelib, args);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws SyntaxErrorException, MisuseException {
		ArrayList<String> msg = getArgs();
		System.out.print("Simulation commentary : ");
		for (String s:msg) {
			System.out.print(s+" ");
		}
	}

	@Override
	public void check() throws SyntaxErrorException {
		// TODO Auto-generated method stub

	}

}
