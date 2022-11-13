package com.brvarona.superhero.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brvarona.superhero.exception.ResourceNotFoundException;
import com.brvarona.superhero.model.Superhero;
import com.brvarona.superhero.payload.SuperheroRequest;
import com.brvarona.superhero.repository.SuperheroRepository;
import com.brvarona.superhero.service.SuperheroService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SuperheroServiceImpl implements SuperheroService {

	private static final String SUPERHERO_STR = "Superhero";
	
	@Autowired
	SuperheroRepository superheroRepository;

	public List<Superhero> getAllSuperheros(String name) {
		log.info("Service: getAllSuperheros");			
		return superheroRepository.findByNameContaining(name);						
	}

	public Superhero getSuperhero(Long id) {
		log.info("Service: getSuperhero, id: {}", id);
		return superheroRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(SUPERHERO_STR, "ID", id));
	}

	public Superhero updateSuperhero(Long id, SuperheroRequest superheroRequest) {
		var superhero = superheroRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(SUPERHERO_STR, "ID", id));

		Optional.ofNullable(superheroRequest.getName()).ifPresent(superhero::setName);
		Optional.ofNullable(superheroRequest.getPower()).ifPresent(superhero::setPower);

		return superheroRepository.save(superhero);
	}

	public void deleteSuperhero(Long id) {
		log.info("Service: deleteSuperhero, id: {}", id);
		var superhero = superheroRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(SUPERHERO_STR, "ID", id));

		superheroRepository.delete(superhero);
	}

}
