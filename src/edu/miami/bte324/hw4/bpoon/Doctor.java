package edu.miami.bte324.hw4.bpoon;

import java.util.Date;

/**
 * @author Barbara Poon
 *
 */
public interface Doctor extends Comparable<Doctor> {
	int getDoctorId();
	String getlname();
	String getfname();
	String getSSN();
	Date getDOB();
	MedicalSpecialty getSpecialty();
	String getActive();
	void setActive(String active);
//	void setDoctorId(int doctorId);
	void setLname(String lname);
	void setFname(String fname);
	void setSsn(String ssn);
	void setDob(Date dob);
	void setSpecialty(MedicalSpecialty specialty);
}
