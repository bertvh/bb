/** Manages data access for Capability **/
package com.github.ginjaninja.bb.account.capability;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAOImpl;

@Repository
@Transactional
public class CapabilityDAO extends GenericDAOImpl<Capability>{

	public CapabilityDAO() {
		super(Capability.class);
	}
	
}
