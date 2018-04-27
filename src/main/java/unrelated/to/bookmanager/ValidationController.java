package unrelated.to.bookmanager;

import java.math.BigDecimal;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.coderslab.app.dao.AuthorDao;
import pl.coderslab.app.dao.BookDao;
import pl.coderslab.app.dao.PublisherDao;
import pl.coderslab.app.entity.Author;
import pl.coderslab.app.entity.Book;
import pl.coderslab.app.entity.Publisher;

@Controller
@Transactional
public class ValidationController {
	
	@Autowired
	Validator validator;
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private AuthorDao authorDao;
	
	@Autowired
	private PublisherDao publisherDao;
	
	
	//day3 ex 4,5 (wprowadzenie do walidacji) - testuje Author i Publisher osobno
		@RequestMapping("/validatejsp2")
		public String validateBook2(Model model) {
			
			Publisher publisher = new Publisher(); //Wymusza by Book mial cascadeType.PERSIST (MERGE nie wystarcza) Not-null property references a transient value - ransient instance must be saved beforeQuery current operation :
			publisher.setName("Books Incorporated");
			publisher.setNip("wrong nip");
			
			Set<ConstraintViolation<Publisher>> publisherViolations = validator.validate(publisher); // the set will contain potential errors
			
			Author author = new Author();
			author.setFirstName("Tommy");
			author.setLastName("Clancy");
			author.setPesel("");
			
			Set<ConstraintViolation<Author>> authorViolations = validator.validate(author); // the set will contain potential errors
			
			//assert authorViolations.size() == 0; // // yeah, no errors
			
			if (!authorViolations.isEmpty()) {
				for (ConstraintViolation<Author> constraintViolation : authorViolations) {
					System.out.println("PropertyPath: " + constraintViolation.getPropertyPath() + ", Invalid value: "
							+ constraintViolation.getInvalidValue() + ", Message: " + constraintViolation.getMessage());
				}
			} else {
				authorDao.createAuthor(author);
			}
			

			if (!publisherViolations.isEmpty()) {
				for (ConstraintViolation<Publisher> constraintViolation : publisherViolations) {
					System.out.println("PropertyPath: " + constraintViolation.getPropertyPath() + ", Invalid value: "
							+ constraintViolation.getInvalidValue() + ", Message: " + constraintViolation.getMessage());
				}
			} else {
				publisherDao.createPublisher(publisher);
			}
			
			model.addAttribute("authorViolations", authorViolations);
			model.addAttribute("publisherViolations", publisherViolations);

			return "validationErrors";
		}
		
	
	
	//day3 ex 3,4,5  (wprowadzenie do walidacji) - testuję Author i Publisher razem z Book
	// rezultat: persistuje Book, mimo, że i Publisher i Author nie mają pesel, regon, itp. Nie widzi, że tam są violations
		// dopóki nie użyje settterów ustawiajacych niepoprawne dane (czyli null przechodzi walidację!!!)
	@RequestMapping("/validatejsp1")
	public String validateBook1(Model model) {
		
		Book book = new Book();
		book.setIsbn("111223333");
		book.setTitle("Zlot");
		book.setRating(new BigDecimal(11));
		book.setDescription("jakis tam opis");
		
		Publisher publisher = new Publisher(); // Not-null property references a transient value - transient instance must be saved beforeQuery current operation :
		publisher.setName("Books Incorporated");
		publisher.setRegon("wrong regon"); // !!!!
		book.setPublisher(publisher);
		
		Author author = new Author();
		author.setFirstName("Tom");
		author.setLastName("Clancy");
		author.setEmail("kozapl");
		//author.setPesel("");   // NIE WYKRYJE ZE NIE MA PESELU< jesli ten nie bedzi eustawiony na @NotNull
		book.addAuthor(author);
		
		Set<ConstraintViolation<Book>> bookViolations = validator.validate(book); // the set will contain potential errors
		Set<ConstraintViolation<Author>> authorViolations = validator.validate(author); // the set will contain potential errors
		Set<ConstraintViolation<Publisher>> publisherViolations = validator.validate(publisher); // the set will contain potential errors
		
		boolean noViolations = true;
		
		if (!bookViolations.isEmpty()) {
			for (ConstraintViolation<Book> constraintViolation : bookViolations) {
				System.out.println("Book - PropertyPath: " + constraintViolation.getPropertyPath() + ", Invalid value: "
						+ constraintViolation.getInvalidValue() + ", Message: " + constraintViolation.getMessage());
				// display the field which didnt pass the test
			}
			
			noViolations = false;
		} 
		
		
		if (!authorViolations.isEmpty()) {
			for (ConstraintViolation<Author> constraintViolation : authorViolations) {
				System.out.println("Author - PropertyPath: " + constraintViolation.getPropertyPath() + ", Invalid value: "
						+ constraintViolation.getInvalidValue() + ", Message: " + constraintViolation.getMessage());
			}
			
			noViolations = false;
		} 
		

		if (!publisherViolations.isEmpty()) {
			for (ConstraintViolation<Publisher> constraintViolation : publisherViolations) {
				System.out.println("Publisher - PropertyPath: " + constraintViolation.getPropertyPath() + ", Invalid value: "
						+ constraintViolation.getInvalidValue() + ", Message: " + constraintViolation.getMessage());
			}
			
			noViolations = false;
		} 
		
		if(noViolations == true)
			bookDao.createBook(book);
		
		
		model.addAttribute("bookViolations", bookViolations);
		model.addAttribute("authorViolations", authorViolations);
		model.addAttribute("publisherViolations", publisherViolations);

		return "validationErrors";
	}
	
	
	
	// day3  (wprowadzenie do walidacji)
	@RequestMapping("/validaterest")
	@ResponseBody
	public String validateBookRest() {
		
		Book book = new Book();
		book.setIsbn("20974001");
		book.setTitle("Zloto111111");
		// book.setRating(new BigDecimal(10)); valid
		book.setRating(new BigDecimal(200)); // not valid
		book.setDescription("jakis tam opis");
		// book.setPages(2); valid
		book.setPages(1); // not valid
		
		Publisher publisher = new Publisher(); // rzuca wyjątkiem jesli nie dam Book cascade Persist: Not-null property references a transient value - transient instance must be saved beforeQuery current operation :
		publisher.setName("Books Incorporated"); 
		book.setPublisher(publisher);
		
		Author author = new Author(); // rzuca wyjątkiem jesli nie dam Book cascade Persist: Not-null property references a transient value - transient instance must be saved beforeQuery current operation :
		author.setFirstName("Tom");
		author.setLastName("Clancy");
		book.addAuthor(author);
		
		Set<ConstraintViolation<Book>> violations = validator.validate(book); // the set will contain potential errors

		if (!violations.isEmpty()) {
			for (ConstraintViolation<Book> constraintViolation : violations) {
				System.out.println("PropertyPath: " + constraintViolation.getPropertyPath() + ", Invalid value: "
						+ constraintViolation.getInvalidValue() + ", Message: " + constraintViolation.getMessage());
				// display the field which didnt pass the test
			}
		} else {
			// do the intended action
			bookDao.createBook(book);

		}

		return "Book added successfully";
	}
	
	
	

}
