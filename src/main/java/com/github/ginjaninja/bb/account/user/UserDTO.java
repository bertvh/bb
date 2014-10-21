/** User object with fewer fields for responding to API requests. **/
package com.github.ginjaninja.bb.account.user;

import java.util.Date;

import com.github.ginjaninja.bb.account.account.AccountDTO;
import com.github.ginjaninja.bb.account.role.RoleDTO;
import com.github.ginjaninja.bb.domain.DomainDTO;

public class UserDTO extends DomainDTO {
	
	private Integer id;
	
	/** Account user belongs to **/
	private AccountDTO account;
	
	/** User role **/
	private RoleDTO role;
	
	/** User's first name **/
	private String firstName;
    
	/** User's last name **/
    private String lastName;

    /** User name for login/display **/
    private String userName;
    
    /** User email **/
    private String email;
    
    /** Whether user is active or not **/
    private String activeInd;
    
    /** Date/Time last updated **/
    private Date activityDtTm;
    
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getActivityDtTm() {
		return activityDtTm;
	}

	public void setActivityDtTm(Date activityDtTm) {
		this.activityDtTm = activityDtTm;
	}

	

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	public AccountDTO getAccount() {
		return account;
	}

	public void setAccount(AccountDTO account) {
		this.account = account;
	}

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}
    
}
