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
public class AuthorDao {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public Author createAuthor(Author entity) {
		entityManager.persist(entity);
		return entity;
	}
	
	public Author updateAuthor(Author entity) {
		Author Author = entityManager.merge(entity);
		return Author;
	}
	
	public Author findById(Long id) {
		return entityManager.find(Author.class, id);
	}
	
	public void deleteById(Long id) {   // ?????
		Author entity = entityManager.find(Author.class, id);
		entityManager.flush();
		entityManager.remove(entity);
	}
	
	public void delete(Author entity) {
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}
	
	
	@SuppressWarnings("unchecked")
	public Collection<Author> findAll() {
		return entityManager.createQuery("SELECT a FROM Author a").getResultList();
	}
	
}
