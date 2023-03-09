package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.*;
import middleLayer.*;
import middleLayer.Users.AuthenticateUser;

import javax.swing.SwingConstants;

public class DisplayLogin {
	private static JTextField userNameField;
	private static JPasswordField passwordField;
	static AuthenticateUser _authUser;

    private static int username;
	private static int password;


	public void displayLogin(JFrame superFrame) {
		superFrame.setEnabled(false);
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frmLogin = new JFrame("York and Co. Pharmacy Management System");
        frmLogin.setTitle("Login");
        DisplayLogin background = new DisplayLogin();
        frmLogin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frmLogin.setContentPane(background.createContentPanel(frmLogin, superFrame));
        frmLogin.setSize(600, 400);
        frmLogin.setVisible(true);
	}
	
	private JPanel createContentPanel(JFrame frame, JFrame superFrame) {
		JPanel totalGUI = new JPanel();
		totalGUI.setLayout(null);

		JLabel lblUserAccount = new JLabel("Username");
		lblUserAccount.setFont(new Font("굴림", Font.BOLD, 20));
		lblUserAccount.setBounds(100, 100, 125, 35);
		totalGUI.add(lblUserAccount);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("굴림", Font.BOLD, 20));
		lblPassword.setBounds(100, 150, 125, 35);
		totalGUI.add(lblPassword);
		
		// textField = new JTextField();
		// textField.setBounds(225, 100, 225, 35);
		// totalGUI.add(textField);
		// textField.setColumns(10);
		userNameField = new JTextField();
		userNameField.setBounds(225, 100, 225, 35);
		totalGUI.add(userNameField);
		userNameField.setColumns(20);
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(225, 150, 225, 35);
		totalGUI.add(passwordField);
		

		passwordField.setColumns(20);
		

		
		JLabel lblTitle = new JLabel("York and Co. Pharmacy Management System");
		lblTitle.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
		lblTitle.setBounds(75, 30, 450, 50);
		totalGUI.add(lblTitle);
		
		
		loginButton(totalGUI, frame, superFrame);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("굴림", Font.BOLD, 25));
		btnCancel.setBounds(300, 243, 150, 50);
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				superFrame.setEnabled(true);
				superFrame.toFront();
			}
			
		});
		totalGUI.add(btnCancel);
		
		
		totalGUI.setVisible(true);
		return totalGUI;
	}
	
	private static void loginButton(JPanel totalGUI, JFrame frame, JFrame superFrame) {
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("굴림", Font.BOLD, 25));
		btnLogin.setBounds(100, 243, 150, 50);
		totalGUI.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(validateInput(totalGUI, userNameField, passwordField)) {
									

					USER userType = AuthenticateUser.getInstance().checkUserValid(username, password); //returns null when validation fails
					if (userType != null) {
						superFrame.dispose();	
						DisplayInitialScreen.displayInitialScreen(userType);
		        		frame.dispose();
						//superFrame.dispose();
						//DisplayInitialScreen screen = new DisplayInitialScreen();
						//DisplayInitialScreen.displayInitialScreen(userType);
						//DisplayInitialScreen.setUserType(userType);
						
						
						//DisplayInitialScreen screen = new DisplayInitialScreen();
						//screen.displayInitialScreen(userType);
						
					}
					else {
						
						DisplayLogin.authenticationFailed(totalGUI);
					}
				}
			}
		});

	}
	
	private static boolean validateInput(JPanel totalGUI, JTextField inputUsername, JPasswordField inputPassword) {
		try {
			username = Integer.parseInt(inputUsername.getText());
			password = Integer.parseInt(new String(inputPassword.getPassword()));
			return true;
		}
		catch(Exception e) {
			DisplayLogin.authenticationFailed(totalGUI);
			return false;
		}
	}
	
	private static void authenticationFailed(JPanel totalGUI) {
		JPanel panelInvalidInput = new JPanel();
		panelInvalidInput.setBounds(100, 195, 350, 35);
		totalGUI.add(panelInvalidInput);
		panelInvalidInput.setLayout(null);
	
		JLabel lblInvalidInput = new JLabel("username or password is invalid");
		lblInvalidInput.setBounds(0, 0, 350, 35);
		panelInvalidInput.add(lblInvalidInput);
	
		lblInvalidInput.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvalidInput.setForeground(new Color(255, 0, 0));
		lblInvalidInput.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
		totalGUI.add(panelInvalidInput);
	}
	
	
	
	public int getUsername() {
		return username;
	}
	
	public int getPassword() {
		return password;
	}
	//public static void main(String[] args) {
	//	DisplayLogin screen = new DisplayLogin();
	//	screen.displayLogin(new JFrame());
	//}
}

