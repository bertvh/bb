package com.github.ginjaninja.bb.account.capability;

import java.util.Date;

import javax.persistence.Column;

import com.github.ginjaninja.bb.account.capability.Capability.Type;
import com.github.ginjaninja.bb.domain.DomainDTO;

public class CapabilityDTO extends DomainDTO{
	/** Capability name **/
	@Column(name = "name", length = 30, nullable = false)
    private String name;
	
	/** Type of capability, chosen from Capability enums above **/
	@Column(name = "type", length = 10, nullable = false)
	private Type type;
	
	/** Whether entity is active or not (can be put in trash without deleting permanently) */
    @Column(name = "active_ind", length = 1, nullable = false)
    private String activeInd;
     
    /** Date/Time last updated **/
    @Column(name = "activity_dt_tm", nullable = false) 
    private Date activityDtTm;
    
    

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
    
    
}
