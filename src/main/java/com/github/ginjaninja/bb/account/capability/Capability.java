/** Capabilities a role has **/
package com.github.ginjaninja.bb.account.capability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.ginjaninja.bb.account.roleCapability.RoleCapability;
import com.github.ginjaninja.bb.domain.DomainObject;

@Entity
@Table(name="acct_capability")
public class Capability extends DomainObject{
	public static enum Type {
		ACCOUNT, ROLE;
	}
	
	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	/** Capability name **/
	@Column(name = "name", length = 30, nullable = false)
    private String name;
	
	/** Type of capability, chosen from enums above **/
	@Column(name = "type", length = 10, nullable = false)
	private Type type;
	
	/** Whether entity is active or not (can be put in trash without deleting permanently) */
    @Column(name = "active_ind", length = 1, nullable = false)
    private String activeInd;
     
    /** Date/Time last updated **/
    @Column(name = "activity_dt_tm", nullable = false) 
    private Date activityDtTm;
    
    /** Date/Time created **/
    @Column(name = "created_dt_tm", nullable = false) 
    private Date createdDtTm;

    @OneToMany(mappedBy = "capability", cascade = {CascadeType.ALL})
    private Collection<RoleCapability> capabilities = new ArrayList<RoleCapability>();
    
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	public Date getActivityDtTm() {
		return activityDtTm;
	}

	public void setActivityDtTm(Date activityDtTm) {
		this.activityDtTm = activityDtTm;
	}

	public Date getCreatedDtTm() {
		return createdDtTm;
	}

	public void setCreatedDtTm(Date createdDtTm) {
		this.createdDtTm = createdDtTm;
	}
    
    
}
