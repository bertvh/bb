/**
 * Accounts are logical groups of users
 */
package com.github.ginjaninja.bb.account.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.ginjaninja.bb.account.user.User;
import com.github.ginjaninja.bb.domain.DomainObject;



@Entity
@Table(name="acct_account")
public class Account extends DomainObject{
	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	/** name of account **/
	@Column(name = "name", length = 50, nullable = false)
	private String name;
	
	/** Whether entity is active or not (can be put in trash without deleting permanently) */
    @Column(name = "active_ind", length = 1, nullable = false)
    private String activeInd;
     
    /** Date/Time last updated **/
    @Column(name = "activity_dt_tm", nullable = false) 
    private Date activityDtTm;
    
    /** Date/Time created **/
    @Column(name = "created_dt_tm", nullable = false) 
    private Date createdDtTm;

    /** All users in account **/
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Collection<User> users = new ArrayList<User>();
    
    
    
    

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
