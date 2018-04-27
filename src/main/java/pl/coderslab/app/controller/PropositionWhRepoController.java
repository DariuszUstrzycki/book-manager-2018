package pl.coderslab.app.controller;

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

import pl.coderslab.app.entity.Author;
import pl.coderslab.app.entity.Book;
import pl.coderslab.app.entity.Category;
import pl.coderslab.app.entity.Publisher;
import pl.coderslab.app.repository.AuthorRepository;
import pl.coderslab.app.repository.BookRepository;
import pl.coderslab.app.repository.CategoryRepository;
import pl.coderslab.app.repository.PublisherRepository;
import pl.coderslab.app.validator.PropositionValidator;

@Controller
@Transactional
public class PropositionWhRepoController {

	@Autowired
	Validator validator; 

	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private PublisherRepository publisherRepo;
	
	@Autowired
	private AuthorRepository authorRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;

	////////////////////////////////// ADD TO MODEL BEFORE EACH METHOD  createPropo

	@ModelAttribute("publisherItems")
	public List<Publisher> populatePublisherItems() {
		return (List<Publisher>) publisherRepo.findAll();
	}

	@ModelAttribute("authorItems")
	public List<Author> populateAuthorItems() {
		return (List<Author>) authorRepo.findAll();
	}
	
	@ModelAttribute("authorItems")
	public List<Category> populateCategoryItems() {
		return (List<Category>) categoryRepo.findAll();
	}	
	
	
	@ModelAttribute("entityName")
	public String addEntityName() {
		return "Proposition";
	}

	//////////////////////////// SAVE ////////////////////////////

	@RequestMapping(value = "/saveProposition", method = RequestMethod.GET)
	public String setUpBookForm(@ModelAttribute Book book, Model model) {
		return "form/propoForm";
	}
	
	@RequestMapping(value = "/saveProposition", method = RequestMethod.POST)
	public String saveBook(@Validated(PropositionValidator.class) Book book, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error);
			}
			
			return "form/propoForm";

		} else {
			book.setProposition(true);
			if(book.getIsbn().equals("")) {
				book.setIsbn(null);
			}
				
			bookRepo.saveAndFlush(book);
			return "redirect:/listPropositions"; // uWAGA: redirect przekierowuje do action,a nie do view!!!
		}
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/listPropositions")
	public String listBooks(Model model) {

		model.addAttribute("bookItems", (List<Book>) bookRepo.findBooksByProposition(true));
		return "bookList";
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("/viewProposition/{id}")
	public String viewBook(@PathVariable long id, Model model) {

		List<Book> list = new ArrayList<>();
		list.add(bookRepo.findOne(id));
		model.addAttribute("bookItems", list);
		return "bookList";
	}

	//////////////////////////// DELETE ////////////////////////////

	@RequestMapping(value = "/deleteProposition/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		return "form/confirmDelete";
	}

	@RequestMapping(value = "/deleteProposition/{id}")
	public String deleteBook(@PathVariable long id) {
		bookRepo.delete(id);
		return "redirect:/listPropositions";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@RequestMapping(value = "/updateProposition/{id}", method = RequestMethod.GET)
	public String setUpBookUpdateForm(@PathVariable long id, Model model) {
		model.addAttribute(bookRepo.findOne(id)); // w JSP jest modelAttribute='book'
		return "form/bookForm";
	}

	@RequestMapping(value = "/updateProposition/{id}", method = RequestMethod.POST)
	public String updateBook(@Valid @ModelAttribute Book book, BindingResult result) {

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
					System.out.println(error);
			}

			return "form/bookForm"; // idzie do tej strony jsp

		} else {
			bookRepo.saveAndFlush(book);
			return "redirect:/listPropositions"; // uWAGA: redirect przekierowuje do action,a nie do view!!!
		}

		}
	
}