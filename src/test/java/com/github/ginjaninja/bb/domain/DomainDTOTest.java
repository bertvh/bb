package com.github.ginjaninja.bb.domain;

import static org.junit.Assert.*;
import org.junit.Test;

import com.github.ginjaninja.bb.account.user.User;
import com.github.ginjaninja.bb.account.user.UserDTO;
import com.github.ginjaninja.bb.config.WebAppConfigurationAware;

public class DomainDTOTest extends WebAppConfigurationAware {

	@Test
	public void testConvert() {
		User user = new User();
		user.setFirstName("Billy Bob");
		user.setLastName("Roland");
		user.setEmail("shashaus");
		user.setPassword("LLL");
		user.setUserName("LLLL");
		user.fillFields();
		
		UserDTO userDTO = new UserDTO();
		userDTO.convert(user);
		
		System.out.println(userDTO.toString());
		
		assertNotNull(userDTO);
	}

}
