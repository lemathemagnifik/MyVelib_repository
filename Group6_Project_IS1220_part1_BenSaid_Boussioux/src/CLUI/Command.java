package CLUI;

import java.util.ArrayList;

import myVelib.*;



public abstract class Command {

	private MyVelib myVelib;
	private ArrayList<String> args = new ArrayList<String>();
	
	public Command(MyVelib myVelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super();
		this.myVelib = myVelib;
		this.args = args;
		check();
	}

	public abstract void execute() throws SyntaxErrorException;	
	public abstract void check() throws SyntaxErrorException;
	public void checkNumOfArgs(int num) throws SyntaxErrorException{
		if(args.size() != num) {
			throw new SyntaxErrorException("This cmd requires "+num+" arguments.");
		}
	}


	public MyVelib getMyVelib() {
		return myVelib;
	}

	public void setMyVelib(MyVelib myVelib) {
		this.myVelib = myVelib;
	}

	public ArrayList<String> getArgs() {
		return args;
	}

	public void setArgs(ArrayList<String> args) {
		this.args = args;
	}
	
}

