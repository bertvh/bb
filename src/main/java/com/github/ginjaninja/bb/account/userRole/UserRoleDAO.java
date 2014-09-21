/** Manages data access for UserRole **/
package com.github.ginjaninja.bb.account.userRole;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAO;

@Repository
@Transactional
public class UserRoleDAO extends GenericDAO<UserRole>{

	public UserRoleDAO() {
		super(UserRole.class);
	}
	
}
