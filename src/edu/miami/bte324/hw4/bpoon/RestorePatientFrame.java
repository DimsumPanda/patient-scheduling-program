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
public class RestorePatientFrame extends JFrame {

	private JPanel contentPane;
	private String SCHEDULER_FILE = "XML\\schedulerData.xml";
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RestorePatientFrame frame = new RestorePatientFrame();
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
	public RestorePatientFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //changed so that it won't close other frames
		setBounds(100, 100, 652, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPatients = new JLabel("Inactive Patients");
		lblPatients.setHorizontalAlignment(SwingConstants.CENTER);
		lblPatients.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPatients.setBounds(100, 14, 414, 14);
		contentPane.add(lblPatients);

//		====================================================
//		Button: Restore
//		====================================================		
		JButton btnRestore = new JButton("Restore");
		btnRestore.setBounds(435, 382, 162, 23);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(40, 382, 367, 23);
		try {
//			====================================================
//			Combo Box
//			====================================================			
			
			
			
			SchedulerData scheduleList;
			scheduleList = SchedulerXMLReaderUtils.readSchedulingXML(SCHEDULER_FILE);
				
			for (Patient p: scheduleList.patientList){
				if (p.getActive().equals("inactive")){
				comboBox.addItem("ID: " + p.getPatientId() + "  NAME: " + p.getlname() + ", " + p.getfname());
				}
			}

			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				String s = (String) comboBox.getSelectedItem();
				
				// Loop through patientList
				for (Patient p: scheduleList.patientList){
					if (s.equals("ID: " + p.getPatientId() + "  NAME: " + p.getlname() + ", " + p.getfname())){
						
					
						
//						====================================================
//						Mark Patient Active
//						====================================================
						btnRestore.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								p.setActive("active");
								
								try {
									SchedulerXMLWriteTest.writeScheduler(SCHEDULER_FILE, scheduleList);
									JOptionPane.showMessageDialog(null, "Successfully restored a Patient.");
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
			contentPane.add(btnRestore);

			
			
			String col[] = {"Patient Id", "First Name", "Last Name", "Date of Birth", "SSN"};
			
			DefaultTableModel tableModel = new DefaultTableModel(col, 0);
//			====================================================
//			Fill the table with Patient Data
//			====================================================
				for (Patient p: scheduleList.patientList){
					if (p.getActive().equals("inactive")){
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
			scrollPane.setBounds(40, 94, 557, 277);
			contentPane.add(scrollPane);
			JTable table_1 = new JTable(tableModel);
			table_1.setBounds(25, 93, 500, 237);
//			table_1.addMouseListener(new MouseAdapter() {
//				@Override
				
//				====================================================
//				Click on Table Event
//				====================================================
//				public void mouseClicked(MouseEvent e) {
//					
//					int row = table_1.getSelectedRow();
//					// get the table click row
//					Object Table_click = table_1.getModel().getValueAt(row, 0);
//					
//					for (Patient p: scheduleList.patientList){
//						if (p.getActive().equals("active")){
//							
//							int id = p.getPatientId();
//							String firstName = p.getfname();
//							String lastName = p.getlname();
//							Date dob = p.getDOB();
//							String ssn = p.getSSN();
//							
//							Object[] data = {id , firstName, lastName, dob, ssn};
//							if (data.equals(Table_click)){
////								tableModel.addRow(data);
//								String add1 = p.getfname();
//								txtfirstName.setText(add1);
//								
//							}
//							
//							
//						}
//					}
//				}
//			});
			scrollPane.setViewportView(table_1);
			

			

			


			
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
