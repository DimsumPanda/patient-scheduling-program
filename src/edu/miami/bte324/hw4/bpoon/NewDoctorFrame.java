package edu.miami.bte324.hw4.bpoon;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
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

import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

/**
 * @author Barbara
 *
 */
public class NewDoctorFrame extends JFrame {

	private JPanel contentPane;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField ssn;
	private JTextField MiddleInitial;
	private final static String SCHEDULER_FILE = "XML\\schedulerData.xml";
	private final static String INPUT_FILE = "XML\\schedulerData.xml";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewDoctorFrame frame = new NewDoctorFrame();
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
	public NewDoctorFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //changed so that it won't close both frames
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JLabel lblAddANew = new JLabel("Add a New Doctor");
		lblAddANew.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddANew.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAddANew.setBounds(10, 11, 414, 14);
		contentPane.add(lblAddANew);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFirstName.setBounds(37, 60, 78, 14);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLastName.setBounds(37, 112, 78, 14);
		contentPane.add(lblLastName);
		
		JLabel lblMiddleInitial = new JLabel("Middle Initial");
		lblMiddleInitial.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMiddleInitial.setBounds(37, 85, 95, 14);
		contentPane.add(lblMiddleInitial);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDateOfBirth.setBounds(37, 162, 95, 14);
		contentPane.add(lblDateOfBirth);
		
		JLabel lblSsn = new JLabel("SSN");
		lblSsn.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSsn.setBounds(37, 137, 95, 14);
		contentPane.add(lblSsn);
		
		firstName = new JTextField();
		firstName.setBounds(149, 54, 230, 20);
		contentPane.add(firstName);
		firstName.setColumns(10);
		
		lastName = new JTextField();
		lastName.setColumns(10);
		lastName.setBounds(149, 110, 230, 20);
		contentPane.add(lastName);
		
		
		ssn = new JTextField();
		ssn.setColumns(10);
		ssn.setBounds(149, 135, 230, 20);
		contentPane.add(ssn);
		
		MiddleInitial = new JTextField();
		MiddleInitial.setColumns(10);
		MiddleInitial.setBounds(149, 83, 230, 20);
		contentPane.add(MiddleInitial);

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
	      datePicker.setBounds(149,162,230,30);
	      contentPane.add(datePicker);
	      
//			================================================
//			Combo Box: MedicalSpecialty
//			================================================		
		JLabel lblMedicalSpecialty = new JLabel("Medical Specialty");
		lblMedicalSpecialty.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMedicalSpecialty.setBounds(37, 212, 121, 14);
		contentPane.add(lblMedicalSpecialty);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(178, 210, 201, 20); 
		comboBox.addItem(MedicalSpecialty.GENERAL_MEDICINE.toString());
		comboBox.addItem(MedicalSpecialty.ONCOLOGY.toString());
		comboBox.addItem(MedicalSpecialty.PEDIATRICS.toString());
		  
	    JButton btnNewButton = new JButton("Submit");
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String s = (String) comboBox.getSelectedItem();
			MedicalSpecialty specialty = MedicalSpecialty.valueOf(s);

			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SchedulerData scheduleList;
					try {
						scheduleList = SchedulerXMLReaderUtils.readSchedulingXML(INPUT_FILE);
						int doctorId;
						Doctor doctor;
						doctorId = (scheduleList.doctorList.size() + 1);

						Date dob = (Date) datePicker.getModel().getValue();
						
						// How about the time?
						String active = "active";
						
						doctor = new DoctorImpl(doctorId, lastName.getText(), firstName.getText(), ssn.getText(), dob, specialty, active);
						scheduleList.addDoctor(doctor);
						
						SchedulerXMLWriteTest.writeScheduler(SCHEDULER_FILE, scheduleList);
						JOptionPane.showMessageDialog(null, "Successfully added a new doctor.");
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
		});
		contentPane.add(comboBox);
		btnNewButton.setBounds(217, 302, 162, 23);
		contentPane.add(btnNewButton);

		

	}
}
