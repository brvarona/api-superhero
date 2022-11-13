package com.brvarona.superhero.service;

import java.util.List;

import com.brvarona.superhero.model.Superhero;
import com.brvarona.superhero.payload.SuperheroRequest;

public interface SuperheroService {

	List<Superhero> getAllSuperheros(String name);

	Superhero getSuperhero(Long id);

	Superhero updateSuperhero(Long id, SuperheroRequest superhero);

	void deleteSuperhero(Long id);

}
