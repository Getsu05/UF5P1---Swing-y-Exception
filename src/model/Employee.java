package model;

import main.Logeable;

public class Employee extends Person implements Logeable{
	

	private int employeeId;
	private final int USER = 123;
	private final String PASSWORD = "test";
	
        public boolean check_login (int user, String password) {
		
		if ( user == USER && password.equals(PASSWORD)) {
          return true;
      }
		return false;
		
	}
	
	public Employee(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
}

