package model;

public class Amount {
	private double value;
	private String currently;
	
	public Amount (double value) {
		this.value = value;
		this.currently = "€";
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getCurrently() {
		return currently;
	}

	public void setCurrently(String currently) {
		this.currently = currently;
	}
	
	public String toString() {
		return value + currently;
	}

}
