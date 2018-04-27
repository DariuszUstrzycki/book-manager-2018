package unrelated.to.bookmanager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.coderslab.app.dao.AuthorDao;
import pl.coderslab.app.dao.BookDao;
import pl.coderslab.app.dao.PublisherDao;
import pl.coderslab.app.entity.Author;
import pl.coderslab.app.entity.Book;
import pl.coderslab.app.entity.Publisher;

@RestController
@Transactional // bez tego błąð ze nie może znależź EntityManager
public class TestController {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	PublisherDao publisherDao;

	@Autowired
	BookDao bookDao;

	@Autowired
	AuthorDao authorDao;
	
	// TESTY tutaj OMIJAJA Controllers zrobione specjalnie dla Person, PersonDetails, Publisher, Author i Book

	@RequestMapping("booktest")
	public String bookTest() {
		
		Book bookToDelete = new Book();
		bookToDelete.setTitle("Book to delete");
		bookToDelete.setDescription("I will be deleted");
		bookDao.createBook(bookToDelete);
		System.out.println("id of bookToDelete is " + bookToDelete.getId());
		bookDao.delete(bookToDelete);
		
		Book book = new Book();
		book.setTitle("Thinking	in Java");
		bookDao.createBook(book);
		
		Book book2 = new Book();
		book2.setTitle("Bobby Brown");
		bookDao.createBook(book2);
		
		book = bookDao.findById(2L);
		book.setDescription("programming");
		bookDao.updateBook(book);
		
		List<Book> books = (List<Book>) bookDao.findAll();
		
		return "Id of the second added book is:" + book.getId() + "<br>"
				 + books;
	}
	
	@RequestMapping("bookpubltest")
	public String bookPublTest() {
		
		Publisher publ1 = new Publisher();
		publ1.setName("PrintComputer House");
		Book book1 = new Book();
		book1.setTitle("Thinking	in Java");
		book1.setPublisher(publ1);
		
		// jezeli nie zasejwuje Publisher PRZED sejwowaniem Book,  że Book references unsaved Publisher
		// object references an unsaved transient instance - save the transient instance beforeQuery 
		// flushing : pl.coderslab.app.entity.Book.publisher -> pl.coderslab.app.entity.Publisher .. 
		publisherDao.createPublisher(publ1);		
		bookDao.createBook(book1);
		
		Publisher publ2 = new Publisher();
		publ2.setName("PrintAdventure House");
		Book book2 = new Book();
		book2.setTitle("Bobby Brown");
		book2.setPublisher(publ2);
		publisherDao.createPublisher(publ2);	
		bookDao.createBook(book2);
		
		//entityManager.flush(); // nie rzuca błedu bez tego
		// automatycznie updatuje zmiany ponizsze, nie potrzeba update() wolac
		book2.setPublisher(publ1); 
		book1.setDescription("bardzo dobra ksiazka");
		publ2.setName("Updated PrintAdventure House");
		book1.setPublisher(null); // Book w taki sposób usuwa swojego publishera
		
		return "Books on the list - expexted 2:<br> " + (List<Book>) bookDao.findAll();
	}
	
	@RequestMapping("bookauthortest")
	public String bookAuthorTest() { 
		
		Author author1 = new Author();
		author1.setFirstName("Michael");
		author1.setLastName(" Caine");
		Book book1 = new Book();
		book1.setTitle("Thinking	in Java");
		book1.addAuthor(author1);
		
		bookDao.createBook(book1);  // authors willbe persisted by Hibernate automatically
		
		Author author2 = new Author();
		author2.setFirstName("John");
		author2.setLastName(" Caine");
		book1.addAuthor(author2);
		book1.setDescription("I should have 2 authors");
		
		Book book2 = new Book();
		book2.setTitle("Java Revisited");
		
		bookDao.createBook(book2);
		
		book2.addAuthor(author1);
		book2.setDescription("Michael Caine should be author of 2 books");
		
		Author author3ToDelete = new Author();
		author1.setFirstName("Robiie");
		author3ToDelete.setLastName(" Delet");
		book1.addAuthor(author3ToDelete);
		book1.removeAuthor(author3ToDelete);
		
		return "done";
	}

	
	@RequestMapping("/test")
	@ResponseBody
	public String testRelation() {
		/*
		 * // ------------------- part 1 -------------------------- Person person = new
		 * Person(); person.setLogin("darcoCL"); person =
		 * personDao.createPerson(person);
		 * 
		 * // onetToOne - onedirectional Person is owner PersonDetails personDetails =
		 * new PersonDetails(); personDetails.setCity("Wroclaw");
		 * personDetails.setStreet("ul Swidnicka"); // personDetails musza byc wpierw
		 * zasejwowane zanim kaze updatowac person
		 * personDetailsDao.createPersonDetails(personDetails);
		 * 
		 * person.setPersonDetails(personDetails); personDao.updatePerson(person);
		 */
		// ------------------- part 2 --------------------------
		Publisher publisher = new Publisher();
		publisher.setName("Printing House");
		publisher = publisherDao.createPublisher(publisher);

		Author author = new Author();
		author.setLastName("Cortazar");
		author = authorDao.createAuthor(author);

		// Book / Publisher - oneToMany bidirectional
		Book book = new Book();
		book.setTitle("Hopscotch");
		book.setDescription("very good novel");
		book.setPublisher(publisher);
		book = bookDao.createBook(book); // dostajemy z powrtotem ksiązkę juz z id

		// Book/Author @ManyToMany bidirectional
		// !!!!!!!!!!!!!!! ustawiamy atrybut dla book tak jak i inne atrybuty
		book.getAuthors().add(author);
		bookDao.updateBook(book);

		// Publisher publisher2 = new Publisher();
		// publisher2.setName("Publisher2");
		// Book book2 = new Book();
		// book2.setTitle("Tytul drugiej ksiazki");
		// publisher2.getBooks().add(book2);
		//
		// publisherDao.createPublisher(publisher2);
		//
		// Publisher publisher3 = new Publisher();
		// publisher3.setName("Publisher3");
		// Book book3 = new Book();
		// book3.setTitle("Tytul tzreciej ksiazki");
		//
		// bookDao.createBook(book3);

		Publisher fromDB = publisherDao.findById(publisher.getId());
		Integer books1Size = fromDB.getBooks().size();

		List<Book> bookList = (List<Book>) bookDao.findAll();

		return "Book list size is " + books1Size + "<br>List of books: " + bookList;
	}

	@RequestMapping("/read")
	@ResponseBody
	public String testRead() {
		Book book = bookDao.findById(1L);
		Publisher publisher = publisherDao.findById(book.getPublisher().getId());

		return book.getPublisher().getName() + "<br> publisher: " + publisher.getName()
				+ " <br>no of books for this publisher: " + publisher.getBooks().size() + " <br>autor: "
				+ book.getAuthors().get(0).getLastName();
	}

	@RequestMapping("/testBook")
	@ResponseBody
	public String testBook() {
		Book book = bookDao.findById(1L); // find book with id 1

		Author author = new Author();
		author.setLastName("Reymont");
		book.getAuthors().add(author); // add new author to book with id 1

		book = bookDao.updateBook(book); // update this book in db

		Publisher publisher = publisherDao.findById(book.getPublisher().getId()); // get the publisher for this book

		return " publishers name from Book class: " + book.getPublisher().getName()
				+ "<br> publishers name from Publisher class: " + publisher.getName() + "<br> books: "
				+ publisher.getBooks().size() + " authors: " + book.getAuthors().size();
	}
}
