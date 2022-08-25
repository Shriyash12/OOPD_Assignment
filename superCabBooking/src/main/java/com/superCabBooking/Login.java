package com.superCabBooking;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {
	JLabel heading = new JLabel("Login"); // Heading for the window
	JFrame loginPage = new JFrame("Login Form"); // JFrame means a window
	JLabel userIDText = new JLabel("Enter UserID:"); // JLabel is a view-only text
	JTextField userID = new JTextField(); // JTextField is an input field
	JLabel passwordText = new JLabel("Enter Password:");
	JPasswordField password = new JPasswordField(); // JPasswordField is a password input field
	JLabel newUserText = new JLabel("New User ?");
	JButton logIn = new JButton("Log In");
	JButton signUpButton = new JButton("Sign Up");

	Login() {

		// Set Positions of Components inside Jframe
		heading.setForeground(Color.blue);
		heading.setFont(new Font("Serif", Font.BOLD, 25));
		heading.setBounds(185, 30, 400, 30);
		userIDText.setBounds(80, 70, 200, 30);
		userID.setBounds(250, 70, 200, 30);
		passwordText.setBounds(80, 110, 200, 30);
		password.setBounds(250, 110, 200, 30);
		logIn.setBounds(250, 150, 200, 30);
		newUserText.setBounds(80, 190, 200, 30);
		signUpButton.setBounds(250, 190, 200, 30);

		// Add components to the JFrame
		loginPage.add(heading);
		loginPage.add(logIn);
		loginPage.add(userIDText);
		loginPage.add(userID);
		loginPage.add(passwordText);
		loginPage.add(password);
		loginPage.add(newUserText);
		loginPage.add(signUpButton);

		loginPage.setLayout(null); // Remove the layout which is present by default
		loginPage.setExtendedState(JFrame.MAXIMIZED_BOTH); // Make JFrame full screen
		loginPage.setLocationRelativeTo(null); // Move JFrame to centre
		loginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Make program exit when window is Closed(X pressed)
		loginPage.getRootPane().setDefaultButton(logIn);// Press button when return key is pressed

		logIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root"); //Connectivity String
					System.out.println("Connnection establised");
					Statement stmt = conn.createStatement();

					stmt.execute("USE CABBOOKING");

					ResultSet rs = stmt
							.executeQuery("SELECT PASSWORD FROM USERS WHERE USERID=\"" + userID.getText() + "\"");
					boolean loginSuccess = false; // Validation of User
					while (rs.next()) {
						if (new String(password.getPassword()).equals(rs.getString("PASSWORD"))) {
							Booking myBooking = new Booking(userID.getText());
							loginPage.setVisible(false);
							myBooking.bookingPage.setVisible(true);
							loginSuccess = true;
						}
					}
					if (!loginSuccess) {
						ToastMessage toastMessage = new ToastMessage("Incorrect UserID or Password", 3000);
						toastMessage.setVisible(true);
					}
					conn.close();
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});

		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Signup mySignUp = new Signup();
				loginPage.setVisible(false);
				mySignUp.register.setVisible(true);
			}
		});
	}

	public static void main(String[] args) {
		Login MyLoginPage = new Login();
		MyLoginPage.loginPage.setVisible(true);
	}
}
