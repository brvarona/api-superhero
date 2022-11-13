package com.brvarona.superhero.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.brvarona.superhero.model.Superhero;
import com.brvarona.superhero.payload.SuperheroRequest;
import com.brvarona.superhero.repository.SuperheroRepository;

@SpringBootTest
class SuperheroServiceTest {

	@MockBean
	SuperheroRepository superheroRepository;

	@Autowired
	SuperheroService superheroService;
	
	private List<Superhero> superheros;

    @BeforeEach
    public void setUp() {
        var hero1 = new Superhero();
        hero1.setId(1L);
        hero1.setName("Superman");
        hero1.setPower("fly");
       
        var hero2 = new Superhero();
        hero2.setId(2L);
        hero2.setName("Spiderman");
        hero2.setPower("clamber");

        superheros = List.of(hero1, hero2);
    }

	@Test
	void getAllSuperherosTest() {		
		
		when(superheroRepository.findByNameContaining(any())).thenReturn(superheros);
		
		List<Superhero> result = superheroService.getAllSuperheros(null);

		assertEquals(2, result.size());
		assertThat(result.get(0).getId()).isEqualTo(1);
		assertThat(result.get(0).getName()).isEqualTo("Superman");
		assertThat(result.get(0).getPower()).isEqualTo("fly");
		assertThat(result.get(1).getId()).isEqualTo(2);
		assertThat(result.get(1).getName()).isEqualTo("Spiderman");
		assertThat(result.get(1).getPower()).isEqualTo("clamber");
		
		verify(superheroRepository, times(1)).findByNameContaining(any());
		verifyNoMoreInteractions(superheroRepository);
	}

	@Test
	void findSuperheroByIdTest() {

		when(superheroRepository.findById(anyLong())).thenReturn(Optional.of(superheros.get(0)));

		Superhero result = superheroService.getSuperhero(1L);
		assertNotNull(result);
		assertThat(result.getId()).isEqualTo(1);
		assertThat(result.getName()).isEqualTo("Superman");
		assertThat(result.getPower()).isEqualTo("fly");
		
		verify(superheroRepository, times(1)).findById(anyLong());
		verifyNoMoreInteractions(superheroRepository);
	}

	@Test
	void updateSuperheroTest() {
		SuperheroRequest superheroRequest = new SuperheroRequest("Superman", "fly");

		when(superheroRepository.findById(anyLong())).thenReturn(Optional.of(superheros.get(0)));
		when(superheroRepository.save(any(Superhero.class))).thenReturn(superheros.get(0));

		Superhero result = superheroService.updateSuperhero(1L, superheroRequest);
		assertNotNull(result);
		assertThat(result.getId()).isEqualTo(1);
		assertThat(result.getName()).isEqualTo("Superman");
		assertThat(result.getPower()).isEqualTo("fly");
		
		verify(superheroRepository, times(1)).findById(anyLong());
		verify(superheroRepository, times(1)).save(any(Superhero.class));
		verifyNoMoreInteractions(superheroRepository);
	}

	@Test
	void deleteSuperheroTest() {

		when(superheroRepository.findById(anyLong())).thenReturn(Optional.of(superheros.get(0)));
		doNothing().when(superheroRepository).delete(any(Superhero.class));
		
		superheroService.deleteSuperhero(1L);
		
		verify(superheroRepository, times(1)).findById(anyLong());
		verify(superheroRepository, times(1)).delete(any(Superhero.class));
		verifyNoMoreInteractions(superheroRepository);
	}

}
