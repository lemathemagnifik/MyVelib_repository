package Tests;

import java.util.ArrayList;

import myVelib.Bicycle.BicycleType;
import myVelib.Station;
import myVelib.Station.NoMoreBikeException;

public class Test2 {
	private String str;
	
	
	public Test2(String str) {
		super();
		this.str = str;
	}


	public String getStr() {
		return str;
	}


	public void setStr(String str) {
		this.str = str;
	}
	
	


	@Override
	public String toString() {
		return "Test2 [str=" + str + "]";
	}


	public static void main(String[] args) {
	ArrayList<Test2> list = new ArrayList<Test2>();
	Test2 t1 = new Test2("1");
	list.add(t1);
	t1.setStr("3");
	System.out.println(list);
	t1=null;
	t1=new Test2("2");
	list.add(t1);
	System.out.println(list);

	}
}
