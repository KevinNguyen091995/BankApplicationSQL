package BankAppSQL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class BankAccountLogin extends JFrame implements ActionListener{
	
	private BankAccountsSQL SQL = new BankAccountsSQL();
	private JPanel contentPane;
	private JPanel contentPane1;
	
	private JTextField username;
	private JTextField password;
	private JTextField balanceField;
	
	private JLabel passwordLabel;
	private JLabel balanceLabel;
	
	private JButton LoginButton;
	private JButton WithdrawButton;
	private JButton DepositButton;
	private JButton SignOutButton;
	
	public BankAccountLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		username = new JTextField();
		username.setBounds(119, 29, 202, 20);
		contentPane.add(username);
		username.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(10, 14, 99, 51);
		contentPane.add(lblNewLabel);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 76, 99, 79);
		contentPane.add(passwordLabel);
		
		password = new JTextField();
		password.setColumns(10);
		password.setBounds(119, 105, 202, 20);
		contentPane.add(password);
		
		JButton LoginButton = new JButton("Login");
		LoginButton.setBounds(157, 194, 130, 56);
		contentPane.add(LoginButton);
		this.LoginButton = LoginButton;
		LoginButton.addActionListener(this);
		setVisible(true);
	}
	
	public void BankAccountMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane1 = new JPanel();
		contentPane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane1);
		contentPane1.setLayout(null);
		
		JButton WithdrawButton = new JButton("Withdraw");
		WithdrawButton.addActionListener(this);
		WithdrawButton.setBounds(10, 202, 120, 48);
		contentPane1.add(WithdrawButton);
		this.WithdrawButton = WithdrawButton;
		
		JButton DepositButton = new JButton("Deposit");
		DepositButton.addActionListener(this);
		DepositButton.setBounds(159, 202, 120, 48);
		contentPane1.add(DepositButton);
		this.DepositButton = DepositButton;
		
		JButton SignOutButton = new JButton("Sign Out");
		SignOutButton.addActionListener(this);
		SignOutButton.setBounds(335, 227, 89, 23);
		contentPane1.add(SignOutButton);
		this.SignOutButton = SignOutButton;
		
		JLabel lblNewLabel = new JLabel("Welcome back, " + SQL.getFirstName() + " " + SQL.getLastName() + "!");
		lblNewLabel.setBounds(10, 11, 269, 23);
		contentPane1.add(lblNewLabel);
		
		JLabel balanceLabel = new JLabel("BALANCE: $" + SQL.getBalance1());
		balanceLabel.setBounds(10, 71, 269, 39);
		contentPane1.add(balanceLabel);
		this.balanceLabel = balanceLabel;
		
		balanceField = new JTextField();
		balanceField.setBounds(10, 140, 269, 29);
		contentPane1.add(balanceField);
		balanceField.setColumns(10);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent LoginMenu) {
	if(LoginMenu.getSource() == LoginButton) {
		if(SQL.SQLUserCheck(username.getText(), password.getText())) {
			setVisible(false);
			BankAccountMenu();
		}
	}
	if(LoginMenu.getSource() == DepositButton) {
		SQL.accountDeposit(balanceField.getText());
		balanceLabel.setText(("BALANCE: $" + SQL.getBalance1()));
		}
	
	if(LoginMenu.getSource() == WithdrawButton) {
		SQL.accountWithdrawal(balanceField.getText());
		balanceLabel.setText(("BALANCE: $" + SQL.getBalance1()));
		}
	if(LoginMenu.getSource() == SignOutButton) {
		System.out.println("Thank you for using KBank!");
		new BankAccountLogin();
		}
	}
}
