package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime;

public class Sale {
	private Client client;
	//Product[] products;
	ArrayList<Product> products = new ArrayList<Product>();
	double amount;
	private LocalDateTime time;
	
	
	public Sale(Client client, ArrayList<Product> products, double amount, LocalDateTime time) {
		super();
		this.client = client;
		this.products = products;
		this.amount = amount;
		this.time = time;
	}
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Sale [client=" + client.getName() + ", products=" + products.toString() + ", amount=" + amount + ", time= " + time +"]";
	}
	
	
	
	

}
