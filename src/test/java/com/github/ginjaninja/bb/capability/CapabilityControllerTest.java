package com.github.ginjaninja.bb.capability;

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
import com.github.ginjaninja.bb.account.capability.Capability;
import com.github.ginjaninja.bb.config.WebAppConfigurationAware;

public class CapabilityControllerTest extends WebAppConfigurationAware {

	@Test
	public void testGet() throws Exception{
		MvcResult result = mockMvc.perform(get("/capability/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
			.andExpect(jsonPath("$.result[*].name", is("add_user")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testGetNonExistentEntity() throws Exception{
		MvcResult result = mockMvc.perform(get("/capability/133"))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Entity with id not found.")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetBadId() throws Exception{
		MvcResult result = mockMvc.perform(get("/capability/abc"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Type mismatch. Please check your request.")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetNoId() throws Exception{
		MvcResult result = mockMvc.perform(get("/capability/"))
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
		ObjectNode capabilityJSON = mapper.createObjectNode();
		capabilityJSON.put("name", "sassypants");
		capabilityJSON.put("type", Capability.Type.ROLE.toString());
		
		MvcResult result = mockMvc.perform(post("/capability")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(capabilityJSON)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
			.andExpect(jsonPath("$.text", is("OK")))
			.andExpect(jsonPath("$.result[*].name", is("sassypants")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testSaveMissingProperty() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode capabilityJSON = mapper.createObjectNode();
		
		MvcResult result = mockMvc.perform(post("/capability")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(capabilityJSON)))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Missing required properties.")))
			.andExpect(jsonPath("$.result", is("The following required properties are missing: class java.lang.String: name, class com.github.ginjaninja.bb.account.capability.Capability$Type: type, ")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testSaveBadContentType() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode capabilityJSON = mapper.createObjectNode();
		capabilityJSON.put("name", "hacker");
		capabilityJSON.put("type", Capability.Type.ROLE.toString());
		
		MvcResult result = mockMvc.perform(post("/capability")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(mapper.writeValueAsBytes(capabilityJSON)))
			.andDo(print())
			.andExpect(status().isUnsupportedMediaType())
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}

	@Test
	public void testUpdate() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode capabilityJSON = mapper.createObjectNode();
		capabilityJSON.put("id", "10");
		capabilityJSON.put("name", "roughrider");
		
		MvcResult result = mockMvc.perform(post("/capability")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(capabilityJSON)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
			.andExpect(jsonPath("$.result[*].name", is("roughrider")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	
	
	@Test
	public void testUpdateNonExistentCapability() throws JsonProcessingException, Exception {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode capabilityJSON = mapper.createObjectNode();
		capabilityJSON.put("id", "1333");
		capabilityJSON.put("name", "holler");
		
		MvcResult result = mockMvc.perform(post("/capability")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(capabilityJSON)))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Entity with id not found.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testDelete() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(delete("/capability/11"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testDeleteNonExistentCapability() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(delete("/capability/256"))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Entity with id not found.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
	}

	@Test
	public void testActivate() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/capability/activate/8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", is("SUCCESS")))
				.andExpect(jsonPath("$.result[*].activeInd", is("Y")))
			    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

	@Test
	public void testDeactivate() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/capability/deactivate/10"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", is("SUCCESS")))
				.andExpect(jsonPath("$.result[*].activeInd", is("N")))
			    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}

}
