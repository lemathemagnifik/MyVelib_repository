package CLUI;


import java.util.ArrayList;
import java.util.Scanner;
import myVelib.*;

public class CLUI {

	private CommandFactory cmdFactory;
	private CmdParser currentCMD;
	private MyVelib myVelib;
	
	public CLUI(MyVelib myVelib) throws InstantiationException, IllegalAccessException {
		super();
		this.myVelib = myVelib;
		this.cmdFactory = new CommandFactory(myVelib);
		this.currentCMD = new CmdParser();		
	}
	
	public void execute() throws InstantiationException, IllegalAccessException {
		Scanner in = new Scanner(System.in);
		String line = "";
		System.out.println("Welcome to the MyVelib Network Management Center. Enter help to get help !");
		
		do {
			try{
				line = in.nextLine();
			}catch(java.util.NoSuchElementException e){
				in.reset();
			}
		}while(parseLine(line));
		
		in.close();
		System.out.println("Simulation finished.");
	}
	
	public boolean parseLine(String line) throws InstantiationException, IllegalAccessException {
		if (line == null) {
			return true; 
		}
		if (line.trim().length() == 0) {
			return true;
		}
		if (line.trim().equals("exit")) {
			return false;
		}
		currentCMD.parse(line);
		try {
			interpret();
		} catch(SyntaxErrorException e) {
			System.out.println(e.getMessage());
		} catch(MisuseException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	private void interpret() 
			throws SyntaxErrorException, MisuseException, InstantiationException, IllegalAccessException {
		
		String cmd = currentCMD.getCmd();
		ArrayList<String> args = currentCMD.getArgs();
		
		if(cmd == null) {
			return;
		}
		
		cmdFactory.createCommand(cmd, args).execute();
		
	}

	public CommandFactory getCmdFactory() {
		return cmdFactory;
	}

	public void setCmdFactory(CommandFactory cmdFactory) {
		this.cmdFactory = cmdFactory;
	}

	public CmdParser getCurrentCMD() {
		return currentCMD;
	}

	public void setCurrentCMD(CmdParser currentCMD) {
		this.currentCMD = currentCMD;
	}

	public MyVelib getMyVelib() {
		return myVelib;
	}

	public void setMyVelib(MyVelib myVelib) {
		this.myVelib = myVelib;
	}



}

