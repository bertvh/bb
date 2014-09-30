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
	public void testFillFieldsDAO() {
		User storedUser = dao.get(16);
		
		User user = new User();
		user.setId(16);
		user.setFirstName("Jack Bob");
		user.setActiveInd("N");
		
		user.fillFields(storedUser);
		
		assertTrue(user.getLastName() != null && user.getActiveInd().equals("N"));
	}

	@Test
	public void testFillFields(){
		User user = new User();
		user.setFirstName("Billy Bob");
		user.setLastName("Roland");
		user.setEmail("shashaus");
		user.setPassword("LLL");
		user.setUserName("LLLL");
		user.fillFields();
		
		System.out.println(user.toString());
		assertTrue(user.getActiveInd().equals("Y") && user.getCreatedDtTm() != null && user.getActivityDtTm() != null);
	}
	
	@Test
	public void testCheckRequiredSuccess() {
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
	public void testCheckRequiredFail() {
		User user = new User();
		user.setFirstName("Billy Bob");
		user.setLastName("Roland");
		user.fillFields();
		
		String result = user.checkRequired();
		System.out.println(result);
		assertThat(result, CoreMatchers.containsString("The following required properties are missing: class java.lang.String: userName, class java.lang.String: email, class java.lang.String: password, "));
	}
}
