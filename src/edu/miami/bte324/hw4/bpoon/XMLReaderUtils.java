package edu.miami.bte324.hw4.bpoon;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * @author Barbara
 *
 */
public class XMLReaderUtils {

	public static String readCharacters(XMLEventReader eventReader, String elementName) throws XMLStreamException {
		XMLEvent firstEvent = eventReader.nextEvent(); // gets the next event
		// first make sure that the current event is the start element of name
		if (!firstEvent.isStartElement()) {
			throw new IllegalStateException("Attempting to read a " + elementName + " but not a start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals(elementName)) {
			throw new IllegalStateException("Attempting to read a " + elementName + " at the wrong start element: found " + firstEvent.asStartElement().getName());
		}
		String chars = eventReader.nextEvent().asCharacters().getData();
		return chars;
	}

	public static Date readDate(XMLEventReader eventReader, String elementName, String dateFormat) throws XMLStreamException {
		XMLEvent firstEvent = eventReader.nextEvent(); // gets the next event
		// first make sure that the current event is the start element of name
		Date pdob = null;
		if (!firstEvent.isStartElement()) {
			throw new IllegalStateException("Attempting to read a " + elementName + " but not a start element: found event of type " + firstEvent.getEventType());
		}
		else if (!firstEvent.asStartElement().getName().getLocalPart().equals(elementName)) {
			throw new IllegalStateException("Attempting to read a " + elementName + " at the wrong start element: found " + firstEvent.asStartElement().getName());
		}
		String dateStr = eventReader.nextEvent().asCharacters().getData();
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			pdob = (Date)df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return pdob;
	}


}
