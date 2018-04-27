package unused.controllers.with.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.coderslab.app.dao.AuthorDao;
import pl.coderslab.app.dao.BookDao;
import pl.coderslab.app.dao.PublisherDao;
import pl.coderslab.app.entity.Author;
import pl.coderslab.app.entity.Book;
import pl.coderslab.app.entity.Publisher;
import pl.coderslab.app.validator.BookValidator;
import pl.coderslab.app.validator.PropositionValidator;

//@Controller

// UWAGA: COMMENTED !!!!!!!!!!!! >> using BookControllerRepo








@Transactional //Spring will handle session management - bez tego failed to lazily initialize a collection of role: 
// ... could not initialize proxy - no Session.
//@RequestMapping("/norepo")
@RequestMapping
public class BookController {
	
	@Autowired
	Validator validator; //dont forget to create LocalValidatorFactoryBean
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private PublisherDao publisherDao;
	
	@Autowired
	private AuthorDao authorDao;
	
	////////////////////////////////// ADD TO MODEL BEFORE EACH METHOD //////////////////////////
	
	@ModelAttribute("publisherItems")
	public List<Publisher> populatePublisherItems(){
		return (List<Publisher>) publisherDao.findAll();
	}
	
	@ModelAttribute("authorItems")
	public List<Author> populateAuthorItems(){
		return (List<Author>) authorDao.findAll();
	}	
	
	//////////////////////////// CREATE ////////////////////////////
	
	@RequestMapping(value = "/createBook", method = RequestMethod.GET)
	public String setUpBookForm(@ModelAttribute Book book) {  // equivalent to model.addAttribute("book", new Book());
		System.out.println("--------------------------Entered setUpBookForm()");
		return "form/bookForm";
	}
	
	@RequestMapping(value = "/createBook", method = RequestMethod.POST)
	public String createBook(@Validated(BookValidator.class) Book book, BindingResult result) {
		
		//System.out.println("Book in POST createBook: " + book);
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error : list) {
				System.out.println(error);
			}
			
			return	"form/bookForm"; // idzie do tej strony jsp
			
		} else {
			
			bookDao.createBook(book); 
			return "redirect:/listBooks"; // uWAGA: redirect przekierowuje do action,a nie do view!!! 
		}
		
	}
	
	//////////////////////////// LIST ////////////////////////////
	
	@RequestMapping("/listBooks")
	public String listBooks(@RequestParam(defaultValue = "0", required = false) int min,
			Model model) {
		
		if(min != 0) {
			model.addAttribute("bookItems", (List<Book>)bookDao.getRatingList(min));
			return "bookList";
		}
		
		model.addAttribute("bookItems", (List<Book>)bookDao.findAll());
		return "bookList";
	}
	
	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("/viewBook/{id}")
	public String viewBook(@PathVariable long id, Model model) {
		// tak było: model.addAttribute("book", bookDao.findById(id));
		//return "viewBook";
		
		List<Book> list = new ArrayList<>();
		list.add(bookDao.findById(id));
		model.addAttribute("bookItems", list);
		return "bookList";
	}
	
	////////////////////////////DELETE ////////////////////////////
	
	@RequestMapping(value = "/deleteBook/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		model.addAttribute("entityName", "Book");
		return "form/confirmDelete";
	}
	
	@RequestMapping(value = "/deleteBook/{id}")
	public String deleteBook(@PathVariable long id) {
		bookDao.deleteById(id); 
		return "redirect:/listBooks";
	}
	
	//////////////////////////// UPDATE ////////////////////////////
	
	@RequestMapping(value = "/updateBook/{id}", method = RequestMethod.GET)
	public String setUpBookUpdateForm(@PathVariable long id, Model model) {
		model.addAttribute(bookDao.findById(id)); // w JSP jest modelAttribute='book'
		return "form/bookForm";
	}
	
	@RequestMapping(value = "/updateBook/{id}", method = RequestMethod.POST)
	public String updateBook(@Valid @ModelAttribute Book book, BindingResult result) {
		
		System.out.println("-------------------------------BOOK UPDATE: Book: " + book);
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error : list) {
				System.out.println(error);
			}
			
			return	"form/bookForm"; // idzie do tej strony jsp
			
		} else {
			bookDao.updateBook(book); 
			return "redirect:/listBooks"; // uWAGA: redirect przekierowuje do action,a nie do view!!! 
		}		
		
	}
	
	/*
	//////////////////////// SEARCH ////////////////////
	
	@RequestMapping(value = "listBooks/rating", method = RequestMethod.GET)
	public String filterBooksByRating(@RequestParam(defaultValue = "1", required = false) int min, Model model) {
		model.addAttribute("bookItems", (List<Book>)bookDao.getRatingList(min));
		return "bookList";
	}
	*/

}
