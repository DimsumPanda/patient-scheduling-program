package edu.miami.bte324.hw4.bpoon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.stream.XMLStreamException;

/**
 * @author Barbara
 *
 */
public class VisitsFrame extends JFrame {

	private JPanel contentPane;
	private String SCHEDULER_FILE = "XML\\schedulerData.xml";
	private JTable table;
	private JTextField txtvisitDate;
	SimpleDateFormat df2 = new SimpleDateFormat("MMMM dd, yyyy");
	Date today = new Date();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisitsFrame frame = new VisitsFrame();
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
	public VisitsFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //changed so that it won't close other frames
		setBounds(100, 100, 650, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPatients = new JLabel("Upcoming Visits");
		lblPatients.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatients.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPatients.setBounds(100, 14, 414, 23);
		contentPane.add(lblPatients);

//		====================================================
//		Button: New Patient
//		====================================================	
		JButton btnNewButton = new JButton("New Visit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				frame.dispose();
				NewVisitFrame newFrame = new NewVisitFrame();
				newFrame.setVisible(true);
//				JOptionPane.showMessageDialog(null, "Clicked");
				
				
			}
		});
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(20, 39, 146, 23);
		contentPane.add(btnNewButton);
		
		
//		====================================================
//		Labels
//		====================================================		
		JLabel lblCurrentPatients = new JLabel("Upcoming Visits");
		lblCurrentPatients.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCurrentPatients.setBounds(20, 394, 122, 19);
		contentPane.add(lblCurrentPatients);
		
		txtvisitDate = new JTextField();
		txtvisitDate.setColumns(10);
		txtvisitDate.setBounds(169, 454, 230, 20);
		contentPane.add(txtvisitDate);


		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(225, 527, 162, 23);
		
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(412, 527, 162, 23);

//		====================================================
//		Combo Box: Visit
//		====================================================			
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(169, 393, 345, 20);
		
		String col[] = {"Patient", "Doctor", "Visit Date"};
		DefaultTableModel tableModel = new DefaultTableModel(col, 0);
		JTable table_1 = new JTable(tableModel);
		table_1.setBounds(25, 93, 500, 237);
		
		JComboBox comboBoxPatient = new JComboBox();
		comboBoxPatient.setBounds(105, 73, 175, 20);
		
		JComboBox comboBoxDoc = new JComboBox();
		comboBoxDoc.setBounds(420, 73, 175, 20);
		try {
			SchedulerData scheduleList;
			scheduleList = SchedulerXMLReaderUtils.readSchedulingXML(SCHEDULER_FILE);

//			====================================================
//			Fill the table with Visit Data
//			====================================================
			Map<Integer, Patient> patientMap = new HashMap<>();
			Map<Integer, Doctor> doctorMap = new HashMap<>();
			Integer user;
			Patient value;
			Doctor doc;
			
				for(int i = 0; i < scheduleList.patientList.size(); i++){
					
					user = scheduleList.patientList.get(i).getPatientId();
					value = scheduleList.patientList.get(i);
					patientMap.put(user, value);
				}
				for(int i = 0; i < scheduleList.doctorList.size(); i++){
					
					user = scheduleList.doctorList.get(i).getDoctorId();
					doc = scheduleList.doctorList.get(i);
					doctorMap.put(user, doc);
				}	
				
				for (Visit<Integer, Integer> v: scheduleList.visitList){
					if (today.before(v.getDate()))
						{
						String patient = (patientMap.get(v.getVisitor()).getfname() + " " + patientMap.get(v.getVisitor()).getlname());
						String doctor = (doctorMap.get(v.getHost()).getfname() + " " + doctorMap.get(v.getHost()).getlname());
						//Format the date
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ignore time zones for simplicity
						String dateStr = df.format(v.getDate().getTime());
						
						Object[] data = {patient, doctor, dateStr};
						tableModel.addRow(data);
					}
				}
//				====================================================
//				Combo Box: Patients and Doctor
//				====================================================
				//fill in the patient Combo Box
				comboBoxPatient.addItem("All Patients");
				for (Patient p1: scheduleList.patientList){
					if (p1.getActive().equals("active")){
					comboBoxPatient.addItem("ID: " + p1.getPatientId() + "  NAME: " + p1.getlname() + ", " + p1.getfname());
					}
				} //end of patient For loop
				// fill in the doctor Combo Box
				comboBoxDoc.addItem("All Doctors");
				for (Doctor d: scheduleList.doctorList){
					if (d.getActive().equals("active")){
					comboBoxDoc.addItem("ID: " + d.getDoctorId() + "  NAME: " + d.getlname() + ", " + d.getfname());
					}				
				} // end of doctorFor Loop
//				====================================================
//				Combo Box: Patients Action
//				====================================================				
				comboBoxPatient.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					String s = (String) comboBoxPatient.getSelectedItem();
					for (Patient p1: scheduleList.patientList){
						if (s.equals("All Patients")){
							for (int i=tableModel.getRowCount() - 1; i >= 0 ; i--){
								tableModel.removeRow(i);
								}
							for (Visit<Integer, Integer> v: scheduleList.visitList){
								if (today.before(v.getDate()))
									{
									String patient = (patientMap.get(v.getVisitor()).getfname() + " " + patientMap.get(v.getVisitor()).getlname());
									String doctor = (doctorMap.get(v.getHost()).getfname() + " " + doctorMap.get(v.getHost()).getlname());
									//Format the date
									DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ignore time zones for simplicity
									String dateStr = df.format(v.getDate().getTime());
									
									Object[] data = {patient, doctor, dateStr};
									tableModel.addRow(data);

								}
							}
							tableModel.fireTableDataChanged();
						}
						else if (s.equals("ID: " + p1.getPatientId() + "  NAME: " + p1.getlname() + ", " + p1.getfname())){
//						====================================================
//						Update Visits with Patient Information
//						====================================================						
						for (int i=tableModel.getRowCount() - 1; i >= 0 ; i--){
						tableModel.removeRow(i);
						}
						for (Visit<Integer, Integer> v: scheduleList.visitList){
							if (today.before(v.getDate()) && p1 == patientMap.get(v.getVisitor()))
								{
								String patient = (patientMap.get(v.getVisitor()).getfname() + " " + patientMap.get(v.getVisitor()).getlname());
								String doctor = (doctorMap.get(v.getHost()).getfname() + " " + doctorMap.get(v.getHost()).getlname());
								//Format the date
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ignore time zones for simplicity
								String dateStr = df.format(v.getDate().getTime());
								
								Object[] newModelData = {patient, doctor, dateStr};
								tableModel.addRow(newModelData);
							}
						}	
						
						tableModel.fireTableDataChanged();
					}
					}}
				});
