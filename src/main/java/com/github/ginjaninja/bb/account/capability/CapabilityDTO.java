package com.github.ginjaninja.bb.account.capability;

import java.util.Date;

import com.github.ginjaninja.bb.domain.DomainDTO;

public class CapabilityDTO extends DomainDTO{
	/** Capability name **/
	private String name;
	
	/** Type of capability, chosen from Capability enums above **/
	private String type;
	
	/** Whether entity is active or not (can be put in trash without deleting permanently) */
    private String activeInd;
     
    /** Date/Time last updated **/
    private Date activityDtTm;
    
    

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
