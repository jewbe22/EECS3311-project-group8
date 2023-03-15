package presentation;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import databaseDAO.superDAO;
import middleLayer.Users.*;


import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Cursor;
import java.awt.Component;


public class DisplayPatientManage {
	private static JFrame frame;
	private static JFrame superFrame;
	private static JTextField textFieldSearchKeyword;
	private static JTextField textFieldFName;
	private static JTextField textFieldLName;
	private static JTextField textFieldHCNumber;
	private static JTextField textFieldPhoneNumber;
	private static JTextField textFieldAddress;
	private static JTextField textFieldDOB;
	private static JComboBox<String> comboBox;
	//private static JTextField textFieldAccount;
	//private static JTextField textFieldPassword;\
	private static JLabel lblNotice;
	//private static JTextArea textAreaOutput;
	private static ListOfPatients listOfPatientsInstance;
	private static final String[] columns= {				
			String.format("%-20s ", "Name"),
			String.format("%-20s ", "Address"),
			String.format("%-10s ", "Phone Number"),
			String.format("%-10s ", "Healthcard"),
			String.format("%-10s\n", "DoB")};
	private static String[][] patients = {{"", "","","",""}};
	private static JTable table;
	private static JScrollPane scrollPane;


	
	public static void displayPatientManage(JFrame previous) {
		listOfPatientsInstance = ListOfPatients.getInstance();
		superFrame = previous;
		superFrame.setEnabled(false);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Patient management");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(1200, 700);
		frame.getContentPane().setLayout(null);
		patientsDisplay();
		inputFields();
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("굴림", Font.BOLD, 18));
		btnClose.setBounds(1004, 545, 170, 35);
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				superFrame.toFront();
				superFrame.setEnabled(true);
			}
			
		});
		frame.getContentPane().add(btnClose);
		frame.setVisible(true);
		
		
		
	}
	
	private static void patientsDisplay() {
		
		JPanel panelPatientList = new JPanel();
		panelPatientList.setBounds(12, 10, 780, 525);
		frame.getContentPane().add(panelPatientList);
		panelPatientList.setLayout(null);
		
		textFieldSearchKeyword = new JTextField();
		textFieldSearchKeyword.setBounds(0, 0, 396, 35);
		textFieldSearchKeyword.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelPatientList.add(textFieldSearchKeyword);
		textFieldSearchKeyword.setColumns(10);
		
		

		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(508, 0, 130, 35);
		btnSearch.setFont(new Font("굴림", Font.BOLD, 18));
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//call method for patient search
				try {	//Exception is thrown when insufficient number of arguments is passed to Patient constructor. if all argument is fed, 
					//addPatient is bound to success
					lblNotice.setText("");
					String _textFieldSearchKeyword = textFieldSearchKeyword.getText().toUpperCase();
					if (_textFieldSearchKeyword.isEmpty()) {
						throw new Exception(); // ensures something is entered 
					}
					
					Owner o1 = new Owner(1,1);
					
					ArrayList<Patient> searchResult;
					
					//Daniel can you please add the drop down and use the drop down to send as parameter instead of the hardcoded string
					searchResult = o1.searchPatientByName(_textFieldSearchKeyword, (String)comboBox.getSelectedItem());
					displayList(table, searchResult);

					
				}
				catch(SQLException exception) { //catch any exceptions and show popup error
					//DisplayErrorPopup.displayErrorPopup("First name, Last name, Address, Phone Number, HealthCardNumber and Date of Birth are required", frame);
					JOptionPane.showMessageDialog(frame,"Duplicated Health Card Number not allowed. A patient with this health card was already found in the system.", "SQL Error", JOptionPane.WARNING_MESSAGE);
				}
				catch(Exception exception) { //catch any exceptions and show popup error
					//DisplayErrorPopup.displayErrorPopup("First name, Last name, Address, Phone Number, HealthCardNumber and Date of Birth are required", frame);
					JOptionPane.showMessageDialog(frame,"input field is empty", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				
			}
			
		});
		panelPatientList.add(btnSearch);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(397, 0, 110, 35);
		comboBox.setFont(new Font("굴림", Font.BOLD, 12));
		comboBox.setBorder(new LineBorder(new Color(0, 0, 0)));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"FirstName", "LastName", "FullName"}));
		panelPatientList.add(comboBox);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(650, 0, 130, 35);
		btnRefresh.setFont(new Font("굴림", Font.BOLD, 18));
		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				displayList(table, listOfPatientsInstance.getAllPatientsList());	//by invoking this method, the list is refreshed.
			//	lblNotice.setText("Patient is added successfully");
			}
			
		});
		panelPatientList.add(btnRefresh);
		
		lblNotice = new JLabel("");
		lblNotice.setBounds(0, 45, 507, 45);
		panelPatientList.add(lblNotice);
		lblNotice.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNotice.setHorizontalAlignment(SwingConstants.LEFT);
		lblNotice.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));
		lblNotice.setForeground(new Color(255, 0, 0));
		

		patientListField(panelPatientList);
		displayList(table, listOfPatientsInstance.getAllPatientsList());
		
	}
	
	private static void inputFields() {
		JPanel panelInputFields = new JPanel();
		panelInputFields.setBounds(804, 10, 370, 525);
		frame.getContentPane().add(panelInputFields);
		panelInputFields.setLayout(null);
		
		JLabel lblFName = new JLabel("First name");
		lblFName.setForeground(new Color(0, 0, 0));
		lblFName.setFont(new Font("굴림", Font.BOLD, 15));
		lblFName.setBounds(0, 295, 110, 35);
		panelInputFields.add(lblFName);
		
		textFieldFName = new JTextField();
		textFieldFName.setBounds(110, 295, 260, 35);
		panelInputFields.add(textFieldFName);
		textFieldFName.setColumns(10);
		
		JLabel lblLName = new JLabel("Last name");
		lblLName.setForeground(new Color(0, 0, 0));
		lblLName.setFont(new Font("굴림", Font.BOLD, 15));
		lblLName.setBounds(0, 343, 110, 35);
		panelInputFields.add(lblLName);
		
		textFieldLName = new JTextField();
		textFieldLName.setBounds(110, 343, 260, 35);
		panelInputFields.add(textFieldLName);
		textFieldLName.setColumns(10);
		
		JLabel lblHCNumber = new JLabel("Healthcard#");
		lblHCNumber.setFont(new Font("굴림", Font.BOLD, 15));
		lblHCNumber.setForeground(new Color(255, 0, 0));
		lblHCNumber.setBounds(0, 91, 110, 35);
		panelInputFields.add(lblHCNumber);
		
		textFieldHCNumber = new JTextField();
		textFieldHCNumber.setBounds(110, 91, 260, 35);
		panelInputFields.add(textFieldHCNumber);
		textFieldHCNumber.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone#");
		lblPhoneNumber.setFont(new Font("굴림", Font.BOLD, 15));
		lblPhoneNumber.setBounds(0, 390, 110, 35);
		panelInputFields.add(lblPhoneNumber);
		
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setBounds(110, 390, 260, 35);
		panelInputFields.add(textFieldPhoneNumber);
		textFieldPhoneNumber.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("굴림", Font.BOLD, 15));
		lblAddress.setBounds(0, 435, 110, 35);
		panelInputFields.add(lblAddress);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setBounds(110, 435, 260, 35);
		panelInputFields.add(textFieldAddress);
		textFieldAddress.setColumns(10);
		
		JLabel lblDOB = new JLabel("Date of Birth");
		lblDOB.setForeground(new Color(0, 0, 0));
		lblDOB.setFont(new Font("굴림", Font.BOLD, 15));
		lblDOB.setBounds(0, 136, 110, 35);
		panelInputFields.add(lblDOB);
		
		textFieldDOB = new JTextField();
		textFieldDOB.setBounds(110, 136, 260, 35);
		panelInputFields.add(textFieldDOB);
		textFieldDOB.setColumns(10);
		
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("굴림", Font.BOLD, 17));
		btnAdd.setBounds(200, 480, 170, 35);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//call method for add patient
				try {	//Exception is thrown when insufficient number of arguments is passed to Patient constructor. if all argument is fed, 
					//addPatient is bound to success

					String _textFieldFName = textFieldFName.getText().toUpperCase();
					String _textFieldLName = textFieldLName.getText().toUpperCase();
					String _textFieldAddress = textFieldAddress.getText().toUpperCase();
					
					if (_textFieldFName.isEmpty() || _textFieldLName.isEmpty() || _textFieldAddress.isEmpty()) {
						throw new Exception(); // ensures a first name, last name and address have been entered
					}
					
					int _textFieldPhoneNumber = Integer.parseInt(textFieldPhoneNumber.getText());
					int _textFieldHCNumber = Integer.parseInt(textFieldHCNumber.getText());
					int _textFieldDOB = Integer.parseInt(textFieldDOB.getText());
					
					Pharmacist p1 = new Pharmacist(1,1);
					p1.addPatient(_textFieldFName, _textFieldLName, _textFieldAddress, _textFieldPhoneNumber, _textFieldHCNumber, _textFieldDOB);
					displayList(table, listOfPatientsInstance.getAllPatientsList());	//by invoking this method, the list is refreshed.
					lblNotice.setText("Patient is added successfully");
//					should we make addPatient() return a boolean to see if it was successful or not? what is the case it is not successful in?
					
				}
				catch(SQLException exception) { //catch any exceptions and show popup error
					//DisplayErrorPopup.displayErrorPopup("First name, Last name, Address, Phone Number, HealthCardNumber and Date of Birth are required", frame);
					JOptionPane.showMessageDialog(frame,"Duplicated Health Card Number not allowed. A patient with this health card was already found in the system.", "SQL Error", JOptionPane.WARNING_MESSAGE);
				}
				catch(Exception exception) { //catch any exceptions and show popup error
					//DisplayErrorPopup.displayErrorPopup("First name, Last name, Address, Phone Number, HealthCardNumber and Date of Birth are required", frame);
					JOptionPane.showMessageDialog(frame,"First name, Last name, Address, Phone Number, HealthCardNumber and Date of Birth are required", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				

			}
			
		});
		panelInputFields.add(btnAdd);
		
		JButton btnModify = new JButton("Modify");
		btnModify.setFont(new Font("굴림", Font.BOLD, 18));
		btnModify.setBounds(0, 480, 170, 35);
		btnModify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//invoke method for modify
				try {
					int _textFieldPatientID = Integer.parseInt(textFieldHCNumber.getText()); // throws exception if HCNum textbox left empty
					Boolean result;
					result = listOfPatientsInstance.modifyPatientDetails(_textFieldPatientID, textFieldFName, textFieldLName, textFieldPhoneNumber, textFieldAddress);
					
					if (result == false) {
						// popup that says patient doesn't exist
						//System.out.println("patient doesn't exist. try again");
						JOptionPane.showMessageDialog(frame,"The Patient does not exist", "Invalid input", JOptionPane.WARNING_MESSAGE);
					}
					
					displayList(table, listOfPatientsInstance.getAllPatientsList() );
					lblNotice.setText("Patient is modified successfully");
					
				}
				catch (Exception e1) {
					// add popup saying need at least 2 things: HCNum and one of fName, lName, PhoneNum, Add
					//System.out.println("not enough param");
					JOptionPane.showMessageDialog(frame,"Healthcard# and one of fName, lName, PhoneNum are required", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
				
			}
			
		});
		panelInputFields.add(btnModify);
		
		JLabel lblNewLabel = new JLabel("The fields below are modifiable");
		lblNewLabel.setForeground(new Color(128, 128, 128));
		lblNewLabel.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 18));
		lblNewLabel.setBounds(0, 250, 370, 35);
		panelInputFields.add(lblNewLabel);
		

	}
	private static void patientListField(JPanel panel) {
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 100, 780, 425);
		panel.add(scrollPane);
		table = new JTable();
		
		/*table = new JTable(patients, columns){

			private static final long serialVersionUID = 1L;

			@Override
		      public Component prepareRenderer(TableCellRenderer renderer, int row,
		          int col) {
		        Component comp = super.prepareRenderer(renderer, row, col);
		        ((JLabel) comp).setHorizontalAlignment(JLabel.LEFT);
		        return comp;
		      }
		    };*/
		table.setFont(new Font("Monospaced", Font.PLAIN, 14));

		//scrollPane.setViewportView(table);
		
	}
	
	private static void displayList(JTable output, ArrayList<Patient> pList){	//if this method is invoked at the end of every  button click operation,													

		output.removeAll();
		
		patients = new String[pList.size()][columns.length]; 
		for(int i = 0; i < pList.size(); i++) {
			
			patients[i][0] = String.format("%-20s", pList.get(i).getFirstName() + " " + pList.get(i).getLastName());
			patients[i][1] = String.format("%-20s", pList.get(i).getAddress());
			patients[i][2] = String.format("%-10s", pList.get(i).getPhoneNum());
			patients[i][3] = String.format("%-10s", pList.get(i).getHealthCardNum());
			patients[i][4] = String.format("%-10s", pList.get(i).getDateOfBirth());

		}
		//System.out.println("patients: " + patients[0][0] + " " + patients[0][1] + " " + patients[0][2]);
		//table = new JTable(patients, columns);
		table = new JTable(patients, columns);
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		
	}
	
	
	public static void main(String[] args) {	//for test purpose
		try {
			superDAO.setPassword("Motp1104#");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DisplayPatientManage.displayPatientManage(new JFrame());
	}
}
