package com.ynov.microservices.tlm.author;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@GetMapping("/authors")
	public String findAuthors(Map<String, Object> model ) {
		Collection<Author> results = this.authors.findAll();
		model.put("selections", results);
		return "redirect:/authors";
	}
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/	
	@PostMapping("/authors/new")
	public String addAuthor(@RequestParam("pseudo") String pseudo) {
		Collection<Author> authorList = authors.findAll();
		
		Author author = new Author();
		author.setPseudo(pseudo);
		if(authorList.isEmpty()) {
			author.setId(1);
		}
		else {
			author.setId(authorList.size()+1);
		}
		this.authors.save(author);
	return "redirect:/authors/" + author.getId();
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
