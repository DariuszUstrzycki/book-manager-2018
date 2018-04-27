package unrelated.to.bookmanager;
/*package pl.coderslab.app.controller;

import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.coderslab.app.dao.PersonDao;
import pl.coderslab.app.entity.Person;
import pl.coderslab.app.entity.PersonDetails;
import pl.coderslab.app.repository.PersonRepository;

@Controller
@Transactional // bez tego failed to lazily initialize a collection of role: ....
@RequestMapping("/")
public class PersonController {
	
	@Autowired
	Validator validator;
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private PersonRepository personRepo;
	
	
	//////////////////////////////// zadanie 1 day 2 ///////////////////////////////


	@RequestMapping(value = "/personFormMParam", method = RequestMethod.GET)
	public String showPersonForm(Model model) {
		model.addAttribute("person", new Person()); // pamiętac by dodać bean, by formularz mógl go użyć
		return "form/personForm";
	}
	
	@RequestMapping(value = "/personFormMParam", method = RequestMethod.POST)
	public String addPersonWithParams(@RequestParam String login, @RequestParam String password,
			@RequestParam String email) {
		System.out.println(">>>>>>>>>Adding Person to the database with @RequestParam");
		Person person = new Person();
		person.setLogin(login);
		person.setPassword(password);
		person.setEmail(email);
		personDao.createPerson(person);
		return "redirect:/success";
	}
	
	//////////////////////////////// zadanie 2 day 2 ///////////////////////////////
	
	@RequestMapping(value = "/personFormModelAtt", method = RequestMethod.GET)
	public String showPersonForm2(Model model) {
		model.addAttribute("person", new Person()); // pamiętac by dodać bean, by formularz mógl go użyć
		return "form/personForm";
	}
 
	@RequestMapping(value = "/personFormModelAtt", method = RequestMethod.POST) // KOLEJNOSC WAZNA !!!
	public String addPersonModelAttrBinding( @Valid Person person,	BindingResult	result) {
		System.out.println(">>>>>>>>>  Adding Person to the database with @ModelAttribute binding");
		
		Set<ConstraintViolation<Person>> violations = validator.validate(person); // the set will contain potential errors
		if (!violations.isEmpty()) {
			for (ConstraintViolation<Person> constraintViolation : violations) {
				System.out.println("Printing from  validator.validate. " + constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage());
			}
		} else {
			personDao.createPerson(person);
		}
		////////////////////////////////
		
		if(result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError error : list) {
				System.out.println(error);
			}
			
			return	"form/personForm"; // idzie do tej strony jsp
			
		} else {
			personDao.createPerson(person);
			return	"redirect:/success"; //redirect jest do akcji nie do view
		}
	
		// personDao.createPerson(person); dodanie tego w przypadku validation error rzuci javax.validation.ConstraintViolationException:
	}
	
	@RequestMapping("/success")
	public String success() {
		return "form/success";
	}
	
	
	////////////////////////////////zadanie 3 day 2 ///////////////////////////////
	
	@RequestMapping(value = "personDetails", method = RequestMethod.GET)
	public String showPersonDetailsForm(Model model) {
		model.addAttribute("personDetails", new PersonDetails());
		System.out.println(">>>>>>>>>Displaying details form");
		return "form/personDetailsForm";
	}
	
	@RequestMapping(value = "personDetails", method = RequestMethod.POST)
	public String addPersonDetails(@ModelAttribute PersonDetails personDetails) {
		System.out.println(">>>>>>>>>Adding PersonDetails to the database with @ModelAttribute binding");
		return "form/personDetailsForm";
	}
	
	@RequestMapping("/localetest")
	//@ResponseBody
	public String handleRequest(Locale locale) {
		return String.format("Request received. Locale: %s%n", locale);
	}
	
	// day3 - (wprowadzenie do walidacji)
	@RequestMapping("/validateperson")
	@ResponseBody
	public String validateTest1() {
		
		Person p2 = new Person();
		p2.setEmail("koza@wp.pl");
		p2.setLogin("darco");
		//p2.setPassword("123");
		
		PersonDetails details = new PersonDetails();
		details.setFirstName("Kazik2");
		details.setLastName("Kurak");
		p2.setPersonDetails(details);
		
		Set<ConstraintViolation<Person>> violations = validator.validate(p2); // the set will contain potential errors

		if (!violations.isEmpty()) {
			for (ConstraintViolation<Person> constraintViolation : violations) {
				System.out.println(constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage());
			    // display the field which didnt pass the test
			}
		} else {
			// save object
			personDao.createPerson(p2);
		}
		
		return "validateResult";

	}	
	
	// day4 - moje testowanie repo
		@RequestMapping("/repoperson")
		@ResponseBody
		public String repoTest() {
			
			Person p2 = new Person();
			p2.setEmail("koza@wp.pl");
			p2.setLogin("darco");
			p2.setPassword("123");
			
			PersonDetails details = new PersonDetails();
			details.setFirstName("Kazimir");
			details.setLastName("Zolza");
			p2.setPersonDetails(details);
			
			personRepo.save(p2);
			
			return "done"
;		}
	
}







*/