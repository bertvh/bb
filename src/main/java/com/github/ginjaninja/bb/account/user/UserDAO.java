/** Manages data access for User **/
package com.github.ginjaninja.bb.account.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ginjaninja.bb.dao.GenericDAO;

@Repository
@Transactional
public class UserDAO extends GenericDAO<User> {

	public UserDAO() {
		super(User.class);
	}
	
}
