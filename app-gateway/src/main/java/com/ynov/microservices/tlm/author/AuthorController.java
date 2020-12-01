package com.ynov.microservices.tlm.author;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/")
public class AuthorController {

	@Autowired
	private AuthorRepository authors;
	
	public AuthorController(AuthorRepository authors) {
		this.authors = authors;
	}
	
	/*
	 * GET MAPPING 
	 */
	@GetMapping("/authors/")
	public Iterable<Author> getAuthor(){
			return authors.findAll();
	}
	
	@GetMapping("/authors/{id}")
	public Optional<Author> getAuthor(@PathVariable("id") Integer id){
			return authors.findById(id);
	}
	/*
	 * POST MAPPING 
	 */
	@PostMapping("/authors/new")
	public void addAuthor(@RequestParam("pseudo") String pseudo){
			Author author = new Author();
			author.setPseudo(pseudo);
			authors.save(author);
	}
	
	@PostMapping("/authors/{authorId}/addQuote")
	public void addPetToOwner(@PathVariable("authorId") Integer authorId, @RequestParam("quoteId") Integer quoteId) {
		Optional<Author> authorOpt = authors.findById(authorId);
		if (authorOpt.isPresent()) {
			Author author = authorOpt.get();
			author.addQuote(quoteId);
			authors.save(author);
		}		
	}
	
	/*
	 * DELETE MAPPING 
	 */
	@DeleteMapping("/authors/{id}")
	public void deleteAuthor(@PathVariable("id") Integer id){
			authors.deleteById(id);
	}
	
}
