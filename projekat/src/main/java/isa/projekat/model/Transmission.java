package isa.projekat.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Transmission {
	MANUELNI("Manuelni"), AUTOMATSKI("Automatski");

	private Transmission(String s) {
		value = s;
	}

	private String value;

	@JsonValue
	@Override
	public String toString() {
		return value;
	}

	public static Transmission getValue(String s) {
		if(s.equals("Manuelni"))
			return Transmission.MANUELNI;
		return AUTOMATSKI;
	}

}
