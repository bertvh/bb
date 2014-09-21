/** Manages data access for Role **/
package com.github.ginjaninja.bb.account.role;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAO;

@Repository
@Transactional
public class RoleDAO extends GenericDAO<Role>{

	public RoleDAO() {
		super(Role.class);
	}
	
}
