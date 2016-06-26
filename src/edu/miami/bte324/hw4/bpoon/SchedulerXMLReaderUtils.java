package edu.miami.bte324.hw4.bpoon;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


//This class will hold utility methods to read the XML data
/**
 * @author Barbara
 *
 */
public class SchedulerXMLReaderUtils {
	//	Auxillary methods to include in this class
	

//  =====================================================================================	
//	Create Method: readPatient, Patient class
//	=====================================================================================

	public static Patient readPatient(XMLEventReader eventReader) throws XMLStreamException {
		
		XMLEvent firstEvent = eventReader.nextEvent(); //gets the next event
		//	first make sure that the current event is the start element of name
		if (!firstEvent.isStartElement()) {
			throw new IllegalStateException("Attempting to read a patient but not a "
					+ "start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals("patient")) {
			throw new IllegalStateException("Attempting to read a patient at the wrong "
					+ "start element: found " + firstEvent.asStartElement().getName());
		}
		
		// now we read the patient
		// first, read the attributes
		int id = 0;
		@SuppressWarnings("unchecked") // getAttributes() guarantees type
		Iterator<Attribute> attributes = firstEvent.asStartElement().getAttributes();
		while (attributes.hasNext()) {
			Attribute attribute = attributes.next();
			if (attribute.getName().getLocalPart().equals("patientId")) {
				id = Integer.valueOf(attribute.getValue()); // we know it is an integer from the schema
			}
			else {
				System.err.println("Found unknown attribute, ignoring; found: "
						+ attribute.getName());
			}
		}
		// now we read the next events until we find the end event
		Patient patient = null;
		String lname = null;
		String fname = null;
		String ssn = null;
		Date dob = null;
//		String dateFormat = "mm/dd/yyyy";
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		String active = null;
		boolean finished = false;
		while(!finished) {
			XMLEvent event = eventReader.peek(); // peek to have the event reader remain before the next start element
			// check the start elements for the nested elements inside the Patient
			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
//				===============================================
//				Name Element
//				===============================================
				if (startElement.getName().getLocalPart().equals("name")) {
					XMLEvent firstEventName = eventReader.nextEvent(); //gets the next event
					// first make sure that the current event is the start element of name
					if (!firstEventName.isStartElement()){
						throw new IllegalStateException("Attempting to read a name but not a start element: found event of type "
								+ firstEventName.getEventType());
					}
					else if (!firstEventName.asStartElement().getName().getLocalPart().equals("name")){
						throw new IllegalStateException("Attempting to read a name at the wrong start element: found "
								+ firstEventName.asStartElement().getName());
					}
					boolean finishedName = false;
					while(!finishedName) {
						XMLEvent eventName = eventReader.nextEvent();
						// check the start elements for the nested elements for the nested elements inside name
						if (eventName.isStartElement()) {
							StartElement startElementName = eventName.asStartElement();
							if (startElementName.getName().getLocalPart().equals("firstName")){
								eventName = eventReader.nextEvent();
								fname = eventName.asCharacters().getData();
							}
							else if (startElementName.getName().getLocalPart().equals("lastName")){
								eventName = eventReader.nextEvent();
								lname = eventName.asCharacters().getData();
							}
							else {
								System.err.println("Unrecognized element, ignoring: " + startElementName.getName() + "line 103");
							}
						}
						//check the end elements to find where the name element is closed
						else if (eventName.isEndElement()) {
							EndElement endElementName = eventName.asEndElement();
							// when the end element is the name element, finishedName equals true.
							if (endElementName.getName().getLocalPart().equals("name")){
								// Schema makes these required, so they must exist
								// would be a good practice to check for existence anyways
								finishedName = true;
							}
						}
						else {
							// ignore other events, such as character events with line feeds and tabs
						}
					}
				} // end of Name element
//				==============================================================
//				Data Element
//				==============================================================
				else if (startElement.getName().getLocalPart().equals("data")) {
					XMLEvent firstEventData = eventReader.nextEvent(); //gets the next event
					// first make sure that the current event is the start element of data
					if (!firstEventData.isStartElement()){
						throw new IllegalStateException("Attempting to read data but not a start element: found event of type "
								+ firstEventData.getEventType());
					}
					else if (!firstEventData.asStartElement().getName().getLocalPart().equals("data")){
						throw new IllegalStateException("Attempting to read data at the wrong start element: found "
								+ firstEventData.asStartElement().getName());
					}
					boolean finishedData = false;
					while(!finishedData) {
						XMLEvent eventData = eventReader.nextEvent();
						// check the start elements for the nested elements for the nested elements inside data
						if (eventData.isStartElement()) {
							StartElement startElementData = eventData.asStartElement();
							if (startElementData.getName().getLocalPart().equals("dob")){
								eventData = eventReader.nextEvent();
								String dateStr = eventData.asCharacters().getData();
								DateFormat df = new SimpleDateFormat(dateFormat);
								try {
									dob = (Date)df.parse(dateStr);
								} catch (ParseException e) {
									e.printStackTrace();
								}

							}
							else if (startElementData.getName().getLocalPart().equals("SSN")){
								eventData = eventReader.nextEvent();
								ssn = eventData.asCharacters().getData();
							}
							else {
								System.err.println("Unrecognized element, ignoring: " + startElementData.getName() + "line 157");
							}
						}
						//check the end elements to find where the name element is closed
						else if (eventData.isEndElement()) {
							EndElement endElementData = eventData.asEndElement();
							// when the end element is the name element, finishedName equals true.
							if (endElementData.getName().getLocalPart().equals("data")){
								// Schema makes these required, so they must exist
								// would be a good practice to check for existence anyways
								finishedData = true;
							}
						}
						else {
							// ignore other events, such as character events with line feeds and tabs
						}
					}
				} // end of Data element
//				==============================================================
//				Active Element
//				==============================================================
				else if (startElement.getName().getLocalPart().equals("active")) {
					active = XMLReaderUtils.readCharacters(eventReader, "active");
				}
				else {
					System.err.println("Unrecognized element, ignoring: " + startElement.getName()  + "line 176");
					event = eventReader.nextEvent(); // skip this event and read the next
				}
			}
				// check the end elements to find where the name element is closed
				else if (event.isEndElement()) {
					event = eventReader.nextEvent(); //retrieve the event
					EndElement endElement = event.asEndElement();
					// when the end element is the name element, create the name return object;
					if (endElement.getName().getLocalPart().equals("patient")){
						// Schema makes these required, so the must exist
						// would be a good practice to check for existence anyways
						patient = new PatientImpl(id, lname, fname, ssn, dob, active);
						finished = true;
					}
				}
				else {
					event = eventReader.nextEvent();
				}

		}
		return patient;
	} //end of readPatient
	
//  =====================================================================================	
//	Create Method: readDoctor, Doctor class
//	=====================================================================================
	
