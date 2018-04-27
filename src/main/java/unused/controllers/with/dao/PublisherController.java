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

import pl.coderslab.app.dao.PublisherDao;
import pl.coderslab.app.entity.Book;
import pl.coderslab.app.entity.Publisher;

//@Controller
@Transactional
public class PublisherController {
	
	@Autowired
	private PublisherDao publisherDao;

	////////////////////////////////// ADD TO MODEL BEFORE EACH METHOD
	/////////////////////////////////////////////////

	

	//////////////////////////// CREATE ////////////////////////////

	@RequestMapping(value = "/createPublisher", method = RequestMethod.GET)
	public String setUpPublisherForm(@ModelAttribute Publisher publisher) { // equivalent to model.addAttribute("publisher", new
																	                                 // Publisher());
		return "form/publisherForm";
	}

	@RequestMapping(value = "/createPublisher", method = RequestMethod.POST)
	public String createPublisher(@Valid Publisher publisher, BindingResult result) {
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error : list) {
				System.out.println(error);
			}
			
			return "form/publisherForm"; // idzie do tej strony jsp
			
		} else {
			publisherDao.createPublisher(publisher); //
			return "redirect:/listPublishers"; // uWAGA: redirect przekierowuje do action,a nie do view!!!
		}
		
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/listPublishers")
	public String listPublishers(Model model) {
		model.addAttribute("publisherItems", (List<Publisher>) publisherDao.findAll());
		return "publisherList";
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("viewPublisher/{id}")
	public String viewPublisher(@PathVariable long id, Model model) {
		/*model.addAttribute("publisher", publisherDao.findById(id));
		return "viewPublisher";*/
		
		List<Publisher> list = new ArrayList<>();
		list.add(publisherDao.findById(id));
		model.addAttribute("publisherItems", list);
		return "publisherList";
	}

	//////////////////////////// DELETE ////////////////////////////

	@RequestMapping(value = "/deletePublisher/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		model.addAttribute("entityName", "Publisher");
		return "form/confirmDelete";
	}
	
	@RequestMapping(value = "/deletePublisher/{id}")
	public String deletePublisher(@PathVariable long id) {
		
		Publisher publisher = publisherDao.findById(id);
		for(Book book : publisher.getBooks()) {
			book.setPublisher(null);   
		}
	
		publisherDao.deleteById(id);
		return "redirect:/listPublishers";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@RequestMapping(value = "/updatePublisher/{id}", method = RequestMethod.GET)
	public String setUpPublisherUpdateForm(@PathVariable long id, Model model) {
		model.addAttribute(publisherDao.findById(id)); // w JSP jest modelAttribute='publisher'
		return "form/publisherForm";
	}

	@RequestMapping(value = "/updatePublisher/{id}", method = RequestMethod.POST)
	public String updatePublisher(@Valid Publisher publisher, BindingResult result) {
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error : list) {
				System.out.println(error);
			}
			
			return "form/publisherForm"; // idzie do tej strony jsp
			
		} else {
			publisherDao.updatePublisher(publisher); //
			return "redirect:/listPublishers"; // uWAGA: redirect przekierowuje do action,a nie do view!!!
		}
		
	}
	
}
