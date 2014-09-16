/** Manages data access for AccountCapability **/
package com.github.ginjaninja.bb.accountCapability;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAOImpl;

@Repository
@Transactional
public class AccountCapabilityDAO extends GenericDAOImpl<AccountCapability> {

	public AccountCapabilityDAO() {
		super(AccountCapability.class);
	}
	

}
