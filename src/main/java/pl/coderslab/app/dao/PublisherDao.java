package pl.coderslab.app.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import pl.coderslab.app.entity.Author;
import pl.coderslab.app.entity.Book;
import pl.coderslab.app.entity.Publisher;

@Component
@Transactional
public class PublisherDao {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public Publisher createPublisher(Publisher entity) {
		entityManager.persist(entity);
		return entity;
	}
	
	public Publisher updatePublisher(Publisher entity) {
		Publisher publisher = entityManager.merge(entity);
		return publisher;
	}
	
	public Publisher findById(Long id) {
		return entityManager.find(Publisher.class, id);
	}
	
	public void deleteById(Long id) {            /// ?????
		Publisher entity = entityManager.find(Publisher.class, id);
		entityManager.remove(entity);
	}
	
	public void delete(Publisher entity) {
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Publisher> findAll() {
		return entityManager.createQuery("SELECT p FROM Publisher p").getResultList();
	}
}
