/**
 * User DTO for processing Login
 */
package com.github.ginjaninja.bb.account.user;

import java.util.Collection;
import com.github.ginjaninja.bb.account.capability.Capability;


public class UserLoginDTO {
	
	private Integer id;
	
    private String userName;
    
    private String password;
    
    /** Stores capabilities upon user login **/
    private Collection<Capability> capabilities;
    
    
    public UserLoginDTO() {
	}
    
    
    /**
     * Construct UserLoginDTO from {@link User}
     * @param user {@link User}
     */
    public UserLoginDTO(User user) {
    	this.id = user.getId();
    	this.password = user.getPassword();
    	this.userName = user.getUserName();
    }
    
    /**
     * Constructor from user object. Used for implementing UserDetails.
     * @param user	{@link User}
     */
    public UserLoginDTO(UserLoginDTO user){
    	this.id = user.id;
    	this.password = user.password;
    	this.userName = user.userName;
    	this.capabilities = user.capabilities;
    }
    
    


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Capability> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(Collection<Capability> capabilities) {
		this.capabilities = capabilities;
	}
    
	
}