//				====================================================
//				Combo Box: Doctors Action
//				====================================================				
				comboBoxDoc.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					String s = (String) comboBoxDoc.getSelectedItem();
					for (Doctor p1: scheduleList.doctorList){
						if (s.equals("All Doctors")){
							for (Visit<Integer, Integer> v: scheduleList.visitList){
								if (today.before(v.getDate()))
									{
									String patient = (doctorMap.get(v.getVisitor()).getfname() + " " + patientMap.get(v.getVisitor()).getlname());
									String doctor = (doctorMap.get(v.getHost()).getfname() + " " + doctorMap.get(v.getHost()).getlname());
									//Format the date
									DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ignore time zones for simplicity
									String dateStr = df.format(v.getDate().getTime());
									
									Object[] data = {patient, doctor, dateStr};
									tableModel.addRow(data);
								}
							}
						}
						else if (s.equals("ID: " + p1.getDoctorId() + "  NAME: " + p1.getlname() + ", " + p1.getfname())){
//						====================================================
//						Update Visits with Doctor Information
//						====================================================						
						for (int i=tableModel.getRowCount() - 1; i >= 0 ; i--){
						tableModel.removeRow(i);
						}
						for (Visit<Integer, Integer> v: scheduleList.visitList){
							if (today.before(v.getDate()) && p1 == doctorMap.get(v.getHost()))
								{
								String patient = (patientMap.get(v.getVisitor()).getfname() + " " + doctorMap.get(v.getVisitor()).getlname());
								String doctor = (doctorMap.get(v.getHost()).getfname() + " " + doctorMap.get(v.getHost()).getlname());
								//Format the date
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ignore time zones for simplicity
								String dateStr = df.format(v.getDate().getTime());
								
								Object[] newModelData = {patient, doctor, dateStr};
								tableModel.addRow(newModelData);
							}
						}	
						
						tableModel.fireTableDataChanged();
					}
					}}
				});
				
				
