package CLUI;
	

import myVelib.*;

public class CLUITest {
	
	public static
	void main(String[] args) throws InstantiationException, IllegalAccessException {
		MyVelib myVelib = new MyVelib();
		CLUI myCLUI = new CLUI(myVelib);
		myCLUI.execute();
	}

}

