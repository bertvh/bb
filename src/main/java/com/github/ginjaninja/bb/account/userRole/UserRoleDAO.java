/** Manages data access for UserRole **/
package com.github.ginjaninja.bb.account.userRole;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAOImpl;

@Repository
@Transactional
public class UserRoleDAO extends GenericDAOImpl<UserRole>{

	public UserRoleDAO() {
		super(UserRole.class);
	}
	
}
