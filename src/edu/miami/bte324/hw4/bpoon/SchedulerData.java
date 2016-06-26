package edu.miami.bte324.hw4.bpoon;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Barbara
 *
 */
public class SchedulerData {
	
//	=====================================================================
//	Declare and initialize variables
//	=====================================================================
//	Initialize the three lists to empty lists.
	List<Patient> patientList = new ArrayList<Patient>();
	List<Doctor> doctorList = new ArrayList<Doctor>();
//	ArrayList<Visit<Patient,Doctor>> visitList = new ArrayList<Visit<Patient,Doctor>>();
	List<Visit<Integer, Integer>> visitList = new ArrayList<Visit<Integer,Integer>>();
	
	Patient p;
	
//	Constructor: takes the patient list, doctor list, and visit list as arguments.
//	I may have to change this later to fit the rest of the projects
//	SchedulerData(ArrayList<Patient> plist, ArrayList<Doctor> dlist, ArrayList<Visit<Patient,Doctor>> vlist){
//		patientList = plist;
//		doctorList = dlist;
//		visitList = vlist;
//	}

	SchedulerData(List<Patient> plist, List<Doctor> dlist, List<Visit<Integer, Integer>> vlist){
		patientList = plist;
		doctorList = dlist;
		visitList = vlist;
	}

//	=====================================================================
//	Getters to retrieve lists
//	=====================================================================
	public List<Patient> getPatientList() {
		return patientList;
	}

	public List<Doctor> getDoctorList() {
		return doctorList;
	}

//	public ArrayList<Visit<Patient,Doctor>> getVisitList() {
//		return visitList;
//	}
	
	public List<Visit<Integer, Integer>> getVisitList() {
		return visitList;
	}
//	=====================================================================
//	Methods to add elements to lists
//	=====================================================================
	void addPatient(Patient p){
		patientList.add(p);
	}
	
	void addDoctor(Doctor d) {
		doctorList.add(d);
	}
	
//	void addVisit(Visit<Patient,Doctor> v) {
//		visitList.add(v);
//	}
	void addVisit(Visit<Integer, Integer> v) {
		visitList.add(v);
	}
}