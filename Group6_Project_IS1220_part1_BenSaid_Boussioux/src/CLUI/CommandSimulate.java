package CLUI;

import java.util.ArrayList;
import java.util.Arrays;

import myVelib.MyVelib;

public class CommandSimulate extends Command{

	public CommandSimulate(MyVelib myVelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myVelib, args);
	}

	public ArrayList<String> args(String str) {
		ArrayList<String> argslist = new ArrayList(Arrays.asList(str.split(" ")));
		return argslist;
	}
	
	@Override
	public void execute() throws SyntaxErrorException, MisuseException {
		MyVelib myVelib = getMyVelib();
//		CommandFactory cmdFactory = new CommandFactory(myVelib);
//		cmdFactory.createCommand("setup", "Paris ").execute();
		
		new CommandSetUp(myVelib, args("Paris")).execute();
		
		
		
	}

	@Override
	public void check() throws SyntaxErrorException {
		// TODO Auto-generated method stub
		
	}

}