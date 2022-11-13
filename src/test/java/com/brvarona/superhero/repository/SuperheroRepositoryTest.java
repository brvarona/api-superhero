package com.brvarona.superhero.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.brvarona.superhero.model.Superhero;

@SpringBootTest
@Sql("/data-test.sql")
class SuperheroRepositoryTest {

	@Autowired
	SuperheroRepository superheroRepository;
	

	@Test
	void findByNullNameContainingTest() {		
				
		List<Superhero> result = superheroRepository.findByNameContaining(null);

		assertEquals(8, result.size());
	}

	@Test
	void findByNameContainingTest() {

		List<Superhero> result = superheroRepository.findByNameContaining("man");

		assertEquals(4, result.size());
	}


}
