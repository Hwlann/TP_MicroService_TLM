package com.ynov.microservices.tlm.author;

import java.util.Collection;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ynov.microservices.tlm.quote.QuoteRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AuthorController {

	/******************************************** VARIABLES ********************************************/
	private final AuthorRepository authors;
	private final QuoteRepository quotes;
	
	/******************************************** CONSTRUCTOR ******************************************/
	public AuthorController(AuthorRepository authors, QuoteRepository quotes) {
		this.authors = authors;
		this.quotes = quotes;
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
	public Iterable<Author> findAuthors() {
		return authors.findAll();			
	}
	
	@GetMapping("/authors/{id}")
	public Author findAuthors(@PathVariable("id") Integer id) {
		return authors.findById(id);	
	}
	
	@GetMapping("/authors/pseudo/{pseudo}")
	public Collection<Author> findAuthorsByPseudo(@PathVariable("pseudo") String pseudo) {
		return authors.findByPseudo(pseudo);
	}
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/authors/new")
	public String addAuthor(@RequestParam("pseudo") String pseudo) {
		Collection<Author> authorList = (Collection<Author>) authors.findAll();
		
		Author author = new Author();
		author.setPseudo(pseudo);
		if(authorList.isEmpty()) {
			author.setId(1);
		}
		else {
			author.setId(authorList.size()+1);
		}
		this.authors.save(author);
	return "redirect:/authors";
	}
	
	@PostMapping("/authors/{authorId}/edit")
	public String updateAuthor(@PathVariable("authorId") Integer authorId, @RequestParam("pseudo") String pseudo) {
		Author author = authors.findById(authorId);
		author.setPseudo(pseudo);
		authors.save(author);
		return "redirect:/authors/" + authorId;			
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/
	@DeleteMapping("authors/{id}")
	public String deleteAuthor(@PathVariable("id") Integer id) {
		authors.deleteById(id);
		return null;		
	}
	
}
