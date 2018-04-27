package pl.coderslab.app.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import pl.coderslab.app.entity.Book;

@Repository
public class BookRepositoryImpl implements BookRepoCustom {
	
	/*
	PersistenceUnit injects an EntityManagerFactory, and PersistenceContext injects an EntityManager. 
	It's generally better to use PersistenceContext
	unless you really need to manage the EntityManager lifecycle manually.
	
	EntityManagers obtain via @PersistenceContext are Container Managed Entity Manager as container will be 
	responsible for managing "Entity Manage" while EntityManagers obtain via 
	@PersistenceUnit / entityManagerFactory.createEntityManager() are Application Managed Entity Manager 
	and developer has to manage certain things in code (for e.g. releasing the resources acquired by EntityManager).
	*/
	
	//@PersistenceContext
	//EntityManager entityManager;
	
	// rozwiazanie wybrane przez CodersLab pdf
	@PersistenceUnit
	private	EntityManagerFactory emFactory;

	@Override
	@Transactional
	public void resetRating(int rating) {
		System.out.println("-----------------------------------resetRating called");
		EntityManager entityManager = emFactory.createEntityManager();
		entityManager.joinTransaction(); // prevents TransactionRequiredException Executing an update/delete query
		// This method should be called on a JTA application managed entity manager that was created outside the  
		//scope of the active transaction to associate it with the current JTA transaction.
		entityManager.createNativeQuery("UPDATE books SET rating = ?;").setParameter(1, rating).executeUpdate();
		entityManager.close();
	}
	
	

}
