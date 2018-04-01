package myVelib;

import java.time.Duration;

import myVelib.Bicycle.BicycleType;

public class CreditCard implements Card {

	private User user;
	private int ID;
	private double balance;
	static double creditLimit=0;
	
	private static double cost1HElectrical = 2;
	private static double cost1HMechanical = 1;
	
	static int IDcounter=0;

	public CreditCard(User user, double balance) {
		super();
		IDcounter++;
		this.user = user;
		this.ID = IDcounter;
		this.balance = balance;
	}
	
	
	public static double getCost1HElectrical() {
		return cost1HElectrical;
	}


	public static double getCost1HMechanical() {
		return cost1HMechanical;
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

	/**
	 * add money to the card balance.
	 * @param amount
	 */
	public void addMoney(double amount) {
		this.balance = this.balance + amount;
	}
	
	/**
	 * retrieve the amount from the card balance.
	 * @param amount
	 */
	public void pay(double amount) {
		if ( (this.balance-amount) < CreditCard.creditLimit) {
			System.out.println("Insufficient funds");
		}
		else {
			this.balance = this.balance - amount;
		}
	}


	@Override
	public Double accept(CardVisitor visitor, Duration tripTime, BicycleType type) throws Exception {
		return visitor.visit(this, tripTime, type);
	}


	@Override
	public String toString() {
		return "CreditCard : user=" + user.getName() + ", ID=" + ID + ", balance=" + balance;
	}
	
	
	
}
