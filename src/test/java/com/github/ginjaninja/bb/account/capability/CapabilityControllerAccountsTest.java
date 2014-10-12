package com.github.ginjaninja.bb.account.capability;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.ginjaninja.bb.config.WebAppConfigurationAware;

public class CapabilityControllerAccountsTest extends WebAppConfigurationAware {
	
	@Test
	public void testGetAccountCapabilities() throws Exception{
		MvcResult result = mockMvc.perform(get("/capability?account=1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
			.andExpect(jsonPath("$.result[0].name", is("VIP")))
		    .andReturn();
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testAddCapabilitySuccess() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/capability/add?account=2&capability=7")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
			.andExpect(jsonPath("$.result[0].name", is("VIP")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testAddCapabilityBadAccount() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/capability/add?account=22&capability=7")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Can't add capability to account. Account not found.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testAddCapabilityBadCapability() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/capability/add?account=2&capability=77")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Can't add capability to account. Capability not found.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testAddCapabilityHasCapability() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/capability/add?account=1&capability=7")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Account already has capability.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testAddCapabilityWrongCapability() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/capability/add?account=2&capability=2")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Can't add role capability to account.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testAddCapabilityInactiveCapability() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/capability/add?account=2&capability=16")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Can't save entity. Does not meet requirements.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testRemoveCapabilitySuccess() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/capability/remove?account=1&capability=15")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void testRemoveCapabilityNotFound() throws JsonProcessingException, Exception {
		MvcResult result = mockMvc.perform(post("/capability/remove?account=4&capability=7")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Can't remove capability. Account doesn't have capability.")))
		    .andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
}