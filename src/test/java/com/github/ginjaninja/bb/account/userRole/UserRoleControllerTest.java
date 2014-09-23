package com.github.ginjaninja.bb.account.userRole;

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

public class UserRoleControllerTest extends WebAppConfigurationAware {

	@Test
	public void testGet() throws Exception{
		MvcResult result = mockMvc.perform(get("/userRole/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
			.andExpect(jsonPath("$.result[*].role[*].name", is("admin")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testGetNonExistentEntity() throws Exception{
		MvcResult result = mockMvc.perform(get("/userRole/133"))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Entity with id not found.")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetBadId() throws Exception{
		MvcResult result = mockMvc.perform(get("/userRole/abc"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Type mismatch. Please check your request.")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetNoId() throws Exception{
		MvcResult result = mockMvc.perform(get("/userRole/"))
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
		ObjectNode userRoleJSON = mapper.createObjectNode();
		userRoleJSON.put("firstName", "Another James");
		userRoleJSON.put("lastName", "Brown");
		userRoleJSON.put("email", "brown@email.com");
		userRoleJSON.put("password", "booya");
		userRoleJSON.put("userRoleName", "ajbrown");
		
		MvcResult result = mockMvc.perform(post("/userRole")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userRoleJSON)))
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
		ObjectNode userRoleJSON = mapper.createObjectNode();
		userRoleJSON.put("firstName", "Boo James");
		userRoleJSON.put("lastName", "Brown");
		
		MvcResult result = mockMvc.perform(post("/userRole")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userRoleJSON)))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Missing required properties.")))
			.andExpect(jsonPath("$.result", is("The following required properties are missing: class java.lang.String: userRoleName, class java.lang.String: email, class java.lang.String: password, ")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testSaveBadContentType() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userRoleJSON = mapper.createObjectNode();
		userRoleJSON.put("firstName", "Another James");
		userRoleJSON.put("lastName", "Brown");
		userRoleJSON.put("activeInd", "Y");
		
		MvcResult result = mockMvc.perform(post("/userRole")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(mapper.writeValueAsBytes(userRoleJSON)))
			.andDo(print())
			.andExpect(status().isUnsupportedMediaType())
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}

	@Test
	public void testUpdate() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userRoleJSON = mapper.createObjectNode();
		userRoleJSON.put("id", "1");
		userRoleJSON.put("firstName", "Old James");
		
		MvcResult result = mockMvc.perform(post("/userRole")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userRoleJSON)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testUpdateAccount() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userRoleJSON = mapper.createObjectNode();
		userRoleJSON.put("id", "12");
		ObjectNode acctJSON = mapper.createObjectNode();
		acctJSON.put("id", "7");
		userRoleJSON.put("account", acctJSON);
		
		MvcResult result = mockMvc.perform(post("/userRole")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userRoleJSON)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
			.andExpect(jsonPath("$.result[*].account[*].name", is("Centidel")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testUpdateNonExistentUserRole() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode userRoleJSON = mapper.createObjectNode();
		userRoleJSON.put("id", "1333");
		userRoleJSON.put("firstName", "Old James");
		
		MvcResult result = mockMvc.perform(post("/userRole")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userRoleJSON)))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Entity with id not found.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(delete("/userRole/6"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testDeleteNonExistentUserRole() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(delete("/userRole/256"))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Entity with id not found.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}

	@Test
	public void testActivate() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/userRole/activate/18"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", is("SUCCESS")))
				.andExpect(jsonPath("$.result[*].activeInd", is("Y")))
			    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testDeactivate() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/userRole/deactivate/19"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", is("SUCCESS")))
				.andExpect(jsonPath("$.result[*].activeInd", is("N")))
			    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

}
