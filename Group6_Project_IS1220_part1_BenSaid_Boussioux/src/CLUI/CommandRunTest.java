package CLUI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import myVelib.MyVelib;

public class CommandRunTest extends Command {

	public CommandRunTest(MyVelib myVelib, ArrayList<String> args) throws SyntaxErrorException, MisuseException {
		super(myVelib, args);
	}

	@Override
	public void execute() throws SyntaxErrorException, MisuseException {
		MyVelib myVelib = getMyVelib();
		String path="";
		for (int i=0;i<getArgs().size()-1;i++) {
			path+=getArgs().get(i)+" ";
		}
		path+=getArgs().get(getArgs().size()-1);
		try{
			InputStream flux=new FileInputStream(path); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff = new BufferedReader(lecture);
			String line;
			while ((line=buff.readLine())!=null){
				CmdParser parser = new CmdParser();
				CommandFactory cmdFactory = new CommandFactory(myVelib);
				parser.parse(line);
				cmdFactory.createCommand(parser.getCmd(),parser.getArgs()).execute();
			}
			buff.close(); 
			}		
			catch (Exception e){
			System.out.println(e.toString());};		
		
		// TODO Auto-generated method stub

	}

	@Override
	public void check() throws SyntaxErrorException {
		// TODO Auto-generated method stub

	}

}
