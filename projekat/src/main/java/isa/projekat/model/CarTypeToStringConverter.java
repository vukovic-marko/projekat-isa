package isa.projekat.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CarTypeToStringConverter implements AttributeConverter<CarType, String> {
 
    @Override
    public String convertToDatabaseColumn(CarType c) {
        return c.toString();
    }
 
    @Override
    public CarType convertToEntityAttribute(String s) {
        return CarType.getValue(s);
    }


}