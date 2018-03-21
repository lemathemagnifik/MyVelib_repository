package myVelib;

public class CreditCard {

	private User user;
	private int ID;
	private double balance;
	static double creditLimit=0;
	
	static int IDcounter=0;

	public CreditCard(User user, double balance) {
		super();
		IDcounter++;
		this.user = user;
		this.ID = IDcounter;
		this.balance = balance;
	}
	
	
	public User getUser() {
		return user;
	}


	public int getID() {
		return ID;
	}


	public double getBalance() {
		return balance;
	}


	public static double getCreditLimit() {
		return creditLimit;
	}


	public void addMoney(double amount) {
		this.balance = this.balance + amount;
	}
	
	public void pay(double amount) {
		if ( (this.balance-amount) < CreditCard.creditLimit) {
			System.out.println("Insufficient funds");
		}
		else {
			this.balance = this.balance - amount;
		}
	}

}
