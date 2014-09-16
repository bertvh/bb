package com.github.ginjaninja.bb.account.user;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Class to store ArrayList<User> so it can be used with ResultMessage or accepted by controller.
 *
 */
public class UserList extends ArrayList<User> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserList(){
		super();
	}
	
	/**
	 * Convert from Collection<User> (ie DAO return object)
	 */
	public UserList(Collection<User> users){
		this.addAll(users);
	}
}
