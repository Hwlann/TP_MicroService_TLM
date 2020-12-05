package com.ynov.microservices.tlm.author;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/")
public class AuthorController {

	/******************************************** VARIABLES ********************************************/
	@Autowired
	private AuthorRepository authors;
	
	/******************************************** CONTRUCTOR *******************************************/
	public AuthorController(AuthorRepository authors) {
		this.authors = authors;
	}
	
	/****************************************************************************************************/
	/******************************************** GET MAPPING *******************************************/
	/****************************************************************************************************/
	@GetMapping("/authors")
	public Iterable<Author> getAuthor(){
			return authors.findAll();
	}
	
	@GetMapping("/authors/{id}")
	public Optional<Author> getAuthorById(@PathVariable("id") Integer id){
			return authors.findById(id);
	}
	
	@GetMapping("/authors/findByPseudo/{pseudo}")
	public Iterable<Author> findByPseudo(@PathVariable("pseudo") String pseudo) throws InterruptedException {
		return authors.findByPseudo(pseudo);
	}
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/authors/new")
	public Author addAuthor(@RequestParam("pseudo") String pseudo){
			Author author = new Author();
			author.setPseudo(pseudo);
			return authors.save(author);
	}
	
	@PostMapping("/authors/")
	public Author save(@RequestBody Author author){
			return authors.save(author);
	}
	
	@PostMapping("/authors/{authorId}/addQuote")
	public void addQuoteToAuthor(@PathVariable("authorId") Integer authorId, @RequestParam("quoteId") Integer quoteId) {
		Optional<Author> authorOpt = authors.findById(authorId);
		if (authorOpt.isPresent()) {
			Author author = authorOpt.get();
			author.addQuote(quoteId);
			authors.save(author);
		}		
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/
	@DeleteMapping("/authors/remove")
	public void deleteAuthor(@RequestParam("id") Integer id){
			authors.deleteById(id);
	}
	
}
