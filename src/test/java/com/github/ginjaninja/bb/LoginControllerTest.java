package com.github.ginjaninja.bb;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import com.github.ginjaninja.bb.config.WebAppConfigurationAware;

public class LoginControllerTest extends WebAppConfigurationAware {

	@Test
	public void testGet() throws Exception {
		mockMvc.perform(get("/login"))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void testPostError() throws Exception {
		mockMvc.perform(post("/login?sandrewst=user&password=Lr9ysX"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.type", is("ERROR")));
	}
	
	
	
}
