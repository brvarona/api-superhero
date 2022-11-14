package com.brvarona.superhero.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.brvarona.superhero.payload.SuperheroRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Sql("/data-test.sql")
@AutoConfigureMockMvc
class SuperheroControllerITTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getAllSuperherosTest_200_Ok() throws Exception {
		mockMvc.perform(get("/api/v1/superheros")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(8)));
	}

	@Test
	void getAllSuperheroesWithFilter_200_Ok() throws Exception {
		mockMvc.perform(get("/api/v1/superheros?name=man")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)));
	}

	@Test
	void getSuperheroById_200_Ok() throws Exception {
		mockMvc.perform(get("/api/v1/superheros/3")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", is(3)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Ant man")));
	}

	@WithMockUser("spring")
	@Test
	void updateSuperheroTest_200_Ok() throws Exception {
		SuperheroRequest superheroRequest = new SuperheroRequest("Superman", "power");

		mockMvc.perform(put("/api/v1/superheros/{id}", 6L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(superheroRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(6)))
				.andExpect(jsonPath("$.name", is("Superman")))
				.andExpect(jsonPath("$.power", is("power")));
	}
	
	@WithMockUser("spring")
	@Test
	void deleteSuperheroTest_200_Ok() throws Exception {
		mockMvc.perform(delete("/api/v1/superheros/{id}", 1L))
				.andExpect(status().isNoContent());
	}

}
