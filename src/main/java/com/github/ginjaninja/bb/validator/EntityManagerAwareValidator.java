package com.github.ginjaninja.bb.validator;

import javax.persistence.EntityManager;



public interface EntityManagerAwareValidator {
	void setEntityManager(EntityManager entityManager);
}
