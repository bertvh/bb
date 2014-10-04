/** Manages data access for AccountCapability **/
package com.github.ginjaninja.bb.account.capability;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAO;

@Repository
@Transactional
public class AccountCapabilityDAO extends GenericDAO<AccountCapability> {

	public AccountCapabilityDAO() {
		super(AccountCapability.class);
	}
	

}