	public static Doctor readDoctor(XMLEventReader eventReader) throws XMLStreamException {
		XMLEvent firstEvent = eventReader.nextEvent(); //gets the next event
		//	first make sure that the current event is the start element of name
		if (!firstEvent.isStartElement()) {
			throw new IllegalStateException("Attempting to read a doctor but not a "
					+ "start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals("doctor")) {
			throw new IllegalStateException("Attempting to read a doctor at the wrong "
					+ "start element: found " + firstEvent.asStartElement().getName());
		}
		
		// now we read the doctor
		// first, read the attributes
		int id = 0;
		@SuppressWarnings("unchecked") // getAttributes() guarantees type
		Iterator<Attribute> attributes = firstEvent.asStartElement().getAttributes();
		while (attributes.hasNext()) {
			Attribute attribute = attributes.next();
			if (attribute.getName().getLocalPart().equals("doctorId")) {
				id = Integer.valueOf(attribute.getValue()); // we know it is an integer from the schema
			}
			else {
				System.err.println("Found unknown attribute, ignoring; found: "
						+ attribute.getName());
			}
		}
		
		// now we read the next events until we find the end event
		Doctor doctor = null;
		String lname = null;
		String fname = null;
		String ssn = null;
		Date dob = null;
//		String dateFormat = "mm/dd/yyyy";
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		MedicalSpecialty specialty = null;
		String specialtyString = null;
		String active = null;
		boolean finished = false;
		while(!finished) {
			XMLEvent event = eventReader.peek(); // peek to have the event reader remain before the next start element
			// check the start elements for the nested elements inside the Patient
			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
//				==================================================
//				Name Element
//				==================================================
				if (startElement.getName().getLocalPart().equals("name")) {
					XMLEvent firstEventName = eventReader.nextEvent(); //gets the next event
					// first make sure that the current event is the start element of name
					if (!firstEventName.isStartElement()){
						throw new IllegalStateException("Attempting to read a name but not a start element: found event of type "
								+ firstEventName.getEventType());
					}
					else if (!firstEventName.asStartElement().getName().getLocalPart().equals("name")){
						throw new IllegalStateException("Attempting to read a name at the wrong start element: found "
								+ firstEventName.asStartElement().getName());
					}
					boolean finishedName = false;
					while(!finishedName) {
						XMLEvent eventName = eventReader.nextEvent();
						// check the start elements for the nested elements for the nested elements inside name
						if (eventName.isStartElement()) {
							StartElement startElementName = eventName.asStartElement();
							if (startElementName.getName().getLocalPart().equals("firstName")){
								eventName = eventReader.nextEvent();
								fname = eventName.asCharacters().getData();
							}
							else if (startElementName.getName().getLocalPart().equals("lastName")){
								eventName = eventReader.nextEvent();
								lname = eventName.asCharacters().getData();
							}
							else {
								System.err.println("Unrecognized element, ignoring: " + startElementName.getName() + "line 274");
							}
						}
						//check the end elements to find where the name element is closed
						else if (eventName.isEndElement()) {
							EndElement endElementName = eventName.asEndElement();
							// when the end element is the name element, finishedName equals true.
							if (endElementName.getName().getLocalPart().equals("name")){
								// Schema makes these required, so they must exist
								// would be a good practice to check for existence anyways
								finishedName = true;
							}
						}
						else {
							// ignore other events, such as character events with line feeds and tabs
						}
					}
				} // end of Name element
//				==============================================================
//				Data Element
//				==============================================================
				if (startElement.getName().getLocalPart().equals("data")) {
					XMLEvent firstEventData = eventReader.nextEvent(); //gets the next event
					// first make sure that the current event is the start element of data
					if (!firstEventData.isStartElement()){
						throw new IllegalStateException("Attempting to read data but not a start element: found event of type "
								+ firstEventData.getEventType());
					}
					else if (!firstEventData.asStartElement().getName().getLocalPart().equals("data")){
						throw new IllegalStateException("Attempting to read data at the wrong start element: found "
								+ firstEventData.asStartElement().getName());
					}
					boolean finishedData = false;
					while(!finishedData) {
						XMLEvent eventData = eventReader.nextEvent();
						// check the start elements for the nested elements for the nested elements inside data
						if (eventData.isStartElement()) {
							StartElement startElementData = eventData.asStartElement();
							if (startElementData.getName().getLocalPart().equals("dob")){
								eventData = eventReader.nextEvent();
								String dateStr = eventData.asCharacters().getData();
								DateFormat df = new SimpleDateFormat(dateFormat);
								try {
									dob = (Date)df.parse(dateStr);
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
							else if (startElementData.getName().getLocalPart().equals("SSN")){
								eventData = eventReader.nextEvent();
								ssn = eventData.asCharacters().getData();
							}
							else {
								System.err.println("Unrecognized element, ignoring: " + startElementData.getName() + "line 328");
							}
						}
						//check the end elements to find where the name element is closed
						else if (eventData.isEndElement()) {
							EndElement endElementData = eventData.asEndElement();
							// when the end element is the name element, finishedName equals true.
							if (endElementData.getName().getLocalPart().equals("data")){
								// Schema makes these required, so they must exist
								// would be a good practice to check for existence anyways
								finishedData = true;
							}
						}
						else {
							// ignore other events, such as character events with line feeds and tabs
						}
					}
				} // end of Data element
				else if (startElement.getName().getLocalPart().equals("specialty")){
					
//					specialtyString = XMLReaderUtils.readCharacters(eventReader, "specialty");
					specialtyString = XMLReaderUtils.readCharacters(eventReader, "specialty");
					specialty = MedicalSpecialty.valueOf(specialtyString);
					//needs error checking to ensure that the
					// MedicalSpecialty exists
				}
//				==============================================================
//				Active Element
//				==============================================================
				else if (startElement.getName().getLocalPart().equals("active")) {
					active = XMLReaderUtils.readCharacters(eventReader, "active");
				}
				else {
//					System.err.println("Unrecognized element, ignoring: " + startElement.getName()  + " line 374");
					event = eventReader.nextEvent(); // skip this event and read the next
				}
			}
				// check the end elements to find where the name element is closed
				else if (event.isEndElement()) {
					event = eventReader.nextEvent(); //retrieve the event
					EndElement endElement = event.asEndElement();
					// when the end element is the name element, create the name return object;
					if (endElement.getName().getLocalPart().equals("doctor")){
						// Schema makes these required, so the must exist
						// would be a good practice to check for existence anyways
						doctor = new DoctorImpl(id, lname, fname, ssn, dob, specialty, active);
						finished = true;
					}
				}
				else {
					event = eventReader.nextEvent();
				}

		}
		return doctor;
	} //end of readDoctor
	
//  =====================================================================================	
//	Create Method: readVisit, Visit class
//	=====================================================================================
	
	public static Visit<Integer, Integer> readVisit(XMLEventReader eventReader) throws XMLStreamException {
		XMLEvent firstEvent = eventReader.nextEvent(); // gets the next event
		// first make sure that the current event is the start element of name
		if (!firstEvent.isStartElement()){
			throw new IllegalStateException("Attempting to read a visit but not a "
					+ "start elemenet: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals("visit")) {
			throw new IllegalStateException("Attempting to read a visit at the wrong "
					+ "start element: found " + firstEvent.asStartElement().getName());
		}
		// now we read the visit
		// first, read the attributes
		Visit <Integer, Integer> visit = null;
		int patientId = 0;
		int doctorId = 0;
		Date visitDate = null;
//		String dateFormat = "MM/dd/yyyy";
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		@SuppressWarnings("unchecked")	//	getAttributes() guarantees type
		Iterator<Attribute> attributes = firstEvent.asStartElement().getAttributes();
		while (attributes.hasNext()) {
			Attribute attribute = attributes.next();
			if(attribute.getName().getLocalPart().equals("patientId")) {
				patientId = Integer.valueOf(attribute.getValue());	//	we know it is an integer from the schema
			}
			else if (attribute.getName().getLocalPart().equals("doctorId")) {
				doctorId = Integer.valueOf(attribute.getValue());
			}
			else if (attribute.getName().getLocalPart().equals("visitDate")){

					String dateStr = attribute.getValue();
					DateFormat df = new SimpleDateFormat(dateFormat);
					try {
						visitDate = (Date)df.parse(dateStr);
					} catch (ParseException e) {
						e.printStackTrace();
					}

			} // end of visitDate
			else {
				System.err.println("Found unknown attribute, ignoring; found: " + attribute.getName());
			}
		}
			boolean finished = false;
			while(!finished){
				XMLEvent event = eventReader.peek();
				if (event.isEndElement()) {
					event = eventReader.nextEvent();
					EndElement endElement = event.asEndElement();
					if (endElement.getName().getLocalPart().equals("visit")){
						visit = new VisitImpl<Integer,Integer>(patientId, doctorId, visitDate);
						finished = true;
					}
				}
				else {
					event = eventReader.nextEvent();
				}
		}
		return visit;
	} // end readVisit
	
//	a. define a static method called readScheduliingXML
//	1. takes a file name as a parameter
//	2. Opens for input a file as specified by the file name using a BufferedReader,
//		and enveloping it with an XMLEventReader. If the file does not exist, throws a FileNotFoundException
	
	public static SchedulerData readSchedulingXML(String xmlFile) throws XMLStreamException, IOException {
		
//		List<SchedulerData> scheduleList = new ArrayList<>();
		List<Patient> patientList = new ArrayList<Patient>();
		List<Doctor> doctorList = new ArrayList<Doctor>();
		List<Visit<Integer, Integer>> visitList = new ArrayList<Visit<Integer,Integer>>();
		SchedulerData schedule = new SchedulerData(patientList, doctorList, visitList);
// 	3. read the XML contents of the XML file, populating a SchedulerData object with all patients, objects, and visits
//		encountered in the XML file (make sure you take the ids from the XML file, instead of auto-generating them)
		//Create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		//Set up a new eventReader
		Path xmlFilePath = Paths.get(xmlFile);
		Reader in = Files.newBufferedReader(xmlFilePath, StandardCharsets.UTF_8);
		XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
		// Read the XML document
		while(eventReader.hasNext()){
			
			// peek the next event
			// use peek so that we can actually read and check the next start element as it happens
			XMLEvent event = eventReader.peek();
			if(event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				if (startElement.getName().getLocalPart() == ("schedulerList")) {
					// just read the next event, which should be a patient
					event = eventReader.nextEvent(); // skip this event
				}
				// if we are at the top element for a patient
				
				else if (startElement.getName().getLocalPart() == ("patient")) {
					Patient p = readPatient(eventReader);
					schedule.patientList.add(p);
				}
				else if (startElement.getName().getLocalPart() == ("doctor")) {
					Doctor d = readDoctor(eventReader);
					schedule.doctorList.add(d);
				}
				else if (startElement.getName().getLocalPart() == ("visit")) {
					Visit<Integer, Integer> v = readVisit(eventReader);
					schedule.visitList.add(v);
				}
				else {
					System.err.println("Unrecognized element, ignoring: " + startElement.getName()  + "line 487");
					event = eventReader.nextEvent(); // skip this event and read the next
				}
			}
			else {
				event = eventReader.nextEvent(); // skip this event and read the next
			}
		}

		eventReader.close();
		
//	4. Returns the SchedulerData object
		return schedule;
	}
}
