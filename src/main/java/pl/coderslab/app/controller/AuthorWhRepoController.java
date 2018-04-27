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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.coderslab.app.entity.Author;
import pl.coderslab.app.entity.Book;
import pl.coderslab.app.repository.AuthorRepository;

@Controller
@Transactional
public class AuthorWhRepoController {

	@Autowired
	Validator validator;

	@Autowired
	private AuthorRepository authorRepo;
	
	//////////////////////////////////ADD TO MODEL BEFORE EACH METHOD //////////////////////////
	
	@ModelAttribute("entityName")
	public String addEntityName() {
		return "Author";
	}

	//////////////////////////// SAVE ////////////////////////////

	@RequestMapping(value = "/saveAuthor", method = RequestMethod.GET)
	public String setUpAuthorForm(@ModelAttribute Author author) {
		System.out.println("2.----------------------------------setUpAuthorForm: " + author);
		return "form/authorForm";
	}

	@RequestMapping(value = "/saveAuthor", method = RequestMethod.POST)
	public String saveAuthor(@Valid Author author, BindingResult result) {

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error);
			}

			return "form/authorForm"; // idzie do tej strony jsp

		} else {
			System.out.println("3.---------------------------------just before save and flush: " + author);

			authorRepo.saveAndFlush(author); //
			return "redirect:/listAuthors"; // uWAGA: redirect przekierowuje do action,a nie do view!!!
		}

	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/listAuthors")
	public String listAuthors(Model model) {
		model.addAttribute("authorItems", (List<Author>) authorRepo.findAll());
		return "authorList";
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("viewAuthor/{id}")
	public String viewAuthor(@PathVariable long id, Model model) {

		List<Author> list = new ArrayList<>();
		list.add(authorRepo.findOne(id));
		model.addAttribute("authorItems", list);
		return "authorList";
	}
	
	//////////////////////////// DELETE ////////////////////////////

	@RequestMapping(value = "/deleteAuthor/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		return "form/confirmDelete";
	}

	@RequestMapping(value = "/deleteAuthor/{id}")
	public String deleteAuthor(@PathVariable long id) {

		Author author = authorRepo.findOne(id);
		for (Book book : author.getBooks()) {
			book.getAuthors().remove(author);
		}

		authorRepo.delete(id);
		return "redirect:/listAuthors";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@RequestMapping(value = "/updateAuthor/{id}", method = RequestMethod.GET)
	public String setUpAuthorUpdateForm(@PathVariable long id, Model model) {
		model.addAttribute(authorRepo.findOne(id));
		System.out.println("1.----------------------------------updateAuthor/{id}: " + authorRepo.findOne(id));
		
		return "form/authorForm";
	}
	
	//////////////////////////// SEARCH ////////////////////////////
	@RequestMapping(value="/search/author/{lastName}/{pesel}/{email}")
	@ResponseBody
	public String searchAuthor(@PathVariable String lastName, @PathVariable(required=false) String pesel, 
															@PathVariable(required=false) String email) {
		
		List<Author> author = authorRepo.findByLastNameIgnoreCase(lastName);
		return author.toString();
	}
	
	@RequestMapping(value="/search/author") // search/author?emailStart=mike
	@ResponseBody
	public String searchAuthorByEmailStart(@RequestParam String emailStart) {
		
		List<Author> author = authorRepo.findByEmailStartsWith(emailStart);
		return emailStart + ", " + author.toString();
	}
	
	
	

}
