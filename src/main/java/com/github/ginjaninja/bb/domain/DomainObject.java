package com.github.ginjaninja.bb.domain;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.persistence.Column;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ginjaninja.bb.dao.GenericDAO;

public abstract class DomainObject {
	private static final Logger LOG = LoggerFactory.getLogger("DomainObject");
	
    	
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	
	/**
	 * Uses reflection to set not nullable members (as defined by @Column) 
	 * from stored object (with same id) when updating object.
	 * Does not set child objects (ie User.role)
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
	 * Set default values for shared not nullable fields. Uses reflection to check for/invoke setters/getters.
	 * Used when saving new object.
	 */
	@SuppressWarnings("rawtypes")
	public void fillFields() {
		//for String class arg
		Class[] stringClassArg = new Class[1];
		stringClassArg[0] = String.class;
		
		//for Date class arg
		Class[] dateClassArg = new Class[1];
		dateClassArg[0] = Date.class;
		
		//for passing Y
		Object[] sArgs = new Object[1];
		sArgs[0] = "Y";
		
		//for passing date
		Object[] dArgs = new Object[1];
		dArgs[0] = new Date();
		
		//does this object have getter/setter for activeInd
		try {
			if(null != this.getClass().getDeclaredMethod("getActiveInd")  
				&& null != this.getClass().getDeclaredMethod("setActiveInd", stringClassArg )){
					Method setActiveInd = this.getClass().getMethod("setActiveInd", stringClassArg);
					setActiveInd.invoke(this, sArgs);
			}
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			LOG.error("Error using fillFields.setActiveInd on instance of: "+ this.getClass(), e);
		}
		
		try {
			if(null != this.getClass().getDeclaredMethod("getCreatedDtTm") 
				&& null != this.getClass().getDeclaredMethod("setCreatedDtTm", dateClassArg)){
					Method setCreatedDtTm = this.getClass().getMethod("setCreatedDtTm", dateClassArg);
					setCreatedDtTm.invoke(this, dArgs);
			}
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			LOG.error("Error using fillFields.setCreatedDtTm on instance of: "+ this.getClass(), e);
		}
		
		try {
			if(null != this.getClass().getDeclaredMethod("getActivityDtTm") 
				&& null != this.getClass().getDeclaredMethod("setActivityDtTm", dateClassArg)){
					Method setCreatedDtTm = this.getClass().getMethod("setActivityDtTm", dateClassArg);
					setCreatedDtTm.invoke(this, dArgs);
			}
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			LOG.error("Error using fillFields.setActivityDtTm on instance of: "+ this.getClass(), e);
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