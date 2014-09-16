/** Manages data access for User **/
package com.github.ginjaninja.bb.account.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAOImpl;

@Repository
@Transactional
public class UserDAO extends GenericDAOImpl<User> {

	public UserDAO() {
		super(User.class);
	}
	
}
