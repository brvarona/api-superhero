package com.brvarona.superhero.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.brvarona.superhero.model.Superhero;
import com.brvarona.superhero.payload.SuperheroRequest;

import io.swagger.annotations.ApiOperation;

public interface SuperheroController {

	@ApiOperation(value = "Get all superheros", notes = "Returns a list with all the superheros")
	ResponseEntity<List<Superhero>> getAllSuperheros(String filter);

	@ApiOperation(value = "Get a superhero by id", notes = "Returns a superhero by id")
	ResponseEntity<Superhero> getSuperhero(Long id);

	@ApiOperation(value = "Update a superhero", notes = "Returns an updated superhero")
	ResponseEntity<Superhero> updateSuperhero(Long id, SuperheroRequest superheroRequest);

	@ApiOperation(value = "Remove a superhero", notes = "The selected superhero is removed")
	ResponseEntity<?> deleteSuperhero(Long id);

	@ApiOperation(value = "Clean superhero cache", notes = "The superhero's cache is cleaned" )
	ResponseEntity<?> cleanCache();
}
