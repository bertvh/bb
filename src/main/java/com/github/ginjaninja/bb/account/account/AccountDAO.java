/** Manages data access for Account **/
package com.github.ginjaninja.bb.account.account;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAO;

@Repository
@Transactional
public class AccountDAO extends GenericDAO<Account> {

	public AccountDAO() {
		super(Account.class);
	}

}
