# BankApplicationSQL

Project that queries a Relationship Database Management System (MYSQL server) similar to a bank.

The initial menu using Java Swing requires a login from the RDM Server which includes a username and password. 

It allows a customer to withdraw/deposit/signout from their account, and updates the SQL table as required.


**Can now query from a online DB hosted by RemoteMySQL.com instead of a local server on MYSQL. Code has been adjusted to make the connection.**



##SQL DATA TO CREATE TABLE FOR TEST PURPOSES IF ONLINE DB IS NOT WORKING:

**CREATE TABLE customer(
customer_id int auto_increment,
customer_first_name varchar(25) NOT NULL,
customer_last_name varchar(25) NOT NULL,
customer_balance decimal(20,1) NOT NULL,
customer_username varchar(20) NOT NULL,
customer_password varchar(20) NOT NULL,
PRIMARY KEY (customer_id)
);**

**INSERT INTO customer (customer_first_name, customer_last_name, customer_balance, customer_username, customer_password) VALUES ("Kevin", "Nguyen", 50000, "KevinN11", "Kevin123");

INSERT INTO customer (customer_first_name, customer_last_name, customer_balance, customer_username, customer_password) VALUES ("John", "Ham", 12320, "Admin111", "Admin00");**

