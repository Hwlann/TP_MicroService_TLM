package com.ynov.microservices.tlm.author;


import java.util.Collection;
import java.util.Optional;

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
	private AuthorRepository authors;
	
	/******************************************** CONTRUCTOR *******************************************/
	public AuthorController(AuthorRepository authors) {
		this.authors = authors;
		
		// Create anonymous Author
		Author author = new Author();
		author.setId(1);
		author.setPseudo("Anonymous");
		authors.save(author);
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
	
	@GetMapping("/authors/pseudo/{pseudo}")
	public Iterable<Author> findByPseudo(@PathVariable("pseudo") String pseudo) throws InterruptedException {
		return authors.findByPseudo(pseudo);
	}
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/authors/new")
	public Author addAuthor(@RequestParam("pseudo") String pseudo){
			Author author = new Author();
			Collection<Author> quoteList = (Collection<Author>) authors.findAll();
			if(quoteList.isEmpty()) {
				author.setId(1);
			}
			else {
				author.setId(quoteList.size()+1);
			}
			author.setPseudo(pseudo);
			return authors.save(author);
	}
	
	@PostMapping("/authors/")
	public Author save(@RequestBody Author author){
			return authors.save(author);
	}
	
	@PostMapping("/authors/{authorId}/add-quote")
	public Author addQuoteToAuthor(@PathVariable("authorId") Integer authorId, @RequestParam("quoteId") Integer quoteId) {
		Optional<Author> authorOpt = authors.findById(authorId);
		if (authorOpt.isPresent()) {
			Author author = authorOpt.get();
			author.addQuote(quoteId);
			authors.save(author);
			return author;
		}		
		return null;
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/
	@DeleteMapping("/authors/{id}")
	public void deleteAuthor(@PathVariable("id") Integer id){
			authors.deleteById(id);
	}
	
}
