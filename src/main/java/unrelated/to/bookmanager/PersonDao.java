package unrelated.to.bookmanager;


import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
@Transactional
public class PersonDao {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public Person createPerson(Person entity) {
		entityManager.persist(entity);
		
		return entity;
	}
	
	public Person updatePerson(Person entity) {
		Person person = entityManager.merge(entity);
		
		return person;
	}
	
	public Person findById(Long id) {
		return entityManager.find(Person.class, id);
	}
	
	public void deleteById(Long id) {
		Person entity = entityManager.find(Person.class, id);
		delete(entity);
		//entityManager.remove(entity);
	}
	
	public void delete(Person entity) {
		entityManager.remove(entityManager.contains(entity) ? 
				entity : entityManager.merge(entity));
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Person> findAll() {
		return entityManager.createQuery("SELECT p FROM Person p").getResultList();
	}
}
