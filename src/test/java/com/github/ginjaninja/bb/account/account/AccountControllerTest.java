package com.github.ginjaninja.bb.account.account;

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

public class AccountControllerTest extends WebAppConfigurationAware {

	@Test
	public void testGet() throws Exception{
		MvcResult result = mockMvc.perform(get("/account/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testGetNonExistentEntity() throws Exception{
		MvcResult result = mockMvc.perform(get("/account/133"))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Entity with id not found.")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetBadId() throws Exception{
		MvcResult result = mockMvc.perform(get("/account/abc"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Type mismatch. Please check your request.")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetNoId() throws Exception{
		MvcResult result = mockMvc.perform(get("/account/"))
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
		ObjectNode accountJSON = mapper.createObjectNode();
		accountJSON.put("name", "Yokohama");
		
		MvcResult result = mockMvc.perform(post("/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(accountJSON)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
			.andExpect(jsonPath("$.text", is("OK")))
			.andExpect(jsonPath("$.result[*].name", is("Yokohama")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testSaveMissingProperty() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode accountJSON = mapper.createObjectNode();
		
		MvcResult result = mockMvc.perform(post("/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(accountJSON)))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Missing required properties.")))
			.andExpect(jsonPath("$.result", is("The following required properties are missing: class java.lang.String: name, ")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testSaveBadContentType() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode accountJSON = mapper.createObjectNode();
		accountJSON.put("firstName", "Another James");
		accountJSON.put("lastName", "Brown");
		accountJSON.put("activeInd", "Y");
		
		MvcResult result = mockMvc.perform(post("/account")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(mapper.writeValueAsBytes(accountJSON)))
			.andDo(print())
			.andExpect(status().isUnsupportedMediaType())
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}

	@Test
	public void testUpdate() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode accountJSON = mapper.createObjectNode();
		accountJSON.put("id", "6");
		accountJSON.put("name", "Old Yeller, Inc.");
		
		MvcResult result = mockMvc.perform(post("/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(accountJSON)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
			.andExpect(jsonPath("$.result[*].name", is("Old Yeller, Inc.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testUpdateNonExistentAccount() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode accountJSON = mapper.createObjectNode();
		accountJSON.put("id", "1333");
		accountJSON.put("name", "Old Yeller");
		
		MvcResult result = mockMvc.perform(post("/account")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(accountJSON)))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Entity with id not found.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(delete("/account/3"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testDeleteNonExistentAccount() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(delete("/account/256"))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Entity with id not found.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}

	@Test
	public void testActivate() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/account/activate/9"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", is("SUCCESS")))
				.andExpect(jsonPath("$.result[*].activeInd", is("Y")))
			    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testDeactivate() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/account/deactivate/8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", is("SUCCESS")))
				.andExpect(jsonPath("$.result[*].activeInd", is("N")))
			    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

}
