package pl.coderslab.app.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;
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
import org.springframework.web.bind.annotation.ResponseBody;

import pl.coderslab.app.dao.AuthorDao;
import pl.coderslab.app.dao.PublisherDao;
import pl.coderslab.app.entity.Author;
import pl.coderslab.app.entity.Book;
import pl.coderslab.app.entity.Category;
import pl.coderslab.app.entity.Publisher;
import pl.coderslab.app.repository.AuthorRepository;
import pl.coderslab.app.repository.BookRepository;
import pl.coderslab.app.repository.CategoryRepository;
import pl.coderslab.app.validator.BookValidator;

@Controller
@Transactional //Spring will handle session management - bez tego failed to lazily initialize a collection of role: 
// ... could not initialize proxy - no Session.
public class BookWhRepoController {

	@Autowired
	Validator validator; //dont forget to create LocalValidatorFactoryBean
	
	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	AuthorRepository authorRepo;
	
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
	
	@ModelAttribute("categoryItems")
	public List<Category> populateCategoryItems(){
		return categoryRepo.findAll();
	}	
	
	@ModelAttribute("entityName")
	public String addEntityName() {
		return "Book";
	}
	
	//////////////////////////// SAVE ////////////////////////////
	
	@RequestMapping(value = "/saveBook", method = RequestMethod.GET)
	public String setUpBookForm(@ModelAttribute Book book, Model model) {  // equivalent to model.addAttribute("book", new Book());
		model.addAttribute("entityName", "Book");
		return "form/bookForm";
	}
	
	@RequestMapping(value = "/saveBook", method = RequestMethod.POST)
	public String saveBook( @Validated(BookValidator.class) Book book, BindingResult result, Model model) {
		
		model.addAttribute("entityName", "Book");
		
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error);
			}

			return "form/bookForm"; // idzie do tej strony jsp

		} else {

			bookRepo.saveAndFlush(book);
			return "redirect:/listBooks"; // uWAGA: redirect przekierowuje do action,a nie do view!!!
		}
	}
	
	//////////////////////////// LIST ////////////////////////////
	
	@RequestMapping("/listBooks")
	public String listBooks(@RequestParam(defaultValue = "0", required = false) int min, Model model) {
		model.addAttribute("bookItems", (List<Book>)bookRepo.findAll());
		model.addAttribute("entityName", "Book");
		return "bookList";
	}
	
	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("/viewBook/{id}")
	public String viewBook(@PathVariable long id, Model model) {
		
		model.addAttribute("entityName", "Book");
		
		List<Book> list = new ArrayList<>();
		list.add(bookRepo.findOne(id));
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
		bookRepo.delete(id);
		return "redirect:/listBooks";
	}
	
	//////////////////////////// UPDATE ////////////////////////////
	
	@RequestMapping(value = "/updateBook/{id}", method = RequestMethod.GET)
	public String setUpBookUpdateForm(@PathVariable long id, Model model) {
		model.addAttribute(bookRepo.findOne(id)); // w JSP jest modelAttribute='book'
		model.addAttribute("entityName", "Book");
		return "form/bookForm";
	}
		
	//////////////////////// SEARCH ////////////////////
	
	@RequestMapping(value = "listBooks/rating", method = RequestMethod.GET)
	public String filterBooksByRating(@RequestParam(defaultValue = "1", required = false) int min, Model model) {
		//model.addAttribute("bookItems", (List<Book>)bookDao.getRatingList(min));
		return "bookList";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam(required = false) String author, Model model) {
		//model.addAttribute("bookItems", (List<Book>)bookDao.getRatingList(min));
		return "bookList";
	}
	
	@RequestMapping(value = "/zad2", method = RequestMethod.GET)
	@ResponseBody
	public String queryBooks() {
		StringBuilder sb = new StringBuilder();
		
		Book book = bookRepo.findByTitleIgnoreCase("terrorists").get(0);
		sb.append("findByTitleIgnoreCase - id:" + book.getId() + " title: " + book.getTitle());
		
		
		book = bookRepo.findByPublisher(book.getPublisher()).get(0);
		sb.append("<br/>findByPublisher - id: " + book.getId() + " title: " + book.getTitle());
	
		book = bookRepo.findByPublisherId(book.getPublisher().getId()).get(0);
		sb.append("<br/>findByPublisherId - id: " + book.getId() + " title: " + book.getTitle());
	
		sb.append("<br/>SIZES:");
		sb.append("<br/> Po tytule znalazlem " + bookRepo.findByTitleIgnoreCase("terrorists").size());		
		sb.append("<br/> Po autorach znalazlem (Authors for a given book) " + bookRepo.findDistinctByAuthorsIn(new HashSet<>(book.getAuthors())).size());
		sb.append("<br/> Po pubsliher znalazlem " + bookRepo.findByPublisherId(book.getPublisher().getId()).size());
		
		book = bookRepo.findDistinctByAuthorsIn(new HashSet<>(book.getAuthors())).get(0);
		sb.append("<br/> findDistinctByAuthorsIn - id: " + book.getId() + " Title of te 1st " + book.getTitle());
		
		return sb.toString();
	}
	
	////////////////////// SEARCH /////////////////////////////
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	@ResponseBody
	public String queryBooks2() {
		Book book = bookRepo.findOne(2L);
		
		Author auhtor1 = authorRepo.findOne(3L);
		List<Author> authorList = new ArrayList<>();
		authorList.add(auhtor1);
		
		List <Book> books = bookRepo.findDistinctByAuthorsIn(authorList);
		return "Author is " + auhtor1 + "<br>His books are " + books;
	}
	
	@RequestMapping("/searchbooks/author")
	@ResponseBody
	public String findBooksByAuthorsLastName(@RequestParam String lastName) {
		
		List <Author> authors = authorRepo.findByLastNameIgnoreCase(lastName);
		List <Book> books = bookRepo.findByAuthor(authors.get(0));
		
		// List <Book> books = bookRepo.findByAuthors_LastNameIgnoreCase(lastName);
		return "Books by " + lastName + " are " + books;
	}
	
	@RequestMapping("/searchbooks/rating/{min}/{max}")
	@ResponseBody
	public String findBooksByRating(@PathVariable BigDecimal min, @PathVariable BigDecimal max) {
		System.out.println(min + "|||" +  max);
		//List <Book> books = bookRepo.findByRatingBetween(min, max);
		List <Book> books = bookRepo.findByRating2(min, max);
		
		return "Books with rating between "  + min + " and " + max + " are:<br>" + books + " Books size is " + books.size();
	}
	
	@RequestMapping("/searchbooks/category/{categoryId}")
	@ResponseBody
	public String findBooksByCategoryId(@PathVariable long categoryId) {
		List <Book> books = bookRepo.findByCategoryIdOrderByTitle(categoryId);
		return "Books with category "  + categoryId + books;
	}
	
	@RequestMapping("/setrating/{value}")
	@ResponseBody
	public String setRatingForAllBooks(@PathVariable int value) {
		bookRepo.resetRating(value);
		return "Books' rating reset to " + value;
	}
	
	@RequestMapping("/propositions")
	@ResponseBody
	public String findPropositions(@RequestParam boolean show) {		
		return bookRepo.findBooksByProposition(show).toString();
	}
	
	

}
