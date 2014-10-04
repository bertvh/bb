/** Manages data access for RoleCapability **/
package com.github.ginjaninja.bb.account.capability;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAO;

@Repository
@Transactional
public class RoleCapabilityDAO extends GenericDAO<RoleCapability>{

	public RoleCapabilityDAO() {
		super(RoleCapability.class);
	}
	
}
