package CLUI;

import java.util.ArrayList;

public class CmdParser {
	private String cmd;
	private ArrayList<String> args = new ArrayList<String>();
	
	public CmdParser() {
		super();
	}
	
	public CmdParser(String in) {
		super();
		parse(in);
	}
	
	public void parse(String in) {
		cmd = null;
		args = new ArrayList<String>();
		
		String[] parts = in.split(" ");
		for(String part : parts) {
			if(part.length() != 0) {
				if(cmd == null) {
					cmd = part;
				} else {
					args.add(part);
				}
			}
		}
	}
	
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public ArrayList<String> getArgs() {
		return args;
	}
	
	public void setArgs(ArrayList<String> args) {
		this.args = args;
	}
}

