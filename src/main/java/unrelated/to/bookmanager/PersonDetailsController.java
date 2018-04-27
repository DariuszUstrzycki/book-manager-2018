package unrelated.to.bookmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Transactional // bez tego failed to lazily initialize a collection of role: ....
public class PersonDetailsController {
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private PersonDetailsDao personDetailsDao;
	
	//////////////////////////////// zadanie 3-4 day 2 ///////////////////////////////
	
	@RequestMapping(value = "personDetailsForm", method = RequestMethod.GET)
	public String showPersonDetailsForm(Model model) {
		System.out.println(">>>>Displayong personDetailsForm...");
		model.addAttribute("personDTO", new PersonDTO()); // pamiętac by dodać bean, by formularz mógl go użyć
		return "form/personDetailsForm";
	}

	@RequestMapping(value = "personDetailsForm", method = RequestMethod.POST)
	public String addPersonDetails(@ModelAttribute PersonDTO personDTO, Model model) {
		System.out.println(">>>>>>>>>Adding Person and PersonDetails to the database with @ModelAttribute binding");
		//personDao.createPerson(person);
		model.addAttribute("personInfo", personDTO.toString());
		
		return "form/success";
	}
		
	/////////////////////// metody, które przy pomocy adnotacji `@ModelAttribute` utworzą zestaw danych //////////////
	
	@ModelAttribute("progSkillsItems") // bez podania nazwy attrybutu nie chodzi, nawet jak nazwa zmiennej sie zgadza
	public List<String> populateProgSkillsItems() {
		List<String> programmingSkills = new ArrayList<>();
		programmingSkills.add("Java");
		programmingSkills.add("JavaScript");
		programmingSkills.add("SQL");
		programmingSkills.add("Hibernate");
		programmingSkills.add("Spring");
		return programmingSkills;
	}
	
	@ModelAttribute("countryItems")
	public List<String> populateCountryItems() {
		String	countries[]	=	new	String[]{"Poland",	"England",	"USA"};
		return Arrays.asList(countries);
	}
	
	@ModelAttribute("hobbyItems") 
	public List<Hobby> populateHobbyItems() {
		List<Hobby> hobbies = new ArrayList<>();
		hobbies.add( new Hobby(1,"sport"));
		hobbies.add(new Hobby(2,"reading"));
		hobbies.add(new Hobby(3,"swimming"));
		return hobbies;
	}
	
	@ModelAttribute("genderItems")
	public List<String> populateGenderItems(){
		return Arrays.asList("Male", "Female", "Other");
	}
	
}
