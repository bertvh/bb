package com.github.ginjaninja.bb.domain;

import static org.junit.Assert.*;

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
		user.setId(1);
		user.setFirstName("Billy Bob");
		
		user.fillFields(dao);
		
		System.out.println(user.toString());
		
		assertNotNull(user.getLastName());
	}

}
