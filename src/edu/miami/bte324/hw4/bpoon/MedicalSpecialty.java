package edu.miami.bte324.hw4.bpoon;

/**
 * @author Barbara Poon
 *
 */
public enum MedicalSpecialty {
	// They are all public, static, final. 
	GENERAL_MEDICINE,
	PEDIATRICS, 
	ONCOLOGY;
	
	
	
	public MedicalSpecialty getFromString(String x){
		if (x == MedicalSpecialty.GENERAL_MEDICINE.toString())
			return MedicalSpecialty.GENERAL_MEDICINE;
		
		if (x == MedicalSpecialty.PEDIATRICS.toString())
			return MedicalSpecialty.PEDIATRICS;
		
		if (x == MedicalSpecialty.ONCOLOGY.toString())
			return MedicalSpecialty.ONCOLOGY;
		else return null;
	}
}
