package presentation;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import middleLayer.Inventory;
import middleLayer.Pharmacist;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class DisplayModifyMerchandise implements ActionListener{
	private static JFrame superFrame;
	private static JFrame frame;
	private static JTextField textFieldName;
	private static JTextField textFieldType;
	private static JTextField textFieldForm;
	private static JTextField textFieldPrice;
	private static JTextField textFieldMercID;
	private static JTextArea textAreaDescription;
	
	public static void displayModifyMerchandise(JFrame previous) {
		superFrame = previous;
		superFrame.setEnabled(false);
		frame = new JFrame();
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(new Dimension(700,400));
		DisplayModifyMerchandise.createContentsPanel(superFrame);
		
		frame.setVisible(true);
		
	}
	public static void createContentsPanel(JFrame superFrame) {
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 10, 662, 443);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		createLabels(panel);
		createInputFields(panel);
		createButtons(panel);
		

		

		
		
	}
	
	public static void createLabels(JPanel panel) {
		
		JLabel lblMercID = new JLabel("MerchandiseID");
		lblMercID.setFont(new Font("굴림", Font.BOLD, 18));
		lblMercID.setBounds(0, 0, 145, 35);
		panel.add(lblMercID);
	}
	
	public static void createInputFields(JPanel panel) {
		textFieldName = new JTextField();
		textFieldName.setBounds(145, 45, 505, 35);
		panel.add(textFieldName);
		textFieldName.setColumns(10);
		
		/*textFieldType = new JTextField();
		textFieldType.setBounds(145, 162, 168, 35);
		panel.add(textFieldType);
		textFieldType.setColumns(10);
		
		textFieldForm = new JTextField();
		textFieldForm.setBounds(470, 116, 180, 35);
		panel.add(textFieldForm);
		textFieldForm.setColumns(10);*/
		
		textFieldPrice = new JTextField();
		textFieldPrice.setBounds(500, 91, 150, 35);
		panel.add(textFieldPrice);
		textFieldPrice.setColumns(10);
		
		textFieldMercID = new JTextField();
		textFieldMercID.setBounds(145, 0, 150, 35);
		panel.add(textFieldMercID);
		textFieldMercID.setColumns(10);
		
		textAreaDescription = new JTextArea();
		textAreaDescription.setBounds(0, 135, 429, 193);
		panel.add(textAreaDescription);
	}
	
	public static void createButtons(JPanel panel) {
		/*JButton btnOk = new JButton("Ok");
		btnOk.setFont(new Font("굴림", Font.BOLD, 20));
		btnOk.setBounds(460, 248, 190, 35);
		btnOk.addActionListener(new DisplayModifyMerchandise());
		panel.add(btnOk);*/
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("굴림", Font.BOLD, 20));
		btnCancel.setBounds(460, 293, 190, 35);
		btnCancel.addActionListener(new DisplayModifyMerchandise());
		panel.add(btnCancel);
		
		JButton btnChangeName = new JButton("Change name");
		btnChangeName.setHorizontalTextPosition(SwingConstants.LEFT);
		btnChangeName.setFont(new Font("굴림", Font.BOLD, 16));
		btnChangeName.setBounds(0, 45, 140, 35);
		btnChangeName.addActionListener(new DisplayModifyMerchandise());
		panel.add(btnChangeName);
		
		JButton btnChangePrice = new JButton("Change Price");
		btnChangePrice.setFont(new Font("굴림", Font.BOLD, 16));
		btnChangePrice.setBounds(348, 90, 145, 35);
		btnChangePrice.addActionListener(new DisplayModifyMerchandise());
		panel.add(btnChangePrice);
		
		JButton btnChangeDescription = new JButton("Change Description");
		btnChangeDescription.setFont(new Font("굴림", Font.BOLD, 16));
		btnChangeDescription.setBounds(0, 90, 189, 35);
		btnChangeDescription.addActionListener(new DisplayModifyMerchandise());
		panel.add(btnChangeDescription);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub				
		String actionCommand = e.getActionCommand();
		
		String errorMessage = "";
		
		try {
			
			if(actionCommand.equals("Cancel")) {
				frame.dispose();
				superFrame.setEnabled(true);
				superFrame.toFront();
			}
			
			else {
				Inventory inv = Inventory.getInstance();
				
				errorMessage = "MediciationID is required";
				int _textFieldMercID = Integer.parseInt(textFieldMercID.getText());
				
				Boolean result = true;
				
			
				
				if (actionCommand.equals("Change name")) {
					String _textFieldName = textFieldName.getText().toUpperCase();
					
					if (_textFieldName.isEmpty()) { // ensures a medication name has been entered
						errorMessage = "MedicationID and/or Name are needed";
						throw new Exception(); 
					}
					try {
						result = inv.modifyMedicationName(_textFieldMercID, _textFieldName);
					}
					catch (Exception e1) {
						// popup
						System.out.println("Already exists");
					}
					
				}
				else if(actionCommand.equals("Change Price")) {
					errorMessage = "MedicationID and/or Price are needed"; // if exception is thrown because no price has been entered, this is the message printed
					int _textFieldPrice = Integer.parseInt(textFieldPrice.getText());
					//invoke method(s) for modifying Merchandise here
				/*if (textFieldName.is && textFieldPrice.isEmpty() && textFieldMercID.) {
					throw new Exception(); // ensures a first name, last name and address have been entered				
				}*/
					result = inv.modifyMedicationPrice(_textFieldMercID, _textFieldPrice); //just changing price for now, will do name+description once buttons present
					
				}
				else if(actionCommand.equals("Change Description")) {
					String _textAreaDescription = textAreaDescription.getText();
					
					if (_textAreaDescription.isEmpty()) { // ensures a description has been entered
						errorMessage = "MedicationID and/or Description are needed";
						throw new Exception(); 
					}
					result = inv.modifyMedicationDescription(_textFieldMercID, _textAreaDescription);
				}
				
				if (result == false) {
					// popup
					errorMessage = "MedicationID Does Not Exist in Inventory";
					System.out.println(errorMessage);
				}
			}
			
		}
		catch(Exception ex) {
			JOptionPane.showMessageDialog(frame,errorMessage, "Invalid input", JOptionPane.WARNING_MESSAGE);
		}

		
	}	
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		//DisplayModifyMerchandise.displayModifyMerchandise(new JFrame());

	}*/
}
