package com.github.ginjaninja.bb.domain;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.ginjaninja.bb.account.user.User;
import com.github.ginjaninja.bb.account.user.UserDAO;
import com.github.ginjaninja.bb.config.WebAppConfigurationAware;

public class DomainObjectTest extends WebAppConfigurationAware {
	@Autowired
	private UserDAO dao;
	
	@Test
	public void testFillFields() throws NoSuchFieldException, SecurityException, IllegalArgumentException, ReflectiveOperationException {
		User user = new User();
		user.setId(16);
		user.setFirstName("Billy Bob");
		
		user.fillFields(dao);
		
		System.out.println(user.toString());
		
		assertNotNull(user.getLastName());
	}

	@Test
	public void testCheckRequiredSuccess() throws IllegalArgumentException, IllegalAccessException{
		User user = new User();
		user.setFirstName("Billy Bob");
		user.setLastName("Roland");
		user.setEmail("shashaus");
		user.setPassword("LLL");
		user.setUserName("LLLL");
		user.fillFields();
		
		assertTrue(user.checkRequired().length() == 0);
	}
	
	@Test
	public void testCheckRequiredFail() throws IllegalArgumentException, IllegalAccessException{
		User user = new User();
		user.setFirstName("Billy Bob");
		user.setLastName("Roland");
		user.fillFields();
		
		String result = user.checkRequired();
		System.out.println(result);
		assertThat(result, CoreMatchers.containsString("The following required properties are missing:"));
	}
}
