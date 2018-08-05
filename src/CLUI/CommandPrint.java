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
		System.out.print("% ");
		for (String s:msg) {
			if (s.equalsIgnoreCase("\n")) {
				System.out.println();
				System.out.print("% ");
			}
			else System.out.print(s+" ");
		}
		System.out.print("\n");
	}

	@Override
	public void check() throws SyntaxErrorException {
		// TODO Auto-generated method stub

	}

}
