/** Capabilities a role has **/
package com.github.ginjaninja.bb.account.capability;

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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.ginjaninja.bb.domain.DomainObject;
import com.github.ginjaninja.bb.utils.CustomDateSerializer;

@NamedQueries({
	@NamedQuery(
	    name="getRoleCapabilities",
	    query="SELECT c FROM RoleCapability rc "
	    		+ "JOIN rc.capability c "
	    		+ "JOIN rc.role r "
	    		+ "WHERE r.id = :id "
	    		+ "AND rc.activeInd = 'Y' "
	    		+ "AND c.activeInd = 'Y'"
	),
	@NamedQuery(
		    name="getAccountCapabilities",
		    query="SELECT c FROM AccountCapability ac "
		    		+ "JOIN ac.capability c "
		    		+ "JOIN ac.account a "
		    		+ "WHERE a.id = :id "
		    		+ "AND ac.activeInd = 'Y' "
		    		+ "AND c.activeInd = 'Y'"
	),
	@NamedQuery(
		    name="getUserCapabilities",
		    query="SELECT c FROM User u "
		    		+ "JOIN u.role r "
		    		+ "JOIN r.capabilities rc "
		    		+ "JOIN rc.capability c "
		    		+ "WHERE u.id = :id "
		    		+ "AND rc.activeInd = 'Y' "
		    		+ "AND c.activeInd = 'Y'"
		)
})

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
	@Column(name = "role_type", length = 10, nullable = false)
	private String type;
	
	/** Whether entity is active or not (can be put in trash without deleting permanently) */
    @Column(name = "active_ind", length = 1, nullable = false)
    private String activeInd;
     
    /** Date/Time last updated **/
    @Column(name = "activity_dt_tm", nullable = false) 
    private Date activityDtTm;
    
    /** Date/Time created **/
    @Column(name = "created_dt_tm", nullable = false) 
    private Date createdDtTm;

    /** RoleCapabilities for Capability **/
    @OneToMany(mappedBy = "capability", cascade = CascadeType.ALL)
    private Collection<RoleCapability> capabilities;
    
    
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

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	public Date getActivityDtTm() {
		return activityDtTm;
	}

	@Override
	public void setActivityDtTm(Date activityDtTm) {
		this.activityDtTm = activityDtTm;
	}

	@Override
	public Date getCreatedDtTm() {
		return createdDtTm;
	}

	@Override
	public void setCreatedDtTm(Date createdDtTm) {
		this.createdDtTm = createdDtTm;
	}

	public Collection<RoleCapability> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(Collection<RoleCapability> capabilities) {
		this.capabilities = capabilities;
	}
    
    
}
