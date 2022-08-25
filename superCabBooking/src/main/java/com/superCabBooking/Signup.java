package com.superCabBooking;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Signup {
	JFrame register = new JFrame("Sign Up Form");
	JLabel heading = new JLabel("Sign Up"); // Heading for the window
	JLabel userNameText = new JLabel("Full Name: ");
	JLabel passwordText = new JLabel("Password: ");
	JLabel emailIDText = new JLabel("Email ID: ");
	JLabel phoneNumText = new JLabel("Phone No: ");

	JTextField userName = new JTextField();
	JPasswordField password = new JPasswordField();
	JTextField emailID = new JTextField();
	JTextField phoneNum = new JTextField();

	JButton goToLoginPage = new JButton("Login Page");
	JButton signUpButton = new JButton("Submit");

	Signup() {
		heading.setForeground(Color.blue);
		heading.setFont(new Font("Serif", Font.BOLD, 25));
		heading.setBounds(185, 30, 400, 30);
		userNameText.setBounds(80, 70, 200, 30);
		userName.setBounds(250, 70, 200, 30);
		passwordText.setBounds(80, 110, 200, 30);
		password.setBounds(250, 110, 200, 30);
		emailIDText.setBounds(80, 150, 200, 30);
		emailID.setBounds(250, 150, 200, 30);
		phoneNumText.setBounds(80, 190, 200, 30);
		phoneNum.setBounds(250, 190, 200, 30);
		goToLoginPage.setBounds(80, 270, 200, 30);
		signUpButton.setBounds(300, 270, 200, 30);
		register.add(heading);
		register.add(userNameText);
		register.add(passwordText);
		register.add(emailIDText);
		register.add(phoneNumText);
		register.add(userName);
		register.add(password);
		register.add(emailID);
		register.add(phoneNum);
		register.add(signUpButton);
		register.add(goToLoginPage);

		register.setExtendedState(JFrame.MAXIMIZED_BOTH);
		register.setLayout(null);
		// register.setVisible(true);
		register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		register.getRootPane().setDefaultButton(signUpButton);
		goToLoginPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login loginpage = new Login();
				register.setVisible(false);
				loginpage.loginPage.setVisible(true);
			}
		});

		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");
					Statement stmt = conn.createStatement();
					stmt.execute("USE CABBOOKING");
					stmt.execute("INSERT INTO USERS(USERNAME,PASSWORD,PHONENUM,EMAILID) VALUES(\"" + userName.getText()
							+ "\",\"" + new String(password.getPassword()) + "\"," + phoneNum.getText() + ",\""
							+ emailID.getText() + "\");");
					ResultSet userIDSet = stmt.executeQuery("SELECT LAST_INSERT_ID()"); // Insert Query to Push records
																						// into DB
					String userID = "";
					if (userIDSet.next()) {
						userID = userIDSet.getString("LAST_INSERT_ID()");
					}
					conn.close();
					Login loginpage = new Login();
					register.setVisible(false);
					loginpage.loginPage.setVisible(true);
					ToastMessage toastMessage = new ToastMessage("Registered Sucessfully!!,Your userID is " + userID,
							3000);
					toastMessage.setVisible(true);
				} catch (Exception exception) {
					System.out.println(exception);
					ToastMessage toastMessage = new ToastMessage("DB Connection Error", 3000);
					toastMessage.setVisible(true);
				}
			}
		});
	}
}
