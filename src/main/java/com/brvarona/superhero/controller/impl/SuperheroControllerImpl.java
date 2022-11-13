package com.brvarona.superhero.controller.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brvarona.superhero.controller.SuperheroController;
import com.brvarona.superhero.model.Superhero;
import com.brvarona.superhero.payload.SuperheroRequest;
import com.brvarona.superhero.service.SuperheroService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/superheros")
public class SuperheroControllerImpl implements SuperheroController {

	@Autowired
	SuperheroService superheroService;

	@GetMapping
	public ResponseEntity<List<Superhero>> getAllSuperheros(@RequestParam (value = "name", required = false) String name) {
		log.info("Getting all superheros by name: {}", name);
		
		return new ResponseEntity<>(superheroService.getAllSuperheros(name), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Superhero> getSuperhero(@PathVariable(value = "id") Long id) {
		log.info("Getting a superhero by id: {}", id);

		return new ResponseEntity<>(superheroService.getSuperhero(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Superhero> updateSuperhero(@PathVariable(value = "id") Long id,
			@Valid @RequestBody SuperheroRequest superheroRequest) {
		log.info("Updating a superhero by id: {}", id);

		return new ResponseEntity<>(superheroService.updateSuperhero(id, superheroRequest), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSuperhero(@PathVariable(value = "id") Long id) {
		log.info("Removing a superhero by id: {}", id);
		superheroService.deleteSuperhero(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/cache")
	public ResponseEntity<?> cleanCache() {
		//TODO: complete 
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
