package edu.miami.bte324.hw4.bpoon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.stream.XMLStreamException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Barbara
 *
 */
public class PatientsFrame extends JFrame {

	private JPanel contentPane;
	private String SCHEDULER_FILE = "XML\\schedulerData.xml";
	private JTable table;
	private JTextField txtfirstName;
	private JTextField txtlastName;
	private JTextField txtssn;
	private JTextField txtdob;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientsFrame frame = new PatientsFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PatientsFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //changed so that it won't close other frames
		setBounds(100, 100, 650, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPatients = new JLabel("Patients");
		lblPatients.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatients.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPatients.setBounds(100, 14, 414, 14);
		contentPane.add(lblPatients);

//		====================================================
//		Button: New Patient
//		====================================================	
		JButton btnNewButton = new JButton("New Patient");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				frame.dispose();
				NewPatientFrame newFrame = new NewPatientFrame();
				newFrame.setVisible(true);
//				JOptionPane.showMessageDialog(null, "Clicked");
				
				
			}
		});
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(20, 39, 146, 23);
		contentPane.add(btnNewButton);

//		====================================================
//		Button: Restore a Patient
//		====================================================
		JButton btnRestoreAPatient = new JButton("Restore a Patient");
		btnRestoreAPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RestorePatientFrame newFrame = new RestorePatientFrame();
				newFrame.setVisible(true);
			}
		});
		btnRestoreAPatient.setForeground(Color.BLACK);
		btnRestoreAPatient.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRestoreAPatient.setBounds(454, 39, 146, 23);
		contentPane.add(btnRestoreAPatient);
		
		
//		====================================================
//		Labels
//		====================================================		
		JLabel lblCurrentPatients = new JLabel("Current Patients");
		lblCurrentPatients.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCurrentPatients.setBounds(20, 341, 122, 14);
		contentPane.add(lblCurrentPatients);
		
		txtfirstName = new JTextField();
		txtfirstName.setColumns(10);
		txtfirstName.setBounds(169, 379, 230, 20);
		contentPane.add(txtfirstName);
		
		txtlastName = new JTextField();
		txtlastName.setColumns(10);
		txtlastName.setBounds(169, 416, 230, 20);
		contentPane.add(txtlastName);
		
		txtssn = new JTextField();
		txtssn.setColumns(10);
		txtssn.setBounds(169, 451, 230, 20);
		contentPane.add(txtssn);
		
		txtdob = new JTextField();
		txtdob.setColumns(10);
		txtdob.setBounds(169, 486, 230, 20);
		contentPane.add(txtdob);


		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(225, 527, 162, 23);
		
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(412, 527, 162, 23);
		
		String col[] = {"Patient Id", "First Name", "Last Name", "Date of Birth", "SSN"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);		
		
		try {
//			====================================================
//			Combo Box
//			====================================================			
			JComboBox comboBox = new JComboBox();
			
			comboBox.setBounds(169, 340, 230, 20);
			SchedulerData scheduleList;
			scheduleList = SchedulerXMLReaderUtils.readSchedulingXML(SCHEDULER_FILE);
				
			for (Patient p: scheduleList.patientList){
				if (p.getActive().equals("active")){
				comboBox.addItem("ID: " + p.getPatientId() + "  NAME: " + p.getlname() + ", " + p.getfname());
				}
			}

			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				String s = (String) comboBox.getSelectedItem();
				
				// Loop through patientList
				for (Patient p: scheduleList.patientList){
					if (s.equals("ID: " + p.getPatientId() + "  NAME: " + p.getlname() + ", " + p.getfname())){

						//Format the date
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ignore time zones for simplicity
						String dateStr = df.format(p.getDOB().getTime());
						
						// Fill default textFields when a name is selected
						txtfirstName.setText(p.getfname());
						txtlastName.setText(p.getlname());
						txtdob.setText(dateStr);
						txtssn.setText(p.getSSN());
						
//						====================================================
//						Update Patient Information
//						====================================================
						btnUpdate.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								p.setFname(txtfirstName.getText());
								p.setLname(txtlastName.getText());
								p.setSsn(txtssn.getText());
								
								String dobnewStr = txtdob.getText();
								Date dobnew = null;
								
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								try {
									dobnew = (Date)df.parse(dobnewStr);
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
								p.setDob(dobnew);
								
								try {
									SchedulerXMLWriteTest.writeScheduler(SCHEDULER_FILE, scheduleList);
									JOptionPane.showMessageDialog(null, "Successfully updated a Patient.");
									tableModel.fireTableDataChanged();
								} catch (XMLStreamException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						});
						
//						====================================================
//						Mark Patient Unactive
//						====================================================
						btnRemove.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								p.setActive("inactive");
								
								try {
									SchedulerXMLWriteTest.writeScheduler(SCHEDULER_FILE, scheduleList);
									JOptionPane.showMessageDialog(null, "Successfully removed a Patient.");
									tableModel.fireTableDataChanged();
								} catch (XMLStreamException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						});
						
					}
					}
				}
				});
			contentPane.add(comboBox);
			contentPane.add(btnRemove);
			contentPane.add(btnUpdate);
			
			

//			====================================================
//			Fill the table with Patient Data
//			====================================================
				for (Patient p: scheduleList.patientList){
					if (p.getActive().equals("active")){
						{
						int id = p.getPatientId();
						String firstName = p.getfname();
						String lastName = p.getlname();
						//Format the date
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ignore time zones for simplicity
						String dateStr = df.format(p.getDOB().getTime());
						String ssn = p.getSSN();
						
						Object[] data = {id , firstName, lastName, dateStr, ssn};
						tableModel.addRow(data);
						}
					}
				}
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(25, 93, 575, 237);
			contentPane.add(scrollPane);
			JTable table_1 = new JTable(tableModel);
			table_1.setBounds(25, 93, 500, 237);
			scrollPane.setViewportView(table_1);
			
			JLabel label = new JLabel("First Name");
			label.setFont(new Font("Tahoma", Font.BOLD, 12));
			label.setBounds(20, 381, 78, 14);
			contentPane.add(label);
			
			JLabel label_1 = new JLabel("Last Name");
			label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			label_1.setBounds(20, 418, 78, 14);
			contentPane.add(label_1);
			
			JLabel label_2 = new JLabel("SSN");
			label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
			label_2.setBounds(20, 453, 95, 14);
			contentPane.add(label_2);
			
			JLabel label_3 = new JLabel("Date of Birth");
			label_3.setFont(new Font("Tahoma", Font.BOLD, 12));
			label_3.setBounds(20, 488, 95, 14);
			contentPane.add(label_3);
			

			


			
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
