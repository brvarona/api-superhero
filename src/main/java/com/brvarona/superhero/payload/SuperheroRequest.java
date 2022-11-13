package com.brvarona.superhero.payload;

import lombok.Data;

@Data
public class SuperheroRequest {

	private String name;
	
	private String power;

	public SuperheroRequest(String name, String power) {
		super();
		this.name = name;
		this.power = power;
	}
}
