package com.brvarona.superhero.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.brvarona.superhero.model.Superhero;
import com.brvarona.superhero.payload.SuperheroRequest;

public interface SuperheroController {

	ResponseEntity<List<Superhero>> getAllSuperheros(String filter);

	ResponseEntity<Superhero> getSuperhero(Long id);

	ResponseEntity<Superhero> updateSuperhero(Long id, SuperheroRequest superheroRequest);

	ResponseEntity<?> deleteSuperhero(Long id);

	ResponseEntity<?> cleanCache();
}
