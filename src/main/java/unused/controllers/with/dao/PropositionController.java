package unused.controllers.with.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
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
import pl.coderslab.app.validator.PropositionValidator;

//@Controller
@Transactional
public class PropositionController {
	
	@Autowired
	private Validator validator; //dont forget to create LocalValidatorFactoryBean
	
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
	
	@RequestMapping(value = "/createPropo", method = RequestMethod.GET)
	public String setUpBookForm(@ModelAttribute Book book, Model model) {  
		model.addAttribute("entityName", "Proposition");
		return "form/propoForm";
	}
	
	@RequestMapping(value = "/createPropo", method = RequestMethod.POST)
	public String createBook(@Validated(PropositionValidator.class) Book book, BindingResult result,Model model) {
		
		System.out.println("<<<<<<<<<<<<<<<Enter createProposition. Description: " + (book.getDescription().equals("")));
		Set<ConstraintViolation<Book>> violations = validator.validate(book, PropositionValidator.class);
		if (!violations.isEmpty()) {
			for (ConstraintViolation<Book> constraintViolation : violations) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + 
			constraintViolation.getPropertyPath() + "	" + constraintViolation.getMessage());
			}
		} else {

			// save object
		}
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error : list) {
				System.out.println(error);
			}
			
			model.addAttribute("entityName", "Proposition");
			return	"form/propoForm"; 
			
		} else {
			
			bookDao.createBook(book); 
			return "redirect:/listPropos"; // uWAGA: redirect przekierowuje do action,a nie do view!!! 
		}
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/listPropos")
	public String listBooks(Model model) {

		model.addAttribute("msg", "propositions");
		model.addAttribute("bookItems", (List<Book>) bookDao.getPropositions());
		return "bookList";
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("/viewPropo/{id}")
	public String viewBook(@PathVariable long id, Model model) {
		/*List<Book> list = new ArrayList<>();
		list.add(bookRepo.findOne(id));
		model.addAttribute("bookItems", list);*/
		return "bookList";
	}
	

	////////////////////////////DELETE ////////////////////////////
	
	@RequestMapping(value = "/deletePropo/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		model.addAttribute("entityName", "Book");
		return "form/confirmDelete";
	}
	
	@RequestMapping(value = "/deletePropo/{id}")
	public String deleteBook(@PathVariable long id) {
		bookDao.deleteById(id); 
		return "redirect:/listPropos";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@RequestMapping(value = "/updatePropo/{id}", method = RequestMethod.GET)
	public String setUpBookUpdateForm(@PathVariable long id, Model model) {
		model.addAttribute(bookDao.findById(id)); // w JSP jest modelAttribute='book'
		return "form/bookForm";
	}

	@RequestMapping(value = "/updatePropo/{id}", method = RequestMethod.POST)
	public String updateBook(@Valid @ModelAttribute Book book, BindingResult result) {

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error);
			}

			return "form/bookForm"; // idzie do tej strony jsp

		} else {
			bookDao.updateBook(book);
			return "redirect:/listPropos"; // uWAGA: redirect przekierowuje do action,a nie do view!!!
		}

}
	

}
