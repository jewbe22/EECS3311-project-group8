package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import databaseDAO.superDAO;
import middleLayer.Orders.*;
import java.awt.Color;

public class DisplayReport {
	private JTextField textFieldRevenue;
	private JTextField textFieldProfit;
	private JFrame frame;
	private JFrame superFrame;
	


	
	public void displayReport(JFrame previous) {
		double revenue = 0;
		double profit = 0;
		ArrayList<String> summary = new ArrayList<String>();
		
		
		this.frame = new JFrame("Report");
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.superFrame = previous;
		this.superFrame.setEnabled(false);

		this.frame.setBounds(new Rectangle(0, 0, 900, 600));
		this.frame.getContentPane().setLayout(null);


		
		JTextArea textAreaSummary = new JTextArea();
		textAreaSummary.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaSummary.setBounds(12, 10, 568, 443);
		this.frame.getContentPane().add(textAreaSummary);
		
		JLabel lblRevenue = new JLabel("Revenue");
		lblRevenue.setFont(new Font("굴림", Font.BOLD, 18));
		lblRevenue.setBounds(592, 15, 197, 35);
		this.frame.getContentPane().add(lblRevenue);
		
		this.textFieldRevenue = new JTextField();
		this.textFieldRevenue.setFont(new Font("굴림", Font.PLAIN, 18));
		this.textFieldRevenue.setBounds(592, 60, 197, 35);
		this.frame.getContentPane().add(textFieldRevenue);
		this.textFieldRevenue.setColumns(10);
		
		JLabel lblNProfit = new JLabel("Profit");
		lblNProfit.setFont(new Font("굴림", Font.BOLD, 18));
		lblNProfit.setBounds(592, 105, 197, 35);
		this.frame.getContentPane().add(lblNProfit);
		
		this.textFieldProfit = new JTextField();
		this.textFieldProfit.setFont(new Font("굴림", Font.PLAIN, 18));
		this.textFieldProfit.setBounds(592, 150, 197, 35);
		this.frame.getContentPane().add(textFieldProfit);
		this.textFieldProfit.setColumns(10);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("굴림", Font.BOLD, 18));
		btnExit.setBounds(592, 418, 197, 35);
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				superFrame.setEnabled(true);
				superFrame.toFront();
				frame.dispose();
			}
			
		});
		this.frame.getContentPane().add(btnExit);
		
		Report report = new Report();
		try {
			revenue = report.calculateRevenue();
			profit = report.calculateProfit();
			summary = report.seeSummaryOfSales();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		this.textFieldProfit.setText(String.format("%.2f", profit));
		this.textFieldRevenue.setText(String.format("%.2f", revenue));
		for(String s : summary) {
			textAreaSummary.append(s);
		}
		
		this.frame.setEnabled(true);
		this.frame.setVisible(true);
	}

}
