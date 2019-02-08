package isa.projekat.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TransmissionConverter implements AttributeConverter<Transmission, String> {

	@Override
	public String convertToDatabaseColumn(Transmission t) {
		return t.toString();
	}

	@Override
	public Transmission convertToEntityAttribute(String s) {
		return Transmission.getValue(s);
	}

}