//				====================================================
//				Combo Box: Visit
//				====================================================
				for (Visit<Integer,Integer> v: scheduleList.visitList){
				
					for(int i = 0; i < scheduleList.patientList.size(); i++){
						
						user = scheduleList.patientList.get(i).getPatientId();
						value = scheduleList.patientList.get(i);
						patientMap.put(user, value);
					}
					for(int i = 0; i < scheduleList.doctorList.size(); i++){
						
						user = scheduleList.doctorList.get(i).getDoctorId();
						doc = scheduleList.doctorList.get(i);
						doctorMap.put(user, doc);
					}

					if (today.before(v.getDate())){	
				comboBox.addItem("Visit Date: " + df2.format(v.getDate())
						+ "  NAME: " + patientMap.get(v.getVisitor()).getlname() + ", " 
						+ patientMap.get(v.getVisitor()).getfname());
					}
			}

			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				String s = (String) comboBox.getSelectedItem();
				
				// Loop through patientList
				for (Visit v: scheduleList.visitList){
					Map<Integer, Patient> patientMap = new HashMap<>();
					Map<Integer, Doctor> doctorMap = new HashMap<>();
					Integer user;
					Patient value;
					Doctor doc;
					
						for(int i = 0; i < scheduleList.patientList.size(); i++){
							
							user = scheduleList.patientList.get(i).getPatientId();
							value = scheduleList.patientList.get(i);
							patientMap.put(user, value);
						}
						for(int i = 0; i < scheduleList.doctorList.size(); i++){
							
							user = scheduleList.doctorList.get(i).getDoctorId();
							doc = scheduleList.doctorList.get(i);
							doctorMap.put(user, doc);
						}
						
					if (s.equals("Visit Date: " + df2.format(v.getDate())
					+ "  NAME: " + patientMap.get(v.getVisitor()).getlname() + ", " 
					+ patientMap.get(v.getVisitor()).getfname())){


						//Format the date
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ignore time zones for simplicity
						String dateStr = df.format(v.getDate().getTime());
						
						// Fill default textFields when a name is selected
						txtvisitDate.setText(dateStr);

						
//						====================================================
//						Update Visit Date
//						====================================================
						btnUpdate.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								String visitDatenewStr = txtvisitDate.getText();
								Date visitDate = null;
								
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								try {
									visitDate = (Date)df.parse(visitDatenewStr);
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
								v.setVdate(visitDate);
								
								try {
									SchedulerXMLWriteTest.writeScheduler(SCHEDULER_FILE, scheduleList);
									JOptionPane.showMessageDialog(null, "Successfully updated a Patient.");
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
//						Remove Visit
//						====================================================
						btnRemove.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								for (int i=0; i < scheduleList.visitList.size(); i++){
									if ((v == scheduleList.visitList.get(i)) && (v.getDate() == scheduleList.visitList.get(i).getDate())){
										scheduleList.visitList.remove(i);
									}
								}
								
								try {
									SchedulerXMLWriteTest.writeScheduler(SCHEDULER_FILE, scheduleList);
									JOptionPane.showMessageDialog(null, "Successfully removed Visit.");
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
			
			

			

						
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 136, 575, 237);
		contentPane.add(scrollPane);
		contentPane.add(comboBoxPatient);
		contentPane.add(comboBoxDoc);


		scrollPane.setViewportView(table_1);
		
		JLabel lblVisitDate = new JLabel("Visit Date");
		lblVisitDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVisitDate.setBounds(20, 456, 95, 14);
		contentPane.add(lblVisitDate);
		
		JLabel lblByPatient = new JLabel("By Patient");
		lblByPatient.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblByPatient.setBounds(20, 76, 95, 14);
		contentPane.add(lblByPatient);
		
		JLabel lblByDoctor = new JLabel("By Doctor");
		lblByDoctor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblByDoctor.setBounds(309, 75, 95, 14);
		contentPane.add(lblByDoctor);
		
		JButton btnUpcomingVisits = new JButton("All Visits");
		btnUpcomingVisits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AllVisitsFrame newFrame = new AllVisitsFrame();
				newFrame.setVisible(true);
			}
		});
		btnUpcomingVisits.setForeground(Color.BLACK);
		btnUpcomingVisits.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnUpcomingVisits.setBounds(449, 39, 146, 23);
		contentPane.add(btnUpcomingVisits);
	}
}
