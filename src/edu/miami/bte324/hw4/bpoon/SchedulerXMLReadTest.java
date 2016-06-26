package edu.miami.bte324.hw4.bpoon;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;


/**
 * @author Barbara
 *
 */
public class SchedulerXMLReadTest {
//	private final static String INPUT_FILE = "XML\\schedulerData.xml";
	private final static String INPUT_FILE = "XML\\test.xml"; 
	private final static String OUTPUT_FILE = "XML\\schedulerData.xml";
//	private final static String OUTPUT_FILE = "XML\\test.xml";
	
	
	public static void main(String[] args) throws XMLStreamException, IOException {
		// TODO Auto-generated method stub
		SchedulerData scheduleList = SchedulerXMLReaderUtils.readSchedulingXML(INPUT_FILE);
		//scheduleList is my old visidIdList
		// Date Variables: format, today
		SimpleDateFormat df2 = new SimpleDateFormat("MMMM dd, yyyy");
		Date today = new Date();
//		=======================================================
//		Create a patientIdMap, doctorIdMap.
//		=======================================================
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

			
//		=======================================================
//		Printout 3: Upcoming visits ordered by visit date.
//		=======================================================	
		
		
		System.out.println("Upcoming Visits Ordered by Visit Date");
		
		System.out.println("--------------------------------------------------");
		

			for (int i = 0; i < 6; i++){
			System.out.println("Visit date: \t\t" + df2.format(scheduleList.visitList.get(i).getDate()));
				System.out.println("Doctor: \t\t" + doctorMap.get(scheduleList.visitList.get(i).getHost()).getfname()
									+ " " + doctorMap.get(scheduleList.visitList.get(i).getHost()).getlname());
				System.out.println("Specialty: \t\t" + doctorMap.get(scheduleList.visitList.get(i).getHost()).getSpecialty());

				//			Calculate the number of days before visit.
				int days = (int)Math.ceil((scheduleList.visitList.get(i).getDate().getTime() - today.getTime())/(1000*60*60*24)+1);
				System.out.println("Days until visit: \t" + days);

				//			Print out Patient Info.
				System.out.println("Patient:");
				System.out.println("\t First name: \t" + patientMap.get(scheduleList.visitList.get(i).getVisitor()).getfname());
				System.out.println("\t Last name: \t" + patientMap.get(scheduleList.visitList.get(i).getVisitor()).getlname());
				System.out.println("\t SSN: \t\t" + patientMap.get(scheduleList.visitList.get(i).getVisitor()).getSSN());
				System.out.println("\t Age: \t\t" + patientMap.get(scheduleList.visitList.get(i).getVisitor()).getAge());
				System.out.print("\n");
			}
			System.out.println("--------------------------------------------------");
			SchedulerXMLWriteTest.writeScheduler(OUTPUT_FILE, scheduleList);
	}
}

