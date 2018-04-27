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

import pl.coderslab.app.entity.Book;
import pl.coderslab.app.entity.Publisher;
import pl.coderslab.app.repository.PublisherRepository;

@Controller
@Transactional
public class PublisherWhRepoController {

	@Autowired
	Validator validator;

	@Autowired
	private PublisherRepository publisherRepo;

	////////////////////////////////// ADD TO MODEL BEFORE EACH METHOD
	////////////////////////////////// //////////////////////////

	@ModelAttribute("entityName")
	public String addEntityName() {
		return "Publisher";
	}

	//////////////////////////// SAVE ////////////////////////////

	@RequestMapping(value = "/savePublisher", method = RequestMethod.GET)
	public String setUpPublisherForm(@ModelAttribute Publisher publisher) {
		return "form/publisherForm";
	}

	@RequestMapping(value = "/savePublisher", method = RequestMethod.POST)
	public String savePublisher(@Valid Publisher publisher, BindingResult result) {

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error);
			}

			return "form/publisherForm"; // idzie do tej strony jsp

		} else {
			publisherRepo.saveAndFlush(publisher); //
			return "redirect:/listPublishers"; // uWAGA: redirect przekierowuje do action,a nie do view!!!
		}

	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/listPublishers")
	public String listPublishers(Model model) {
		model.addAttribute("publisherItems", (List<Publisher>) publisherRepo.findAll());
		return "publisherList";
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("viewPublisher/{id}")
	public String viewPublisher(@PathVariable long id, Model model) {

		List<Publisher> list = new ArrayList<>();
		list.add(publisherRepo.findOne(id));
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

		Publisher publisher = publisherRepo.findOne(id);
		for (Book book : publisher.getBooks()) {
			book.setPublisher(null);
		}

		publisherRepo.delete(id);
		return "redirect:/listPublishers";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@RequestMapping(value = "/updatePublisher/{id}", method = RequestMethod.GET)
	public String setUpPublisherUpdateForm(@PathVariable long id, Model model) {
		model.addAttribute(publisherRepo.findOne(id));
		return "form/publisherForm";
	}

}
