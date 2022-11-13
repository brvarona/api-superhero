package com.brvarona.superhero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brvarona.superhero.model.Superhero;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Long> {

	List<Superhero> findByNameContaining(String name);

}