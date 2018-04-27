package pl.coderslab.app.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.coderslab.app.entity.Author;
import pl.coderslab.app.entity.Book;
import pl.coderslab.app.entity.Category;
import pl.coderslab.app.entity.Publisher;

public interface BookRepository extends JpaRepository<Book,	Long>, BookRepoCustom {
	
	List<Book> findBooksByProposition(boolean show);
	
	
	List<Book> findByTitleIgnoreCase(String title);         //metodę wyszukującą książki dla zadanego tytułu.
	
	List<Book> findByCategory_Name(Category category); 
	List<Book> findByCategory_Name(String name); // metodę wyszukującą książki dla zadanej kategorii
	List<Book> findByCategoryId(long id); // metodę wyszukującą książki dla zadanego id kategorii
	
	
	// Listę książek dla zadanego wydawcy
	List<Book> findByPublisher(Publisher publisher);
	List<Book> findByPublisherId(Long publisherId);
	List<Book> findByPublisherName(String publisherName);
	
	
	// Listę książek dla zadanego autora
	List<Book> findDistinctByAuthorsIn(Collection<Author> authors); 
	//List<Book> findDistinctByAuthorsIn(Collection<Author> authors, Author author); // ???
	
	List<Book> findByAuthors_FirstNameIgnoreCase(String firstName);
	List<Book> findByAuthors_LastNameIgnoreCase(String lastName);
	List<Book> findByAuthors_Id(long id);
	
	//  Listę książek dla określonego ratingu
	List<Book> findByRating(BigDecimal rating); 
	List<Book> findByRatingGreaterThan(BigDecimal rating);  
	List<Book> findByRatingLessThan(BigDecimal rating); 
	List<Book> findByRatingBetween(BigDecimal min, BigDecimal max); 
	
	 //książki z zadanej kategorii, z sortowaniem po tytule.
	List<Book> findByCategoryIdOrderByTitle(long categoryId);
	 
/*
	 utwórz metody pobierające dane za pomocą zapytań `Query`:
 */
	//  metodę wyszukującą książki dla zadanego tytułu.
	@Query("select b from Book b where b.title = :title")
	List<Book> readByTitle(@Param("title")String title);
	
	// metodę wyszukującą pierwszą książkę dla zadanej kategorii
	@Query("select b from Book b where b.category = :category")
	List<Book> readByTitle(@Param("category")Category category);
	
	// Listę książek dla zadanego wydawcy.
	@Query("select b from Book b where b.publisher.id = :publisherId")
	List<Book> readByPublisherId(@Param("publisherId")Long publisherId);
	
	@Query("select b from Book b where b.publisher = :publisher")
	List<Book> getByPublisher(@Param("publisher")Publisher publisher);
	
	// Listę książek dla zadanego autora
	@Query("select b from Book  AS b WHERE :author MEMBER OF b.authors")
	List<Book> findByAuthor(@Param("author")Author author);
	
	// Listę książek dla których rating jest pomiędzy zadanymi parametrami np. między 3 a 5.
	@Query("select b from Book  b WHERE b.rating BETWEEN :min AND :max")
	List<Book> findByRating2(@Param(value = "min") BigDecimal min, @Param(value = "max") BigDecimal max);

	/*
	#### Zadanie 4
	1. Utwórz interfejs a następnie własną implementację tego interfejsu.
	2. Interfejs ma zawierać metodę `void resetRating(int rating)`.
	3. Implementacja ma zawierać zapytanie wywołane za pomocą EntityManagera które ustawi zadany rating dla wszystkich książek.
	4. Włącz tą implementacje do istniejącego repozytorium klasy `Book`.
*/
	//void resetRating(int rating);
	
	
	
}
