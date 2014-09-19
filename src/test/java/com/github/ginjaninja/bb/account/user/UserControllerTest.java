package com.github.ginjaninja.bb.account.user;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
			.andExpect(jsonPath("$.type", is("SUCCESS")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testGetNonExistentEntity() throws Exception{
		MvcResult result = mockMvc.perform(get("/user/133"))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Entity with id not found.")))
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
	public void testGetNoId() throws Exception{
		MvcResult result = mockMvc.perform(get("/user/"))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Missing id or parameters to fetch.")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testSave() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userJSON = mapper.createObjectNode();
		userJSON.put("firstName", "Another James");
		userJSON.put("lastName", "Brown");
		userJSON.put("email", "brown@email.com");
		userJSON.put("password", "booya");
		userJSON.put("userName", "ajbrown");
		
		MvcResult result = mockMvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userJSON)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
			.andExpect(jsonPath("$.text", is("OK")))
			.andExpect(jsonPath("$.result[*].firstName", is("Another James")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testSaveMissingProperty() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userJSON = mapper.createObjectNode();
		userJSON.put("firstName", "Boo James");
		userJSON.put("lastName", "Brown");
		
		MvcResult result = mockMvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userJSON)))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Missing required properties.")))
			.andExpect(jsonPath("$.result", is("The following required properties are missing: class java.lang.String: userName, class java.lang.String: email, class java.lang.String: password, ")))
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
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Entity with id not found.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(delete("/user/5"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testDeleteNonExistentUser() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(delete("/user/256"))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("User not found. Could not delete user.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}

	@Test
	public void testActivate() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/user/activate/18"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", is("SUCCESS")))
				.andExpect(jsonPath("$.result[*].activeInd", is("Y")))
			    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testDeactivate() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/user/deactivate/19"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", is("SUCCESS")))
				.andExpect(jsonPath("$.result[*].activeInd", is("N")))
			    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

}
