package edu.miami.bte324.hw4.bpoon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//Make the class final, so that it is immutable. There cannot be a subclass of PatientImpl
/**
 * @author Barbara Poon
 *
 */
public final class PatientImpl implements Patient {
	
	int patientId;
	String lname;
	String fname;


	String ssn;
	Date dob;
	int age;
	Date today = new Date();
	String today2 = null;
	String pdateofbirth = null;
	String active;
	
	//Constructor of PatientImpl that takes as args: full name, SSN, dob
//	PatientImpl(String lname, String fname, String ssn, Date dob){
//		this.fname = fname;
//		this.lname = lname;
//		this.ssn = ssn;
//		this.dob = dob;
//		this.active = "active";
//	}
	PatientImpl(String lname, String fname, String ssn, Date dob, String active){
		this.fname = fname;
		this.lname = lname;
		this.ssn = ssn;
		this.dob = dob;
		this.active = active;
	}
	//Overload the constructor to take more parameters
//	PatientImpl(int patientId, String lname, String fname, String ssn, Date dob){
//		this.patientId = patientId;
//		this.fname = fname;
//		this.lname = lname;
//		this.ssn = ssn;
//		this.dob = dob;
//		this.age = this.getAge();
//		this.active = "active";
//	}
	
	PatientImpl(int patientId, String lname, String fname, String ssn, Date dob, String active){
		this.patientId = patientId;
		this.fname = fname;
		this.lname = lname;
		this.ssn = ssn;
		this.dob = dob;
		this.age = this.getAge();
		this.active = active;
	}
	public void setInactive(){
		active = "inactive";
	}
	
	@Override
	public int getPatientId() {
		return patientId;
	}

	@Override
	public String getlname() {
		return lname;
	}

	@Override
	public String getfname() {
		return fname;
	}

	@Override
	public String getSSN() {
		return ssn;
	}

	@Override
	public Date getDOB() {
		return dob;
	}

	@Override
	public int getAge() {
			try {
				
				SimpleDateFormat df3 = new SimpleDateFormat("MM-dd-yyyy");
				pdateofbirth = df3.format(dob);

				today2 = df3.format(today);
				Date date1 = new SimpleDateFormat("MM-dd-yyyy").parse(pdateofbirth);
				Date date2 = new SimpleDateFormat("MM-dd-yyyy").parse(today2);
				
				Calendar cal1 = new GregorianCalendar();
				Calendar cal2 = new GregorianCalendar();
				int factor = 0;
				cal1.setTime(date1);
				cal2.setTime(date2);
				if (cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
					factor = -1;
				}
				this.age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
				} catch (ParseException e) {
					System.out.println(e);
				}
		return age;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PatientImpl other = (PatientImpl) obj;
		if ((this.getlname()!= null)&&(this.getfname()!= null)&&(this.getlname()!=null)&&(this.getSSN()!= null)
				&& (this.getDOB() != null)){
			return false;
		}
		if ((this.getPatientId()==(other.getPatientId()))&& (this.getlname().equals(other.getlname()))
				&& (this.getfname().equals(other.getfname()))&&(this.getSSN().equals(other.getSSN()))
				&& (this.getDOB().equals(other.getDOB())))
			return true;
		else
			return false;
	}
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + this.patientId;
		hash = 53 * hash + (this.fname != null ? this.fname.hashCode() : 0);
		hash = 53 * hash + (this.lname != null ? this.lname.hashCode() : 0);
		hash = 53 * hash + (this.ssn != null ? this.ssn.hashCode() : 0);
		hash = 53 * hash + (this.dob != null ? this.dob.hashCode() : 0);
		return hash;
	}
	
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setPdateofbirth(String pdateofbirth) {
		this.pdateofbirth = pdateofbirth;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
}
