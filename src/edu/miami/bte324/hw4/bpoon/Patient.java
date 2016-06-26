package edu.miami.bte324.hw4.bpoon;

import java.util.*;

/**
 * @author Barbara Poon
 *
 */
public interface Patient {
	
	int getPatientId();
	String getlname();
	String getfname();
	String getSSN();
	Date getDOB();
	int getAge();
	String getActive();
	void setActive(String active);
//	void setPatientId(int patientId);
	void setLname(String lname);
	void setFname(String fname);
	void setSsn(String ssn);
	void setDob(Date dob);
}
