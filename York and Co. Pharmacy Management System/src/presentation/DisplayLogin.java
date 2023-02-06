package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;

public class DisplayLogin {
	private JTextField userNameField;
	private JPasswordField passwordField;
	
	private static int username;
	private static int password;

	public void displayLogin() {
		JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frmLogin = new JFrame("York and Co. Pharmacy Management System");
        frmLogin.setTitle("Login");
        DisplayLogin background = new DisplayLogin();
        frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmLogin.setContentPane(background.createContentPanel(frmLogin));
        frmLogin.setSize(600, 400);
        frmLogin.setVisible(true);
	}
	
	public JPanel createContentPanel(JFrame frame) {
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
		
		userNameField = new JTextField();
		userNameField.setBounds(225, 100, 225, 35);
		totalGUI.add(userNameField);
		userNameField.setColumns(20);
		userNameField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(validateInput(totalGUI, userNameField, passwordField)) {
						frame.dispose();
					}
				}
			}
		});
		
		passwordField = new JPasswordField();
		passwordField.setBounds(225, 150, 225, 35);
		totalGUI.add(passwordField);
		passwordField.setColumns(20);
		passwordField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(validateInput(totalGUI, userNameField, passwordField)) {
						frame.dispose();
					}
				}
			}
		});
		

		
		JLabel lblTitle = new JLabel("York and Co. Pharmacy Management System");
		lblTitle.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 20));
		lblTitle.setBounds(75, 30, 450, 50);
		totalGUI.add(lblTitle);
		
		this.loginButton(totalGUI, frame);
		
		
		totalGUI.setVisible(true);
		return totalGUI;
	}
	
	private void loginButton(JPanel totalGUI, JFrame frame) {
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("굴림", Font.BOLD, 25));
		btnLogin.setBounds(225, 244, 150, 50);
		totalGUI.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(validateInput(totalGUI, userNameField, passwordField)) {
					frame.dispose();
				}
			}
		});

	}
	
	private boolean validateInput(JPanel totalGUI, JTextField inputUsername, JPasswordField inputPassword) {
		try {
			username = Integer.getInteger(inputUsername.getText(), 0);
			password = Integer.getInteger(new String(inputPassword.getPassword()));
			return true;
		}
		catch(Exception e) {
		
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
			return false;
		}
	}
	
	
	
	public int getUsername() {
		return username;
	}
	
	public int getPassword() {
		return password;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DisplayLogin login = new DisplayLogin();
		login.displayLogin();
	}
}
