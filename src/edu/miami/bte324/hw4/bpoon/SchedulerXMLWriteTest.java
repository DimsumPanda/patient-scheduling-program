package edu.miami.bte324.hw4.bpoon;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;

/**
 * @author Barbara
 *
 */
public class SchedulerXMLWriteTest {
	private static String NAMESPACE = "http://Namespace";
	// just a namespace, make it up
	private final static String SCHEMA_INSTANCE_PREFIX = "xsi";
	private final static String SCHEMA_INSTANCE_NS = "http://www.w3.org/2001/XMLSchema-instance";
	private final static String SCHEMA_LOCATION_ATTRNAME = "schemaLocation";
	private final static String SCHEMA_FILE_NAME = "scheduling.xsd";
	
//	===============================================
//	Function: writeName
//	===============================================	
	public static void writeName(XMLEventFactory eventFactory, XMLEventWriter eventWriter, 
			String firstName, String lastName,  int level) throws XMLStreamException {
		// first, write as many tabs as levels needed
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		// start element
		eventWriter.add(eventFactory.createStartElement("", "", "name"));
		eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
		// first name
		XMLWriterUtils.writeNode(eventFactory, eventWriter, "firstName", firstName, level + 1);
		// last name
		XMLWriterUtils.writeNode(eventFactory, eventWriter, "lastName", lastName, level + 1);
		// end element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level)); //also indent it
		eventWriter.add(eventFactory.createEndElement("", "", "name"));
		eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
	}
//	===============================================
//	Function: writeData
//	===============================================	
	public static void writeData(XMLEventFactory eventFactory, XMLEventWriter eventWriter, 
			Date dob, String ssn,  int level) throws XMLStreamException {
		// first, write as many tabs as levels needed
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		// start element
		eventWriter.add(eventFactory.createStartElement("", "", "data"));
		eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
		// first name
		XMLWriterUtils.writeDate(eventFactory, eventWriter, "dob", dob, level + 1);
		// last name
		XMLWriterUtils.writeNode(eventFactory, eventWriter, "SSN", ssn, level + 1);
		// end element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level)); //also indent it
		eventWriter.add(eventFactory.createEndElement("", "", "data"));
		eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
	}
//	===============================================
//	Function: writePatient
//	===============================================
	public static void writePatient(XMLEventFactory eventFactory, XMLEventWriter 
			eventWriter, Patient e, int level) throws XMLStreamException{
		// writes a single patient through to the XML event writer
		// create the patient start element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		StartElement patientStart = eventFactory.createStartElement("", "", "patient");
		eventWriter.add(patientStart);
		// create the id attribute
		// note the use of Integer.toString to get a string representation
		Attribute patientId = eventFactory.createAttribute("patientId", Integer.toString(e.getPatientId()));
		eventWriter.add(patientId);
		eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
		// now create the nested elements
		writeName(eventFactory, eventWriter, e.getfname(), e.getlname(), level + 1); //call writeName, do the level thing and add 1.
		writeData(eventFactory, eventWriter, e.getDOB(), e.getSSN(), level + 1);
		XMLWriterUtils.writeNode(eventFactory, eventWriter, "active", e.getActive(), level + 1);
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		EndElement patientEnd = eventFactory.createEndElement("", "", "patient");
		eventWriter.add(patientEnd);
		
	} // end of writePatient
	
//	===============================================
//	Function: writeDoctor
//	===============================================
	public static void writeDoctor(XMLEventFactory eventFactory, XMLEventWriter 
			eventWriter, Doctor e, int level) throws XMLStreamException{
		// writes a single doctor through to the XML event writer
		// create the doctor start element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		StartElement doctorStart = eventFactory.createStartElement("", "", "doctor");
		eventWriter.add(doctorStart);
		// create the id attribute
		// note the use of Integer.toString to get a string representation
		Attribute doctorId = eventFactory.createAttribute("doctorId", Integer.toString(e.getDoctorId()));
		eventWriter.add(doctorId);
		eventWriter.add(eventFactory.createIgnorableSpace("\n")); // line feed for readability
		// now create the nested elements
		writeName(eventFactory, eventWriter, e.getfname(), e.getlname(), level + 1); //call writeName, do the level thing and add 1.
		writeData(eventFactory, eventWriter, e.getDOB(), e.getSSN(), level + 1);
		XMLWriterUtils.writeSpecialty(eventFactory, eventWriter, "specialty", e.getSpecialty(), level + 1);
		XMLWriterUtils.writeNode(eventFactory, eventWriter, "active", e.getActive(), level + 1);
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		EndElement doctorEnd = eventFactory.createEndElement("", "", "doctor");
		eventWriter.add(doctorEnd);
		
	} // end of writeDoctor
	
//	===============================================
//	Function: writeVisit
//	===============================================
	public static void writeVisit(XMLEventFactory eventFactory, XMLEventWriter 
			eventWriter, Visit<Integer,Integer> e, int level) throws XMLStreamException{
		// writes a single doctor through to the XML event writer
		// create the doctor start element
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		StartElement visitStart = eventFactory.createStartElement("", "", "visit");
		eventWriter.add(visitStart);
		// create the id attribute
		// note the use of Integer.toString to get a string representation
		Attribute patientId = eventFactory.createAttribute("patientId", Integer.toString(e.getVisitor()));
		eventWriter.add(patientId);
		Attribute doctorId = eventFactory.createAttribute("doctorId", Integer.toString(e.getHost()));
		eventWriter.add(doctorId);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = df.format(e.getDate().getTime());
		Attribute visitDate = eventFactory.createAttribute("visitDate", dateStr);
		eventWriter.add(visitDate);		
		eventWriter.add(XMLWriterUtils.getIndentation(eventFactory, level));
		EndElement visitEnd = eventFactory.createEndElement("", "", "visit");
		eventWriter.add(visitEnd);
		
	} // end of writeVisit
	
//	===============================================
//	Function: writeScheduler
//	===============================================
	public static void writeScheduler(String outFile, SchedulerData scheduler) throws 
	XMLStreamException, IOException {
		// Create a XMLOutputFactory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		// Create XMLEventWriter
		Path outputFilePath = Paths.get(outFile);
		Writer outputFile = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8);
		XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(outputFile);
		// Create an XMLEventFactory
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		// Create and write Start Tag
		StartDocument startDocument = eventFactory.createStartDocument("UTF-8", "1.0");
		eventWriter.add(startDocument);
		// put a linefeed for readability
		eventWriter.add(eventFactory.createIgnorableSpace("\n"));
		// create the root element
		StartElement root = eventFactory.createStartElement("", "", "schedulerList");
		eventWriter.add(root);
		eventWriter.setDefaultNamespace(NAMESPACE); // set the default namespace for the root before add it
		// add any other namespaces to the root
		eventWriter.add(eventFactory.createNamespace(NAMESPACE));
		eventWriter.add(eventFactory.createNamespace(SCHEMA_INSTANCE_PREFIX, 
				SCHEMA_INSTANCE_NS));
		// add the schema attributes to the root element
		String schemaLocationArg = NAMESPACE + " " + SCHEMA_FILE_NAME;
		eventWriter.add(eventFactory.createAttribute(SCHEMA_INSTANCE_PREFIX, 
				SCHEMA_INSTANCE_NS, SCHEMA_LOCATION_ATTRNAME, schemaLocationArg));
		// iterate over the list of students and create an element for each
		for (Patient p: scheduler.patientList){
			writePatient(eventFactory, eventWriter, p, 1); 
			// write the patient with one level of indentation
			eventWriter.add(eventFactory.createIgnorableSpace("\n"));
			// don't forget this, there's a line feed that you needed
		}
		for (Doctor d: scheduler.doctorList){
			writeDoctor(eventFactory, eventWriter, d, 1); 
			// write the doctor with one level of indentation
			eventWriter.add(eventFactory.createIgnorableSpace("\n"));
			// don't forget this, there's a line feed that you needed
		}
		for (Visit<Integer,Integer> v: scheduler.visitList){
			writeVisit(eventFactory, eventWriter, v, 1);
			// write the visit with one level of indentation
			eventWriter.add(eventFactory.createIgnorableSpace("\n"));
			// don't forget this, there's a line feed that you needed
		}
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}
}
