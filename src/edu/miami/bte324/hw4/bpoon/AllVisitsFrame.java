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
public class AllVisitsFrame extends JFrame {

	private JPanel contentPane;
	private String SCHEDULER_FILE = "XML\\schedulerData.xml";
	private JTable table;
	SimpleDateFormat df2 = new SimpleDateFormat("MMMM dd, yyyy");
//	Date today = new Date();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AllVisitsFrame frame = new AllVisitsFrame();
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
	public AllVisitsFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //changed so that it won't close other frames
		setBounds(100, 100, 650, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPatients = new JLabel("All Visits");
		lblPatients.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatients.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPatients.setBounds(100, 14, 414, 26);
		contentPane.add(lblPatients);
		
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
				
				for (Patient p1: scheduleList.patientList){
//					if (s.equals("All Patients")){
						for (Visit<Integer, Integer> v: scheduleList.visitList){
//							if (today.before(v.getDate()))
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
					}
//				====================================================
//				Combo Box: Patients Action
//				====================================================				
				comboBoxPatient.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					String s = (String) comboBoxPatient.getSelectedItem();
					for (Patient p1: scheduleList.patientList){
						if (s.equals("All Patients")){
							for (Visit<Integer, Integer> v: scheduleList.visitList){
//								if (today.before(v.getDate()))
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
						}
						else if (s.equals("ID: " + p1.getPatientId() + "  NAME: " + p1.getlname() + ", " + p1.getfname())){
//						====================================================
//						Update Visits with Patient Information
//						====================================================						
						for (int i=tableModel.getRowCount() - 1; i >= 0 ; i--){
						tableModel.removeRow(i);
						}
						for (Visit<Integer, Integer> v: scheduleList.visitList){
							if (p1 == patientMap.get(v.getVisitor()))
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
//								if (today.before(v.getDate()))
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
							if (p1 == doctorMap.get(v.getVisitor()))
								{
								String Doctor = (doctorMap.get(v.getVisitor()).getfname() + " " + doctorMap.get(v.getVisitor()).getlname());
								String doctor = (doctorMap.get(v.getHost()).getfname() + " " + doctorMap.get(v.getHost()).getlname());
								//Format the date
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ignore time zones for simplicity
								String dateStr = df.format(v.getDate().getTime());
								
								Object[] newModelData = {Doctor, doctor, dateStr};
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

			}
			
			

			

						
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
		
		JLabel lblByPatient = new JLabel("By Patient");
		lblByPatient.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblByPatient.setBounds(20, 76, 95, 14);
		contentPane.add(lblByPatient);
		
		JLabel lblByDoctor = new JLabel("By Doctor");
		lblByDoctor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblByDoctor.setBounds(309, 75, 95, 14);
		contentPane.add(lblByDoctor);
	}
	
}