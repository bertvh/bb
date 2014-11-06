package com.github.ginjaninja.bb.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.github.ginjaninja.bb.account.account.Account;
import com.github.ginjaninja.bb.account.account.AccountDTO;
import com.github.ginjaninja.bb.account.user.User;
import com.github.ginjaninja.bb.account.user.UserDTO;
import com.github.ginjaninja.bb.config.WebAppConfigurationAware;

public class DomainDTOTest extends WebAppConfigurationAware {

	@Test
	public void testConvertUser() {
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

	@Test
	public void testConvertAccount(){
		Account account = new Account();
		account.setName("Yokohama");
		account.fillFields();
		
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.convert(account);
		
		System.out.println(accountDTO);
		
		assertNotNull(accountDTO);
	}
}
