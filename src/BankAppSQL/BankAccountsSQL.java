package BankAppSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JFrame;

public class BankAccountsSQL{
    PreparedStatement ps = null;
    ResultSet rs = null;
    
	private int accountNumber;
	
	private float balance;
	private float withdrawAmount;
	private float depositAmount;
	private float initialBalance;
	
	private String first_name = "";
	private String last_name = "";
	private String customer_username = "";
	private String customer_password = "";
	
	private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	private String serverName = "jdbc:mysql://remotemysql.com:3306/DmrmDDa2RC";
	private String userName = "DmrmDDa2RC";
	private String password = "VmZwyXQo4v";
	
	//Check if username and password combination is valid.
	public boolean SQLUserCheck(String customer_username, String customer_password) {
		try 
		{
			//Connection to DB
			Class.forName(jdbcDriver);
			Connection con = DriverManager.getConnection(serverName, userName, password);
			
			//Insert new customer to SQL table customer on MYSQL
			ps = con.prepareStatement("SELECT * FROM customer WHERE customer_username = ? AND customer_password = ?");
			ps.setString(1, customer_username);
			ps.setString(2, customer_password);
			
			rs = ps.executeQuery();
			
			if(!rs.next()) {
				System.out.println("Try again. Incorrect password or username.");
				return false;
			}
			
			this.first_name = rs.getString(2);
			this.last_name = rs.getString(3);
			this.customer_username = rs.getString(5);
			this.customer_password = rs.getString(6);
			
		}
		
		
		catch (ClassNotFoundException | SQLException ex) 
		{
			System.out.println(ex);
		}
		
		getBalance();
		
		return true;
	}
    
    //Queries all the column/row from a table (customer) in the DB
    public void getAllAccounts() {
    	try 
		{
			//Connection to DB
			Class.forName(jdbcDriver);
			Connection con = DriverManager.getConnection(serverName, userName, password);
			
			//Reads all accounts from DB with Account # / First - Last name / Balance Amount
			Statement st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM customer;");
			
			System.out.println("Account\t\tFirst\t\tLast Name\tBalance");
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "\t\t"  + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t" + rs.getFloat(4));
			}
		}
    	
    	
		catch (ClassNotFoundException | SQLException ex) 
		{
				System.out.println(ex);
		}
    }
    
	//Adds a new customer to the SQL database
	//SPECIFY A FIRST/LAST NAME + initial balance
	public void newAccount(String first_name, String last_name, float balance) 
	{
		this.first_name = first_name;
		this.last_name = last_name;
		this.balance = balance;
		
		Scanner getUsername = new Scanner(System.in);
		System.out.print("Enter a username for this account: ");
		customer_username = getUsername.next();

		
		Scanner getPassword = new Scanner(System.in);
		System.out.print("Enter a password for this account: ");
		customer_password = getPassword.next();
		
		try 
		{
			//Connection to DB
			Class.forName(jdbcDriver);
			Connection con = DriverManager.getConnection(serverName, userName, password);
			
			//Insert new customer to SQL table customer on MYSQL
			ps = con.prepareStatement("INSERT INTO customer (customer_first_name, customer_last_name, customer_balance, customer_username, customer_password) VALUES(?,?,?,?,?)");
			ps.setString(1, first_name);
			ps.setString(2, last_name);
			ps.setFloat(3, balance);
			ps.setString(4, customer_username);
			ps.setString(5, customer_password);
			ps.executeUpdate();
			
			System.out.println(first_name + " " + last_name + " was added to the data base with balance of $" + balance);
		}
		

			catch (ClassNotFoundException | SQLException ex) 
		{
				System.out.println(ex);
		}
	}
	
	
	
	//GETS Balance of a customer when user submits password and username.
	public void getBalance() {
		try 
		{
			//Connection to DB
			Class.forName(jdbcDriver);
			Connection con = DriverManager.getConnection(serverName, userName, password);
			
			//Statement for SQL to read customer account
			ps = con.prepareStatement("SELECT * FROM customer WHERE customer_username = ? AND customer_password = ?;");
			ps.setString(1, this.customer_username);
			ps.setString(2, this.customer_password);
			
			//Reads Results and prints balance amount for customer
			rs = ps.executeQuery();
			
			while(rs.next()) {
				this.balance = rs.getFloat(4);
			}
			
			
		}
		

			catch (ClassNotFoundException | SQLException ex) 
		{
				System.out.println(ex);
		}
	}
	
	
	//Allows a customer to withdraw from their account with an account number and FIRST/LAST name
	public void accountWithdrawal(String withdraw) {
		//User input to withdraw amount
		float withdrawAmount = Float.parseFloat(withdraw);
		
		try 
		{
			//SQLDB Connection
			Class.forName(jdbcDriver);
			Connection con = DriverManager.getConnection(serverName, userName, password);
			
			//Statement to run for SQL 
			ps = con.prepareStatement("SELECT * FROM customer WHERE customer_username = ? AND customer_password = ?;");
			ps.setString(1, this.customer_username);
			ps.setString(2, this.customer_password);
			
			//Reads Results and updates balance for withdrawal.
			rs = ps.executeQuery();
			while(rs.next()) {
				this.balance = rs.getFloat(4);
				if(withdrawAmount > this.balance) {
					System.out.println("Not enough $ to withdraw from bank.\n" + "Current Balance: $" + balance);
					break;
				}
				
				else {
					this.balance = balance - withdrawAmount;
					
					//Updating balance to SQL server
					PreparedStatement ps1 = con.prepareStatement("UPDATE customer SET customer_balance = ? WHERE customer_first_name = ? AND customer_last_name = ?;");
					ps1.setFloat(1, this.balance);
					ps1.setString(2, this.first_name);
					ps1.setString(3, this.last_name);
					ps1.executeUpdate();
					
					System.out.println(first_name + " " + last_name + " new balance is $" + balance);
					break;
				}
			}
		}
		
			catch (ClassNotFoundException | SQLException ex) 
		{
				System.out.println(ex);
		}
		
	}
	
	//Allows an account to deposit
	public void accountDeposit(String deposit) {
		float depositAmount = Float.parseFloat(deposit);
		
		try 
		{
			//SQLDB Connection
			Class.forName(jdbcDriver);
			Connection con = DriverManager.getConnection(serverName, userName, password);
			
			//Statement to run for SQL 
			ps = con.prepareStatement("SELECT * FROM customer WHERE customer_username = ? AND customer_password = ?;");
			ps.setString(1, this.customer_username);
			ps.setString(2, this.customer_password);
			
			//Reads Results and updates balance for deposit.
			rs = ps.executeQuery();
			while(rs.next()) {
				this.balance = rs.getFloat(4) + depositAmount;
				
				
				//Updating balance to SQL server
				PreparedStatement ps1 = con.prepareStatement("UPDATE customer SET customer_balance = ? WHERE customer_first_name = ? AND customer_last_name = ?;");
				ps1.setFloat(1, this.balance);
				ps1.setString(2, this.first_name);
				ps1.setString(3, this.last_name);
				ps1.executeUpdate();
					
				System.out.println(first_name + " " + last_name + " new balance is $" + balance);
				}
			}
		
			catch (ClassNotFoundException | SQLException ex) 
		{
				System.out.println(ex);
		}
		
	}
	
	public String getFirstName() {
		return this.first_name;
	}
	
	public String getLastName() {
		return this.last_name;
	}
	
	public float getBalance1() {
		return this.balance;
	}
}
