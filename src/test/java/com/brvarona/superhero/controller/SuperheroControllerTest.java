package com.brvarona.superhero.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.brvarona.superhero.exception.ResourceNotFoundException;
import com.brvarona.superhero.model.Superhero;
import com.brvarona.superhero.payload.SuperheroRequest;
import com.brvarona.superhero.service.SuperheroService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class SuperheroControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SuperheroService superheroService;
	
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
	void getAllSuperherosTest_200_Ok() throws Exception {
		
		when(superheroService.getAllSuperheros(any())).thenReturn(superheros);

		mockMvc.perform(get("/api/v1/superheros"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("Superman")))
				.andExpect(jsonPath("$[0].power", is("fly")))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("Spiderman")))
				.andExpect(jsonPath("$[1].power", is("clamber")));

		verify(superheroService, times(1)).getAllSuperheros(any());
		verifyNoMoreInteractions(superheroService);
	}

	@Test
	void getAllSuperherosTestWithNameContaining_200_Ok() throws Exception {

		when(superheroService.getAllSuperheros(anyString())).thenReturn(superheros);

		mockMvc.perform(get("/api/v1/superheros").param("name", "man"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("Superman")))
				.andExpect(jsonPath("$[0].power", is("fly")))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("Spiderman")))
				.andExpect(jsonPath("$[1].power", is("clamber")));

		verify(superheroService, times(1)).getAllSuperheros(anyString());
		verifyNoMoreInteractions(superheroService);
	}

	@Test
	void getSuperheroByIdTest_200_Ok() throws Exception {

		when(superheroService.getSuperhero(anyLong())).thenReturn(superheros.get(0));

		mockMvc.perform(get("/api/v1/superheros/{id}", 1))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Superman")))
				.andExpect(jsonPath("$.power", is("fly")));

		verify(superheroService, times(1)).getSuperhero(anyLong());
		verifyNoMoreInteractions(superheroService);
	}

	@Test
	void getSuperheroByIdTest_400_BadRequest() throws Exception {
		mockMvc.perform(get("/api/v1/superheros/{id}", "abc")).andExpect(status().isBadRequest());
	}

	@Test
	void getSuperheroByIdTest_404_NotFound() throws Exception {
		when(superheroService.getSuperhero(anyLong())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get("/api/v1/superheros/{id}", 1)).andExpect(status().isNotFound());

		verify(superheroService, times(1)).getSuperhero(anyLong());
		verifyNoMoreInteractions(superheroService);
	}

    @WithMockUser("spring")
	@Test
	void updateSuperheroTest_200_Ok() throws Exception {
		SuperheroRequest superheroRequest = new SuperheroRequest("Superman", "fuerza");

		when(superheroService.updateSuperhero(anyLong(), any(SuperheroRequest.class))).thenReturn(superheros.get(0));

		mockMvc.perform(put("/api/v1/superheros/{id}", superheros.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(superheroRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Superman")))
				.andExpect(jsonPath("$.power", is("fly")));

		verify(superheroService, times(1)).updateSuperhero(anyLong(), any(SuperheroRequest.class));
		verifyNoMoreInteractions(superheroService);
	}
    
   	@Test
   	void updateSuperheroTest_401_Unauthorized() throws Exception {
   		SuperheroRequest superheroRequest = new SuperheroRequest("Superman", "fuerza");

   		when(superheroService.updateSuperhero(anyLong(), any(SuperheroRequest.class))).thenReturn(superheros.get(0));

   		mockMvc.perform(put("/api/v1/superheros/{id}", superheros.get(0).getId())
   				.contentType(MediaType.APPLICATION_JSON)
   				.content(new ObjectMapper().writeValueAsString(superheroRequest)))
   				.andExpect(status().isUnauthorized());

   		verifyNoMoreInteractions(superheroService);
   	}

    @WithMockUser("spring")
	@Test
	void updateSuperheroTest_404_NotFound() throws Exception {
		SuperheroRequest superheroRequest = new SuperheroRequest("Superman", "fuerza");

		doThrow(ResourceNotFoundException.class).when(superheroService).updateSuperhero(anyLong(),
				any(SuperheroRequest.class));
		mockMvc.perform(put("/api/v1/superheros/{id}", superheros.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(superheroRequest)))
				.andExpect(status().isNotFound());

		verify(superheroService, times(1)).updateSuperhero(anyLong(), any(SuperheroRequest.class));
		verifyNoMoreInteractions(superheroService);
	}

    @WithMockUser("spring")
	@Test
	void deleteSuperheroTest_200_Ok() throws Exception {

		doNothing().when(superheroService).deleteSuperhero(anyLong());
		mockMvc.perform(delete("/api/v1/superheros/{id}", superheros.get(0).getId()))
				.andExpect(status().isNoContent());

		verify(superheroService, times(1)).deleteSuperhero(anyLong());
		verifyNoMoreInteractions(superheroService);
	}
    
   	@Test
   	void deleteSuperheroTest_401_Unauthorized() throws Exception {

   		doNothing().when(superheroService).deleteSuperhero(anyLong());
   		mockMvc.perform(delete("/api/v1/superheros/{id}", superheros.get(0).getId()))
   				.andExpect(status().isUnauthorized());

   		verifyNoMoreInteractions(superheroService);
   	}

    @WithMockUser("spring")
	@Test
	void deleteSuperheroTest_404_NotFound() throws Exception {

		doThrow(ResourceNotFoundException.class).when(superheroService).deleteSuperhero(anyLong());
		mockMvc.perform(delete("/api/v1/superheros/{id}", superheros.get(0).getId()))
				.andExpect(status().isNotFound());

		verify(superheroService, times(1)).deleteSuperhero(anyLong());
		verifyNoMoreInteractions(superheroService);
	}
	
	@Test
	void cleanCacheTest_200_Ok() throws Exception {
		mockMvc.perform(delete("/api/v1/superheros/cache"))
				.andExpect(status().isNoContent());

		verifyNoMoreInteractions(superheroService);
	}

}
