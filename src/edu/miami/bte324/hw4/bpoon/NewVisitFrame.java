package edu.miami.bte324.hw4.bpoon;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.xml.stream.XMLStreamException;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 * @author Barbara
 *
 */
public class NewVisitFrame extends JFrame {

	private JPanel contentPane;
	private final static String SCHEDULER_FILE = "XML\\schedulerData.xml";
	private final static String INPUT_FILE = "XML\\schedulerData.xml";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewVisitFrame frame = new NewVisitFrame();
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
	public NewVisitFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //changed so that it won't close other frames
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAddANew = new JLabel("Add a New Visit");
		lblAddANew.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddANew.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAddANew.setBounds(10, 11, 414, 14);
		contentPane.add(lblAddANew);
		
		JLabel lblFirstName = new JLabel("Patient");
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFirstName.setBounds(37, 60, 100, 14);
		contentPane.add(lblFirstName);
		
		JLabel lblMiddleInitial = new JLabel("Doctor");
		lblMiddleInitial.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMiddleInitial.setBounds(37, 85, 95, 14);
		contentPane.add(lblMiddleInitial);
		
		JLabel lblDateOfBirth = new JLabel("Date");
		lblDateOfBirth.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDateOfBirth.setBounds(37, 112, 95, 14);
		contentPane.add(lblDateOfBirth);
		
		JLabel lblSsn = new JLabel("Time (24Hr)");
		lblSsn.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSsn.setBounds(37, 164, 95, 14);
		contentPane.add(lblSsn);
//		================================================
//		DatePicker
//		================================================
		JDatePickerImpl datePicker;

	      UtilDateModel model=new UtilDateModel();
	      Properties p = new Properties();
	      p.put("text.today", "Today");
	      p.put("text.month", "Month");
	      p.put("text.year", "Year");
	      
	      JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	      datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	      datePicker.setBounds(149,112,230,30);
	      contentPane.add(datePicker);
//		====================================================
//		Combo Box: Patients
//		====================================================			
		JComboBox comboBoxPatient = new JComboBox();	
		comboBoxPatient.setBounds(149, 54, 230, 20);
//		====================================================
//		Combo Box: Doctors
//		====================================================			
		JComboBox comboBoxDoc = new JComboBox();
		comboBoxDoc.setBounds(149, 83, 230, 20);
//		====================================================
//		Combo Box: Time - HH
//		====================================================
		JComboBox comboBoxHH = new JComboBox();
		comboBoxHH.setBounds(159, 162, 42,20);
		for(int i=0; i<25; i++){
			
			comboBoxHH.addItem(i);
		}
		
//		====================================================
//		Combo Box: Time - mm
//		====================================================
		JComboBox comboBoxmm = new JComboBox();
		comboBoxmm.setBounds(217, 162, 42,20);
			
			comboBoxmm.addItem("00");
			comboBoxmm.addItem("15");
			comboBoxmm.addItem("30");
			comboBoxmm.addItem("45");

		try {
			SchedulerData scheduleList;
			scheduleList = SchedulerXMLReaderUtils.readSchedulingXML(SCHEDULER_FILE);


			//fill in the patient Combo Box
			for (Patient p1: scheduleList.patientList){
				if (p1.getActive().equals("active")){
				comboBoxPatient.addItem("ID: " + p1.getPatientId() + "  NAME: " + p1.getlname() + ", " + p1.getfname());
				}
			} //end of patient For loop
			// fill in the doctor Combo Box
			for (Doctor d: scheduleList.doctorList){
				if (d.getActive().equals("active")){
				comboBoxDoc.addItem("ID: " + d.getDoctorId() + "  NAME: " + d.getlname() + ", " + d.getfname());
				}				
			} // end of doctorFor Loop
			} catch (XMLStreamException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
		JButton btnNewButton = new JButton("Submit");

		comboBoxPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		comboBoxDoc.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					SchedulerData scheduleList;
					scheduleList = SchedulerXMLReaderUtils.readSchedulingXML(SCHEDULER_FILE);
					int patientId = 0;
					int doctorId = 0;
					Date visitDate = (Date) datePicker.getModel().getValue();
					// Set patientId
					String s = (String) comboBoxPatient.getSelectedItem();
					for (Patient p1: scheduleList.patientList){
						if (s.equals("ID: " + p1.getPatientId() + "  NAME: " + p1.getlname() + ", " + p1.getfname())){
							patientId = p1.getPatientId();
						}
					} // end of Patient loop to get PatientId
					String docStr = (String) comboBoxDoc.getSelectedItem();
					for (Doctor d: scheduleList.doctorList){
						if (docStr.equals("ID: " + d.getDoctorId() + "  NAME: " + d.getlname() + ", " + d.getfname()))
							doctorId = d.getDoctorId();
					} // end of doctorFor Loop
					
					Visit<Integer,Integer> newVisit = new VisitImpl<>(patientId, doctorId, visitDate);
					scheduleList.addVisit(newVisit);
					SchedulerXMLWriteTest.writeScheduler(SCHEDULER_FILE, scheduleList);
					JOptionPane.showMessageDialog(null, "Successfully added a new Visit.");
				} catch (XMLStreamException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}); //end comboBoxPatient
			}
		}); //end comboBoxDoctor
			}
		}); //end comboBoxSubmit
		contentPane.add(comboBoxPatient);
		contentPane.add(comboBoxDoc);
		contentPane.add(comboBoxHH);
		contentPane.add(comboBoxmm);
		btnNewButton.setBounds(215, 248, 162, 23);
		contentPane.add(btnNewButton);
		
		JLabel label = new JLabel(":");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(186, 165, 46, 14);
		contentPane.add(label);
	}
}
