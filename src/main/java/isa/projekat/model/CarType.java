package isa.projekat.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CarType {
	KUPE("Kupe"), SUV("SUV"),LIMUZINA("Limuzina"), PIKAP("Pikap"),KARAVAN("Karavan"),HEČBEK("Hečbek");

	private CarType(String s) {
		value = s;
	}

	private String value;

	@JsonValue
	@Override
	public String toString() {
		return value;
	}

	public static CarType getValue(String s) {
		if(s.equals("Kupe"))
			return KUPE;
		if(s.equals("Limuzina"))
			return LIMUZINA;
		if(s.equals("Pikap"))
			return PIKAP;
		if(s.equals("Karavan"))
			return CarType.KARAVAN;
		if(s.equals("SUV"))
			return CarType.SUV;
		return HEČBEK;
		
	}

}
