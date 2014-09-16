package com.github.ginjaninja.bb.account.user;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.ginjaninja.bb.config.WebAppConfigurationAware;

public class UserControllerTest extends WebAppConfigurationAware {

	@Test
	public void testGet() throws Exception{
		MvcResult result = mockMvc.perform(get("/user/1"))
			.andDo(print())
			.andExpect(status().isOk())
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testGetNullEntity() throws Exception{
		MvcResult result = mockMvc.perform(get("/user/133"))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("User not found.")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetBadId() throws Exception{
		MvcResult result = mockMvc.perform(get("/user/abc"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Type mismatch. Please check your request.")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
	

	@Test
	public void testSave() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userJSON = mapper.createObjectNode();
		userJSON.put("firstName", "Another James");
		userJSON.put("lastName", "Brown");
		userJSON.put("activeInd", "Y");
		
		MvcResult result = mockMvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userJSON)))
			.andDo(print())
			.andExpect(status().isOk())
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}

	@Test
	public void testSaveBadContentType() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userJSON = mapper.createObjectNode();
		userJSON.put("firstName", "Another James");
		userJSON.put("lastName", "Brown");
		userJSON.put("activeInd", "Y");
		
		MvcResult result = mockMvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(mapper.writeValueAsBytes(userJSON)))
			.andDo(print())
			.andExpect(status().isUnsupportedMediaType())
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}

	@Test
	public void testUpdate() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userJSON = mapper.createObjectNode();
		userJSON.put("id", "1");
		userJSON.put("firstName", "Old James");
		
		MvcResult result = mockMvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userJSON)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testUpdateNonExistentUser() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userJSON = mapper.createObjectNode();
		userJSON.put("id", "1333");
		userJSON.put("firstName", "Old James");
		
		MvcResult result = mockMvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userJSON)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(delete("/user/3"))
			.andDo(print())
			.andExpect(status().isOk())
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testDeleteNonExistentUser() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(delete("/user/256"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("user not found. Could not delete user.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}

	@Test
	public void testActivate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeactivate() {
		fail("Not yet implemented");
	}

}
