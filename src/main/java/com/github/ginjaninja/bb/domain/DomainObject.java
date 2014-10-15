package com.github.ginjaninja.bb.domain;

import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.Column;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ginjaninja.bb.dao.GenericDAO;

public abstract class DomainObject {
	private static final Logger LOG = LoggerFactory.getLogger("DomainObject");
	
    private String activeInd;
    private Date createdDtTm;
    private Date activityDtTm;
    
	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	public Date getCreatedDtTm() {
		return createdDtTm;
	}

	public void setCreatedDtTm(Date createdDtTm) {
		this.createdDtTm = createdDtTm;
	}

	public Date getActivityDtTm() {
		return activityDtTm;
	}

	public void setActivityDtTm(Date activityDtTm) {
		this.activityDtTm = activityDtTm;
	}

	
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	
	/**
	 * Uses reflection to set not nullable members (as defined by @Column) 
	 * from stored object (with same id) when updating object
	 * @param dao {@link GenericDAO} autowired dao
	 * @param dao
	 */
	public void fillFields(DomainObject o) {
		//for each field in object
		for(Field field : this.getClass().getDeclaredFields()){
			//make private field accessible
			field.setAccessible(true);
			//if value field in this is null
			try {
				if(field.get(this) == null){
					//get @column annotation
					Column columnAnnotation = field.getAnnotation(Column.class);
					//if @column annotation exists and field is not nullable
					if(columnAnnotation != null && !columnAnnotation.nullable()){
						//get field from stored object set value in this object
						Field oField = o.getClass().getDeclaredField(field.getName());
						//make private field accessible
						oField.setAccessible(true);
						//set value in this object with value from stored object
						field.set(this, oField.get(o));		
					}			
				}
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException e) {
				LOG.error("Error running fillfields on DomainObject", e);
			}
		}
	}

	/**
	 * Set default values for shared not nullable fields.
	 * Used when saving new object.
	 */
	public void fillFields(){
		if(this.getActiveInd() == null){
			this.setActiveInd("Y");
		}
		if(this.getCreatedDtTm() == null){
			this.setCreatedDtTm(new Date());
		}
		if(this.getActivityDtTm() == null){
			this.setActivityDtTm(new Date());
		}
	}
	
	/**
	 * Check that all not nullable properties are sent.
	 * Return error message and list of missing properties if check fails.
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public String checkRequired() {
		StringBuilder fieldString = new StringBuilder("The following required properties are missing: ");
		boolean missing = false;
		//for each field in object
		for(Field field : this.getClass().getDeclaredFields()){
			//make private field accessible
			field.setAccessible(true);
			//if value field in this is null
			try {
				if(field.get(this) == null){
					//get @column annotation
					Column columnAnnotation = field.getAnnotation(Column.class);
					//if @column annotation exists and field is not nullable
					if(columnAnnotation != null && !columnAnnotation.nullable()){
						
						missing = true;
						fieldString.append(field.getType());
						fieldString.append(": ");
						fieldString.append(field.getName());
						fieldString.append(", ");
					}			
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				LOG.error("Error running checkRequired on DomainObject", e);
			}
		}
		if(!missing){
			fieldString.setLength(0);
		}
		return fieldString.toString();
	}
	
	
}