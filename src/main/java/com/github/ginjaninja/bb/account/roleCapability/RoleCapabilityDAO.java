/** Manages data access for RoleCapability **/
package com.github.ginjaninja.bb.account.roleCapability;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAOImpl;

@Repository
@Transactional
public class RoleCapabilityDAO extends GenericDAOImpl<RoleCapability>{

	public RoleCapabilityDAO() {
		super(RoleCapability.class);
	}
	
}
