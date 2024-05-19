package model;

import main.Payable;

public class Client extends Person implements Payable{
	
	protected int memberId;
	protected Amount balance;
	protected final int MEMBER_ID = 456;
	protected final double BALANCE = 50.00;	
	
	public Client(String name, int memberId, Amount balance) {
		super(name);
		this.memberId = memberId;
		this.balance = balance;
		// TODO Auto-generated constructor stub
	}
	
	

	public int getMemberId() {
		return memberId;
	}



	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}



	public Amount getBalance() {
		return balance;
	}



	public void setBalance(Amount balance) {
		this.balance = balance;
	}



	public int getMEMBER_ID() {
		return MEMBER_ID;
	}



	public double getBALANCE() {
		return BALANCE;
	}



	public boolean pay(Amount saleAmount) {
	    double newBalance = balance.getValue() - saleAmount.getValue();
	    if (newBalance >= 0) {
	        balance.setValue(newBalance);
	        System.out.println("Venta realizada, balance actual: " + balance);
	        return true;
	    } else {
	        System.out.println("Balance insuficiente para realizar la venta");
	        return false;
	    }
	}
	
}
