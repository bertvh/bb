/**
 * Application users
 */
package com.github.ginjaninja.bb.account.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.ginjaninja.bb.account.account.Account;
import com.github.ginjaninja.bb.domain.DomainObject;

@Entity
@Table(name="acct_user")
public class User extends DomainObject {
	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	/** Account user belongs to **/
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	/** User's first name **/
	@Column(name = "first_name", length = 30, nullable = false)
    private String firstName;
    
	/** User's last name **/
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    /** User name for login/display **/
    @Column(name = "user_name", length = 30, nullable = false)
    private String userName;
    
    /** User email **/
    @Column(name = "email", length = 256, nullable = false)
    private String email;
    
    /** User password **/
    @Column(name = "password", length = 256, nullable = false)
    private String password;
    
    /** Whether entity is active or not (can be put in trash without deleting permanently) */
    @Column(name = "active_ind", length = 1, nullable = false)
    private String activeInd;
     
    /** Date/Time last updated **/
    @Column(name = "activity_dt_tm", nullable = false) 
    private Date activityDtTm;
    
    /** Date/Time created **/
    @Column(name = "created_dt_tm", nullable = false) 
    private Date createdDtTm;
    
    
    
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	
	@Override
    public void setId(Integer id){
    	this.id = id;
    }
    @Override
    public Integer getId(){
    	return id;
    }
    
	@Override
	public String getActiveInd() {
		return activeInd;
	}
	@Override
	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}
	@Override
	public Date getCreatedDtTm() {
		return createdDtTm;
	}
	@Override
	public void setCreatedDtTm(Date createdDtTm) {
		this.createdDtTm = createdDtTm;
	}
	@Override
	public Date getActivityDtTm() {
		return activityDtTm;
	}
	@Override
	public void setActivityDtTm(Date activityDtTm) {
		this.activityDtTm = activityDtTm;
	}
}
