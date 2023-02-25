package presentation;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;

//import javax.swin//////JTextAreaea;

import java.awt.Color;
import java.awt.Component;


import javax.swing.ListCellRenderer;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;

import middleLayer.AuthenticateUser;
import middleLayer.Merchandise;
import middleLayer.Order;
import middleLayer.Owner;
import middleLayer.Patient;
import middleLayer.Inventory;
import middleLayer.MERCHANDISE_FORM;
import middleLayer.MERCHANDISE_TYPE;

import javax.swing.JList;
import javax.swing.JOptionPane;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;





public class DisplayInitialScreen {
	private static JTextField inputFieldName;
	private static JTextField inputFieldType;
	private static JTextField inputFieldForm;
	private static JTextField inputFieldQty;
	private static JTextField inputFieldPrice;
	private static JTextField inputKeyword;
	private static JFrame frame;
	private static JList<Merchandise> outputList;
	private static Inventory inv = Inventory.getInstance();
	private static JLabel lblOperationResult;
	
	private static String name;
	private static int username;
	private static String type;
	private static String form;
	private static int qty;
	private static double price;
	private static boolean isOTC;
	private static String searchKeyword;
	private static USER userType;
	
	private static String operationResult;
	

	
	public void displayInitialScreen(USER user) {
		userType = user;
		JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("York and Co. Pharmacy Management System");
        DisplayInitialScreen background = new DisplayInitialScreen();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(background.createContentPanel(userType));
        frame.setSize(1400, 800);
        frame.getContentPane().setLayout(null);

        frame.setVisible(true);
	}
	
