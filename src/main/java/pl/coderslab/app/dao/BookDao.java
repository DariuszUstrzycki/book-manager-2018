package pl.coderslab.app.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import pl.coderslab.app.entity.Book;
import pl.coderslab.app.entity.Publisher;

@Component
@Transactional
public class BookDao {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public Book createBook(Book entity) {
		/*if( entity.getPublisher().getId()  != null ) {
			entityManager.merge(entity.getPublisher());
		}*/
		entityManager.persist(entity);
		return entity;
	}
	
	public Book updateBook(Book entity) {
		System.out.println("-----------------BookDao:updateBook(Book entity): " + entity);
		Book book = entityManager.merge(entity);
		return book;
	}
	
	public Book findById(Long id) {
		return entityManager.find(Book.class, id);
	}
	
	public void deleteById(Long id) {     
		
		Book entity = entityManager.find(Book.class, id);
		delete(entity);
		
		//entityManager.remove(entity);
		
		/*
		 ALTERNATYWNE SPRAWDZANIE CZY USUWA
		System.out.println("..........................................deleting..............................");
		entityManager.createQuery("delete from Book where id = :id")
		  .setParameter("id", id)
		  .executeUpdate();
		*/
	}

/*
	Unflushed changes made to the entity if any (including removal of the entity), will not be synchronized to the database. 
	Entities which previously referenced the detached entity will continue to reference it.*/
	public void delete(Book entity) {
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}
	/*
	 if remove() accepted a detached entity, remove() would have to decide for you: either merge the detached entity 
	 and execute the remove operation based on what the detached entity contains, or simply load the entity 
	 having the same ID as the detached entity, and execute the operation based on the loaded entity.
	 */
	
	@SuppressWarnings("unchecked")
	public Collection<Book> findAll() {
		return entityManager.createQuery("SELECT b FROM Book b").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> getRatingList(int rating){ // JPQL
		BigDecimal bdRating = BigDecimal.valueOf(rating);
		Query query = entityManager.createQuery("SELECT b FROM	Book b where rating	>:rating");
		query.setParameter("rating", bdRating);
		// query.setMaxResults(1);  and .getSingleResult()
		return query.getResultList();
				//entityManager.createQuery("SELECT b FROM Book b where rating >	4").getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Book> getPropositions(){ // JPQL
		Query query = entityManager.createQuery("SELECT b FROM	Book b where proposition =:proposition");
		query.setParameter("proposition", true);
		// query.setMaxResults(1);  and .getSingleResult()
		System.out.println("------------------List of propositions size: " + query.getResultList().size());
		return query.getResultList();
				//entityManager.createQuery("SELECT b FROM Book b where rating >	4").getResultList();
	}
}
