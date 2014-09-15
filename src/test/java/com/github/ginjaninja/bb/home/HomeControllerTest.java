package com.github.ginjaninja.bb.home;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.ginjaninja.bb.config.WebAppConfigurationAware;

public class HomeControllerTest extends WebAppConfigurationAware {

	@Test
	public void testGet() throws Exception {
		mockMvc.perform(get("/"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type", is("SUCCESS")));
	}

	@Test
	public void testPost() throws Exception {
		mockMvc.perform(post("/"))
			.andDo(print())
			.andExpect(status().isIAmATeapot())
			.andExpect(jsonPath("$.type", is("WARNING")));
	}
	
	@Test
	public void testPut() throws Exception {
		mockMvc.perform(put("/"))
			.andDo(print())
			.andExpect(status().isNotImplemented())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Reqest methods PUT and DELETE are not implemented for this address.")));
	}
	
	@Test
	public void testDelete() throws Exception {
		mockMvc.perform(delete("/"))
			.andDo(print())
			.andExpect(status().isNotImplemented())
			.andExpect(jsonPath("$.type", is("ERROR")))
			.andExpect(jsonPath("$.text", is("Reqest methods PUT and DELETE are not implemented for this address.")));
	}
}
