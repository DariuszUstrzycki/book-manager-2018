package unused.controllers.with.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.coderslab.app.dao.AuthorDao;
import pl.coderslab.app.entity.Author;
import pl.coderslab.app.entity.Book;

//@Controller
@Transactional
public class AuthorController {

	@Autowired
	private AuthorDao authorDao;

	////////////////////////////////// ADD TO MODEL BEFORE EACH METHOD
	/////////////////////////////////////////////////

	

	//////////////////////////// CREATE ////////////////////////////

	@RequestMapping(value = "/createAuthor", method = RequestMethod.GET)
	public String setUpAuthorForm(@ModelAttribute Author author) { // equivalent to model.addAttribute("author", new
																	// Author());
		return "form/authorForm";
	}

	@RequestMapping(value = "/createAuthor", method = RequestMethod.POST)
	public String createAuthor(@Valid Author author, BindingResult	result) {
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error : list) {
				System.out.println(error);
			}
			
			return	"form/authorForm"; // idzie do tej strony jsp
			
		} else {
			authorDao.createAuthor(author); 
			return "redirect:/listAuthors"; // uWAGA: redirect przekierowuje do action,a nie do view!!!
		}
		
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/listAuthors")
	public String listAuthors(Model model) {
		model.addAttribute("authorItems", (List<Author>) authorDao.findAll());
		return "authorList";
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("viewAuthor/{id}")
	public String viewAuthor(@PathVariable long id, Model model) {
		/*model.addAttribute("author", authorDao.findById(id));
		return "viewAuthor";*/
		
		List<Author> list = new ArrayList<>();
		list.add(authorDao.findById(id));
		model.addAttribute("authorItems", list);
		return "authorList";
	}

	//////////////////////////// DELETE ////////////////////////////

	@RequestMapping(value = "/deleteAuthor/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		model.addAttribute("entityName", "Author");
		return "form/confirmDelete";
	}
	
	@RequestMapping(value = "/deleteAuthor/{id}")
	public String deleteAuthor(@PathVariable long id) {
		
		Author author = authorDao.findById(id);
		for(Book book : author.getBooks()) {
			book.getAuthors().remove(author);   
		}
	
		authorDao.deleteById(id);
		return "redirect:/listAuthors";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@RequestMapping(value = "/updateAuthor/{id}", method = RequestMethod.GET)
	public String setUpAuthorUpdateForm(@PathVariable long id, Model model) {
		model.addAttribute(authorDao.findById(id)); // w JSP jest modelAttribute='author'
		return "form/authorForm";
	}

	@RequestMapping(value = "/updateAuthor/{id}", method = RequestMethod.POST)
	public String updateAuthor(@Valid Author author, BindingResult result) {
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error : list) {
				System.out.println(error);
			}
			
			return	"form/authorForm"; // idzie do tej strony jsp
			
		} else {
			authorDao.updateAuthor(author);
			return "redirect:/listAuthors"; // uWAGA: redirect przekierowuje do action,a nie do view!!!
		}
		
	}
	
}
