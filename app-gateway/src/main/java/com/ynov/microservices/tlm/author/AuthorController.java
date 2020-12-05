package com.ynov.microservices.tlm.author;

import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AuthorController {

	/******************************************** VARIABLES ********************************************/
	private final AuthorRepository authors;
	
	/******************************************** CONSTRUCTOR ******************************************/
	public AuthorController(AuthorRepository authors) {
		this.authors = authors;
	}
	
	/****************************************** BINDER **************************************************/
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/****************************************************************************************************/
	/******************************************** GET MAPPING *******************************************/
	/****************************************************************************************************/
	
	@GetMapping("/authors/find")
	public String initFind(Map<String, Object> model) throws InterruptedException {
		//Thread.sleep(1000); // Test Hystrix circuit breaker
		model.put("author", new Author());
		return "authors/findAuthor";
	}
	
	public String initFindFallback(Map<String, Object> model) {
		System.out.println("Fallback Activated");
		model.put("author", new Author());
		return "authors/findAuthor";
	}

	@GetMapping("/authors")
	public String processFind(Author author, BindingResult result, Map<String, Object> model) {

		// find authors by last name
		Collection<Author> results = this.authors.findByPseudo(author.getPseudo());
		if (results.isEmpty()) {
			// no authors found
			result.rejectValue("Pseudo", "notFound", "not found");
			return "authors/findAuthors";
		}
		else if (results.size() == 1) {
			// 1 author found
			author = results.iterator().next();
			return "redirect:/authors/" + author.getId();
		}
		else {
			// multiple authors found
			model.put("selections", results);
			return "authors/authorsList";
		}
	}
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/	
	@PostMapping("/authors/new")
	public String processCreation(@Valid Author author, BindingResult result) {
		if (result.hasErrors()) {
			return "500 : Error binding result"; // Error
		}
		else {
			this.authors.save(author);
			return "redirect:/authors/" + author.getId();
		}
	}
	
	@PostMapping("/authors/{authorId}/edit")
	public String processUpdateOwnerForm(@Valid Author author, BindingResult result,
			@PathVariable("authorId") int authorId) {
		if (result.hasErrors()) {
			return "500 : author not found"; // Error
		}
		else {
			author.setId(authorId);
			this.authors.save(author);
			return "redirect:/authors/{authorId}";
		}
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/	
}