	private JPanel createContentPanel(USER user) {
		String loginButton = "Login";
		JPanel totalGUI = new JPanel();
		totalGUI.setFont(new Font("굴림", Font.BOLD, 18));
		totalGUI.setLayout(null);
		
		if(user == USER.OWNER || user == USER.PHARMACIST) {	//to display contents allowed to OWNER/PHARMACIST only
			this.createPanelVisibleToAdmin(totalGUI);
		}
		else if(user == USER.PATIENT){
			this.createPanalVisibleToPatient(totalGUI);
		}
		this.createPanelVisibleToAdmin(totalGUI); //for test purpose
		this.createPanalVisibleToPatient(totalGUI);//for test purpose
        this.createExtraContents(totalGUI);
        this.createPanelVisibleToAll(totalGUI);
        if(user != USER.GUEST) {
        	loginButton = "Logout";
        }
        JButton btnNewButton = new JButton(loginButton);
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		DisplayLogin login = new DisplayLogin();
        		
        		login.displayLogin(frame);
        	}
        });
        btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton.setBounds(1100, 40, 125, 35);
        totalGUI.add(btnNewButton);
        		
		return totalGUI;
		
	}
	
	private void createPanelVisibleToAdmin(JPanel totalGUI) {
		JPanel panelVisibleToAdmin = new JPanel();
        panelVisibleToAdmin.setBounds(45, 536, 1200, 194);
        panelVisibleToAdmin.setLayout(null);  
        
        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("굴림", Font.BOLD, 18));
        lblName.setBounds(0, 0, 125, 35);
        panelVisibleToAdmin.add(lblName);
        
        inputFieldName = new JTextField();
        inputFieldName.setBounds(125, 0, 350, 35);
        panelVisibleToAdmin.add(inputFieldName);
        inputFieldName.setColumns(20);
        
        JLabel lblType = new JLabel("Type");
        lblType.setFont(new Font("굴림", Font.BOLD, 18));
        lblType.setBounds(0, 45, 125, 35);
        panelVisibleToAdmin.add(lblType);
        
        JLabel lblForm = new JLabel("Form");
        lblForm.setFont(new Font("굴림", Font.BOLD, 18));
        lblForm.setBounds(0, 90, 125, 35);
        panelVisibleToAdmin.add(lblForm);
        
        inputFieldType = new JTextField();
        inputFieldType.setBounds(125, 45, 350, 35);
        panelVisibleToAdmin.add(inputFieldType);
        inputFieldType.setColumns(30);
        
        inputFieldForm = new JTextField();
        inputFieldForm.setColumns(10);
        inputFieldForm.setBounds(125, 90, 350, 35);
        panelVisibleToAdmin.add(inputFieldForm);       
        
        JLabel lblQty = new JLabel("Qty");
        lblQty.setFont(new Font("굴림", Font.BOLD, 18));
        lblQty.setBounds(500, 0, 125, 35);
        panelVisibleToAdmin.add(lblQty);
        
        JLabel lblPrice = new JLabel("Price");
        lblPrice.setFont(new Font("굴림", Font.BOLD, 18));
        lblPrice.setBounds(500, 45, 125, 35);
        panelVisibleToAdmin.add(lblPrice);
        
        inputFieldQty = new JTextField();
        inputFieldQty.setFont(new Font("굴림", Font.BOLD, 18));
        inputFieldQty.setBounds(625, 0, 90, 35);
        panelVisibleToAdmin.add(inputFieldQty);
        inputFieldQty.setColumns(10);     
        
        inputFieldPrice = new JTextField();
        inputFieldPrice.setFont(new Font("굴림", Font.BOLD, 18));
        inputFieldPrice.setBounds(625, 45, 90, 35);
        panelVisibleToAdmin.add(inputFieldPrice);
        inputFieldPrice.setColumns(10);       
        
        JRadioButton rdbtnRx = new JRadioButton("Rx");
        rdbtnRx.setFont(new Font("굴림", Font.BOLD, 18));
        rdbtnRx.setSelected(true);
        rdbtnRx.setBounds(831, 7, 113, 23);
        panelVisibleToAdmin.add(rdbtnRx);
        
        JRadioButton rdbtnOTC = new JRadioButton("OTC");
        rdbtnOTC.setFont(new Font("굴림", Font.BOLD, 18));
        rdbtnOTC.setBounds(831, 39, 113, 23);
        panelVisibleToAdmin.add(rdbtnOTC);
        
        ButtonGroup group = new ButtonGroup();
        group.add(rdbtnOTC);
        group.add(rdbtnRx);             
        
        JButton btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("굴림", Font.BOLD, 20));
        btnAdd.setBounds(500, 135, 125, 35);
        panelVisibleToAdmin.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//Exception is thrown when insufficient number of arguments is passed to Merchandise constructor. if all argument is fed, 
						//addToInventory is bound to success
				
					String _inputFieldName = inputFieldName.getText().toUpperCase();
					if (_inputFieldName.isEmpty()) throw new Exception(); // ensures a medication name has been entered
					int _inputFieldQty = Integer.parseInt(inputFieldQty.getText());
					double _inputFieldPrice = Double.parseDouble(inputFieldPrice.getText());
					MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(inputFieldType.getText().toUpperCase());
					MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(inputFieldForm.getText().toUpperCase());
					Boolean _isOTC = false;
					if(rdbtnOTC.isSelected()) {
						_isOTC = true;
					}
				

					
					Boolean medicationAdded = false;
					
					Merchandise newMerchandise = new Merchandise(_inputFieldName, _inputFieldQty, _inputFieldPrice, _inputFieldType, _inputFieldForm, _isOTC);
					medicationAdded = inv.addToInventory(newMerchandise);
					
					//String temp = "";
					if (medicationAdded == true) {
						operationResult = "Add successful. See updated inventory";
					}
					else {
						operationResult = "Add unsuccessful. The medication (same name, type, form and OTC/Rx) already exists in the inventory. See current inventory";
					}

					displayMercList(outputList, inv.getMerchandise());
					lblOperationResult.setText(operationResult);
				}
				catch(Exception exception) { //catch any exceptions and show popup error
					//DisplayErrorPopup.displayErrorPopup("name, Qty, price, type, and form are required", frame);
					JOptionPane.showMessageDialog(frame,"name, Qty, price, type, and form are required", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
			
			}
		});
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("굴림", Font.BOLD, 20));
        btnDelete.setBounds(637, 135, 125, 35);
        panelVisibleToAdmin.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//catches exceptions thrown from delete() caused by not passing enough number of arguments
					String _inputFieldName = inputFieldName.getText().toUpperCase();
					if (_inputFieldName.isEmpty()) throw new Exception(); // ensures a medication name has been entered
					MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(inputFieldType.getText().toUpperCase());
					MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(inputFieldForm.getText().toUpperCase());
					Boolean _isOTC = false;
					if(rdbtnOTC.isSelected()) {
						_isOTC = true;
					}
				
					Boolean medicationRemoved = false;
				
					medicationRemoved = inv.delete(_inputFieldName, _inputFieldType, _inputFieldForm, _isOTC);
				
					
					if (medicationRemoved == false) {
						operationResult = "Remove unsuccessful. No such medication currently exists in the inventory. See current inventory";
					}
					else {
						operationResult = "Remove successful. See updated inventory";
					}

					displayMercList(outputList, inv.getMerchandise());
					lblOperationResult.setText(operationResult);
				}
				catch (Exception ex) {	//display error popup
					//DisplayErrorPopup.displayErrorPopup("name, type, and form are required", frame);
					JOptionPane.showMessageDialog(frame,"name, type, and form are required", "Invalid input", JOptionPane.WARNING_MESSAGE);
				}
			
			}
		});
        
        JButton btnIncrease = new JButton("Increase");
        btnIncrease.setFont(new Font("굴림", Font.BOLD, 20));
        btnIncrease.setBounds(774, 135, 125, 35);
        panelVisibleToAdmin.add(btnIncrease);
        btnIncrease.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//catches exception thrown by increaseQuantity() caused by passing insufficient number of arguments
					String _inputFieldName = inputFieldName.getText().toUpperCase();
					if (_inputFieldName.isEmpty()) throw new Exception(); // ensures a medication name has been entered
					int _inputFieldQty = Integer.parseInt(inputFieldQty.getText());
					MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(inputFieldType.getText().toUpperCase());
					MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(inputFieldForm.getText().toUpperCase());
					Boolean _isOTC = false;
					if(rdbtnOTC.isSelected()) {
						_isOTC = true;
					}
				
					Boolean medicationIncreased = false;
			
					medicationIncreased = inv.increaseQuantity(_inputFieldName, _inputFieldQty, _inputFieldType, _inputFieldForm, _isOTC);
				
				
					if (medicationIncreased == false) {
						operationResult = "Increase unsuccessful. No such medication currently exists in the inventory. See current inventory";
					}
					else {
						operationResult = "Increase successful. See updated inventory";
					}

				
					displayMercList(outputList, inv.getMerchandise());
					lblOperationResult.setText(operationResult);
				}
				catch(Exception ex) {
					//DisplayErrorPopup.displayErrorPopup("name, Qty, type, and form are required", frame);
					JOptionPane.showMessageDialog(frame,"name, Qty, type, and form are required","Invalid input", JOptionPane.WARNING_MESSAGE);
				}
			
			}
		});
        
        JButton btnDecrease = new JButton("Decrease");
        btnDecrease.setFont(new Font("굴림", Font.BOLD, 18));
        btnDecrease.setBounds(911, 135, 125, 35);
        panelVisibleToAdmin.add(btnDecrease);
        btnDecrease.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {	//catches exception thrown from decreseQuantity() caused by passing insufficient number of arguments
					String _inputFieldName = inputFieldName.getText().toUpperCase();
					if (_inputFieldName.isEmpty()) throw new Exception(); // ensures a medication name has been entered
					int _inputFieldQty = Integer.parseInt(inputFieldQty.getText());
					MERCHANDISE_TYPE _inputFieldType = MERCHANDISE_TYPE.valueOf(inputFieldType.getText().toUpperCase());
					MERCHANDISE_FORM _inputFieldForm = MERCHANDISE_FORM.valueOf(inputFieldForm.getText().toUpperCase());
					Boolean _isOTC = false;
					if(rdbtnOTC.isSelected()) {
						_isOTC = true;
					}
				
				
					boolean[] medicationDecreasedANDEnoughQuantityToDecrease = {false, false, false};
				
					medicationDecreasedANDEnoughQuantityToDecrease = inv.decreaseQuantity(_inputFieldName, _inputFieldQty, _inputFieldType, _inputFieldForm, _isOTC);
					
					
					if (medicationDecreasedANDEnoughQuantityToDecrease[0] == true && medicationDecreasedANDEnoughQuantityToDecrease[1] == true) {
						//entry to decrease its quantity is found in the list and the current_quantity >= quantity_to_decrease 
						operationResult = "Decrease successful. See inventory";
					}
					else if (medicationDecreasedANDEnoughQuantityToDecrease[0] == false && medicationDecreasedANDEnoughQuantityToDecrease[1] == true) {
						//entry to decrease its quantity is not found in the list
						operationResult = "Decrease unsuccessful. No such medication currently exists in the inventory. See inventory";
					}
					else {
						//entry is found but current_quantity < quantity_to_decrease
						operationResult = "Decrease unsuccessful. There is not enough quantity of the medication to decrease by " + _inputFieldQty + ". See updated inventory";
					}
				
					if (medicationDecreasedANDEnoughQuantityToDecrease[2] == true) {
						//notify popup for medication low in stock
						//DisplayErrorPopup.displayErrorPopup(_inputFieldName + " is low on stock, please order.", frame);
						JOptionPane.showMessageDialog(frame,_inputFieldName + " is low on stock, please order.", "Warning", JOptionPane.WARNING_MESSAGE);
					}
					lblOperationResult.setText(operationResult);
					displayMercList(outputList, inv.getMerchandise());
				}
				catch(Exception ex) {	//display popup when the number of arguments passed to decreaseQuantity() is insufficient
					//DisplayErrorPopup.displayErrorPopup("name, Qty, type, and form are required", frame);
					JOptionPane.showMessageDialog(frame,"name, Qty, type, and form are required", "Invalid input", JOptionPane.WARNING_MESSAGE);
					
				}
			
			}
		});
        
        JButton btnDisplay = new JButton("Display");
        btnDisplay.setFont(new Font("굴림", Font.BOLD, 20));
        btnDisplay.setBounds(1048, 134, 125, 35);
        panelVisibleToAdmin.add(btnDisplay);
        btnDisplay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayMercList(outputList, inv.getMerchandise());
			
			}
		});
        JPanel panelVisibleToAdminSub1 = new JPanel();
        panelVisibleToAdminSub1.setBounds(1001, 98, 170, 397);
        totalGUI.add(panelVisibleToAdminSub1);
        panelVisibleToAdminSub1.setLayout(null);
        
        JButton btnManagePatients = new JButton("<html>Manage<br>Patients</html> ");
        btnManagePatients.setFont(new Font("굴림", Font.BOLD, 18));
        btnManagePatients.setBounds(0, 0, 170, 60);
        btnManagePatients.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DisplayPatientManage.displayPatientManage(frame);

			}
        	
        });
        panelVisibleToAdminSub1.add(btnManagePatients);
        
        totalGUI.add(panelVisibleToAdmin);
        totalGUI.add(panelVisibleToAdminSub1);
        
        JButton btnAddOrder = new JButton("<html>Add<br>Order</html>");
        btnAddOrder.setFont(new Font("굴림", Font.BOLD, 18));
        btnAddOrder.setBounds(0, 70, 170, 60);
        btnAddOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DisplayAddOrder.displayAddOrder(frame);

			}
        	
        });
        panelVisibleToAdminSub1.add(btnAddOrder);
	}
	
	private void createExtraContents(JPanel totalGUI) {
        JButton btnExit = new JButton("Exit");
        btnExit.setFont(new Font("굴림", Font.BOLD, 20));
        btnExit.setBounds(1249, 40, 125, 35);
        btnExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.setBorder(new LineBorder(new Color(192, 192, 192)));
        horizontalBox.setBackground(new Color(0, 0, 0));
        horizontalBox.setBounds(12, 515, 1362, 2);
        
        totalGUI.add(horizontalBox);
        totalGUI.add(btnExit);
	}
	
	private void createPanelVisibleToAll(JPanel totalGUI) {
		JPanel panelVisibleToAll = new JPanel();
        panelVisibleToAll.setBounds(45, 40, 944, 465);
        panelVisibleToAll.setLayout(null);
        
        inputKeyword = new JTextField();
        inputKeyword.setBounds(0, 0, 650, 35);
        panelVisibleToAll.add(inputKeyword);
        inputKeyword.setColumns(40);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(650, 0, 145, 35);
        comboBox.setBorder(new LineBorder(new Color(0, 0, 0)));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Name", "Type"}));
        comboBox.setSelectedIndex(0);
        comboBox.setFont(new Font("굴림", Font.BOLD, 18));
        comboBox.setBackground(new Color(255, 255, 255));
        panelVisibleToAll.add(comboBox);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(819, 0, 125, 35);
        btnSearch.setFont(new Font("굴림", Font.BOLD, 20));
        panelVisibleToAll.add(btnSearch);
        btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String _inputKeyword = inputKeyword.getText().toUpperCase();
				String searchBy = (String)comboBox.getSelectedItem();
				ArrayList<Merchandise> methodResult = new ArrayList<Merchandise>();
				if(userType == USER.OWNER || userType == USER.PHARMACIST) {
					Owner owner1 = new Owner(1,1);

					if(searchBy.compareTo("Name") == 0) {
						methodResult = owner1.searchOTCMedicineByName(_inputKeyword);
					}
					else if(searchBy.compareTo("Type") == 0) {
						methodResult = owner1.searchOTCMedicineByType(MERCHANDISE_TYPE.getValue(_inputKeyword));
					}
					else {	//left empty intentionally for further expansion of feature in the future
						
					}
					
					
				}
				else {
					Patient patient1 = new Patient(1,1);
					
					if(searchBy.compareTo("Name") == 0) {
						methodResult = patient1.searchOTCMedicineByName(_inputKeyword);
					}
					else if(searchBy.compareTo("Type") == 0) {
						methodResult = patient1.searchOTCMedicineByType(MERCHANDISE_TYPE.getValue(_inputKeyword));
					}
					else {
						
					}
					
				}
				if(methodResult.isEmpty()) {
					operationResult = _inputKeyword + " is not in the inventory";
					lblOperationResult.setText(operationResult);
				}
				displayMercList(outputList, methodResult);
			}
		});
        
        

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 75, 944, 350);
        outputList = new JList<Merchandise>();
        outputList.setFont(new Font("굴림", Font.PLAIN, 15));
        outputList.setLocation(0, 45);
        outputList.setSize(944, 410);

        outputList.setLayoutOrientation(JList.VERTICAL);
        outputList.setCellRenderer(createListRenderer());
        scrollPane.setViewportView(outputList);

        panelVisibleToAll.add(scrollPane);
        displayMercList(outputList, inv.getMerchandise());
        totalGUI.add(panelVisibleToAll);
        
        JPanel panelColumn = new JPanel();
        panelColumn.setBounds(0, 56, 944, 20);
        panelVisibleToAll.add(panelColumn);
        panelColumn.setLayout(null);
        
        JButton btnNewButton_2 = new JButton("Name");
        btnNewButton_2.setFont(new Font("굴림", Font.BOLD, 12));
        btnNewButton_2.setHorizontalAlignment(SwingConstants.LEFT);
        btnNewButton_2.setHorizontalTextPosition(SwingConstants.LEFT);
        btnNewButton_2.setBounds(0, 0, 365, 20);
        panelColumn.add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("Qty");
        btnNewButton_3.setFont(new Font("굴림", Font.BOLD, 12));
        btnNewButton_3.setHorizontalAlignment(SwingConstants.LEFT);
        btnNewButton_3.setBounds(364, 0, 80, 20);
        panelColumn.add(btnNewButton_3);
        
        JButton btnNewButton_4 = new JButton("Price");
        btnNewButton_4.setHorizontalAlignment(SwingConstants.LEFT);
        btnNewButton_4.setFont(new Font("굴림", Font.BOLD, 12));
        btnNewButton_4.setBounds(443, 0, 100, 20);
        panelColumn.add(btnNewButton_4);
        
        JButton btnNewButton_5 = new JButton("Type");
        btnNewButton_5.setHorizontalAlignment(SwingConstants.LEFT);
        btnNewButton_5.setFont(new Font("굴림", Font.BOLD, 12));
        btnNewButton_5.setBounds(542, 0, 150, 20);
        panelColumn.add(btnNewButton_5);
        
        JButton btnNewButton_6 = new JButton("Form");
        btnNewButton_6.setHorizontalAlignment(SwingConstants.LEFT);
        btnNewButton_6.setFont(new Font("굴림", Font.BOLD, 12));
        btnNewButton_6.setBounds(691, 0, 150, 20);
        panelColumn.add(btnNewButton_6);
        
        JButton btnNewButton_7 = new JButton("isOTC");
        btnNewButton_7.setHorizontalAlignment(SwingConstants.LEFT);
        btnNewButton_7.setFont(new Font("굴림", Font.BOLD, 12));
        btnNewButton_7.setBounds(840, 0, 104, 20);
        panelColumn.add(btnNewButton_7);
        
        lblOperationResult = new JLabel();
        lblOperationResult.setForeground(new Color(255, 0, 0));
        lblOperationResult.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 18));
        lblOperationResult.setBounds(0, 425, 944, 40);
        panelVisibleToAll.add(lblOperationResult);
        


	}
	
	private void createPanalVisibleToPatient(JPanel totalGUI) {
		JPanel panelVisibleToPatient = new JPanel();
        panelVisibleToPatient.setBounds(1183, 98, 170, 397);
        totalGUI.add(panelVisibleToPatient);
        panelVisibleToPatient.setLayout(null);
        
        JButton btnNewButton_1 = new JButton("<html>Change<br>Profile</html>");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 17));
        btnNewButton_1.setBounds(0, 0, 170, 60);
        panelVisibleToPatient.add(btnNewButton_1);
        
        totalGUI.add(panelVisibleToPatient);
	}
	
	private static void displayMercList(JList<Merchandise> list, ArrayList<Merchandise> merchandises) {

		DefaultListModel<Merchandise> model = new DefaultListModel<Merchandise>();	//the list will automatically be refreshed
		list.removeAll();
		for(Merchandise m : merchandises) {

			model.addElement(m);
			
		}
		list.setModel(model);
	}
	
	private static ListCellRenderer<? super Merchandise> createListRenderer(){
		 return new DefaultListCellRenderer() {
	          private Color background = new Color(0, 100, 255, 15);
	          private Color defaultBackground = (Color) UIManager.get("List.background");

	          @Override
	          public Component getListCellRendererComponent(JList<?> list, Object value, int index,
	                                                        boolean isSelected, boolean cellHasFocus) {
	              Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	              if (c instanceof JLabel) {
	                  JLabel label = (JLabel) c;
	                  Merchandise merc = (Merchandise) value;
	                  
	                  label.setText(String.format("%s %s %s %s %s %s", merc.getName(), merc.getQuantity(), merc.getPrice(), merc.getType(), merc.getForm(), merc.getisOTC()));
	                  //label.setText(MercString(merc));
	                  if (!isSelected) {
	                      label.setBackground(index % 2 == 0 ? background : defaultBackground);
	                  }
	              }
	              return c;
	          }
	      };
	}
	
	/*private static String MercString(Merchandise m) {
		String result = new String();
		int paddingLength;

		result += String.format("%" + (-(78 - m.getName().length())) + "s", m.getName());
		result += String.format("%" + (-(10 - String.valueOf(m.getQuantity()).length())) + "s", m.getQuantity());
		return result;
	}*/
	public String getName() {
		return new String(name);
	}
	public int getUsername() {
		return username;
	}
	public String getType() {
		return new String(type);
	}
	public String getForm() {
		return new String(form);
	}
	public int getQty() {
		return qty;
	}
	public double getPrice() {
		return price;
	}
	public boolean getIsOTC() {
		return isOTC;
	}
	public String getSearchKeyword() {
		return new String(searchKeyword);
	}
	public JFrame getFrame() {
		return frame;
	}
	
	public static void main(String[] args) {	//for test purpose
		DisplayInitialScreen screen = new DisplayInitialScreen();
		screen.displayInitialScreen(USER.OWNER);
	}
}
