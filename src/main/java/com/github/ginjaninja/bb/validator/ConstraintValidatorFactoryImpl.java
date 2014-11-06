package com.github.ginjaninja.bb.validator;

import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstraintValidatorFactoryImpl implements ConstraintValidatorFactory {
	private static final Logger LOG = LoggerFactory.getLogger("ConstraintValidatorFactoryImpl");
	private EntityManagerFactory entityManagerFactory;
	
	public ConstraintValidatorFactoryImpl(EntityManagerFactory entityManagerFactory){
		this.entityManagerFactory = entityManagerFactory;
	}
	
	@Override
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		T instance = null;
		
		try{
			instance = key.newInstance();
		}catch(Exception e){
			//unable to instantiant class
			LOG.error("Error instantiating class", e);
		}
		
		if(EntityManagerAwareValidator.class.isAssignableFrom(key)){
			EntityManagerAwareValidator validator = (EntityManagerAwareValidator) instance;
			validator.setEntityManager(entityManagerFactory.createEntityManager());
		}
		
		return instance;
	}
	
}
