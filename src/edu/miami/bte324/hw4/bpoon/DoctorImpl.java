package edu.miami.bte324.hw4.bpoon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


//The class is marked final, so that it cannot be modified.
/**
 * @author Barbara Poon
 *
 */
public final class DoctorImpl implements Doctor {

	int doctorId;
	String lname;
	String fname;
	String ssn;
	Date dob;
	int age;
	Date today = new Date();
	String today2 = null;
	String pdateofbirth = null;
	MedicalSpecialty specialty = null;
	String specialtyString;
	String active;
	
	//Constructor takes as args: full name, SSN, dob, specialty
//	DoctorImpl(String lname, String fname, String ssn, Date dob, String specialtyString){
//		this.lname = lname;
//		this.fname = fname;
//		this.ssn = ssn;
//		this.dob = dob;
//		this.specialtyString = specialtyString;
//		specialty = specialty.getFromString(specialtyString);
//	}
	//Constructor takes as args: full name, SSN, dob
//	DoctorImpl(int doctorId, String lname, String fname, String ssn, Date dob){
//		this.doctorId = doctorId;
//		this.fname = fname;
//		this.lname = lname;
//		this.ssn = ssn;
//		this.dob = dob;
//	}
	//Overload the constructor again. String Specialty
//	DoctorImpl(int doctorId, String lname, String fname, String ssn, Date dob, String specialtyString){
//		this.doctorId = doctorId;
//		this.lname = lname;
//		this.fname = fname;
//		this.ssn = ssn;
//		this.dob = dob;
//		this.specialtyString = specialtyString;
//		specialty = specialty.getFromString(specialtyString);
//		active = "active";
//	}
//	DoctorImpl(int doctorId, String lname, String fname, String ssn, Date dob, String specialtyString, String active){
//		this.doctorId = doctorId;
//		this.lname = lname;
//		this.fname = fname;
//		this.ssn = ssn;
//		this.dob = dob;
//		this.specialtyString = specialtyString;
//		specialty = specialty.getFromString(specialtyString);
//		this.active = active;
//	}
//	DoctorImpl(int doctorId, String lname, String fname, String ssn, Date dob, MedicalSpecialty specialty){
//		this.doctorId = doctorId;
//		this.lname = lname;
//		this.fname = fname;
//		this.ssn = ssn;
//		this.dob = dob;
//		this.specialty = specialty;
//		active = "active";
//	}
	DoctorImpl(int doctorId, String lname, String fname, String ssn, Date dob, MedicalSpecialty specialty, String active){
		this.doctorId = doctorId;
		this.lname = lname;
		this.fname = fname;
		this.ssn = ssn;
		this.dob = dob;
		this.specialty = specialty;
		this.active = active;
	}
	
	public void setInactive(){
		active = "inactive";
	}
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getDOB(){
		return dob;
	}
	public String getSSN(){
		return ssn;
	}
	@Override
	public int getDoctorId() {
		return doctorId;
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
	public MedicalSpecialty getSpecialty() {
		return specialty;
	}
	
	
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
		final DoctorImpl other = (DoctorImpl) obj;
		if ((this.getlname()!= null)&&(this.getfname()!= null)&&(this.getlname()!=null)&&(this.getSSN()!= null)
				&& (this.getDOB() != null) && (this.getSpecialty() != null)){
			return false;
		}
		if ((this.getDoctorId()==(other.getDoctorId()))&& (this.getlname().equals(other.getlname()))
				&& (this.getfname().equals(other.getfname()))&&(this.getSSN().equals(other.getSSN()))
				&& (this.getDOB().equals(other.getDOB())) && (this.getSpecialty().equals(other.getSpecialty())))
			return true;
		else
			return false;
	}
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + this.doctorId;
		hash = 53 * hash + (this.fname != null ? this.fname.hashCode() : 0);
		hash = 53 * hash + (this.lname != null ? this.lname.hashCode() : 0);
		hash = 53 * hash + (this.ssn != null ? this.ssn.hashCode() : 0);
		hash = 53 * hash + (this.dob != null ? this.dob.hashCode() : 0);
		hash = 53 * hash + (this.specialty != null ? this.specialty.hashCode():0);
		return hash;
	}
	@Override
	public int compareTo(Doctor o) {
		DoctorImpl other = (DoctorImpl) o;
		int i = this.lname.compareTo(other.lname);
		if (i != 0) return i;
		
	    i = this.fname.compareTo(other.fname);
	    if (i != 0) return i;
	    
	    i = this.dob.compareTo(other.dob);
	    if (i != 0) return i;
	    
	    i = this.ssn.compareTo(other.ssn);
	    if (i != 0) return i;
	    else return 0;
		
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
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
	public void setToday(Date today) {
		this.today = today;
	}
	public void setToday2(String today2) {
		this.today2 = today2;
	}
	public void setPdateofbirth(String pdateofbirth) {
		this.pdateofbirth = pdateofbirth;
	}
	public void setSpecialty(MedicalSpecialty specialty) {
		this.specialty = specialty;
	}
	public void setSpecialtyString(String specialtyString) {
		this.specialtyString = specialtyString;
	}
	
	

}
