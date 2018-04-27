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

import pl.coderslab.app.entity.Category;
import pl.coderslab.app.repository.CategoryRepository;

@Controller
@Transactional
public class CategoryWhRepoController {

	@Autowired
	Validator validator;

	@Autowired
	CategoryRepository categoryRepo;

	////////////////////////////////// ADD TO MODEL BEFORE EACH METHOD 	/////////////////////////

	@ModelAttribute("categoryItems")
	public List<Category> populateCategoryItems() {
		return categoryRepo.findAll();
	}
	
	@ModelAttribute("entityName")
	public String addEntityName() {
		return "Category";
	}

	//////////////////////////// SAVE ////////////////////////////

	@RequestMapping(value = "/saveCategory", method = RequestMethod.GET)
	public String setUpCategoryForm(@ModelAttribute Category category) { // equivalent to model.addAttribute("book", new
		return "form/categoryForm";
	}

	@RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
	public String saveBook(@Valid Category category, BindingResult result) {
		
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Created category:" + category.toString());

		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			for (ObjectError error : list) {
				System.out.println(error);
			}

			return "form/categoryForm"; // idzie do tej strony jsp

		} else {

			categoryRepo.saveAndFlush(category);
			return "redirect:/listCategories"; // uWAGA: redirect przekierowuje do action,a nie do view!!!
		}
	}

	//////////////////////////// LIST ////////////////////////////

	@RequestMapping("/listCategories")
	public String listBooks(Model model) {
		model.addAttribute("categoryItems", categoryRepo.findAll());
		return "categoryList";
	}

	//////////////////////////// VIEW ONE ////////////////////////////

	@RequestMapping("/viewCategory/{id}")
	public String viewBook(@PathVariable long id, Model model) {
		List<Category> list = new ArrayList<>();
		list.add(categoryRepo.findOne(id));
		model.addAttribute("categoryItems", list);
		return "categoryList";
	}

	//////////////////////////// UPDATE ////////////////////////////

	@RequestMapping(value = "/updateCategory/{id}", method = RequestMethod.GET)
	public String setUpCategoryUpdateForm(@PathVariable long id, Model model) {
		model.addAttribute(categoryRepo.findOne(id));
		return "form/categoryForm";
	}

	//////////////////////////// DELETE ////////////////////////////

	@RequestMapping(value = "/deleteCategory/{id}/confirm")
	public String showConfirmationPage(@PathVariable long id, Model model) {
		model.addAttribute("entityName", "Category");
		return "form/confirmDelete";
	}

	@RequestMapping(value = "/deleteCategory/{id}")
	public String deleteBook(@PathVariable long id) {
		categoryRepo.delete(id);
		return "redirect:/listCategories";
	}

	

}
