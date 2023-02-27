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
import javax.swing.JButton;
import javax.swing.JTextArea;

public class DisplayModifyMerchandise implements ActionListener{
	private static JFrame superFrame;
	private static JFrame frame;
	private static JTextField textFieldName;
	private static JTextField textFieldType;
	private static JTextField textFieldForm;
	private static JTextField textFieldPrice;
	private static JTextField textFieldMercID;
	
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
		
		JTextArea textAreaDescription = new JTextArea();
		textAreaDescription.setBounds(0, 135, 429, 193);
		panel.add(textAreaDescription);
		
		
	}
	
	public static void createLabels(JPanel panel) {
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("굴림", Font.BOLD, 18));
		lblName.setBounds(0, 45, 145, 35);
		panel.add(lblName);
		
		/*JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("굴림", Font.BOLD, 18));
		lblType.setBounds(0, 161, 145, 35);
		panel.add(lblType);
		
		JLabel lblForm = new JLabel("Form");
		lblForm.setFont(new Font("굴림", Font.BOLD, 18));
		lblForm.setBounds(325, 116, 145, 35);
		panel.add(lblForm);*/
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("굴림", Font.BOLD, 18));
		lblPrice.setBounds(354, 90, 145, 35);
		panel.add(lblPrice);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("굴림", Font.BOLD, 18));
		lblDescription.setBounds(0, 90, 125, 35);
		panel.add(lblDescription);
		
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
	}
	
	public static void createButtons(JPanel panel) {
		JButton btnOk = new JButton("Ok");
		btnOk.setFont(new Font("굴림", Font.BOLD, 20));
		btnOk.setBounds(460, 248, 190, 35);
		btnOk.addActionListener(new DisplayModifyMerchandise());
		panel.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("굴림", Font.BOLD, 20));
		btnCancel.setBounds(460, 293, 190, 35);
		btnCancel.addActionListener(new DisplayModifyMerchandise());
		panel.add(btnCancel);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Cancel")) {
			frame.dispose();
			superFrame.setEnabled(true);
			superFrame.toFront();
		}
		else {

			try {
				//invoke method(s) for modifying Merchandise here
			}
			catch(Exception ex) {
				JOptionPane.showMessageDialog(frame,"Name, type, form, and price are required", "Invalid input", JOptionPane.WARNING_MESSAGE);
			}
		}
	}	
	
	//public static void main(String[] args) {
		// TODO Auto-generated method stub
		//DisplayModifyMerchandise.displayModifyMerchandise(new JFrame());

	//}


}
