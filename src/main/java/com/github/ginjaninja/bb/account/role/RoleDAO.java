/** Manages data access for Role **/
package com.github.ginjaninja.bb.account.role;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAOImpl;

@Repository
@Transactional
public class RoleDAO extends GenericDAOImpl<Role>{

	public RoleDAO() {
		super(Role.class);
	}
	
}
