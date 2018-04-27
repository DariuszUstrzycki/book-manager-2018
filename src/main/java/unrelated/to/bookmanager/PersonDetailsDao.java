package unrelated.to.bookmanager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
@Transactional
public class PersonDetailsDao {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public PersonDetails createPersonDetails(PersonDetails entity) {
		entityManager.persist(entity);
		
		return entity;
	}
	
	public PersonDetails updatePersonDetails(PersonDetails entity) {
		PersonDetails personDetails = entityManager.merge(entity);
		
		return personDetails;
	}
	
	public PersonDetails findById(Long id) {
		return entityManager.find(PersonDetails.class, id);
	}
	
	public void deleteById(Long id) {
		PersonDetails entity = entityManager.find(PersonDetails.class, id);
		entityManager.remove(entity);
	}
	
	public void delete(PersonDetails entity) {
		entityManager.remove(entityManager.contains(entity) ? 
				entity : entityManager.merge(entity));
	}
}
